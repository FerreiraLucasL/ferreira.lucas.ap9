package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Random;
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
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {}

    public Card(CardType type, CardColor color, String number, Client client) {
        LocalDateTime today = LocalDateTime.now();
        Random randomcito = new Random();
        this.type = type;
        this.color = color;
        this.number = number;
        this.ccv = randomcito.nextInt(999);
        this.client = client;
        this.cardHolder = (client.getFirstName() + " " + client.getLastName().toUpperCase()) ;
        this.fromDate= today;
        this.thruDate = today.plusYears(5);
        client.getCards().add(this);
    }

    public Card(CardType type, CardColor color, String number, int ccv, String cardHolder, LocalDateTime fromDate, LocalDateTime thruDate, Client client) {
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

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
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

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
