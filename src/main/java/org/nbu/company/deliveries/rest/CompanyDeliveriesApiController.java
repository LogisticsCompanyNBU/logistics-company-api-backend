package org.nbu.company.deliveries.rest;

import static org.nbu.utils.AttributeMerger.mergeAttribute;

import java.text.MessageFormat;

import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.deliveries.model.DeliveryDto;
import org.nbu.company.deliveries.persistence.DeliveryRepository;
import org.nbu.company.deliveries.rest.api.CompanyDeliveriesApi;
import org.nbu.company.model.Company;
import org.nbu.company.packages.model.Package;
import org.nbu.company.packages.persistence.PackageRepository;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.EntityDoesNotExistException;
import org.nbu.shared.BaseCompanyController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyDeliveriesApiController extends BaseCompanyController implements CompanyDeliveriesApi {
    private final DeliveryRepository deliveryRepository;
    private final PackageRepository packageRepository;

    public CompanyDeliveriesApiController(DeliveryRepository deliveryRepository, PackageRepository packageRepository,
                                          CompanyRepository companyRepository) {
        super(companyRepository);
        this.deliveryRepository = deliveryRepository;
        this.packageRepository = packageRepository;
    }

    @Override
    public ResponseEntity<Delivery> deleteCompanyPackageDeliveryById(int companyId, int packageId, int deliveryId) {
        Delivery foundDelivery = findDelivery(companyId, packageId, deliveryId);
        Package foundPackage = findDeliveryPackage(companyId, packageId, deliveryId);
        deliveryRepository.delete(foundDelivery);
        packageRepository.save(foundPackage.toBuilder()
                                           .delivery(null)
                                           .build());
        return ResponseEntity.noContent()
                             .build();
    }

    @Override
    public ResponseEntity<Delivery> updateCompanyPackageDeliveryById(int companyId, int packageId, int deliveryId,
                                                                     DeliveryDto deliveryDto) {
        Delivery foundDelivery = findDelivery(companyId, packageId, deliveryId);
        return ResponseEntity.ok(deliveryRepository.save(updateDelivery(deliveryDto, foundDelivery)));
    }

    private Delivery updateDelivery(DeliveryDto delta, Delivery original) {
        return Delivery.builder()
                       .id(original.getId())
                       .status(mergeAttribute(original.getStatus(), delta.getStatus()))
                       .deliveryAddress(mergeAttribute(original.getDeliveryAddress(), delta.getDeliveryAddress()))
                       .addressType(mergeAttribute(original.getAddressType(), delta.getAddressType()))
                       .createdAt(original.getCreatedAt())
                       .deliveryPrice(original.getDeliveryPrice())
                       .build();
    }

    @Override
    public ResponseEntity<Delivery> getCompanyPackageDeliveryById(int companyId, int packageId, int deliveryId) {
        Delivery foundDelivery = findDelivery(companyId, packageId, deliveryId);
        return ResponseEntity.ok(foundDelivery);
    }

    private Delivery findDelivery(int companyId, int packageId, int deliveryId) {
        findDeliveryPackage(companyId, packageId, deliveryId);
        Delivery foundDelivery = deliveryRepository.findById(deliveryId)
                                                   .orElse(null);
        if (foundDelivery == null) {
            throw new EntityDoesNotExistException(MessageFormat.format("Delivery with id \"{0}\" does not exist", deliveryId));
        }
        return foundDelivery;
    }

    private Package findDeliveryPackage(int companyId, int packageId, int deliveryId) {
        Company company = getCompanyById(companyId);
        Package deliveryPackage = packageRepository.findByCompanyAndId(company, packageId);
        if (deliveryPackage == null) {
            throw new EntityDoesNotExistException(MessageFormat.format("Package with id \"{0}\" for delivery with id \"{1}\" and company \"{2}\" does not exist",
                                                                       packageId, deliveryId, companyId));
        }
        return deliveryPackage;
    }
}
