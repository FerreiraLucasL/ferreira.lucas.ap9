package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);
    Account findById(Long id);
    List<AccountDTO> getAccountsDTO();
    Account findByNumber(String number);
    String createAccountNumber();
}
