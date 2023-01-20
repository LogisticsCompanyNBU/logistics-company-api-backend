package org.nbu.company.employee.rest;

import java.text.MessageFormat;

import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.EntityDoesNotExistException;

public class BaseCompanyController {
    private CompanyRepository companyRepository;

    protected BaseCompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    protected Company getCompanyById(int companyId) {
        Company foundCompany = companyRepository.findById(companyId)
                                                .orElse(null);
        validateCompanyExists(companyId, foundCompany);
        return foundCompany;
    }

    private void validateCompanyExists(int companyId, Company company) {
        if (company == null) {
            throw new EntityDoesNotExistException(MessageFormat.format("Company with id \"{0}\" does not exist", companyId));
        }
    }

}
