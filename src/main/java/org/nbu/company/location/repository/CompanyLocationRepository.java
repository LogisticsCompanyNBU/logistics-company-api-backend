package org.nbu.company.location.repository;

import java.util.List;

import org.nbu.company.location.model.CompanyLocation;
import org.nbu.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Integer> {

    public CompanyLocation findByIdAndCompany(int companyLocationId, Company company);

    public List<CompanyLocation> findAllByCompany(Company company);
}
