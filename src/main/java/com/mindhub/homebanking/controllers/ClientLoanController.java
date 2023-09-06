package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
        @Autowired private ClientLoanService clientLoanService;
        @Autowired private ClientService clientService;
        @Autowired private AccountService accountService;
        @Autowired private LoanService loanService;
        @Autowired private TransactionService transactionService;

    @GetMapping("/clientLoans")
        public ResponseEntity<Object>  getClientLoans(Authentication authentication){
            return new ResponseEntity(clientService.getCurrent(authentication).getLoans().stream().map
                    (clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList())
                    ,HttpStatus.FOUND);
        }

    @GetMapping("/clientLoans/{id}")
    public ResponseEntity<Object> getClientLoans(@PathVariable Long id, Authentication authentication){
        if((( clientService.getCurrent(authentication) != null) && ( clientLoanService.findById(id) != null )) &&
            ( clientLoanService.findById(id).getClient().equals(clientService.getCurrent(authentication)) )){
            return new ResponseEntity<>(new ClientLoanDTO(clientLoanService.findById(id)),HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("solicitud de prestamo no existe o no le pertenece",HttpStatus.NOT_FOUND);
        }
    }
    //POST para nuevo prestamo pre-aprobado :v
    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> newLoan(@RequestBody LoanAplicationDTO loanAplicationDTO, Authentication authentication ){
        if (loanService.findById(loanAplicationDTO.getLoanId())==null //comprobar parametros vacíos
                || loanAplicationDTO.getLoanId()==0 ||
                loanAplicationDTO.getAmount()==0 ||
                loanAplicationDTO.getPayments()==0
                || loanAplicationDTO.getAccountNumber().isEmpty()
        ) {
            return new ResponseEntity<>("préstamo no existe o datos inválidos", HttpStatus.FORBIDDEN);
        }else{
            if (loanAplicationDTO.getAmount() > loanService.findById(loanAplicationDTO.getLoanId()).getMaxAmount()) {//comprobar validez del monto
                return new ResponseEntity<>("el monto solicitado supera el maximo permitido", HttpStatus.FORBIDDEN);
            }else{
                if(!loanService.findById(loanAplicationDTO.getLoanId()).getPayments().contains(loanAplicationDTO.getPayments())) {//comprobar validez de las cuotas
                    return new ResponseEntity<>("la cantidad de cuotas solicitada no está disponible", HttpStatus.FORBIDDEN);
                }else {
                    if ((loanAplicationDTO.getAccountNumber() == null) || //comprobar validez de la cuenta
                            !(accountService.findByNumber(loanAplicationDTO.getAccountNumber()).getClient().equals(clientService.getCurrent(authentication)))) {
                        return new ResponseEntity<>("la cuenta destino no existe ó pertenece a otro cliente", HttpStatus.FORBIDDEN);
                    } else {
                        //se guarda la cuenta con el nuevo balance
                        accountService.findByNumber(loanAplicationDTO.getAccountNumber()).//set de cambios en el balance de la cuenta
                                setBalance(accountService.findByNumber(loanAplicationDTO.getAccountNumber()).getBalance()+loanAplicationDTO.getAmount());
                        accountService.save(accountService.findByNumber(loanAplicationDTO.getAccountNumber()));//persisto cambios
                        //se guarda la transaccion                public Transaction(TransactionType type, Double amount, Account account) {

                        Transaction transaction = new Transaction(TransactionType.CREDIT, loanAplicationDTO.getAmount(),
                                accountService.findByNumber(loanAplicationDTO.getAccountNumber()),
                                //description
                                clientService.getCurrent(authentication).getFirstName() + " " + clientService.getCurrent(authentication).getLastName()
                                + " " + loanService.findById(loanAplicationDTO.getLoanId()).getName() + " " + loanAplicationDTO.getPayments()
                        );
                        transactionService.save(transaction);
                        //se guarda una entidad con la relacion cliente, prestamo, pago y cant de pagos
                        ClientLoan clientLoan = new ClientLoan(clientService.getCurrent(authentication),
                                //se guarda el monto con los intereses
                                loanService.findById(loanAplicationDTO.getLoanId()), loanAplicationDTO.getPayments(),
                                (loanAplicationDTO.getAmount() * 1.2 ) );
                        clientLoanService.save(clientLoan);
                        return new ResponseEntity<>(HttpStatus.CREATED);
                    }
                }
            }
        }
    }

}


