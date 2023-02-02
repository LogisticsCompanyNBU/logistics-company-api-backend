package org.nbu.company.packages.rest.api;

import org.nbu.company.client.model.Client;
import org.nbu.company.packages.model.Package;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public interface CompanyPackagesApi {
    @PostMapping(value = "/api/companies/{companyId}/sender/{clientSenderId}/recipient/{clientRecipientId}/employee/{employeeId}/packages", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Package> createCompanyPackage(@PathVariable("companyId") int companyId, @PathVariable("clientSenderId") int clientSenderId,
                                                 @PathVariable("clientRecipientId") int clientRecipientId, @PathVariable("employeeId") int employeeId,
                                                 @Valid @RequestBody Package package1);

    @DeleteMapping("/api/package/{packageId}")
    ResponseEntity<Void> deleteCompanyPackageById(@PathVariable("packageId") int packageId);

    @GetMapping(value = "/api/packages/{packageId}", produces = { "application/json" })
    ResponseEntity<Package> getCompanyPackageById(@PathVariable("packageId") int packageId);

    @GetMapping(value = "/api/packages/companies/{companyId}", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllCompanyPackages(@PathVariable("companyId") int companyId);

    @GetMapping(value = "/api/employee/{employeeId}/packages", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllCompanyPackagesByEmployeeId(@PathVariable("employeeId") int employeeId);

    @GetMapping(value = "/api/sent/packages", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllSentCompanyPackages();

    @GetMapping(value = "/api/packages/sender/{senderId}", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllCompanyPackagesBySender(@PathVariable("senderId") int senderId);

    @GetMapping(value = "/api/packages/recipient/{recipientId}", produces = { "application/json" })
    ResponseEntity<List<Package>> getAllCompanyPackagesByRecipient(@PathVariable("recipientId") int recipientId);
}
