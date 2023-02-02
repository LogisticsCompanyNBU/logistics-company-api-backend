package org.nbu.company.packages.rest;

import org.nbu.company.client.persistence.ClientRepository;
import org.nbu.company.employee.persistence.EmployeeRepository;
import org.nbu.company.packages.model.Package;
import org.nbu.company.packages.persistence.PackageRepository;
import org.nbu.company.packages.rest.api.CompanyPackagesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyPackagesApiController implements CompanyPackagesApi {

    private final PackageRepository packageRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyPackagesApiController(PackageRepository packageRepository,
                                        ClientRepository clientRepository,
                                        EmployeeRepository employeeRepository) {
        this.packageRepository = packageRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<Package> createCompanyPackage(int companyId, int clientSenderId,
                                                        int clientRecipientId, int employeeId, Package package1) {
        Package package2 = getPackageForPersisting(companyId, clientSenderId, clientRecipientId, employeeId, package1);
        packageRepository.save(package2);
        return ResponseEntity.ok(package2);
    }

    @Override
    public ResponseEntity<Void> deleteCompanyPackageById(int packageId) {
        packageRepository.deleteById(packageId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Package> getCompanyPackageById(int packageId) {
        return ResponseEntity.ok(packageRepository.findById(packageId).orElse(null));
    }

    @Override
    public ResponseEntity<List<Package>> getAllCompanyPackages(int companyId) {
        List<Package> result = new ArrayList<>();
        packageRepository.findAll().forEach(result::add);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Package>> getAllCompanyPackagesByEmployeeId(int employeeId) {
        List<Package> result = new ArrayList<>(packageRepository.findByEmployee_Id(employeeId));
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Package>> getAllSentCompanyPackages() {
        List<Package> result = new ArrayList<>(packageRepository.findByDeliveries_Status_Status("SENT"));
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Package>> getAllCompanyPackagesBySender(int senderId) {
        List<Package> result = new ArrayList<>(packageRepository.findByClientSender_Id(senderId));
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Package>> getAllCompanyPackagesByRecipient(int recipientId) {
        List<Package> result = new ArrayList<>(packageRepository.findByClientRecipient_Id(recipientId));
        return ResponseEntity.ok(result);
    }


    private Package getPackageForPersisting(int companyId, int clientSenderId, int clientRecipientId, int employeeId, Package package1) {
        return Package.builder()
                .clientSender(clientRepository.findById(clientSenderId).orElse(null))
                .clientRecipient(clientRepository.findById(clientRecipientId).orElse(null))
                .description(package1.getDescription())
                .employee(employeeRepository.findById(employeeId).orElse(null))
                .weight(package1.getWeight())
                .build();
    }
}
