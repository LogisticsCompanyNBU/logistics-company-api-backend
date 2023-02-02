package org.nbu.company.packages.persistence;

import org.nbu.company.employee.model.Employee;
import org.nbu.company.packages.model.Package;
import org.nbu.shared.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PackageRepository extends CrudRepository<Package, Integer> {
    List<Package> findByEmployee_Id(int employeeId);

    List<Package> findByDeliveries_Status_Status(String status);

    List<Package> findByClientSender_Id(int clientSenderId);

    List<Package> findByClientRecipient_Id(int clientRecipientId);



}