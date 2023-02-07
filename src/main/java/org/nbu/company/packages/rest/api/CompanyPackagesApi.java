package org.nbu.company.packages.rest.api;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.nbu.company.deliveries.model.DeliveryDto;
import org.nbu.company.packages.model.Package;
import org.nbu.company.packages.model.PackageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyPackagesApi {
    @PostMapping(value = "/api/companies/{companyId}/packages", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Package> createCompanyPackage(@PathVariable("companyId") int companyId, @Valid @RequestBody PackageDto packageDto);

    @DeleteMapping("/api/companies/{companyId}/packages/{packageId}")
    ResponseEntity<Void> deleteCompanyPackageById(@PathVariable("companyId") int companyId, @PathVariable("packageId") int packageId);

    @GetMapping(value = "/api/companies/{companyId}/packages/{packageId}", produces = { "application/json" })
    ResponseEntity<Package> getCompanyPackageById(@PathVariable("companyId") int companyId, @PathVariable("packageId") int packageId);

    @PostMapping(value = "/api/companies/{companyId}/packages/{packageId}/send", produces = { "application/json" })
    ResponseEntity<Package> sendCompanyPackageById(@PathVariable("companyId") int companyId, @PathVariable("packageId") int packageId,
                                                   @Valid @RequestBody DeliveryDto deliveryDto);

    @GetMapping(value = "/api/companies/{companyId}/packages", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllCompanyPackages(@PathVariable("companyId") int companyId, @RequestParam Optional<String> status,
                                                        @RequestParam Optional<Integer> sender, @RequestParam Optional<Integer> recipient,
                                                        @RequestParam Optional<Integer> employee);

    @GetMapping(value = "/api/companies/{companyId}/employees/{employeeId}/packages", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllCompanyPackagesByEmployeeId(@PathVariable("companyId") int companyId,
                                                                    @PathVariable("employeeId") int employeeId);
}
