package org.nbu.company.employee.persistence;

import java.util.List;

import org.nbu.company.employee.model.Employee;
import org.nbu.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmailAndCompany(String email, Company company);

    List<Employee> findAllByCompany(Company company);
}
