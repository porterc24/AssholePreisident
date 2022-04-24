package com.example.presidentasshole.cards;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * This class is used for managing groups of cards. It contains useful methods for generating
 * decks, shuffling, and picking out selections of cards from the deck.
 */
public class
Deck {

    private ArrayList<Card> cards;
    public final int MAX_CARDS = 52;

    public Deck() {
        this.cards = new ArrayList<Card>();
    }

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Deck(int i) {
        ArrayList<Card> c = new ArrayList<Card>(i);
        this.cards = c;
    }

    // Copy ctor for Deck
    public Deck(Deck orig) {
        this.cards = new ArrayList<Card>();
        this.cards.addAll(orig.cards); // Copying all of the cards into this deck
    }

    /**
     * Removes a card from the given deck. It does not match the card objects, but their rank
     * and suite.
     * @param card
     */
    public void removeCard(Card card) {
        Card flag = null;
        for (int i = 0; i < cards.size(); i++) {
            Card this_card = cards.get(i);
            if (card.cardEquals(this_card)) {
                flag = this_card;
                break;
            }
        }
        if (flag != null) {
            this.cards.remove(flag);
        }
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public ArrayList<Card> returnCards() { return this.cards; }

    /**
     * This method generates a full deck of 52 cards. It then shuffles all of those cards.
     * Instead of returning a new deck, it simply generates a full one in THIS deck.
     */
    public void generateMasterDeck() {
        // Outer loop is for suites
        for (int suite = 1; suite <= 4; suite ++) {
            // Inner loop is for ranks
            for (int rank = 1; rank <= 13; rank ++) {
//                Log.i("President","SUITE: " + suite + " RANK: " + rank); // DEBUG
                this.cards.add(new Card(rank+2,suite)); // +2 is necessary (see CardValues)
            }
        }
        shuffle();
    }

    public void generateRiggedDeck() {
        ArrayList<Card> deck_cards = new ArrayList<>();
        deck_cards.add(new Card(3, 1));
        deck_cards.add(new Card(4, 1));
        deck_cards.add(new Card(5, 1));
        deck_cards.add(new Card(6, 1));
        deck_cards.add(new Card(7, 1));
        deck_cards.add(new Card(7, 1));
        deck_cards.add(new Card(8, 1));
        deck_cards.add(new Card(8, 1));
        deck_cards.add(new Card(8, 1));
        deck_cards.add(new Card(9, 1));
        deck_cards.add(new Card(9, 1));
        deck_cards.add(new Card(9, 1));
        deck_cards.add(new Card(10, 1));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                this.cards.add(deck_cards.get(j));
            }
        }
    }

    public int getNumCards() {
        return this.cards.size();
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

    public void addCards(ArrayList<Card> that_cards) {
        this.cards.addAll(that_cards);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }
}
