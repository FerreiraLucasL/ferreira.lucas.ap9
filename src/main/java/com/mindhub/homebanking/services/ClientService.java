package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientsDTO();
    ClientDTO getClientDTO(Long id);
    Client getClient(Long id);
    Client getCurrent(Authentication authentication);
    Client findByEmail(String email);
    void save(Client client);

}
