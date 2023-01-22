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
        Company company1 = companyRepository.saveAndFlush(company);
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
                .companyOffice(mergeAttribute(original.getCompanyOffice(), delta.getCompanyOffice()))
                .clientList(mergeAttribute(original.getClientList(), delta.getClientList()))
                .shipmentList(mergeAttribute(original.getShipmentList(), delta.getShipmentList()))
                .employees(mergeAttribute(original.getEmployees(), delta.getEmployees())).build();
    }
}
