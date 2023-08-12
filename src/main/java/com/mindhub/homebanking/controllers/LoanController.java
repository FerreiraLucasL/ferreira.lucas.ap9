package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @GetMapping("/loans")
    public List<Loan> getLoans(){
        List<Loan> loans = loanRepository.findAll();
        return loans;
    }
    @GetMapping("/loans/{id}")
    public Optional<Loan> getLoan(@PathVariable Long id){
        return loanRepository.findById(id);
    }
}
