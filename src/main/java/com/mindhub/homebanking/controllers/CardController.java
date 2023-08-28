package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired private CardRepository cardRepository;
    @Autowired private ClientRepository clientRepository;
    @GetMapping("/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }
    @GetMapping("cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCards(@RequestParam CardType cardType,
                                              @RequestParam CardColor cardColor,
                                              Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        //mejor si pregunto nomas que tipo de tarjeta y color tengo, sino existe puedo crearla.

    return new ResponseEntity<>(createCardNumber(), HttpStatus.OK);
    }

        public String createCardNumber(){
        String number;
        Random randomcito = new Random();
        do {
            number = String.valueOf(randomcito.nextInt(9999)) + "-"
            + String.valueOf(randomcito.nextInt(9999)) + "-"
            + String.valueOf(randomcito.nextInt(9999)) + "-"
            + String.valueOf(randomcito.nextInt(9999));
        }while (cardRepository.existsByNumber(number));
        return number;
    }

}
