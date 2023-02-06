package org.nbu.company.rest.api;

import javax.validation.Valid;

import org.nbu.company.model.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyApi {
    @PostMapping(value = "/api/companies", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Company> createCompany(@Valid @RequestBody Company company);

    @DeleteMapping("/api/companies/{companyId}")
    ResponseEntity<Void> deleteCompanyById(@PathVariable("companyId") int companyId);

    @GetMapping(value = "/api/companies/{companyId}", produces = { "application/json" })
    ResponseEntity<Company> getCompanyById(@PathVariable("companyId") int companyId);

    @PatchMapping(value = "/api/companies/{companyId}", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<Company> updateCompanyById(@PathVariable("companyId") int companyId, @Valid @RequestBody Company company);
}
