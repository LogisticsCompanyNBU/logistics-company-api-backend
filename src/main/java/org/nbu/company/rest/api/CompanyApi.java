package org.nbu.company.rest.api;

import org.nbu.company.model.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public interface CompanyApi {
    @PostMapping(value = "/api/companies/create", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Company> createCompany(@Valid @RequestBody Company company);

    @DeleteMapping("/api/companies/delete/{companyId}")
    ResponseEntity<Void> deleteCompanyById(@PathVariable("companyId") int companyId);

    @GetMapping(value = "/api/companies/get/{companyId}", produces = { "application/json" })
    ResponseEntity<Company> getCompanyById(@PathVariable("companyId") int companyId);

    @PatchMapping(value = "/api/companies/edit/{companyId}", produces = { "application/json" }, consumes = {
            "application/json" })
    ResponseEntity<Company> updateCompanyById(@PathVariable("companyId") int companyId, @Valid @RequestBody Company company);
}
