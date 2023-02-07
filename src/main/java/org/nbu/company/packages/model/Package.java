package org.nbu.company.packages.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.nbu.company.client.model.Client;
import org.nbu.company.deliveries.model.Delivery;
import org.nbu.company.employee.model.Employee;
import org.nbu.company.model.Company;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    private Delivery delivery;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client clientSender;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client clientRecipient;
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private String description;
    @Default
    @Column(name = "created_at")
    private Date createdAt = new Date();
}
