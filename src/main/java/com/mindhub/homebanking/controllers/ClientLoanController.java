package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
        @Autowired private ClientLoanRepository clientLoanRepository;
        @Autowired private ClientRepository clientRepository;
        @Autowired private AccountRepository accountRepository;
        @Autowired private LoanRepository loanRepository;
        @Autowired private TransactionRepository transactionRepository;

    @GetMapping("/clientLoans")
        public ResponseEntity<Object>  getClientLoans(Authentication authentication){
            Client current = clientRepository.findByEmail(authentication.getName());
            return new ResponseEntity(current.getLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList())
                    ,HttpStatus.FOUND);

        }

        @GetMapping("/clientLoans/{id}")
        public ResponseEntity<Object> getClientLoans(@PathVariable Long id, Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        ClientLoan clientLoan = clientLoanRepository.findById(id).orElse(null);
        if((( current != null) && ( clientLoan != null )) && ( clientLoan.getClient().equals(current) )){
            return new ResponseEntity<>(new ClientLoanDTO(clientLoan),HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("solicitud de prestamo no existe o no le pertenece",HttpStatus.NOT_FOUND);
        }
        }

        //post del font
        //axios.post("/api/loans", { loanId: this.loanTypeId, amount: this.amount, payments: this.payments, toAccountNumber: this.accountToNumber })
        @Transactional
        @RequestMapping(path = "/loans", method = RequestMethod.POST)
        public ResponseEntity<Object> newLoan(@RequestBody ClientLoan solicitedLoan, String accountNumber, Authentication authentication ){
            Client current = clientRepository.findByEmail(authentication.getName());
            Account account = accountRepository.findByNumber(accountNumber);
            Loan loan = loanRepository.findById(solicitedLoan.getId()).orElse(null);

            if (loan==null || solicitedLoan.getLoan().getName().isEmpty() || solicitedLoan.getLoan().getPayments().isEmpty() || solicitedLoan.getLoan().getMaxAmount()==null) {
                return new ResponseEntity<>("préstamo no existe o datos inválidos", HttpStatus.FORBIDDEN);
            }else{
                if (solicitedLoan.getLoan().getMaxAmount() > loan.getMaxAmount()) {
                    return new ResponseEntity<>("el monto solicitado supera el maximo permitido", HttpStatus.FORBIDDEN);
                }else{
                    if(!loan.getPayments().contains(solicitedLoan.getPayments())) {
                        return new ResponseEntity<>("la cantidad de cuotas solicitada no está disponible", HttpStatus.FORBIDDEN);
                    }else {
                        if ((accountNumber == null) || !(account.getClient().equals(current))) {
                            return new ResponseEntity<>("la cuenta destino no existe o pertenece a otro cliente", HttpStatus.FORBIDDEN);
                        } else {
                            account.setBalance(account.getBalance()+solicitedLoan.getAmount());
                            accountRepository.save(account);
                            //TransactionType type, Double amount, Account account, String description
                            Transaction transaction = new Transaction(TransactionType.DEBIT,solicitedLoan.getAmount(),account,
                                    /*descripcion*/(solicitedLoan.getLoan().getName()+ " " + solicitedLoan.getAmount() + " " + solicitedLoan.getPayments())  );
                            transactionRepository.save(transaction);
                            solicitedLoan.setAmount(solicitedLoan.getAmount() + solicitedLoan.getAmount()*.20);
                            solicitedLoan.setClient(current);
                            solicitedLoan.setLoan(loan);
                            clientLoanRepository.save(solicitedLoan);
                            return new ResponseEntity<>(HttpStatus.CREATED);
                        }
                    }
                }
            }
        }

}


