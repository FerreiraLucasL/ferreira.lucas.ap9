package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

@Entity
public class Client {
//atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "clientId", fetch = FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

    //relacion uno a muchos cliente CientePrestamos
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    Set<ClientLoan> loans = new HashSet<>();

//constructores
    public Client(){}

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Set<ClientLoan> getLoans() {
        return loans;
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//metodos
    //agregar cuenta
    public void addAccount(Account account){
        account.setClientId(this);
        accounts.add(account);
    }

    //agregar transaccion
    public void addTransaction(Transaction transaction, Account account){
        account.addTransaction(transaction);
    }
    public void ClientLoan(Client client, Loan loan, Integer payments, Long amount){
        ClientLoan clientLoan = new ClientLoan();
        clientLoan.setLoan(loan);
        clientLoan.setClient(client);
        clientLoan.setPayments(payments);
        clientLoan.setAmount(amount);

    }

}
