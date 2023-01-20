package org.nbu.company.employee.rest;

import static org.nbu.utils.AttributeMerger.mergeAttribute;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.nbu.company.employee.model.Employee;
import org.nbu.company.employee.persistence.EmployeeRepository;
import org.nbu.company.employee.rest.api.CompanyEmployeesApi;
import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.EntityAlreadyExistsException;
import org.nbu.exception.EntityDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyEmployeesApiController extends BaseCompanyController implements CompanyEmployeesApi {

    private EmployeeRepository employeeRepository;

    @Autowired
    public CompanyEmployeesApiController(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        super(companyRepository);
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<Employee> createCompanyEmployee(int companyId, Employee employee) {
        Company foundCompany = getCompanyById(companyId);
        validateEmployeeWithSameEmailDoesNotExistInCompany(foundCompany, employee);
        Employee employeeForPersistence = fillCompanyData(fillDateFieldsIfMissing(employee), foundCompany);
        Employee savedEmployee = employeeRepository.save(employeeForPersistence);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedEmployee);
    }

    private void validateEmployeeWithSameEmailDoesNotExistInCompany(Company company, Employee employee) {
        Employee foundEmployee = employeeRepository.findByEmailAndCompany(employee.getEmail(), company);
        if (foundEmployee != null) {
            throw new EntityAlreadyExistsException(MessageFormat.format("Employee with email \"{0}\" already exists", employee.getEmail()));
        }
    }

    private Employee fillDateFieldsIfMissing(Employee employee) {
        Employee.EmployeeBuilder<?, ?> result = employee.toBuilder();
        if (employee.getCreatedAt() == null) {
            result.createdAt(new Date());
        }
        if (employee.getUpdatedAt() == null) {
            result.updatedAt(new Date());
        }
        return result.build();
    }

    private Employee fillCompanyData(Employee employee, Company company) {
        return employee.toBuilder()
                       .company(company)
                       .build();
    }

    @Override
    public ResponseEntity<Void> deleteCompanyEmployeeById(int companyId, int employeeId) {
        Employee foundEmployee = findEmployee(companyId, employeeId);
        employeeRepository.deleteById(foundEmployee.getId());
        return ResponseEntity.noContent()
                             .build();
    }

    @Override
    public ResponseEntity<Employee> getCompanyEmployeeById(int companyId, int employeeId) {
        return ResponseEntity.ok(findEmployee(companyId, employeeId));
    }

    @Override
    public ResponseEntity<Employee> updateCompanyEmployeeById(int companyId, int employeeId, Employee employee) {
        getCompanyById(companyId);
        Employee foundEmployee = findEmployee(companyId, employeeId);
        Employee employeeForUpdate = merge(foundEmployee, employee);
        return ResponseEntity.ok(employeeRepository.save(employeeForUpdate));
    }

    private Employee findEmployee(int companyId, int employeeId) {
        getCompanyById(companyId);
        Optional<Employee> foundEmployeeOptional = employeeRepository.findById(employeeId);
        if (!foundEmployeeOptional.isPresent()) {
            throw new EntityDoesNotExistException(MessageFormat.format("Employee with id \"{0}\" does not exist", employeeId));
        }
        return foundEmployeeOptional.get();
    }

    private Employee merge(Employee original, Employee delta) {
        Employee.EmployeeBuilder<?, ?> employeeBuilder = Employee.builder()
                                                                 .id(original.getId());
        employeeBuilder.fullName(mergeAttribute(original.getFullName(), delta.getFullName()));
        employeeBuilder.telephone(mergeAttribute(original.getTelephone(), delta.getTelephone()));
        employeeBuilder.email(mergeAttribute(original.getEmail(), delta.getEmail()));
        employeeBuilder.createdAt(mergeAttribute(original.getCreatedAt(), delta.getCreatedAt()));
        employeeBuilder.updatedAt(new Date());
        employeeBuilder.company(original.getCompany());
        return employeeBuilder.build();
    }

    @Override
    public ResponseEntity<List<Employee>> getAllCompanyEmployees(int companyId) {
        Company foundCompany = getCompanyById(companyId);
        return ResponseEntity.ok(employeeRepository.findAllByCompany(foundCompany));
    }

}
