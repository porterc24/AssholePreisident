package presidentasshole;

import android.util.Log;

import gameframework.infoMessage.GameState;
import presidentasshole.actions.ClearDeckAction;
import presidentasshole.actions.DealCardAction;
import presidentasshole.actions.GameAction;
import presidentasshole.actions.PassAction;
import presidentasshole.actions.PlayCardAction;
import presidentasshole.actions.PromptAction;
import presidentasshole.cards.Card;
import presidentasshole.cards.CardStack;
import presidentasshole.cards.CardSuites;
import presidentasshole.cards.CardValues;
import presidentasshole.cards.Deck;
import presidentasshole.players.Player;

import java.util.ArrayList;
import java.util.Collections;

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
 *
 */
public class PresidentGameState extends GameState {

    public int maxPlayers;
    public int playerTurn;
    //TurnCounter currTurn;
    public ArrayList<ArrayList<Card>> playerDecks;
    public ArrayList<Card> masterDeck;
    public CardStack playPile;
    public boolean[] playerStatus;

    /**
     * PresidentGameState: our constructor, sets up the game by creating the master deck, player decks, and playPile
     * also creates and sets the playerStatus boolean array to true(s), etc.
     * @param playerAmt
     */
    public PresidentGameState(int playerAmt) {
        super();
        this.maxPlayers = 4;
        this.playerTurn = 0;
        this.playerDecks = new ArrayList<>(maxPlayers);
        this.masterDeck = new ArrayList<>(52);
        this.playPile = new CardStack();
        this.playerStatus = new boolean[] {true, true, true, true};
        for(int i = 0; i<playerAmt; i++){
            this.playerDecks.add(new ArrayList<>(13));
        }
        this.dealCards();
    }

    /**
     * PresidentGameState Deep Copy constructor
     * @param orig: the target instance of PGS to deep copy
     */
    public PresidentGameState(PresidentGameState orig) {
        if(orig == null){
            return;
        }
        this.maxPlayers = orig.maxPlayers;
        this.playerTurn = orig.playerTurn;
        this.playerDecks = new ArrayList<>(orig.maxPlayers);
        this.playPile = new CardStack(orig.playPile);
        this.masterDeck = new ArrayList<>(orig.masterDeck);
        this.playerStatus = new boolean[orig.playerStatus.length];
        for (int i = 0; i < orig.playerStatus.length; i++) {
            playerStatus[i] = orig.playerStatus[i];
        }

        for (int i = 0; i < orig.playerDecks.size(); i++) {
            playerDecks.add(new ArrayList<>(7));
            for (int j = 0; j < orig.playerDecks.get(i).size(); j++ ) {
                playerDecks.get(i).add(new Card(orig.playerDecks.get(i).get(j)));
            }
        }

    }

    /**
     * This method is used to deal the cards at the start of the game.
     * First, it generates the full "master deck" of 52 cards. Then,
     * it takes out slices of that master deck and deals them
     * out to the players.
     */

    public boolean gameSetup(){
        return false;
    }

    /**
     * generateMasterDeck creates a new deck containing all of the the card values needed
     * and maximum 52 cards.
     * @return
     */
    public boolean generateMasterDeck() {
        for (int suite = 1; suite <= 4; suite ++) {
            // Inner loop is for ranks
            for (int rank = 1; rank <= 13; rank ++) {
                Log.i("President","SUITE: " + suite + " RANK: " + rank); // DEBUG
                masterDeck.add(new Card(rank+2,suite)); // +2 is necessary (see CardValues)
                Collections.shuffle(masterDeck);
                return true;
            }
        }
        return false;
    }


    /**
     * deals the cards to each players hands
     * @return if it was completed
     */
    public boolean dealCards() {
        if(generateMasterDeck()){
            for(int i = 0; i < maxPlayers; i ++) {
                for(int j = 0; i <= 12; i ++ ){
                    moveStart(this.masterDeck.get(j), this.masterDeck, this.playerDecks.get(i));
                }
            }
            for(int i = 0; i < maxPlayers; i ++) {
                Collections.shuffle(playerDecks.get(i));
            }
            return true;
        }
        return false;
    }


    /**
     * This method prints out returns a String with the attributes of the current PresidentGameState
     * @return info.toString() returns the StringBuilder with the info
     */

    @Override
    public String toString() {
      return "finish this later!";
    }


    /**
     * returns which players turn it is
      */
    public int getPlayerTurn() { return playerTurn; }

    /**
     * returns an int on who as won the game if over,
     * or -1 if nobody has won yet.
     */
    public int gameOver() {
        return endGame(playerStatus);
    }

    /**
     * Tests if the game is over
     * @param playerStatus - an array indicated which players are in the game (true) and which are
     *                     out (false)
     * @return - index # of winner if there is only one in (true) player in playerStatus, -1 if there are more
     * than one players still playing
     */
    public int endGame(boolean[] playerStatus){
        int out = 0;
        for(int i = 0; i < playerStatus.length; i++){
            if(playerStatus[i] == false) {
                out++;
            }
        }
        if(out == 3){
            for (int i = 0; i < playerStatus.length; i++) {
                if (playerStatus[i] == true) {
                    return i;
                }
            }
        }
        return -1;
    }




    public boolean isValidMove() {
        if ((playerDecks.get(playerTurn) != null) && playerStatus[playerTurn]) {
            return true;
        }
        return false;
    }


    public boolean playCard(int playerTurn, CardValues value, ArrayList<Card> source, ArrayList<Card> dest) {
       //TODO make a working playcard method
        return false;
    }

    /**
     * This is how the GameState receives information from other Player objects.
     * PlayCardAction and PassAction are basically the only two things a player can do
     * in this game.
     * @param action The GameAction the player has made.
     * @return Whether or not the move was valid or not

    public boolean receiveInfo(GameAction action) {

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
*/

    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * The helper function to move a card obj from src to dest arrayLists iff it contains that card
     * @param card - card object to be moved
     * @param src - source ArrayList
     * @param dest - destination ArrayLIst
     * @return - returns true if it was able to move, false if not
     */
    public boolean moveStart(Card card, ArrayList<Card> src, ArrayList<Card> dest){
        if(src.contains(card)){
            dest.add(card);
            src.remove(card);
            return true;
        }
        return false;
    }


    /**
     * getter method to get the index of a card in an arrayList
     * @param type - the cardtype in mind to search for an object for
     * @param src - the source arrayList to look in
     * @return - returns the index that the card object is located at, or -1 if it is not found in src
     */
    public int getCardIndex(CardValues type, ArrayList<Card> src) {
        for(int index = 0; index < src.size(); index++) {
            if(src.get(index).getRank() == type.value) {
                return index;
            }
        }
        return -1;
    }

    /**
     * gets a card object of a certain type from a source arrayLIst
     * @param type - cardtype to search for
     * @param src - the source arrayList to search in
     * @return - retruns the card Object if it exists, null if it doesn't exist
     */
    public Card getCard(CardValues value, ArrayList<Card> src){
        for(Card card: src){
            if(card.getRank() == value.value){
                return card;
            }
        }
        return null;
    }

}
