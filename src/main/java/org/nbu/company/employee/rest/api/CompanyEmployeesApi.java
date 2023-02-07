package org.nbu.company.employee.rest.api;

import java.util.List;

import javax.validation.Valid;

import org.nbu.company.employee.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CompanyEmployeesApi {
    @PostMapping(value = "/api/companies/{companyId}/employees", consumes = { "application/json" }, produces = { "application/json" })
    ResponseEntity<Employee> createCompanyEmployee(@PathVariable("companyId") int companyId, @Valid @RequestBody Employee employee);

    @DeleteMapping("/api/companies/{companyId}/employees/{employeeId}")
    ResponseEntity<Void> deleteCompanyEmployeeById(@PathVariable("companyId") int companyId, @PathVariable("employeeId") int employeeId);

    @GetMapping(value = "/api/companies/{companyId}/employees/{employeeId}", produces = { "application/json" })
    ResponseEntity<Employee> getCompanyEmployeeById(@PathVariable("companyId") int companyId, @PathVariable("employeeId") int employeeId);

    @PatchMapping(value = "/api/companies/{companyId}/employees/{employeeId}", produces = { "application/json" }, consumes = {
        "application/json" })
    ResponseEntity<Employee> updateCompanyEmployeeById(@PathVariable("companyId") int companyId, @PathVariable("employeeId") int employeeId,
                                                       @RequestBody Employee employee);

    @GetMapping(value = "/api/companies/{companyId}/employees", produces = { "application/json" })
    ResponseEntity<List<Employee>> getAllCompanyEmployees(@PathVariable("companyId") int companyId);

}
