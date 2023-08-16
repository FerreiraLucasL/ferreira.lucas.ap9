package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private CardType type;
    private CardColor color;
    private String number;
    private int ccv;
    private String cardHolder;
    private LocalDate fromDate;
    private LocalDate thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {}

    public Card(CardType type, CardColor color, String number, int ccv, String cardHolder, LocalDate fromDate, LocalDate thruDate, Client client) {
        this.type = type;
        this.color = color;
        this.number = number;
        this.ccv = ccv;
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
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

    public Client getClient() {
        return client;
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

    public void setClient(Client client) {
        this.client = client;
    }
}
