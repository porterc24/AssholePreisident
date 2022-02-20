package com.example.myapplication;

import java.util.UUID;

public class HumanPlayer {
    Deck deck;
    UUID id;

    public HumanPlayer(Deck deck) {
        this.id = UUID.randomUUID();
        this.deck = deck;
    }

    public HumanPlayer() {
        this.id = UUID.randomUUID();
        this.deck = new Deck();
    }

    public Deck getDeck() {
        return deck;
    }

    public UUID getId() {
        return id;
    }
}
