package org.nbu.company.persistence;

import org.nbu.company.model.CompanyAdministrator;
import org.nbu.shared.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAdministratorRepository extends UserRepository<CompanyAdministrator> {

}
