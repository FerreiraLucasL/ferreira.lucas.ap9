package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
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
        if((clientService.getCurrent(authentication)==null) &&(accountService.findById(id)!=null)
                && (accountService.findById(id).getClient().equals(clientService.getCurrent(authentication)))){
            return new ResponseEntity<>(new AccountDTO(accountService.findById(id)),HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("cuenta no existe o no le pertenece",HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(Authentication authentication){
        if ( (authentication!=null ) && (clientService.getCurrent(authentication)!=null)) {
            if(clientService.getCurrent(authentication).getAccounts().size()<3){
                accountService.save(new Account(clientService.getCurrent(authentication), accountService.createAccountNumber()));
                return new ResponseEntity<>("se ha creado la cuenta", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("el usuario ya tiene 3 cuentas ",HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>("no está logueado",HttpStatus.FORBIDDEN);
        }
    }
}
