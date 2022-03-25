package com.soj.item.transaction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "bank")
public class Bank {
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public static final String OBJECT_TYPE = "bank";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "BankName")
    private String name;

    @Column(name = "BankAccNo")
    private long accNo;
    @Column(name = "BankAddress")
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccNo() {
        return accNo;
    }

    public void setAccNo(long accNo) {
        this.accNo = accNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
