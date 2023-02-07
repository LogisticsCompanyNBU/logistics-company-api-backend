package org.nbu.company.deliveries.persistence;

import org.nbu.company.deliveries.model.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Integer> {

}
