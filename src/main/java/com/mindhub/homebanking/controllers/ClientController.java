package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.controllers.AccountController;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ClientService clientService;
    @Autowired private AccountRepository accountRepository;


    //peticion HTTP(get) para devolver todos los clientesDTO * devolver soolo el cliente actual por cuestiones de seguridad :D
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClientsDTO();
    }
    //peticion HTTP(get) para devolver 1 clienteDTO, si el que solicita es el dueño de esa informacion
    @GetMapping("/clients/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id, Authentication authentication){
        if ( (clientService.getClient(id)!=null) )  {
            if (clientService.getClient(id).equals(clientService.getCurrent(authentication))) {
                return new ResponseEntity<>( clientService.getClientDTO(id), HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>("La informacion que intenta acceder está fuera de su alcance", HttpStatus.I_AM_A_TEAPOT);
            }
        }else{
            return new ResponseEntity<>("cliente no existe", HttpStatus.BAD_REQUEST);
        }
    }

    //get de cliente logueado actualmente
    @RequestMapping(path = "/clients/current", method = RequestMethod.GET)
    public ClientDTO getCurrent(Authentication authentication){
        return new ClientDTO(clientService.getCurrent(authentication));
    }

    //post de nuevo cliente
    @RequestMapping(path = "/clients/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        //nuevo cliente objeto y persistencia
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.save(client);
        //nueva cuenta asignada al nuevo cliente
        Account newAccount = new Account(client, createAccountNumber());
        accountRepository.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //me parece anti polimorfico copiar nomas el método que genera numero de tarjeta acá, pero no supe donde ponerlo para poder reutilizarlo,
    // ya que ocupa el repositorio de cuenta y no se donde iría :D
    public String createAccountNumber(){
        String number;
        Random randomcito = new Random();
        do {
            number = "VIN" + String.valueOf(randomcito.nextInt(99999999));
        }while (accountRepository.existsByNumber(number));
        return number;
    }
}
