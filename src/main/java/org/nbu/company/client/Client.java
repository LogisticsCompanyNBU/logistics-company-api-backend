package org.nbu.company.client;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {
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
