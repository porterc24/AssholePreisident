package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;

public class PresidentGameState {
    int maxPlayers;

    ArrayList<HumanPlayer> players;
    TurnCounter currTurn;

    public PresidentGameState(ArrayList<HumanPlayer> players) {
        this.players = players;
        this.maxPlayers = players.size();

        this.currTurn = new TurnCounter(this.maxPlayers);

        dealCards();
    }

    /**
     * This method is used to deal the cards at the start of the game.
     * First, it generates the full "master deck" of 52 cards. Then,
     * it takes out slices of that master deck and deals them
     * out to the players.
     */
    private void dealCards() {
        Deck masterDeck = new Deck(new ArrayList<Card>());

        masterDeck.generateMasterDeck();

        // THIS IS FOR TESTING
        this.players.forEach(p -> {
            Log.i("DECKS","PLAYER: " + p.getDeck().toString());
        });
    }
}
