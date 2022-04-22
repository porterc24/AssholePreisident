package com.example.presidentasshole.info;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.cards.Deck;

/**
 * Encapsulates information about players. Stored in the player_info[] array in
 * PresidentGameState.
 */
public class PlayerInfo {

    private String name;
    private Deck deck;
    private CardStack selected_cards;

    private int id;
    private int score;
    private int gui_deck_size; // This is only used when sending UpdateGUIInfo to players

    private boolean collapse;

    public PlayerInfo(String name, int id) {
        this.name = name;
        this.id = id;
        this.score = 0;
        this.collapse = false;
        this.deck = new Deck();
        this.selected_cards = new CardStack();
    }

    public PlayerInfo(PlayerInfo that) {
        this.name = that.name;
        this.id = that.id;
        this.score = that.score;
        this.collapse = that.collapse;
        this.deck = new Deck(that.deck);
        this.selected_cards = new CardStack(that.selected_cards);
    }

    public boolean selectCard(Card card) {
        return this.selected_cards.add(card);
    }

    public boolean deselectCard(Card card) {
        return this.selected_cards.remove(card);
    }

    public void addCard(Card card) {
        this.deck.addCard(card);
    }

    public void resetDeck() {
        this.deck.getCards().clear();
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getId() {
        return id;
    }

    public CardStack getSelectedCards() {
        return this.selected_cards;
    }

    public boolean getCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public int getScore() {
        return score;
    }

    public void setGUIDeckSize(int deck_size) {
        this.gui_deck_size = deck_size;
    }

    public int getGUIDeckSize() {
        return gui_deck_size;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int i) {
        this.score += i;
    }
}
