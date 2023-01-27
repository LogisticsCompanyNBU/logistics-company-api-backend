package org.nbu.company.rest;

import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.company.rest.api.CompanyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

import java.util.Date;

import static org.nbu.utils.AttributeMerger.mergeAttribute;

@RestController
public class CompanyServiceImpl implements CompanyApi {
    @Autowired
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    @Modifying
    @Override
    public ResponseEntity<Company> createCompany(Company company) {
        Company company1 = companyRepository.save(company);
        return ResponseEntity.ok(company1);
    }

    @Override
    public ResponseEntity<Void> deleteCompanyById(int companyId) {
        companyRepository.deleteById(companyId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Company> getCompanyById(int companyId) {
        return ResponseEntity.ok(companyRepository.findById(companyId).orElse(null));
    }

    @Override
    public ResponseEntity<Company> updateCompanyById(int companyId, Company company) {
        Company company1 = companyRepository.findById(companyId).orElse(null);
        return ResponseEntity.ok(companyRepository.save(merge(company1, company)));
    }

    private Company merge(Company original, Company delta) {
        return Company.builder()
                .id(original.getId())
                .centralAddress(mergeAttribute(original.getCentralAddress(), delta.getCentralAddress()))
                .clients(mergeAttribute(original.getClients(), delta.getClients()))
                .shipments(mergeAttribute(original.getShipments(), delta.getShipments()))
                .employees(mergeAttribute(original.getEmployees(), delta.getEmployees()))
                .updatedAt(new Date()).build();
    }
}
