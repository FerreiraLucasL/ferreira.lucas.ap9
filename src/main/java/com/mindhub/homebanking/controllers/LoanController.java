package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
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
    @Autowired private LoanService loanService;
    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){ return loanService.getLoansDTO(); }
    @GetMapping("/loans/{id}")
    public LoanDTO getLoan(@PathVariable Long id){ return new LoanDTO(loanService.findById(id) );
    }
}
