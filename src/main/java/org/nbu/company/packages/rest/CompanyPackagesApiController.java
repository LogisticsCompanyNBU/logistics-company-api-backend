package org.nbu.company.packages.rest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.nbu.company.client.model.Client;
import org.nbu.company.client.persistence.ClientRepository;
import org.nbu.company.deliveries.model.AddressType;
import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.deliveries.model.DeliveryDto;
import org.nbu.company.deliveries.model.Status;
import org.nbu.company.deliveries.persistence.DeliveryRepository;
import org.nbu.company.employee.model.Employee;
import org.nbu.company.employee.persistence.EmployeeRepository;
import org.nbu.company.model.Company;
import org.nbu.company.packages.model.Package;
import org.nbu.company.packages.model.PackageDto;
import org.nbu.company.packages.persistence.PackageRepository;
import org.nbu.company.packages.rest.api.CompanyPackagesApi;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.BadRequestException;
import org.nbu.exception.EntityDoesNotExistException;
import org.nbu.shared.BaseCompanyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyPackagesApiController extends BaseCompanyController implements CompanyPackagesApi {

    private static final int DEFAULT_PRICE_FOR_CLIENT_LOCATION_DELIVERY = 9;
    private static final int DEFAULT_PRICE_FOR_OFFICE_LOCATION_DELIVERY = 5;
    private final PackageRepository packageRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public CompanyPackagesApiController(PackageRepository packageRepository, ClientRepository clientRepository,
                                        EmployeeRepository employeeRepository, CompanyRepository companyRepository,
                                        DeliveryRepository deliveryRepository) {
        super(companyRepository);
        this.packageRepository = packageRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public ResponseEntity<Package> createCompanyPackage(int companyId, PackageDto packageDto) {
        Company company = getCompanyById(companyId);
        validatePackageRequest(packageDto);
        Package packageForPersistence = convertDtoToPackage(company, packageDto);
        return ResponseEntity.ok(packageRepository.save(packageForPersistence));
    }

    private void validatePackageRequest(PackageDto packageDto) {
        validateClientExists(packageDto.getClientSenderId(),
                             MessageFormat.format("Sender client with id \"{0}\" does not exist", packageDto.getClientSenderId()));
        validateClientExists(packageDto.getClientRecipientId(),
                             MessageFormat.format("Recipient client with id \"{0}\" does not exist", packageDto.getClientSenderId()));
        validateEmployeeExists(packageDto.getEmployeeId());
    }

    private void validateClientExists(int clientId, String errorMessage) {
        Client client = findClient(clientId);
        if (client == null) {
            throw new BadRequestException(errorMessage);
        }
    }

    private Client findClient(int clientId) {
        return clientRepository.findById(clientId)
                               .orElse(null);
    }

    @Override
    public ResponseEntity<Void> deleteCompanyPackageById(int companyId, int packageId) {
        packageRepository.deleteById(findCompanyPackage(companyId, packageId).getId());
        return ResponseEntity.noContent()
                             .build();
    }

    @Override
    public ResponseEntity<Package> getCompanyPackageById(int companyId, int packageId) {
        Package foundPackage = findCompanyPackage(companyId, packageId);
        return ResponseEntity.ok(foundPackage);
    }

    private Package findCompanyPackage(int companyId, int packageId) {
        Package foundPackage = packageRepository.findByCompanyAndId(getCompanyById(companyId), packageId);
        if (foundPackage == null) {
            throw new EntityDoesNotExistException(MessageFormat.format("Package with id \"{0}\" for company \"{1}\" does not exist",
                                                                       packageId, companyId));
        }
        return foundPackage;
    }

    @Override
    public ResponseEntity<List<Package>> getAllCompanyPackages(int companyId, Optional<String> status, Optional<Integer> sender,
                                                               Optional<Integer> recipient, Optional<Integer> employee) {
        Company foundCompany = getCompanyById(companyId);
        // List<Package> allCompanyPackages =
        // packageRepository.findAllPackagesByCompanyAndClientSender_IdOrClientRecipient_IdOrDelivery_Status(foundCompany,
        // sender.orElse(-1),
        // recipient.orElse(-1),
        // status.orElse(null));

        List<Package> allCompanyPackages = packageRepository.findAllPackagesByCompany(foundCompany);
        List<Package> filteredPackages = allCompanyPackages.stream()
                                                           .filter(filterByStatus(status))
                                                           .filter(filterBySender(sender))
                                                           .filter(filterByRecipient(recipient))
                                                           .filter(filterByEmployee(employee))
                                                           .collect(Collectors.toList());
        return ResponseEntity.ok(filteredPackages);
    }

    private Predicate<Package> filterByStatus(Optional<String> status) {
        if (status.isPresent()) {
            return p -> p.getDelivery() != null && p.getDelivery()
                                                    .getStatus() == Status.valueOf(status.get());
        }

        return p -> true;
    }

    private Predicate<Package> filterBySender(Optional<Integer> sender) {
        if (sender.isPresent()) {
            return p -> p.getClientSender()
                         .getId() == sender.get();
        }
        return p -> true;
    }

    private Predicate<Package> filterByRecipient(Optional<Integer> recipient) {
        if (recipient.isPresent()) {
            return p -> p.getClientRecipient()
                         .getId() == recipient.get();
        }
        return p -> true;
    }

    private Predicate<Package> filterByEmployee(Optional<Integer> employee) {
        if (employee.isPresent()) {
            return p -> p.getEmployee()
                         .getId() == employee.get();
        }
        return p -> true;
    }

    @Override
    public ResponseEntity<List<Package>> getAllCompanyPackagesByEmployeeId(int companyId, int employeeId) {
        Company company = getCompanyById(companyId);
        validateEmployeeExists(employeeId);
        List<Package> result = new ArrayList<>(packageRepository.findByCompanyAndEmployee_Id(company, employeeId));
        return ResponseEntity.ok(result);
    }

    private void validateEmployeeExists(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                                              .orElse(null);
        if (employee == null) {
            throw new BadRequestException(MessageFormat.format("Employee with id \"{0}\" not found", employeeId));
        }
    }

    private Package convertDtoToPackage(Company company, PackageDto packageDto) {
        return Package.builder()
                      .clientSender(findClient(packageDto.getClientSenderId()))
                      .clientRecipient(findClient(packageDto.getClientRecipientId()))
                      .description(packageDto.getDescription())
                      .employee(employeeRepository.findById(packageDto.getEmployeeId())
                                                  .orElse(null))
                      .weight(packageDto.getWeight())
                      .company(company)
                      .build();
    }

    @Override
    public ResponseEntity<Package> sendCompanyPackageById(int companyId, int packageId, DeliveryDto deliveryDto) {
        Package foundPackage = findCompanyPackage(companyId, packageId);
        Delivery persistedDelivery = deliveryRepository.save(createDelivery(deliveryDto, foundPackage));
        return ResponseEntity.ok(packageRepository.save(updatePackageWithDelivery(foundPackage, persistedDelivery)));
    }

    private Delivery createDelivery(DeliveryDto deliveryDto, Package foundPackage) {
        return Delivery.builder()
                       .deliveryAddress(deliveryDto.getDeliveryAddress())
                       .deliveryPrice(calculateDeliveryPrice(deliveryDto.getAddressType(), foundPackage.getWeight()))
                       .deliveryPackage(foundPackage)
                       .addressType(deliveryDto.getAddressType())
                       .status(Status.SENT)
                       .build();
    }

    private double calculateDeliveryPrice(AddressType addressType, double weight) {
        return addressType == AddressType.OFFICE_LOCATION ? DEFAULT_PRICE_FOR_OFFICE_LOCATION_DELIVERY * weight
            : DEFAULT_PRICE_FOR_CLIENT_LOCATION_DELIVERY * weight;
    }

    private Package updatePackageWithDelivery(Package foundPackage, Delivery persistedDelivery) {
        return foundPackage.toBuilder()
                           .delivery(persistedDelivery)
                           .build();
    }
}
