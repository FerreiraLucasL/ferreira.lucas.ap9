package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.TransactionService;
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
    @Autowired private TransactionService transactionService;
    @Autowired private AccountService accountService;
    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Object> getTransaction(@PathVariable Long id){
        Transaction transaction = transactionService.getTransaction(id);
        if (transaction != null) {
            return new ResponseEntity<>(new TransactionDTO(transaction) ,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("transaccion no existe", HttpStatus.FORBIDDEN);
        }
    }

    //transacciones cuenta a cuenta
    @Transactional
    @RequestMapping(path = "/transactions" , method = RequestMethod.POST)
    public ResponseEntity newTransaction(Authentication authentication,
                                         @RequestParam Double amount,
                                         @RequestParam String description,
                                         @RequestParam String fromAccountNumber,
                                         @RequestParam String toAccountNumber){
        Account accountTo = accountService.findByNumber(toAccountNumber);
        Account accountFrom = accountService.findByNumber(fromAccountNumber);

        if( ( amount == 0 ) || (description.isEmpty()) ){
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
                        //guardo las transacciones Transaction(TransactionType type, Double amount, Account account, String description)
                        transactionService.save(new Transaction(TransactionType.DEBIT, amount, accountFrom, description));//transaction to account
                        transactionService.save(new Transaction(TransactionType.CREDIT, amount, accountTo, description));//transaction from account
                        // persisto las cuentas con sus cambios
                        accountService.save(accountFrom);
                        accountService.save(accountTo);
                        return new ResponseEntity("transaccion completa :D", HttpStatus.CREATED);
                    }
                }
            }
        }
    }

}
