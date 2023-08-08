package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
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
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> convertedList = accounts.stream().
                map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return convertedList;
    }

    @GetMapping("/account/{id}")
    public Optional<Account> getAccount(@PathVariable Long id){
        return accountRepository.findById(id);
    }

}
