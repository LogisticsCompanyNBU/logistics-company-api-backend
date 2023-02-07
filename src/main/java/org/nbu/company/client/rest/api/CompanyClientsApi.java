package org.nbu.company.client.rest.api;

import java.util.List;

import javax.validation.Valid;

import org.nbu.company.client.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyClientsApi {
    @PostMapping(value = "/api/companies/{companyId}/clients", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Client> createCompanyClient(@PathVariable("companyId") int companyId, @Valid @RequestBody Client client);

    @DeleteMapping("/api/companies/{companyId}/clients/{clientId}")
    ResponseEntity<Void> deleteCompanyClientById(@PathVariable("companyId") int companyId, @PathVariable("clientId") int clientId);

    @GetMapping(value = "/api/companies/{companyId}/clients/{clientId}", produces = { "application/json" })
    ResponseEntity<Client> getCompanyClientById(@PathVariable("companyId") int companyId, @PathVariable("clientId") int clientId);

    @PatchMapping(value = "/api/companies/{companyId}/clients/{clientId}", produces = { "application/json" }, consumes = {
        "application/json" })
    ResponseEntity<Client> updateCompanyClientById(@PathVariable("companyId") int companyId, @PathVariable("clientId") int clientId,
                                                   @RequestBody Client client);

    @GetMapping(value = "/api/companies/{companyId}/clients", produces = { "application/json" })
    ResponseEntity<List<Client>> getAllCompanyClients(@PathVariable("companyId") int companyId);
}
