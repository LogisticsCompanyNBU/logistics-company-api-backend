package org.nbu.company.rest.api;

import javax.validation.Valid;

import org.nbu.company.model.CompanyAdministrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyAdministratorsApi {

    @PostMapping(value = "/api/companies/{companyId}/administrators", produces = { "application/json" })
    ResponseEntity<CompanyAdministrator> registerCompanyAdministrator(@PathVariable("companyId") int companyId,
                                                                      @Valid @RequestBody CompanyAdministrator companyAdministrator);

}
