package com.example.myapplication;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is used for managing groups of cards. It contains useful methods for generating
 * decks, shuffling, and picking out selections of cards from the deck.
 */
public class Deck {
    ArrayList<Card> cards;
    public final int MAX_CARDS = 52;

    public Deck() {
        this.cards = new ArrayList<Card>();
    }

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    // Copy ctor for Deck
    public Deck(Deck orig) {
        this.cards = new ArrayList<Card>();
        this.cards.addAll(orig.cards); // Copying all of the cards into this deck
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * This method generates a full deck of 52 cards. It then shuffles all of those cards.
     * Instead of returning a new deck, it simply generates a full one in THIS deck.
     */
    public void generateMasterDeck() {
        // Outer loop is for suites
        for (int suite = 1; suite <= 4; suite ++) {
            // Inner loop is for ranks
            for (int rank = 1; rank <= 13; rank ++) {
                Log.i("President","SUITE: " + suite + " RANK: " + rank); // DEBUG
                this.cards.add(new Card(suite,rank));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        this.cards.forEach(c -> {
            output.append("SUITE: " + c.getSuite() +
                          " RANK: " + c.getRank() + "\n");
        });

        return output.toString();
    }
}
