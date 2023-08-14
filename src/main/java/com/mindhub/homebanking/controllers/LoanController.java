package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
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
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        List<Loan> loans = loanRepository.findAll();
        List<LoanDTO> convertedList = loans.stream()
                .map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
        return convertedList;
    }
    @GetMapping("/loans/{id}")
    public LoanDTO getLoan(@PathVariable Long id){
        LoanDTO loanDTO = new LoanDTO(loanRepository.getReferenceById(id));
        return loanDTO;
    }
}
