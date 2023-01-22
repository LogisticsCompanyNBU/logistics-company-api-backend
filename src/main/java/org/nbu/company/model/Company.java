package org.nbu.company.model;

import lombok.*;
import org.nbu.company.client.Client;
import org.nbu.company.employee.Employee;
import org.nbu.company.shipment.Shipment;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Employee> employees;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Client> clientList;

    @Column(nullable = false, unique = true)
    private String companyOffice;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shipment> shipmentList;
}
