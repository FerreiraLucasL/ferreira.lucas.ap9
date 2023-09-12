package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired private CardService cardService;
    @Autowired private ClientService clientService;
    @Autowired private AccountService accountService;
    @GetMapping("/cards")
    public List<CardDTO> getCards(Authentication authentication){
            return clientService.getCurrent(authentication).getCards().stream().map(card ->
                    new CardDTO(card)).collect(Collectors.toList());
    }
    @GetMapping("/cards/{id}")
    public ResponseEntity<Object> getCards(@PathVariable Long id, Authentication authentication) {
        Card card = cardService.findById(id);
        if ((clientService.getCurrent(authentication) != null) && (card != null) && (card.getClient().equals(clientService.getCurrent(authentication)))) {
            return new ResponseEntity<>(new CardDTO(card), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("tarjeta no existe o no le pertenece", HttpStatus.NOT_FOUND);
        }
    }

    public CardDTO getCard(@PathVariable Long id){
        return new CardDTO(cardService.findById(id));
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCards(@RequestParam CardType cardType,
                                              @RequestParam CardColor cardColor,
                                              Authentication authentication){
        //check si existe una tarjeta de ese color y tipo, sino la puede crear
        if( (clientService.getCurrent(authentication).getCards().stream().filter(card -> card.getType().equals(cardType)).filter(card -> card.getColor().equals(cardColor)).
                collect(Collectors.toSet()).isEmpty())) {
            cardService.save(new Card(cardType,cardColor, cardService.createCardNumber(), clientService.getCurrent(authentication)));
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ya existe una tarjeta de:" +cardType+ " de color" + cardColor, HttpStatus.FORBIDDEN);
        }
    }

}
