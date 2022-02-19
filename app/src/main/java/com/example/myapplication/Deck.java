package com.example.myapplication;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void generateMasterDeck() {
        // Outer loop is for suites
        for (int suite = 1; suite < 4; suite ++) {
            // Inner loop is for ranks
            for (int rank = 1; rank < 13; rank ++) {
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
        return "TODO";
    }
}
