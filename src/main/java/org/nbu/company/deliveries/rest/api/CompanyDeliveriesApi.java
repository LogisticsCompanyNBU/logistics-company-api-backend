package org.nbu.company.deliveries.rest.api;

import javax.validation.Valid;

import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.deliveries.model.DeliveryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyDeliveriesApi {

    @DeleteMapping(value = "/api/companies/{companyId}/packages/{packageId}/deliveries/{deliveryId}")
    ResponseEntity<Delivery> deleteCompanyPackageDeliveryById(@PathVariable("companyId") int companyId,
                                                              @PathVariable("packageId") int packageId,
                                                              @PathVariable("deliveryId") int deliveryId);

    // update delivery by setting the status ONLY to DELIVERED
    @PatchMapping(value = "/api/companies/{companyId}/packages/{packageId}/deliveries/{deliveryId}", produces = { "application/json" })
    ResponseEntity<Delivery>
                  updateCompanyPackageDeliveryById(@PathVariable("companyId") int companyId, @PathVariable("packageId") int packageId,
                                                   @PathVariable("deliveryId") int deliveryId, @Valid @RequestBody DeliveryDto deliveryDto);

    // get a delivery by id
    @GetMapping(value = "/api/companies/{companyId}/packages/{packageId}/deliveries/{deliveryId}", produces = { "application/json" })
    ResponseEntity<Delivery> getCompanyPackageDeliveryById(@PathVariable("companyId") int companyId,
                                                           @PathVariable("packageId") int packageId,
                                                           @PathVariable("deliveryId") int deliveryId);
}
