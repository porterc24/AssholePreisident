package com.example.myapplication;

import java.util.ArrayList;

public class PresidentGameState {
    int maxPlayers;

    ArrayList players;
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
    }
}
