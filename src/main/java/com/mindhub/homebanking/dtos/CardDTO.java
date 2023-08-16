package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private CardType type;
    private CardColor color;
    private String number;
    private int ccv;
    private String cardHolder;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO (Card card){
        id= card.getId();
        type=card.getType();
        color=card.getColor();
        number=card.getNumber();
        ccv= card.getCcv();
        cardHolder= card.getCardHolder();
        fromDate=card.getFromDate();
        thruDate=card.getThruDate();
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCcv() {
        return ccv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
}
