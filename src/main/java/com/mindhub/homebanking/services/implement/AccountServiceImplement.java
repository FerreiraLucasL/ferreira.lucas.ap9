package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired private AccountRepository accountRepository;
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
    @Override
    public Account findByNumber(String number) {
         if (accountRepository.existsByNumber(number)){
             return accountRepository.findByNumber(number);
         }else{
             return null;
         }
    }

    @Override
    public String createAccountNumber() {
        String number;
        {
            number = AccountUtils.createAccountNumber();
            System.out.println(number.length());
        }while(accountRepository.existsByNumber(number) && number.length() != 11);
        return number;
    }
}
