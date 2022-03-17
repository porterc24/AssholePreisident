package com.example.presidentasshole;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * Pretty simply class. Represents card data (rank and suite).
 */
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
        this.suite = orig.suite;;
    }

    public int getRank() {
        return rank;
    }

    public int getSuite() { return suite; }

    public boolean cardEquals(Card that) {
        return that.getRank() == this.rank && that.getSuite() == this.suite;
    }

}
