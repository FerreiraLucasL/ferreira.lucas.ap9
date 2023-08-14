package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDTO> convertedList = transactions.stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
        return convertedList;
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id){
        TransactionDTO transactionDto = new TransactionDTO(transactionRepository.getReferenceById(id));
        return transactionDto;
    }
}
