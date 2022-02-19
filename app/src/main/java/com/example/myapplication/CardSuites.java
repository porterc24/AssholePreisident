package com.example.myapplication;

public enum CardSuites {
    DIAMONDS(1),
    CLUBS(2),
    HEARTS(3),
    SPADES(4);

    public final int suite;
    CardSuites(int suite) {
        this.suite = suite;
    }
}
