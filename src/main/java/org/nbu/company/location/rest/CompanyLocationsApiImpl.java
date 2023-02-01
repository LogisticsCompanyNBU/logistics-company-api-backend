package org.nbu.company.location.rest;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.nbu.company.location.model.CompanyLocation;
import org.nbu.company.location.repository.CompanyLocationRepository;
import org.nbu.company.location.rest.api.CompanyLocationsApi;
import org.nbu.company.model.Company;
import org.nbu.company.persistence.CompanyRepository;
import org.nbu.exception.EntityDoesNotExistException;
import org.nbu.shared.BaseCompanyController;
import org.nbu.utils.AttributeMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CompanyLocationsApiImpl extends BaseCompanyController implements CompanyLocationsApi {

    private CompanyLocationRepository companyLocationRepository;

    @Autowired
    public CompanyLocationsApiImpl(CompanyRepository companyRepository, CompanyLocationRepository companyLocationRepository) {
        super(companyRepository);
        this.companyLocationRepository = companyLocationRepository;
    }

    @Override
    public ResponseEntity<CompanyLocation> getCompanyLocationById(int companyId, int locationId) {
        Company company = getCompanyById(companyId);
        CompanyLocation foundCompanyLocation = findCompanyLocation(companyId, locationId, company);
        return ResponseEntity.ok(foundCompanyLocation);
    }

    @Override
    public ResponseEntity<CompanyLocation> updateCompanyLocationById(int companyId, int locationId, CompanyLocation companyLocationDelta) {
        Company company = getCompanyById(companyId);
        CompanyLocation existingCompanyLocation = findCompanyLocation(companyId, locationId, company);
        CompanyLocation updatedCompanyLocation = merge(existingCompanyLocation, companyLocationDelta);
        return ResponseEntity.ok(companyLocationRepository.save(updatedCompanyLocation));
    }

    private CompanyLocation merge(CompanyLocation existingCompanyLocation, CompanyLocation companyLocationDelta) {
        CompanyLocation.CompanyLocationBuilder resultBuilder = CompanyLocation.builder()
                                                                              .id(existingCompanyLocation.getId())
                                                                              .company(existingCompanyLocation.getCompany())
                                                                              .createdAt(existingCompanyLocation.getCreatedAt());
        resultBuilder.address(AttributeMerger.mergeAttribute(existingCompanyLocation.getAddress(), companyLocationDelta.getAddress()));
        resultBuilder.city(AttributeMerger.mergeAttribute(existingCompanyLocation.getCity(), companyLocationDelta.getCity()));
        resultBuilder.name(AttributeMerger.mergeAttribute(existingCompanyLocation.getName(), companyLocationDelta.getName()));
        resultBuilder.updatedAt(new Date());
        return resultBuilder.build();
    }

    @Override
    public ResponseEntity<List<CompanyLocation>> getAllCompanyLocations(int companyId) {
        Company company = getCompanyById(companyId);
        return ResponseEntity.ok(company.getCompanyLocations());
    }

    @Override
    public ResponseEntity<CompanyLocation> createCompanyLocation(int companyId, CompanyLocation companyLocation) {
        Company company = getCompanyById(companyId);
        CompanyLocation modifiedCompanyLocation = addDatesIfMissing(companyLocation);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(companyLocationRepository.save(updateCompanyLocationWithCompany(company, modifiedCompanyLocation)));
    }

    private CompanyLocation addDatesIfMissing(CompanyLocation companyLocation) {
        CompanyLocation.CompanyLocationBuilder companyLocationBuilder = companyLocation.toBuilder();
        if (companyLocation.getCreatedAt() == null) {
            companyLocationBuilder.createdAt(new Date());
        }
        if (companyLocation.getUpdatedAt() == null) {
            companyLocationBuilder.updatedAt(new Date());

        }
        return companyLocationBuilder.build();

    }

    private CompanyLocation updateCompanyLocationWithCompany(Company company, CompanyLocation modifiedCompanyLocation) {
        return modifiedCompanyLocation.toBuilder()
                                      .company(company)
                                      .build();
    }

    @Override
    public ResponseEntity<Void> deleteCompanyLocationById(int companyId, int locationId) {
        Company company = getCompanyById(companyId);
        CompanyLocation foundCompanyLocation = findCompanyLocation(companyId, locationId, company);
        companyLocationRepository.delete(foundCompanyLocation);
        return ResponseEntity.noContent()
                             .build();
    }

    private CompanyLocation findCompanyLocation(int companyId, int locationId, Company company) {
        CompanyLocation foundCompanyLocation = companyLocationRepository.findByIdAndCompany(locationId, company);
        if (foundCompanyLocation == null) {
            throw new EntityDoesNotExistException(MessageFormat.format("Company location with id \"{0}\" for company with id \"{1}\" does not exist",
                                                                       locationId, companyId));
        }
        return foundCompanyLocation;
    }

}
