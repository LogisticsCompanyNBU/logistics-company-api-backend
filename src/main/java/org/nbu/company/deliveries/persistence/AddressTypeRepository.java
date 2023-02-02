package org.nbu.company.deliveries.persistence;

import org.nbu.company.deliveries.model.AddressType;
import org.springframework.data.repository.CrudRepository;

public interface AddressTypeRepository extends CrudRepository<AddressType, Integer> {
}
