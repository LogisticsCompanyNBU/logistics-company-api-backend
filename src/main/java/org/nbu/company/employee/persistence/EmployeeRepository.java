package org.nbu.company.employee.persistence;

import org.nbu.company.employee.model.Employee;
import org.nbu.shared.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends UserRepository<Employee> {

}
