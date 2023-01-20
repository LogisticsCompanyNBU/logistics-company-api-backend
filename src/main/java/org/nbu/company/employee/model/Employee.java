package org.nbu.company.employee.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.nbu.company.shared.User;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "employees")
public class Employee extends User {

}
