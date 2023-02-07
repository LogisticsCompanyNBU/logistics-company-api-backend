package org.nbu.company.client.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.nbu.company.client.model.Client;
import org.nbu.company.client.model.Client.ClientBuilder;
import org.nbu.company.client.persistence.ClientRepository;
import org.nbu.company.client.rest.api.CompanyClientsApi;
import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.shared.AbstractCompanyUserApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyClientsApiController extends AbstractCompanyUserApiController<Client, ClientBuilder<?, ?>>
    implements CompanyClientsApi {

    @Autowired
    protected CompanyClientsApiController(CompanyRepository companyRepository, ClientRepository userRepository) {
        super(companyRepository, userRepository);
    }

    @Override
    public ResponseEntity<Client> createCompanyClient(int companyId, Client client) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createCompanyUser(companyId, client));
    }

    @Override
    public ResponseEntity<Void> deleteCompanyClientById(int companyId, int clientId) {
        deleteCompanyUser(companyId, clientId);
        return ResponseEntity.noContent()
                             .build();
    }

    @Override
    public ResponseEntity<Client> getCompanyClientById(int companyId, int clientId) {
        return ResponseEntity.ok(findUser(companyId, clientId));
    }

    @Override
    public ResponseEntity<Client> updateCompanyClientById(int companyId, int clientId, Client client) {
        Client updatedClient = updateUser(companyId, clientId, client);
        return ResponseEntity.ok(updatedClient);
    }

    @Override
    public ResponseEntity<List<Client>> getAllCompanyClients(int companyId) {
        Company foundCompany = getCompanyById(companyId);
        return ResponseEntity.ok(userRepository.findAllByCompany(foundCompany));
    }

    @Override
    protected Client fillCompanyDataOnCreate(Client client, Company company) {
        List<Company> clientCompanies = updateClientCompaniesWithNewCompany(client, company);
        return client.toBuilder()
                     .company(clientCompanies)
                     .build();
    }

    @Override
    protected Client mergeCompanyDataOnUpdate(Client client, ClientBuilder<?, ?> clientBuilder, Company userCompany) {
        List<Company> clientCompanies = updateClientCompaniesWithNewCompany(client, userCompany);
        return clientBuilder.company(clientCompanies)
                            .build();
    }

    private List<Company> updateClientCompaniesWithNewCompany(Client client, Company userCompany) {
        List<Company> clientCompanies = getClientCompanies(client);
        addCompanyIfDoesNotAlreadyExists(userCompany, clientCompanies);
        return clientCompanies;
    }

    private void addCompanyIfDoesNotAlreadyExists(Company userCompany, List<Company> clientCompanies) {
        Company existingClientCompany = clientCompanies.stream()
                                                       .filter(clientCompany -> clientCompany.getId() == userCompany.getId())
                                                       .findAny()
                                                       .orElse(null);
        if (existingClientCompany != null) {
            return;
        }
        clientCompanies.add(userCompany);
    }

    private List<Company> getClientCompanies(Client client) {
        return new ArrayList<>(client.getCompany());
    }

    @Override
    protected ClientBuilder<?, ?> getBuilder(Client client) {
        return client.toBuilder();
    }

}
