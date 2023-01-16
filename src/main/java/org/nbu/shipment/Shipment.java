package org.nbu.shipment;

import javax.persistence.*;

@Entity
@Table(name = "shipment")
public class Shipment {
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
