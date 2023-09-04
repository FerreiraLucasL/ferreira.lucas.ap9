package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanAplicationDTO;
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
        public ResponseEntity<Object> newLoan(@RequestBody LoanAplicationDTO loanAplicationDTO, Authentication authentication ){
            Client current = clientRepository.findByEmail(authentication.getName());
            Account account = accountRepository.findByNumber(loanAplicationDTO.getAccountNumber());
            Loan loan = loanRepository.findById(loanAplicationDTO.getLoanId()).orElse(null);

            if (loan==null || loanAplicationDTO.getLoanId()==0 || loanAplicationDTO.getAmount()==0 || loanAplicationDTO.getPayments()==0
                    || loanAplicationDTO.getAccountNumber().isEmpty()
            ) {
                return new ResponseEntity<>("préstamo no existe o datos inválidos", HttpStatus.FORBIDDEN);
            }else{
                if (loanAplicationDTO.getAmount() > loan.getMaxAmount()) {
                    return new ResponseEntity<>("el monto solicitado supera el maximo permitido", HttpStatus.FORBIDDEN);
                }else{
                    if(!loan.getPayments().contains(loanAplicationDTO.getPayments())) {
                        return new ResponseEntity<>("la cantidad de cuotas solicitada no está disponible", HttpStatus.FORBIDDEN);
                    }else {
                        if ((loanAplicationDTO.getAccountNumber() == null) || !(account.getClient().equals(current))) {
                            return new ResponseEntity<>("la cuenta destino no existe ó pertenece a otro cliente", HttpStatus.FORBIDDEN);
                        } else {
                            //se guarda la cuenta con el nuevo balance
                            account.setBalance(account.getBalance()+loanAplicationDTO.getAmount());
                            accountRepository.save(account);
                            //se guarda la transaccion
                            Transaction transaction =
                            transactionRepository.save(new Transaction(
                                    TransactionType.DEBIT,loanAplicationDTO.getAmount(),account,
                                    /*descripcion*/(loan.getName()+ " " + loanAplicationDTO.getAmount() + " " + loanAplicationDTO.getPayments())));
                            //se guarda una entidad con la relacion cliente, prestamo, pago y cant de pagos
                            ClientLoan clientLoan = new ClientLoan(current, loan, loanAplicationDTO.getPayments(),
                                    //se guarda el monto con los intereses
                                    (loanAplicationDTO.getAmount() * 1.2 ) );
                            clientLoanRepository.save(clientLoan);
                            /*
                            //se actualizan los clientes y prestamos para que corresponda la info
                            current.addClientLoan(clientLoan);
                            clientRepository.save(current);
                            loan.addClientLoan(clientLoan);
                            loanRepository.save(loan);*/
                            return new ResponseEntity<>(HttpStatus.CREATED);
                        }
                    }
                }
            }
        }

}


