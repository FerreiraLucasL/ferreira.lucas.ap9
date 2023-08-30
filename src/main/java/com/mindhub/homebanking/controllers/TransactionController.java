package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private AccountRepository accountRepository;
    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id){
        return new TransactionDTO(transactionRepository.getReferenceById(id));
    }
    //endpoint desde el front
    // /api/transactions${this.accountFromNumber}${this.accountToNumber}${this.amount}${this.description}`
    @Transactional
    @RequestMapping(path = "/transactions" , method = RequestMethod.POST)
    public ResponseEntity newTransaction(Authentication authentication,
                                         @RequestParam Double amount,
                                         @RequestParam String description,
                                         @RequestParam String fromAccountNumber,
                                         @RequestParam String toAccountNumber){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account accountTo = accountRepository.findByNumber(toAccountNumber);
        Account accountFrom = accountRepository.findByNumber(fromAccountNumber);
        if( ( amount == null ) || (description.isEmpty()) ){
            return new ResponseEntity("descripcion o monto vacíos D:", HttpStatus.FORBIDDEN);

        }else{
            if ( (accountTo == null || accountFrom == null) ){
                return new ResponseEntity("la cuenta no existe XC", HttpStatus.FORBIDDEN);
            }else {
                if ((accountFrom.getBalance() < amount)) {
                    return new ResponseEntity("fondos insuficientes :C ", HttpStatus.FORBIDDEN);
                }else{
                    if (toAccountNumber.equals(fromAccountNumber)) {
                        return new ResponseEntity("no puedes transferir a la misma cuenta (? ", HttpStatus.FORBIDDEN);
                    }else{
                        //transfiero los montos entre cuentas
                        accountFrom.setBalance(accountFrom.getBalance() - amount);
                        accountTo.setBalance(accountTo.getBalance() + amount);
                        //guardo las transacciones Transaction(TransactionType type, Double amount, String description, LocalDateTime date)
                        Transaction transactionFrom = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now());
                        transactionRepository.save(transactionFrom);
                        Transaction transactionTo = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now());
                        transactionRepository.save(transactionTo);
                        //persisto las cuentas con sus cambios
                        accountRepository.save(accountFrom);
                        accountRepository.save(accountTo);
                        return new ResponseEntity("transaccion completa :D", HttpStatus.CREATED);
                    }
                }
            }
        }

    }

}
