package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    Card findById(Long id);
    void save(Card card);
    String createCardNumber();
}
