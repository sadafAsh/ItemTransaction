package com.soj.item.transaction.model;

import javax.persistence.*;

@Entity
@Table(name = "Customer")
public class Customer {
    public static final String OBJECT_TYPE = "customer";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "CustomerName")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Bank_id")
    private Bank bank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
