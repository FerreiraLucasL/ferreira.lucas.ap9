package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    //relacion muchos a uno clientes
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    //relacion muchos a uno prestamos
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    private Integer payments;
    private Double amount;

    public ClientLoan() {}

    public ClientLoan(Integer payments, Double amount) {
        this.payments = payments;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Loan getLoan() { return loan;  }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void setPayments(Integer payment) {
        this.payments = payment;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }




}
