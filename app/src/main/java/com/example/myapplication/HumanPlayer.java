package com.example.myapplication;

import java.util.UUID;

public class HumanPlayer {
    Deck deck;
    int score;

    // This UUID is automatically generated each time a new HumanPlayer is made.
    // It's useful for comparing two player objects.
    UUID id;
    boolean isOut;

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
        return this.deck;
    }

    public UUID getId() {return this.id; }

    public int getScore() {return this.score;}

    public boolean getIsOut() {return this.isOut;}
}
