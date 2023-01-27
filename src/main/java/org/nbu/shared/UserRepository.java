package org.nbu.shared;

import java.util.List;

import org.nbu.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {
    T findByEmailAndCompany(String email, Company company);

    List<T> findAllByCompany(Company company);
}
