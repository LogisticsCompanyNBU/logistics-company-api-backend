package org.nbu.company.deliveries.rest;

import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.deliveries.persistence.AddressTypeRepository;
import org.nbu.company.deliveries.persistence.DeliveryRepository;
import org.nbu.company.deliveries.persistence.StatusRepository;
import org.nbu.company.deliveries.rest.api.CompanyDeliveriesApi;
import org.nbu.company.packages.model.Package;
import org.nbu.company.packages.persistence.PackageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyDeliveriesApiController implements CompanyDeliveriesApi {
    private final DeliveryRepository deliveryRepository;
    private final StatusRepository statusRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final PackageRepository packageRepository;

    public CompanyDeliveriesApiController(DeliveryRepository deliveryRepository,
                                          StatusRepository statusRepository,
                                          AddressTypeRepository addressTypeRepository,
                                          PackageRepository packageRepository) {
        this.deliveryRepository = deliveryRepository;
        this.statusRepository = statusRepository;
        this.addressTypeRepository = addressTypeRepository;
        this.packageRepository = packageRepository;
    }


    @Override
    public ResponseEntity<Delivery> createCompanyDelivery(int addresstypeId, int statusId, Delivery delivery) {
        Delivery deliveryPers = deliveryRepository.save(getDeliveryForPersisting(addresstypeId, statusId, delivery));
        return ResponseEntity.ok(deliveryPers);
    }

    @Override
    public ResponseEntity<Void> deleteCompanyDeliveryById(int deliveryId) {
        deliveryRepository.deleteById(deliveryId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Delivery> getCompanyDeliveryById(int deliveryId) {
        return ResponseEntity.ok(deliveryRepository.findById(deliveryId).orElse(null));
    }

    @Override
    public ResponseEntity<List<Delivery>> getAllCompanyDeliveries() {
        List<Delivery> result = new ArrayList<>();
        deliveryRepository.findAll().forEach(result::add);
        return ResponseEntity.ok(result);
    }

    private Delivery getDeliveryForPersisting(int addresstypeId, int statusId, Delivery delivery) {
        return Delivery.builder()
                .status(statusRepository.findById(statusId).orElse(null))
                .deliveryPrice(delivery.getDeliveryPrice())
                .addressType(addressTypeRepository.findById(addresstypeId).orElse(null))
                .build();
    }
}
