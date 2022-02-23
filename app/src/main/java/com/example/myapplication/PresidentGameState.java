package com.example.myapplication;

import android.util.Log;
import java.util.ArrayList;

public class PresidentGameState {

    // IMPORTANT: If you add a new instance variable, make sure you update the
    // deep copy ctor!!!!!
    int maxPlayers;
    int currentStage;

    ArrayList<HumanPlayer> players;
    TurnCounter currTurn;
    Deck discardPile;

    /**
     * Default setup for a game.
     * @param players The players to be added to the game
     */
    public PresidentGameState(ArrayList<HumanPlayer> players) {
        this.players = players;
        this.currentStage = 0;
        this.maxPlayers = players.size();
        this.discardPile = new Deck();

        this.currTurn = new TurnCounter(this.maxPlayers);

        dealCards();
    }

    // Deep copy ctor for PresidentGameState
    public PresidentGameState(PresidentGameState orig) {

        this.players = orig.players; // Players should not be deep copied (passed by ref)

        // Mutable primitives
        this.maxPlayers = orig.maxPlayers;
        this.currentStage = orig.currentStage;

        // Mutable class types
        this.discardPile = new Deck(orig.discardPile);
        this.currTurn = new TurnCounter(orig.currTurn);
    }

    /**
     * This method prunes out all players except the given parameter from this GameState.
     * Useful for when you want to hide information.
     * @param player the player to be kept in the GameState. All other players are removed.
     */
    public void prunePlayers(HumanPlayer player) {
        this.players.forEach(p -> {
            if (p.getId() != player.getId()) {
                this.players.remove(p);
            }
        });
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

        // THIS IS FOR TESTING/DEBUG
        this.players.forEach(p -> {
            Log.i("DECKS","PLAYER: " + p.getDeck().toString());
        });
    }

    @Override
    public String toString() {
        int playerNo = 1;
        StringBuilder info = new StringBuilder("{GameState Info: maxPlayers = " + maxPlayers + ", currTurn = " + currTurn +
                ", Players [ ");
        for (HumanPlayer player: players) {
            info.append("(Player " + playerNo + ", ID: " + player.getId() + ", Cards: ");
            for (Card card: player.deck.cards) {
                info.append("{ " + card.getRank() + " of " + card.getSuite() + " } ");
            }
            info.append(", " + "Points: " + player.getScore() + " ) \n");
            playerNo++;
        }

        info.append(" ]}");
        return info.toString();
    }

    /**
     * This method checks if it's a player's turn.
     * @param player the player's turn to be checked
     * @return TRUE if it's the player's turn, FALSE if not
     */
    public boolean isPlayerTurn(HumanPlayer player) {
        for (int i = 0; i < maxPlayers; i++) {
            if ((i+1) == currTurn.turn) { // Need to add '1' to 'i' because turns start from 1
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
        if (isPlayerTurn(player)) {
            return true;
        }
        return false;
    }

    public boolean pass(HumanPlayer player) {
        if (isPlayerTurn(player)) {
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
