package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
public class CardUtils {
    @Autowired CardService cardService;
    public static String createCardNumber(){
        String number;
        int min = 999;
        int max = 9999;
            number = String.valueOf( (int)Math.floor(Math.random() * (max - min + 1) + min)) + "-"
            + String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min)) + "-"
            + String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min)) + "-"
            + String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min));
        return number;

    }
}
