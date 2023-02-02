package org.nbu.company.deliveries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.nbu.company.client.model.Client;
import org.nbu.company.employee.model.Employee;
import org.nbu.company.packages.model.Package;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private AddressType addressType;
    @Column
    private Double deliveryPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;
    @Column(name = "created_at")
    private Date createdAt = new Date();
}
