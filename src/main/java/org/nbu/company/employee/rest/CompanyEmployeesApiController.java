package org.nbu.company.employee.rest;

import java.util.List;

import org.nbu.company.employee.model.Employee;
import org.nbu.company.employee.model.Employee.EmployeeBuilder;
import org.nbu.company.employee.persistence.EmployeeRepository;
import org.nbu.company.employee.rest.api.CompanyEmployeesApi;
import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.shared.AbstractCompanyUserApiController;
import org.nbu.shared.ApplicationRole;
import org.nbu.shared.keycloak.repository.KeycloakRoleRepository;
import org.nbu.shared.keycloak.repository.KeycloakUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyEmployeesApiController extends AbstractCompanyUserApiController<Employee, Employee.EmployeeBuilder<?, ?>>
    implements CompanyEmployeesApi {

    @Autowired
    public CompanyEmployeesApiController(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                         KeycloakUserRepository keycloakUserRepository, KeycloakRoleRepository keycloakRoleRepository) {
        super(companyRepository, employeeRepository, keycloakUserRepository, keycloakRoleRepository);
    }

    @Override
    public ResponseEntity<Employee> registerCompanyEmployee(int companyId, Employee employee) {
        Employee createdEmployee = createCompanyUser(companyId, employee, ApplicationRole.COMPANY_EMPLOYEE);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdEmployee);
    }

    @Override
    public ResponseEntity<Void> deleteCompanyEmployeeById(int companyId, int employeeId) {
        deleteCompanyUser(companyId, employeeId);
        return ResponseEntity.noContent()
                             .build();
    }

    @Override
    public ResponseEntity<Employee> getCompanyEmployeeById(int companyId, int employeeId) {
        return ResponseEntity.ok(findUser(companyId, employeeId));
    }

    @Override
    public ResponseEntity<Employee> updateCompanyEmployeeById(int companyId, int employeeId, Employee employee) {
        Employee updatedEmployee = updateUser(companyId, employeeId, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Override
    public ResponseEntity<List<Employee>> getAllCompanyEmployees(int companyId) {
        Company foundCompany = getCompanyById(companyId);
        return ResponseEntity.ok(userRepository.findAllByCompany(foundCompany));
    }

    @Override
    protected EmployeeBuilder<?, ?> getBuilder(Employee employee) {
        return employee.toBuilder();
    }

    @Override
    protected Employee fillCompanyDataOnCreate(Employee employee, Company company) {
        return employee.toBuilder()
                       .company(company)
                       .build();
    }

    @Override
    protected Employee mergeCompanyDataOnUpdate(Employee employee, EmployeeBuilder<?, ?> employeeBuilder, Company userCompany) {
        return employeeBuilder.company(userCompany)
                              .build();
    }

}
