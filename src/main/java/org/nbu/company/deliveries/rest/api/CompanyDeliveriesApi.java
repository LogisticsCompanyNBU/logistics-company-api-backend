package org.nbu.company.deliveries.rest.api;

import org.nbu.company.deliveries.model.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public interface CompanyDeliveriesApi {
    @PostMapping(value = "/api/package/{packageId}/addresstype/{addresstypeId}/status/{statusId}", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Delivery> createCompanyDelivery(@PathVariable("addresstypeId") int addresstypeId,
                                                  @PathVariable("statusId") int statusId, @Valid @RequestBody Delivery delivery);

    @DeleteMapping("/api/deliveries/package/{packageId}")
    ResponseEntity<Void> deleteCompanyDeliveryById(@PathVariable("deliveryId") int deliveryId);

    @GetMapping(value = "/api/deliveries/packages/{packageId}", produces = { "application/json" })
    ResponseEntity<Delivery> getCompanyDeliveryById(@PathVariable("deliveryId") int deliveryId);

    @GetMapping(value = "/api/companies/{companyId}/packages", produces = { "application/json" })
    ResponseEntity<List<Delivery>> getAllCompanyDeliveries();
}
