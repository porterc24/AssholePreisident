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

    public Deck() {
        this.cards = new ArrayList<Card>();
    }

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
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

//    /**
//     * This method is used for dealing cards from the master deck to the other players.
//     * It divides the deck evenly into the given number of slices, and returns all of the sliced
//     * decks as an ArrayList.
//     * @param slices How many decks that the deck will be cut into
//     * @return All of the sliced decks
//     */
//    public ArrayList<Deck> sliceDeck(int slices) {
//
//        int slice_quantity = (int) (52 / slices); // The number of cards in each sliced deck
//        ArrayList<Deck> sliced_decks = new ArrayList<>();
//
//        for (int i = 0; i < slices; i++) {
//            sliced_decks.add(new Deck());
//            for (int j = 0; j < slice_quantity; j++) {
//                // This line pulls a card from the current deck into the new one
//                sliced_decks.get(slices).addCard(
//                        this.cards.get((i*slice_quantity) + j)
//                );
//                Log.i("SLICE","" + (i*slice_quantity) + j);
//            }
//        }
//
//        return sliced_decks;
//    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        this.cards.forEach(c -> {
            output.append("SUITE: " + c.getSuite() +
                          "RANK: " + c.getRank() + "\n");
        });

        return output.toString();
    }
}
