package org.nbu.employee;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    public int getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
}
