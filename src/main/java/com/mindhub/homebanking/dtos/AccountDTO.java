package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private Client client;

    private Set<TransactionDTO> transactions;


    public AccountDTO(Account account) {
        id = account.getId();
        number = account.getNumber();
        creationDate = account.getCreationDate();
        balance = account.getBalance();
        client = account.getClient();
        this.transactions = account.getTransactions()
                .stream().map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toSet());
    }

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

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
