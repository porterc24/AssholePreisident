package com.example.myapplication;

import java.util.ArrayList;

public class PresidentGameState {
    int maxPlayers;

    ArrayList<HumanPlayer> players;
    TurnCounter currTurn;

    public PresidentGameState(ArrayList players) {
        this.players = players;
        this.maxPlayers = players.size();

        this.currTurn = new TurnCounter(this.maxPlayers);

        dealCards();
    }

    private void dealCards() {
        Deck masterDeck = new Deck(new ArrayList<Card>());
        masterDeck.generateMasterDeck();

        for (HumanPlayer player: this.players) {
            for (int i = 0; i < 52 / players.size(); i++) {
                Card randomCard = (masterDeck.cards.get((int) Math.random() * masterDeck.MAX_CARDS));
                player.deck.cards.add(randomCard);
                masterDeck.cards.remove(randomCard);
            }
        }
    }
}
