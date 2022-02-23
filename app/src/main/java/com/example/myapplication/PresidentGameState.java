package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;

public class PresidentGameState {
    int maxPlayers;

    ArrayList<HumanPlayer> players;
    TurnCounter currTurn;
    Deck discardPile;

    /**
     * Default setup for a game.
     * @param players The players to be added to the game
     */
    public PresidentGameState(ArrayList<HumanPlayer> players) {
        this.players = players;
        this.maxPlayers = players.size();
        this.discardPile = new Deck();

        this.currTurn = new TurnCounter(this.maxPlayers);

        dealCards();
    }

    /**
     * Deep copy constructor. Hides all information from players that aren't
     * the given player.
     * @param that The PresidentGameState to be copied
     * @param player The player that this copy will be sent to
     */
    public PresidentGameState(PresidentGameState that, HumanPlayer player) {

        this.players = that.players; // Players should not be deep copied

        PresidentGameState that_clone = (PresidentGameState) that.clone();
        this.maxPlayers = that_clone.maxPlayers;
        this.discardPile = that_clone.discardPile;
        this.currTurn = that_clone.currTurn;

        // Removing all other players from this game state, so that the info
        // cannot be illegally accessed.
        this.players.forEach(p -> {
            if (!(p.getId() == player.getId())) {
                this.players.remove(p);
            }
        });
    }

    /**
     * External Citation
     * Date: 19 Feb 2022
     * Problem: Needed a way to deep copy a PresidentGameState object.
     *
     * Resource: https://www.baeldung.com/java-deep-copy
     * Solution: I used their code for this clone method.
     *
     * @return
     */
    @Override
    public Object clone() {
        try {
            return (PresidentGameState) super.clone();
        } catch (CloneNotSupportedException e) {
            return new PresidentGameState(this.players);
        }
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

        for (HumanPlayer player: this.players) {
            for (int i = 0; i < (52 / players.size()); i++) {
                //selects a random card of the 52 in masterDeck
                Card randomCard = (masterDeck.cards.get((int) Math.random() * masterDeck.MAX_CARDS));

                //adds the card to the players deck/hand and removes it from masterDeck
                player.deck.cards.add(randomCard);
                masterDeck.cards.remove(randomCard);
                //Log.i("DECKS", "Value of i: " + i);
            }
        }

        // THIS IS FOR TESTING
        this.players.forEach(p -> {
            Log.i("DECKS","PLAYER: " + p.getDeck().toString());
        });
    }

    @Override
    public String toString() {
        return "{GameState Info: maxPlayers = " + maxPlayers + ", currTurn = " + currTurn +
                ", Player1 = " + players.get(0) + ", Player2 = " + players.get(1) + "}\n";
    }

    public boolean isValidMove(HumanPlayer player) {
        for (int i = 0; i < maxPlayers; i++) {
            if (i == currTurn.turn) {
                if (player.getId() == players.get(i).getId()) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean playCard(HumanPlayer player) {
        if (isValidMove(player)) {
            return true;
        }
        return false;
    }

    public boolean pass(HumanPlayer player) {
        if (isValidMove(player)) {
            return true;
        }
        return false;
     }

     public boolean selectCard(HumanPlayer player) {
        return true;
     }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ArrayList<HumanPlayer> getPlayers() {
        return players;
    }

    public TurnCounter getCurrTurn() {
        return currTurn;
    }

    public Deck getDiscardPile() {
        return discardPile;
    }
}
