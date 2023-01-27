package org.nbu.shared;

import static org.nbu.utils.AttributeMerger.mergeAttribute;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;

import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.EntityAlreadyExistsException;
import org.nbu.exception.EntityDoesNotExistException;

public abstract class AbstractCompanyUserApiController<T extends User, B extends User.UserBuilder<?, ?>> extends BaseCompanyController {

    protected UserRepository<T> userRepository;

    protected AbstractCompanyUserApiController(CompanyRepository companyRepository, UserRepository<T> userRepository) {
        super(companyRepository);
        this.userRepository = userRepository;
    }

    protected T createCompanyUser(int companyId, T user) {
        Company foundCompany = getCompanyById(companyId);
        validateEmployeeWithSameEmailDoesNotExistInCompany(foundCompany, user);
        T employeeForPersistence = fillCompanyDataOnCreate(fillDateFieldsIfMissing(user), foundCompany);
        return userRepository.save(employeeForPersistence);
    }

    private void validateEmployeeWithSameEmailDoesNotExistInCompany(Company company, T employee) {
        T foundUser = userRepository.findByEmailAndCompany(employee.getEmail(), company);
        if (foundUser != null) {
            throw new EntityAlreadyExistsException(MessageFormat.format("User with email \"{0}\" already exists in company with id \"{1}\"",
                                                                        employee.getEmail(), company.getId()));
        }
    }

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
