package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardServiceImplement implements CardService {
    @Autowired private CardRepository cardRepository;

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    @Override
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

