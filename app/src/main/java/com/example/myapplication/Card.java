package com.example.myapplication;

public class Card {
    private final int rank;
    private final int suite;

    public Card(int rank, int suite) {
        this.rank = rank;
        this.suite = suite;
    }

    // Copy ctor for Card class
    public Card(Card orig) {
        this.rank = orig.rank;
        this.suite = orig.suite;
    }

    public int getRank() {
        return rank;
    }

    public int getSuite() { return suite; }
}
