package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

//constructores
    public Account() {}

    public Account(String number, Double balance, LocalDate date) {
        this.number = number;
        this.creationDate = date;
        this.balance = balance;
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Client getClient() {
        return client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    //setters

    public void setNumber(String number) {
        this.number = number;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //metodos
    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }


}
