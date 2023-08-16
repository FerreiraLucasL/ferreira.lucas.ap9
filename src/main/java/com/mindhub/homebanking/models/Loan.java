package com.mindhub.homebanking.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount;

    @ElementCollection
    @Column(name="payments")
    private Set<Integer> payments = new HashSet<>();

    //relacion uno a muchos prestamo CientePrestamos
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<ClientLoan> clients = new ArrayList<>();

    public Loan() {}

    public Loan(String name, Double maxAmount, Set<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }

    public List<ClientLoan> getClients() {
        return clients;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clients.add(clientLoan);
    }

}
