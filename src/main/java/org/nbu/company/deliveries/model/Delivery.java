package org.nbu.company.deliveries.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.nbu.company.packages.model.Package;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @Column
    private Double deliveryPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
    @JsonIgnore
    @OneToOne(mappedBy = "delivery")
    private Package deliveryPackage;
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Default
    @Column(name = "created_at")
    private Date createdAt = new Date();
}
