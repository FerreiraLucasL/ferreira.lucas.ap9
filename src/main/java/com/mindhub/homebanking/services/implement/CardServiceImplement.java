package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.CardUtils;
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
    //funcion para generar numero de tarjeta aleatorio que: sea tenga 16 numeros y no sea repetido
    @Override
    public String createCardNumber(){
        String number;
        {
            {
                number = CardUtils.createCardNumber();
                System.out.println(number.length());
            }while ( cardRepository.existsByNumber(number) );
        }while ( (number.length() != 19) && (number.length() != 0));
        return number;
    }

}

