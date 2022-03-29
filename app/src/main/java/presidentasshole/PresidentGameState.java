package presidentasshole;

import android.util.Log;

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
public class PresidentGameState {
    // TODO
    // implement a method that deals with invalid moves (when a player cannot play a card)

    // IMPORTANT: If you add a new instance variable, make sure you update the
    // deep copy ctor!!!!!
    int maxPlayers;
    int currentStage;

    ArrayList<Player> players;
    TurnCounter currTurn;
    CardStack inPlayPile;
    Deck discardPile;
    CurrentState state;
    PresidentGame game;

    /**
     * Default ctor. Second one is definitely better, though.
     */
    public PresidentGameState() {
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
    public PresidentGameState(ArrayList<Player> players, PresidentGame game) {

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
    public PresidentGameState(PresidentGameState orig) {

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
                //Log.i("DECKS", "Value of i: " + i);
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

            this.game.sendInfo(new PromptAction(null), getPlayerFromTurn());
            this.game.renderPlayPile();
            return true;
        }
        return false;
    }

    public boolean pass(Player player) {
        if (isPlayerTurn(player)) {
            this.currTurn.nextTurn();
            this.game.sendInfo(new PromptAction(null), getPlayerFromTurn());
            this.game.print("Pass accepted.");
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

    //TODO potential AI behavior?
    /**
     * loops through the players hand and finds the valid cards, then selects an equal amount of a
     * valid card rank (i.e. two 9s, three Kings etc.)
     * @param player
     * @return Deck
     */

    // function is edited for Proj. E, and isn't functional for gameplay

//    public CardStack selectCards(Player player) {
//        player.getSelectedCardStack().clear();
//        ArrayList<Card> card_buffer = new ArrayList<>();
//
//        // checking if the player is the first to play cards
//        if (inPlayPile.getStackSize() == 0) {
//            // pick a random card from the player's deck
//            Card card = player.getDeck().getCards().get((int) Math.random() * player.getDeck().getCards().size());
//
//            // add all instances of that card to the selectedCards deck
//            for (Card c: player.getDeck().getCards()) {
//                if (c.getRank() == card.getRank()) {
//                    card_buffer.add(c);
//                }
//            }
//        }
//        else {
//            // looping through the player's cards and adding the valid ones to the validCards deck
//            for (Card card: player.getDeck().getCards()) {
//                if (card.getRank() >= inPlayPile.getStackRank()) {
//                    player.getValidCards().addCard(card);
//                }
//            }
//        }
//
//        // repeat while the player's selectedCards doesn't equal the inPlayPile's cards
//        // i.e. the player only selects one higher card when a pair of cards is in play
//
//        while (player.getSelectedCardStack().getStackSize() < inPlayPile.getStackSize()) {
//            // clear the deck from any past iterations of the loop
//            player.getSelectedCardStack().clear();
//
//            // picking a random card from the validCards deck to play
//            Card selectedMoveCard = player.getValidCards().getCards().get((int) (Math.random() * player.getValidCards().getCards().size()));
//
//            // looping through the player's valid cards and adding all of the same rank cards to the
//            // selectedCards deck
//            for (Card card: player.getValidCards().getCards()) {
//                if (card.getRank() == selectedMoveCard.getRank()) {
//                    player.getSelectedCardStack().add(card);
//                }
//            }
//        }
//        return player.getSelectedCardStack();
//    }

    /**
     * This is how the GameState receives information from other Player objects.
     * PlayCardAction and PassAction are basically the only two things a player can do
     * in this game.
     * @param action The GameAction the player has made.
     * @return Whether or not the move was valid or not
     */
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

    public void setGame(PresidentGame game) {
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
}
