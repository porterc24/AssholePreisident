package com.example.myapplication;

public class Card {
    private final int rank;
    private final int suite;

    public Card(int suite, int rank) {
        this.suite = suite;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getSuite() {
        return suite;
    }
}
