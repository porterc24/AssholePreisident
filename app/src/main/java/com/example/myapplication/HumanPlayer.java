package com.example.myapplication;

import java.util.UUID;

public class HumanPlayer {
    Deck deck;
    int score;
    UUID id;

    public HumanPlayer(Deck deck) {
        this.id = UUID.randomUUID();
        this.deck = deck;
        this.score = 0;
    }

    public HumanPlayer() {
        this.id = UUID.randomUUID();
        this.deck = new Deck();
        this.score = 0;
    }

    public Deck getDeck() {
        return deck;
    }


    public UUID getId() {
        return id;
    }
}
