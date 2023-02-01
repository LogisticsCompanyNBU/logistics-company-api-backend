package org.nbu.company.location.repository;

import org.nbu.company.location.model.CompanyLocation;
import org.nbu.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Integer> {

    public CompanyLocation findByIdAndCompany(int companyLocationId, Company company);
}
