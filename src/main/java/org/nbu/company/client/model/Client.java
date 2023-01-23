package org.nbu.company.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.nbu.company.model.Company;
import org.nbu.shared.User;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "clients")
public class Client extends User {

    @Default
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Company> company = new ArrayList<>();
}
