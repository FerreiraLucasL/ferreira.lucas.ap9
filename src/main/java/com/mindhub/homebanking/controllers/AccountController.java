package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired private ClientService clientService;
    @Autowired private AccountService accountService;

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        return clientService.getCurrent(authentication).getAccounts().stream().map(
                                         account -> new AccountDTO(account)).collect(Collectors.toList());
    }
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication){
        Client client = clientService.getCurrent(authentication);
        Account account = accountService.findById(id);
        if((client != null) && (account!=null)
                && (account.getClient().equals(client))){
            return new ResponseEntity<>(new AccountDTO(accountService.findById(id)),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("cuenta no existe o no le pertenece",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication){
        Client client = clientService.getCurrent(authentication);
        if ( (authentication!=null ) && (client!=null)) {
            if(client.getAccounts().size()<3){
                accountService.save(new Account(client, accountService.createAccountNumber()));
                return new ResponseEntity<>("se ha creado la cuenta", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("el usuario ya tiene 3 cuentas ",HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>("no está logueado",HttpStatus.FORBIDDEN);
        }
    }
}
