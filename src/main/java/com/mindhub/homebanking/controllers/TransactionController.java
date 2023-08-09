package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(){
        List<Transaction> all = transactionRepository.findAll();
        return all;
    }

    @GetMapping("/transactions/{id}")
    public Optional<Transaction> getTransaction(@PathVariable Long id){
        Optional<Transaction> byId;
        byId = transactionRepository.findById(id); return byId;
    }



}
