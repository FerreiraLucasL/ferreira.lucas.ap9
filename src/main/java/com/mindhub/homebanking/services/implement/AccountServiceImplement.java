package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired private AccountRepository accountRepository;

    @Override
    public String createAccountNumber(){
        String number;
        Random randomcito = new Random();
        do {
            number = "VIN" + String.valueOf(randomcito.nextInt(99999999));
        }while (accountRepository.existsByNumber(number));
        return number;
    }

    @Override
    public void save(Account account) {accountRepository.save(account);}

    @Override
    public Account findById(Long id){
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountDTO> getAccountsDTO() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

}
