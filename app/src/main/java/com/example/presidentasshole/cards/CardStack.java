package com.example.presidentasshole.cards;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Woods
 *
 * This class is used to manage the cards in a player's selection, and the game's PlayPile.
 * It's basically just a list of cards, except that it has a special rule: cards cannot be added
 * to the stack if they have a different rank from the cards already in the stack. If there are no
 * cards, then it'll just add it to the stack.
 *
 * set -> sets the contents of the stack. If set to a list of cards, all the cards in the list
 * must be the same rank, otherwise it will be rejected.
 *
 * add -> adds a card to the stack. Must have the same rank as the other cards in the stack.
 *
 * clear -> clears the stack.
 */
public class CardStack {

    // TODO remove method for deselecting cards

    private ArrayList<Card> cards;

    public CardStack() {
        this.cards = new ArrayList<>();
    }

    public CardStack(Card card) {
        set(card);
    }

    // Copy ctor
    public CardStack(CardStack that) {
        set(that.cards());
    }

    public void set(Card card) {
        this.cards = new ArrayList<Card>();
        this.cards.add(card);
    }

    public void set(List<Card> card_list) {
        this.cards = new ArrayList<>();
        if (validateCards(card_list)) {
            this.cards.addAll(card_list);
        } else {
            Log.i("CardStackError","Unable to add card to stack!");
        }
    }

    /**
     * Adds a card to the deck.
     * @param card
     * @return TRUE if operation was succesful, FALSE otherwise
     */
    public boolean add(Card card) {
        if (isEmpty()) {
            set(card);
            return true;
        }
        if (validateCard(card)) {
            this.cards.add(card);
            return true;
        }
        return false;
    }

    public void add(List<Card> card_list) {
        if (card_list != null) {
            if (validateCards(card_list)) {
                // TODO This is garbage and frustrating
                try {
                    this.cards.addAll(card_list);
                } catch (NullPointerException e) {
                    // dont care
                }
            }
        }
    }

    // Ensures that this particular card is of the same rank in the deck.

    private boolean validateCard(Card card) {
        if (isEmpty()) {
            return true;
        }
        // So that the user doesn't select a card they've already
        // selected....
        if (card.isSelected()) {
            return false;
        }
        return card.getRank() == getStackRank();
    }

    // Ensures that every card is of the same rank as each other.
    private boolean validateCards(List<Card> card_list) {
        boolean flag = true;
        // Checking if the given card list contains all the same card ranks
        for (int i = 0; i < card_list.size(); i++) {
            if (card_list.get(i).getRank() != card_list.get(0).getRank()) {
                Log.i("CardStackError","Could not add stack!");
                return false;
            }
        }
        return true;
    }

    // Sets all of the cards in the given list to be selected
    private void setListToSelected(List<Card> card_list) {
        card_list.forEach(c -> {
            c.setSelected(true);
        });
    }

    public boolean remove(Card card) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).cardEquals(card)) {
                card.setSelected(false);
                this.cards.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getStackRank() {
        return this.cards.get(0).getRank();
    }

    public ArrayList<Card> cards() {
        return this.cards;
    }

    public Card getCard(int index) { return this.cards.get(index); }

    public int getStackSize() {
        if (isEmpty()) {
            return 0;
        }
        return this.cards.size();
    }

    public void print() {
        for (Card card : this.cards) {
            Log.i("CARD STACK","RANK: " + card.getRank() + " SUITE: " + card.getSuite());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : this.cards) {
            sb.append("CARD: RANK " + card.getRank() + " SUITE " + card.getSuite());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void clear() {
        this.cards = null;
    }

    public boolean isEmpty() {
        if (this.cards == null) {
            return true;
        }
        if (this.cards.size() == 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }
}
