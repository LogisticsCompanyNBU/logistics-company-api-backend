package org.nbu.company.location.rest.api;

import java.util.List;

import javax.validation.Valid;

import org.nbu.company.location.model.CompanyLocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyLocationsApi {
    @GetMapping(value = "/api/companies/{companyId}/locations/{locationId}", produces = { "application/json" })
    ResponseEntity<CompanyLocation> getCompanyLocationById(@PathVariable("companyId") int companyId,
                                                           @PathVariable("locationId") int locationId);

    @PatchMapping(value = "/api/companies/{companyId}/locations/{locationId}", produces = { "application/json" }, consumes = {
        "application/json" })
    ResponseEntity<CompanyLocation> updateCompanyLocationById(@PathVariable("companyId") int companyId,
                                                              @PathVariable("locationId") int locationId,
                                                              @Valid @RequestBody CompanyLocation companyLocation);

    @GetMapping(value = "/api/companies/{companyId}/locations", produces = { "application/json" })
    ResponseEntity<List<CompanyLocation>> getAllCompanyLocations(@PathVariable("companyId") int companyId);

    @PostMapping(value = "/api/companies/{companyId}/locations", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<CompanyLocation> createCompanyLocation(@PathVariable("companyId") int companyId,
                                                          @Valid @RequestBody CompanyLocation companyLocation);

    @DeleteMapping(value = "/api/companies/{companyId}/locations/{locationId}")
    ResponseEntity<Void> deleteCompanyLocationById(@PathVariable("companyId") int companyId, @PathVariable("locationId") int locationId);

}
