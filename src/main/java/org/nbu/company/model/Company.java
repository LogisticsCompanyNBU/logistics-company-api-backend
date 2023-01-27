package org.nbu.company.model;

<<<<<<< HEAD
import lombok.*;
import org.nbu.company.client.Client;
import org.nbu.company.employee.Employee;
import org.nbu.company.shipment.Shipment;

import javax.persistence.*;
import java.util.List;

=======
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.nbu.company.employee.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

>>>>>>> main
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
<<<<<<< HEAD
@Setter
=======
>>>>>>> main
@Entity
@Table(name = "companies")
public class Company {
    @Id
<<<<<<< HEAD
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Client> clientList;

    @Column(nullable = false, unique = true)
    private String companyOffice;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shipment> shipmentList;
=======
    @Column
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employees;
>>>>>>> main
}
