package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    //servicio que busca en la BD
    private ClientRepository clientRepository;
    //devolver todos los clientes
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> convertedList = clients.stream().
                map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return convertedList;
    }
    //devolver cliente por id
    @GetMapping("/clients/{id}")
    public Optional<Client> getClient(@PathVariable Long id){
        return clientRepository.findById(id);
    }


}
