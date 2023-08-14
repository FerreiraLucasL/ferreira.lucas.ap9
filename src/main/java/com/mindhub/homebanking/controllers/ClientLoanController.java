package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
        @Autowired
        private ClientLoanRepository clientLoanRepository;
        @GetMapping("/clientLoans")
        public List<ClientLoanDTO> getClientLoans(){
            List<ClientLoan> clientloans = clientLoanRepository.findAll();
            List<ClientLoanDTO> convertedList = clientloans.stream()
                    .map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
            return convertedList;
        }

        @GetMapping("/clientLoans/{id}")
        public ClientLoanDTO getClientLoans(@PathVariable Long id){
            ClientLoanDTO clientLoanDTO = new ClientLoanDTO(clientLoanRepository.getReferenceById(id));
            return clientLoanDTO;
        }
    }


