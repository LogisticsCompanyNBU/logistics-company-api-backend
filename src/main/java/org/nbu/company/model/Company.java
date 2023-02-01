package org.nbu.company.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.nbu.company.client.model.Client;
import org.nbu.company.employee.model.Employee;
import org.nbu.company.location.model.CompanyLocation;
import org.nbu.company.shipment.Shipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String centralAddress;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Client> clients;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Shipment> shipments;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CompanyLocation> companyLocations;

    @Default
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
