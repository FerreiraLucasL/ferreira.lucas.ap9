package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired private AccountRepository accountRepository;
    @Autowired private ClientRepository clientRepository;
    //endpoint cuentas, desde task 7 solo se muestran las cuentas del cliente logueado
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);
        if((client!=null)&&(account!=null)&&(account.getClient().equals(client))){
            return new ResponseEntity<>(new AccountDTO(account),HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("cuenta no existe o no le pertenece",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if ( (authentication!=null ) && (client!=null)) {
            if(client.getAccounts().size()<3){
                Account newAccount = new Account(client, createAccountNumber());
                accountRepository.save(newAccount);
                return new ResponseEntity<>("se ha creado la cuenta", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("el usuario ya tiene 3 cuentas ",HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>("no está logueado",HttpStatus.FORBIDDEN);
        }
    }

    //crear numero aleatorio de cuenta

    public String createAccountNumber(){
        String number;
        Random randomcito = new Random();
        do {
            number = "VIN" + String.valueOf(randomcito.nextInt(99999999));
        }while (accountRepository.existsByNumber(number));
        return number;
    }

}
