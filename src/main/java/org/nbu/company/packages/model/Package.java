package org.nbu.company.packages.model;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.nbu.company.client.model.Client;
import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.employee.model.Employee;
import org.nbu.company.model.Company;
import org.nbu.company.shipment.Shipment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Delivery> deliveries;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client clientSender;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client clientRecipient;
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private String description;
    @Column(name = "created_at")
    private Date createdAt = new Date();
}
