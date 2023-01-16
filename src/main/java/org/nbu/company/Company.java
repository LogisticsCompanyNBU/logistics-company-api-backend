package org.nbu.company;

import org.nbu.client.Client;
import org.nbu.employee.Employee;
import org.nbu.shipment.Shipment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @OneToMany
    private List<Employee> employeeList;
    @OneToMany
    private List<Client> clientList;

    private String companyOffice;
    @OneToMany
    private List<Shipment> shipmentList;

    public int getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public List<Employee> getsEmployeeList(){
        return employeeList;
    }
    public void setEmployeeList(List<Employee> employeeList){
        this.employeeList = employeeList;
    }

    public List<Client> getClientList(){
        return clientList;
    }
    public void setClientList(List<Client> clientList){
        this.clientList = clientList;
    }

    public String getCompanyOffice(){
        return companyOffice;
    }
    public void setCompanyOffice(String companyOffice){
        this.companyOffice = companyOffice;
    }

    public List<Shipment> getShipmentList(){
        return shipmentList;
    }
    public void setShipmentList(List<Shipment> shipmentList){
        this.shipmentList = shipmentList;
    }
}
