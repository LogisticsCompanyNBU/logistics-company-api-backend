package org.nbu.company.rest;

import javax.validation.Valid;

import org.nbu.company.model.Company;
import org.nbu.company.model.CompanyAdministrator;
import org.nbu.company.model.CompanyAdministrator.CompanyAdministratorBuilder;
import org.nbu.company.persistence.CompanyAdministratorRepository;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.company.rest.api.CompanyAdministratorsApi;
import org.nbu.shared.AbstractCompanyUserApiController;
import org.nbu.shared.ApplicationRole;
import org.nbu.shared.keycloak.repository.KeycloakRoleRepository;
import org.nbu.shared.keycloak.repository.KeycloakUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyAdministratorsApiImpl
    extends AbstractCompanyUserApiController<CompanyAdministrator, CompanyAdministrator.CompanyAdministratorBuilder<?, ?>>
    implements CompanyAdministratorsApi {

    @Autowired
    protected CompanyAdministratorsApiImpl(CompanyRepository companyRepository,
                                           CompanyAdministratorRepository companyAdministratorRepository,
                                           KeycloakUserRepository keycloakUserRepository, KeycloakRoleRepository keycloakRoleRepository) {
        super(companyRepository, companyAdministratorRepository, keycloakUserRepository, keycloakRoleRepository);
    }

    @Override
    public ResponseEntity<CompanyAdministrator> registerCompanyAdministrator(int companyId,
                                                                             @Valid CompanyAdministrator companyAdministrator) {
        CompanyAdministrator createdCompanyAdministrator = createCompanyUser(companyId, companyAdministrator,
                                                                             ApplicationRole.COMPANY_ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdCompanyAdministrator);
    }

    @Override
    protected CompanyAdministrator fillCompanyDataOnCreate(CompanyAdministrator user, Company company) {
        return user.toBuilder()
                   .company(company)
                   .build();
    }

    @Override
    protected CompanyAdministratorBuilder<?, ?> getBuilder(CompanyAdministrator user) {
        return user.toBuilder();
    }

    @Override
    protected CompanyAdministrator mergeCompanyDataOnUpdate(CompanyAdministrator user, CompanyAdministratorBuilder<?, ?> userBuilder,
                                                            Company userCompany) {
        return null;
    }

}
