package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired private ClientRepository clientRepository;

    //@Override
    // acá se pega lo que está en el controlador

}
