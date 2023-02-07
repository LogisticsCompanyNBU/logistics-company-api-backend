package org.nbu.company.packages.persistence;

import java.util.List;

import org.nbu.company.model.Company;
import org.nbu.company.packages.model.Package;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends CrudRepository<Package, Integer> {
    List<Package> findByCompanyAndEmployee_Id(Company company, int employeeId);

    Package findByCompanyAndId(Company company, int packageId);

    List<Package> findAllPackagesByCompany(Company company);

}