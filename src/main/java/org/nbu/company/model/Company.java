package org.nbu.company.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.nbu.company.client.model.Client;
import org.nbu.company.employee.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.nbu.company.shipment.Shipment;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
    @Column(name = "created_at")
    private Date createdAt = new Date();
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
