package org.nbu.company.deliveries.persistence;

import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.deliveries.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Integer> {
}
