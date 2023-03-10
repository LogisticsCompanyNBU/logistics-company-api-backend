package org.nbu.shared;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonInclude(value = Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @NotBlank(message = "username must be provided")
    @Column(name = "username")
    private String username;
    @NotBlank(message = "password must be provided")
    @Transient
    private String password;
    @NotBlank(message = "full_name must be provided")
    @Column(name = "full_name")
    private String fullName;
    @NotBlank(message = "telephone must be provided")
    @Column(name = "telepone")
    private String telephone;
    @NotBlank(message = "emain must be provided")
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
}
