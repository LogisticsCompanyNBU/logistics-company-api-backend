package org.nbu.company.deliveries.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryDto {
    private AddressType addressType;
    private String deliveryAddress;
    private Status status;
}
