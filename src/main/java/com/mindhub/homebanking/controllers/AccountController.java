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
    @Autowired private AccountRepository accountRepository;
    @Autowired private ClientRepository clientRepository;
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication){
        //termine comentando nomas porque me tiraba error al recuperar las transactions en el front
        //Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);
        if ( (account!= null)/* && (client != null)*/){
            /*
            if (account.getClient().equals(client)) {
                return new ResponseEntity<>( new AccountDTO(account), HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>("La informacion que intenta acceder está fuera de su alcance", HttpStatus.I_AM_A_TEAPOT);
            }
            */
            return new ResponseEntity<>(new AccountDTO(account),HttpStatus.FOUND);
        }else{
           return new ResponseEntity<>("esa cuenta no existe", HttpStatus.BAD_REQUEST);
        }
    }


}
