package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
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
    public List<CardDTO> getCards(Authentication authentication){
            Client client = clientRepository.findByEmail(authentication.getName());
            return client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Object> getCards(@PathVariable Long id, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Card card = cardRepository.findById(id).orElse(null);
        if ((client != null) && (card != null) && (card.getClient().equals(client))) {
            return new ResponseEntity<>(new CardDTO(card), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("tarjeta no existe o no le pertenece", HttpStatus.NOT_FOUND);
        }
    }

    public CardDTO getCard(@PathVariable Long id){
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCards(@RequestParam CardType cardType,
                                              @RequestParam CardColor cardColor,
                                              Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        //check si existe una tarjeta de ese color y tipo, sino la puede crear
        if( (client.getCards().stream().filter(card -> card.getType().equals(cardType)).filter(card -> card.getColor().equals(cardColor)).
                collect(Collectors.toSet()).isEmpty())) {
            cardRepository.save(new Card(cardType,cardColor,createCardNumber(),client));
            return new ResponseEntity<>(createCardNumber(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ya existe una tarjeta de:" +cardType+ " de color" + cardColor, HttpStatus.FORBIDDEN);
        }
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
