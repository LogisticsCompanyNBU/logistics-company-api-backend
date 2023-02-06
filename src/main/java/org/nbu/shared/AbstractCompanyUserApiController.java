package org.nbu.shared;

import static org.nbu.utils.AttributeMerger.mergeAttribute;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.RoleRepresentation;
import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.EntityAlreadyExistsException;
import org.nbu.exception.EntityDoesNotExistException;
import org.nbu.shared.keycloak.repository.KeycloakRoleRepository;
import org.nbu.shared.keycloak.repository.KeycloakUserRepository;
import org.nbu.shared.keycloak.repository.KeycloakUserRepository.UserRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCompanyUserApiController<T extends User, B extends User.UserBuilder<?, ?>> extends BaseCompanyController {

    protected final UserRepository<T> userRepository;
    private final KeycloakUserRepository keycloakUserRepository;
    private final KeycloakRoleRepository keycloakRoleRepository;

    protected AbstractCompanyUserApiController(CompanyRepository companyRepository, UserRepository<T> userRepository,
                                               KeycloakUserRepository keycloakUserRepository,
                                               KeycloakRoleRepository keycloakRoleRepository) {
        super(companyRepository);
        this.userRepository = userRepository;
        this.keycloakUserRepository = keycloakUserRepository;
        this.keycloakRoleRepository = keycloakRoleRepository;
    }

    protected T createCompanyUser(int companyId, T user, String role) {
        Company foundCompany = getCompanyById(companyId);
        validateEmployeeWithSameEmailDoesNotExistInCompany(foundCompany, user);
        createUserInKeycloak(user, role);
        T userForCreation = fillCompanyDataOnCreate(fillDateFieldsIfMissing(user), foundCompany);
        return storeUserInDatabase(userForCreation);
    }

    private void validateEmployeeWithSameEmailDoesNotExistInCompany(Company company, T employee) {
        T foundUser = userRepository.findByEmailAndCompany(employee.getEmail(), company);
        if (foundUser != null) {
            throw new EntityAlreadyExistsException(MessageFormat.format("User with email \"{0}\" already exists in company with id \"{1}\"",
                                                                        employee.getEmail(), company.getId()));
        }
    }

    private void createUserInKeycloak(T user, String role) {
        Response createUserResponse = keycloakUserRepository.createUser(UserRequest.builder()
                                                                                   .username(user.getUsername())
                                                                                   .password(user.getPassword())
                                                                                   .build());
        String userId = CreatedResponseUtil.getCreatedId(createUserResponse);
        RoleRepresentation foundRole = keycloakRoleRepository.findRoleByName(role);
        keycloakUserRepository.assignRole(userId, foundRole);
    }

    @SuppressWarnings("unchecked")
    private T fillDateFieldsIfMissing(T user) {
        B result = getBuilder(user);
        if (user.getCreatedAt() == null) {
            result.createdAt(new Date());
        }
        if (user.getUpdatedAt() == null) {
            result.updatedAt(new Date());
        }
        return (T) result.build();
    }

    private T storeUserInDatabase(T employeeForPersistence) {
        try {
            return userRepository.save(employeeForPersistence);
        } catch (Exception e) {
            log.error("Error during saving user in database - initiating rollback user creation");
            keycloakUserRepository.deleteByUsername(employeeForPersistence.getUsername());
            log.debug("User successfully rollbacked");
            throw e;
        }
    }

    protected abstract T fillCompanyDataOnCreate(T user, Company company);

    protected void deleteCompanyUser(int companyId, int userId) {
        T foundEmployee = findUser(companyId, userId);
        userRepository.deleteById(foundEmployee.getId());
    }

    protected T updateUser(int companyId, int userId, T user) {
        Company foundCompany = getCompanyById(companyId);
        T foundUser = findUser(companyId, userId);
        T userForUpdate = merge(foundUser, user, foundCompany);
        return userRepository.save(userForUpdate);
    }

    protected T findUser(int companyId, int employeeId) {
        getCompanyById(companyId);
        Optional<T> foundEmployeeOptional = userRepository.findById(employeeId);
        if (!foundEmployeeOptional.isPresent()) {
            throw new EntityDoesNotExistException(MessageFormat.format("User with id \"{0}\" does not exist", employeeId));
        }
        return foundEmployeeOptional.get();
    }

    @SuppressWarnings("unchecked")
    private T merge(T original, T delta, Company userCompany) {
        B userBuilder = (B) getBuilder(original).id(original.getId());
        userBuilder.fullName(mergeAttribute(original.getFullName(), delta.getFullName()));
        userBuilder.telephone(mergeAttribute(original.getTelephone(), delta.getTelephone()));
        userBuilder.email(mergeAttribute(original.getEmail(), delta.getEmail()));
        userBuilder.createdAt(mergeAttribute(original.getCreatedAt(), delta.getCreatedAt()));
        userBuilder.updatedAt(new Date());
        return mergeCompanyDataOnUpdate(original, userBuilder, userCompany);
    }

    protected abstract B getBuilder(T user);

    protected abstract T mergeCompanyDataOnUpdate(T user, B userBuilder, Company userCompany);
}
