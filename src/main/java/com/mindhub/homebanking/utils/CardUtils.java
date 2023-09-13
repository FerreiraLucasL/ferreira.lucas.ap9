package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class CardUtils {
    @Autowired CardService cardService;
    public static String createCardNumber(){
        String number;
        Random randomcito = new Random();
            number = String.valueOf(randomcito.nextInt(999,9999)) + "-"
            + String.valueOf(randomcito.nextInt(999,9999)) + "-"
            + String.valueOf(randomcito.nextInt(999,9999)) + "-"
            + String.valueOf(randomcito.nextInt(999,9999));
        return number;

    }
}
