package com.example.presidentasshole;

import android.util.Log;

import com.example.presidentasshole.actions.ClearDeckAction;
import com.example.presidentasshole.actions.DealCardAction;
import com.example.presidentasshole.actions.GameAction;
import com.example.presidentasshole.actions.PassAction;
import com.example.presidentasshole.actions.PlayCardAction;
import com.example.presidentasshole.actions.PromptAction;
import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.cards.CardSuites;
import com.example.presidentasshole.cards.CardValues;
import com.example.presidentasshole.cards.Deck;
import com.example.presidentasshole.players.Player;

import java.util.ArrayList;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * This class contains information about the current state of the game, e.g:
 * - The players and their scores, hands, etc.
 * - The cards in play
 * - The stage of the game (setup, play, etc.)
 *
 * Information is transferred between the GameState and the Player through the PresidentGame, which
 * uses the sendInfo() method to send GameAction objects. It's kind of like how two clients send
 * each other packets via a server.
 *
 */
public class PresidentGameStateDeprecated {
    // TODO
    // implement a method that deals with invalid moves (when a player cannot play a card)

    // IMPORTANT: If you add a new instance variable, make sure you update the
    // deep copy ctor!!!!!
    int maxPlayers;
    int currentStage;
    int passCounter; // keeps track of num of passes
    Player lastPlayed; // last person who played a card
    public boolean game_over = false;

    ArrayList<Player> players;
    TurnCounter currTurn;
    CardStack inPlayPile;
    Deck discardPile;
    CurrentState state;
    PresidentGameDeprecated game;

    /**
     * Default ctor. Second one is definitely better, though.
     */
    public PresidentGameStateDeprecated() {
        this.currentStage = 0;
        this.discardPile = new Deck();
        this.currTurn = new TurnCounter(this.maxPlayers);
        this.inPlayPile = new CardStack();

        state = CurrentState.INIT_ARRAYS;
    }

    /**
     * Setps up a new GameState.
     * @param players The players to be added to the game
     * @param game The PresidentGame reference
     */
    public PresidentGameStateDeprecated(ArrayList<Player> players, PresidentGameDeprecated game) {

        this.players = players;
        this.currentStage = 0;
        this.maxPlayers = players.size();
        this.discardPile = new Deck();
        this.game = game;
        this.currTurn = new TurnCounter(this.maxPlayers);

        // At the start of the game, the first player is the first person to put down a card.
        // The first card is set to null so that the game knows it's valid for that player to
        // put down a card on the first turn.
        this.inPlayPile = new CardStack();

        state = CurrentState.INIT_ARRAYS;

    }

    // Deep copy ctor for PresidentGameState
    public PresidentGameStateDeprecated(PresidentGameStateDeprecated orig) {

        this.players = orig.players; // Players should not be deep copied (passed by ref)

        // Mutable primitives
        this.maxPlayers = orig.maxPlayers;
        this.currentStage = orig.currentStage;

        // Mutable class types
        this.discardPile = new Deck(orig.discardPile);
        this.currTurn = new TurnCounter(orig.currTurn);
        this.inPlayPile = new CardStack(orig.inPlayPile);
        this.game = orig.game;

        state = CurrentState.INIT_OBJECTS;
    }

    /**
     * This method is used to deal the cards at the start of the game.
     * First, it generates the full "master deck" of 52 cards. Then,
     * it takes out slices of that master deck and deals them
     * out to the players.
     */
    public void dealCards() {
        state = CurrentState.GAME_SETUP;
        Deck masterDeck = new Deck(new ArrayList<Card>());
        masterDeck.generateMasterDeck();


        for (Player player : this.players) {
            this.game.print("Sending deck slice to player " + player.getId());
            for (int i = 0; i < (52 / players.size()); i++) {
                //selects a random card of the 52 in masterDeck
                Card randomCard = (masterDeck.getCards().get((int) Math.random() * masterDeck.MAX_CARDS));

                this.game.sendInfo(new DealCardAction(
                        null,
                        randomCard
                ), player);
                masterDeck.getCards().remove(randomCard);
                //Log.i("DECKS", "Value of i: " + i);
            }
        }

        // THIS IS FOR TESTING/DEBUG
        this.players.forEach(p -> {
            Log.i("DECKS","PLAYER: " + p.getDeck().toString());
        });

        state = CurrentState.MAIN_PLAY;
    }

    /**
     * Instead of generating a master deck, this method takes a pre-made deck and deals it out
     * to the players. Useful for testing.
     */
    public void dealRiggedCards(Deck rigged_deck) {
        state = CurrentState.GAME_SETUP;

        for (Player player : this.players) {
            for (int i = 0; i < (52 / players.size()); i++) {
                Card riggedCard = (rigged_deck.getCards().get(i));
                this.game.sendInfo(new DealCardAction(
                        null,
                        riggedCard
                ), player);
            }
        }

        // THIS IS FOR TESTING/DEBUG
        this.players.forEach(p -> {
            Log.i("RIGGED","PLAYER: " + p.getDeck().toString());
        });

        state = CurrentState.MAIN_PLAY;
    }

    public CardStack getPlayPile() {
        return inPlayPile;
    }

    /**
     * This method checks if it's a player's turn.
     * @param player the player's turn to be checked
     * @return TRUE if it's the player's turn, FALSE if not
     */
    public boolean isPlayerTurn(Player player) {
        if (getPlayerFromTurn().getId().equals(player.getId())) {
            return true;
        }
        return false;
    }

    /**
     * Returns the HumanPlayer who's turn it is.
     */
    public Player getPlayerFromTurn() {
        return this.players.get(currTurn.turn-1); // Turn counter starts from 1
    }

    /**
     * checks that the first index of the passed deck (selectedCards)'s rank is higher than the
     * first index of the inPlayPile's rank, and whether the length of the (selectedCards) is longer
     * than or equal to the inPlayPile's length
     *
     * inPlayPile is a max length 4, so
     * @param deck
     * @return
     */

    // potential bug - currently initializing inPlayPile with a length of 0, may mess up when

    public boolean isValidMove(Deck deck) {
        //TODO
        if (deck.getCards().get(0).getRank() > inPlayPile.getStackRank()) {
            if (deck.getCards().size() >= inPlayPile.getStackSize()) {
                return true;
            }
        }
        // cannot play cards, illegal move
        // with this implementation shouldn't be running
        return false;
    }

    /**
     * "plays" the card to the top of the inPlayPile, returns true if successful, else returns false
     * @param player
     * @return boolean
     */
    public boolean playCards(Player player) {
        if (isPlayerTurn(player)) {
            inPlayPile.set(player.getSelectedCardStack().cards());
            inPlayPile.print();
            // The PromptAction is for telling the AI player to make a move:
            this.currTurn.nextTurn();
            this.game.updateTurnText(this.currTurn.getTurn());

            this.game.sendInfo(new PromptAction(null), getPlayerFromTurn());
            this.game.renderPlayPile();

            lastPlayed = player;
            passCounter = 0;

            return true;
        }
        return false;
    }

    public boolean pass(Player player) {
        if (isPlayerTurn(player)) {
            this.currTurn.nextTurn();
            this.game.updateTurnText(this.currTurn.getTurn());
            this.game.print("Pass accepted.");

            this.game.sendInfo(new PromptAction(null), getPlayerFromTurn());
            passCounter++;

            // Reset play stack if everyone has passed
            if (passCounter == (this.maxPlayers - 1)) {
                passCounter = 0;
                this.inPlayPile = new CardStack();
                this.game.renderPlayPile();
            }
            // Reset game if everyone has passed?
            if (passCounter == this.maxPlayers) {
                Log.i("game","Game over!");
            }
            return true;
        }
        this.game.print("Pass rejected. Not player's turn.");
        return false;
    }

    public void clearDecks() {
        this.players.forEach(p -> {
            this.game.sendInfo(new ClearDeckAction(null), p);
        });
    }

    /**
     * This is how the GameState receives information from other Player objects.
     * PlayCardAction and PassAction are basically the only two things a player can do
     * in this game.
     * @param action The GameAction the player has made.
     * @return Whether or not the move was valid or not
     */
    public boolean receiveInfo(GameAction action) {

        if (game_over) {
            return false;
        }

        // If the player has tried to play a card...
        if (action instanceof PlayCardAction) {
            Player player = action.getSender();
            PlayCardAction pca = (PlayCardAction) action;

            if (isPlayerTurn(player)) {
                if (this.inPlayPile.getStackSize() == 0) { // If it's the very first turn...
                    this.game.print("Playing first card.");
                    playCards(player);
                    return true;
                }

                // If there's a 2 on the deck (card reset):
                if (this.inPlayPile.getStackRank() == 15) {
                    playCards(player);
                    return true;
                }

                // If they play a 2 (card reset):
                if (pca.getPlayedCards().getStackRank() == 15) {
                    playCards(player);
                    return true;
                }

                CardStack selected_cards = pca.getPlayedCards();
                // Rules for playing cards
                if (this.inPlayPile.getStackSize() <= selected_cards.getStackSize()) {
                    if (this.inPlayPile.getStackRank() < selected_cards.getStackRank()) {
                        this.game.print("Card accepted.");
                        playCards(player);
                        return true;
                    } else {
                        this.game.print("Card rejected. Rank is too low.");
                    }
                } else {
                    this.game.print("Card rejected. Card quantity is too low.");
                }
                return false;
            } else {
                this.game.print("Card rejected. Not the player's turn.");
                return false;
            }
        } // PlayCardAction

        // If the player passes, go to the next turn
        else if (action instanceof PassAction) {
            Player player = action.getSender();
            return pass(player);
        } // PassAction

        return false;
    } // receiveInfo

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public TurnCounter getCurrTurn() {
        return currTurn;
    }

    public Deck getDiscardPile() {
        return discardPile;
    }

    public boolean endGame(ArrayList<Player> players){
        int out = 0;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getIsOut() == false) {
                out++;
            }
        }
        if(out == players.size() - 1){
            state = CurrentState.GAME_END;
            return true;
        }
        return false;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        this.maxPlayers = players.size();
        this.currTurn = new TurnCounter(this.maxPlayers);
    }

    public void setGame(PresidentGameDeprecated game) {
        this.game = game;
    }

    public void addPlayer(Player player) {
        if (this.players == null || this.players.size() == 0) {
            this.players = new ArrayList<>();
        }
        this.players.add(player);
        this.maxPlayers = this.players.size();
        this.currTurn = new TurnCounter(this.maxPlayers);
    }

    /**
     * This method prints out returns a String with the attributes of the current PresidentGameState
     * @return info.toString() returns the StringBuilder with the info
     */

    @Override
    public String toString() {
        int playerNo = 1;
        StringBuilder info = new StringBuilder("{GameState Info: maxPlayers = " + maxPlayers + ", currTurn = " + currTurn +
                ", Players [ ");
        for (Player player: players) {
            info.append("(Player " + playerNo + ", ID: " + player.getId() + ", Cards: ");
            for (Card card: player.getDeck().getCards()) {
                info.append("{ " + CardValues.getCardValue(card.getRank()) + " of " + CardSuites.getSuiteName(card.getSuite()) + " } ");
            }
            info.append(", " + "Points: " + player.getScore() + " ) \n");
            playerNo++;
        }

        info.append(" ]");
        info.append("\n");
        info.append("[PlayPile Info: ");
        info.append(this.inPlayPile.toString());
        info.append("]}");
        return info.toString();
    }
}