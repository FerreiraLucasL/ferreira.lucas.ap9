package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Random;
public class AccountUtils {
    @Autowired static AccountRepository accountRepository;
    public static String createAccountNumber(){
        String number;
        Random randomcito = new Random();
            number = "VIN" + String.valueOf(randomcito.nextInt(99999999));
        return number;
    }
}
