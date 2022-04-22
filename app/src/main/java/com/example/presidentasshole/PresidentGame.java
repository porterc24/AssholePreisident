package com.example.presidentasshole;

import android.content.DialogInterface;
import android.os.Message;
import android.util.Log;

import com.example.presidentasshole.actions.AISelectCardAction;
import com.example.presidentasshole.actions.CollapseCardAction;
import com.example.presidentasshole.actions.PassAction;
import com.example.presidentasshole.actions.PlayCardAction;
import com.example.presidentasshole.actions.SelectCardAction;
import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.cards.Deck;
import com.example.presidentasshole.game.GameMainActivity;
import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.LocalGame;
import com.example.presidentasshole.game.actionMsg.GameAction;
import com.example.presidentasshole.game.config.GameConfig;
import com.example.presidentasshole.game.util.MessageBox;
import com.example.presidentasshole.info.PlayerInfo;
import com.example.presidentasshole.info.UpdateDeckInfo;
import com.example.presidentasshole.info.UpdateGUIInfo;
import com.example.presidentasshole.info.UpdatePlayPileInfo;

/**
 * This class handles all of the game logic for President (Asshole).
 *
 * Information about the game is stored in the golden_state field. The golden_state is a
 * PresidentGameState object which contains the following important information:
 *  - Each GamePlayer has a corresponding PlayerInfo object which contains information about
 *    their score, cards, etc. An array of PlayerInfo corresponding to every player's ID num
 *    is kept inside of the golden_state.
 *
 *  Players are sent information about the state of the game through the sendInfo() and
 *  sendUpdatedStateTo() methods. Players are sent GameInfo objects containing information about
 *  the game that they need in order to make decisions. See the receiveInfo() method inside of the
 *  GamePlayer (and relevant subclasses) for more information.
 */
public class PresidentGame extends LocalGame implements DialogInterface.OnClickListener {

    private final int NUM_PLAYERS;

    private GameConfig config;
    private PresidentGameState golden_state;
    private GameMainActivity activity;

    public PresidentGame(GameConfig config, GameMainActivity activity) {
        this.config = config;
        this.activity = activity;
        this.NUM_PLAYERS = config.getNumPlayers();
    }

    /**
     * This method is overridden from the original because of some extra things that need to be
     * setup (in addition to what the parent class method does).
     *
     * It initializes the golden game state, registers new player information, and initializes
     * all of the player objects with their necessary information (such as their Game object ref,
     * and their number)
     * @param players the players in the game
     */
    @Override
    public void start(GamePlayer[] players) {
        super.start(players);


        // If the game is restarted, previous player score information needs to be remembered
        boolean isRestart = false;
        PresidentGameState prev_state = null;
        if (this.golden_state != null) {
            isRestart = this.golden_state.isGameOver();
            prev_state = new PresidentGameState(this.golden_state);
        }
        this.golden_state = new PresidentGameState(config);

        // Initializing player data objects...
        if (isRestart) { // To avoid removing the previous score data:
            for (int i = 0; i < NUM_PLAYERS; i++) {
                this.golden_state.registerPlayer(config.getSelName(i), i, prev_state.getPlayerData(i).getScore());
            }
        } else {
            for (int i = 0; i < NUM_PLAYERS; i++) {
                this.golden_state.registerPlayer(config.getSelName(i), i);
            }
        }

        for (int i = 0; i < NUM_PLAYERS; i++) {
            GamePlayer p = this.players[i];
            p.setGame(this);
            p.setPlayerNum(i);
        }
        this.players[0].setAsGui(this.activity);
        dealCards();
        sendAllUpdatedState();
    }

    /**
     * Called by a GamePlayer object when they'd like their PlayerInfo. Sends a copy of their
     * PlayerInfo data.
     * @param player
     */
    public PlayerInfo requestInfo(GamePlayer player) {

        // To ensure invalid players don't get this information:
        if (isPlayerTurn(player)) {
            return this.golden_state.getPlayerData(player.getPlayerNum());
        }
        return null;
    }

    /**
     * This method creates all of the cards that players will have in their hands. These cards
     * are then added to every player's PlayerInfo object.
     *
     * However, this method does NOT send GameInfo objects. It only adds information to PlayerInfo
     * objects.
     */
    public void dealCards() {
        Deck masterDeck = new Deck();
        masterDeck.generateMasterDeck();


        for (int i = 0; i < NUM_PLAYERS; i++) {

            PlayerInfo player_data = this.golden_state.getPlayerData(i);
            player_data.getDeck().getCards().clear();
            for (int j = 0; j < (52 / NUM_PLAYERS); j++) {
                //selects a random card of the 52 in masterDeck
                Card randomCard = (masterDeck.getCards().get((int) Math.random() * masterDeck.MAX_CARDS));


                player_data.addCard(randomCard);
                masterDeck.getCards().remove(randomCard);
            }
        }
    }

    /**
     * Sends every player relevant information from their corresponding PlayerInfo class inside of
     * the golden_state.
     * @param p
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

        // For sending only other player's scores:
        PlayerInfo[] pruned_pi = new PlayerInfo[NUM_PLAYERS];
        for (int i = 0; i < NUM_PLAYERS; i++) {
            PlayerInfo orig = this.golden_state.getPlayerData(i);
            pruned_pi[i] = new PlayerInfo(orig.getName(), orig.getId());
            pruned_pi[i].setScore(orig.getScore());
        }

        //TODO make this all a copy?
        p.sendInfo(new UpdateDeckInfo(
                this.golden_state.getPlayerData(p.getPlayerNum()),
                this.golden_state.getPlayerData(p.getPlayerNum()).getCollapse())
        );
        p.sendInfo(new UpdatePlayPileInfo(
                this.golden_state.getPlayPile()));
        p.sendInfo(new UpdateGUIInfo(
                this.golden_state.getTurn(),
                pruned_pi,
                (PresidentMainActivity) this.activity
        ));



        if (isPlayerTurn(p)) {
            p.sendInfo(new PresidentGameState(this.golden_state, p.getPlayerNum()));
        }
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    /**
     * This method is called whenever a game over condition might've been met (see playCards())
     * It also asks the current user if they'd like to play again.
     * @return
     */
    @Override
    public String checkIfGameOver() {

        if (this.golden_state.isGameOver()) {
            String msg = "Player " + getCurrPlayer().getName() + " wins!\n" +
                    "Would you like to play again?";
            MessageBox.popUpChoice(msg,
                    "Yes",
                    "No",
                    this,
                    this,
                    this.activity);
            return msg;
        }
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }

    /**
     * This method is called by the sendAction() method by GamePlayers. Whenever a player makes
     * a move, such as passing, selecting, or collapsing their cards, the information is sent to
     * this method. This method then checks if it's a valid move, and sends/updates information
     * accordingly.
     * @param msg
     */
    @Override
    protected void receiveMessage(Message msg) {
        super.receiveMessage(msg);

        if (this.golden_state.isGameOver()) {
            return;
        }

        if (msg.obj instanceof GameAction) {

            GameAction action = (GameAction) msg.obj;
            GamePlayer player = action.getPlayer();

            if (action instanceof SelectCardAction) {

                SelectCardAction sca = (SelectCardAction) action;
                CardImage card_image = sca.getCardImage();

                // isSelection() determines whether or not a player is selecting or de-selecting
                // a card
                if (sca.isSelection()) {
                    if (this.golden_state.selectCard(card_image.getCard(), this.golden_state.getTurn())) {
                        card_image.getCard().setSelected(true);
                        Log.i("President", "Selected card action was valid");
                    }
                } else {
                    if (this.golden_state.deselectCard(card_image.getCard(), this.golden_state.getTurn())) {
                        card_image.getCard().setSelected(false);
                        Log.i("President","De-selected card action was valid");
                    }
                }

                sendUpdatedStateTo(player);
            }// select card action

            if (action instanceof AISelectCardAction) {

                AISelectCardAction aisca = (AISelectCardAction) action;

                PlayerInfo player_info = getPlayerData(player.getPlayerNum());

                player_info.getSelectedCards().set(aisca.getCardStack().getCards());
            }// aiselectcard action

            if (action instanceof PlayCardAction) {

                PlayCardAction pca = (PlayCardAction) action;

                PlayerInfo player_info = getPlayerData(player.getPlayerNum());

                if (player_info.getSelectedCards().isEmpty()) {
                    return;
                }

                if (isPlayerTurn(pca.getPlayer())) {
                    CardStack play_pile = this.golden_state.getPlayPile();

                    if (play_pile.getStackSize() == 0) { // If it's the very first turn...
                        playCards(player);
                        return;
                    }

                    // If there's a 2 on the deck (card reset):
                    else if (play_pile.getStackRank() == 15) {
                        playCards(player);
                        Log.i("President","Playing 2 card!");
                        return;
                    }

                    else {
                        CardStack selected_cards = getPlayerData(
                                pca.getPlayer().getPlayerNum()).getSelectedCards();
                        // Rules for playing cards
                        if (selected_cards.getStackRank() == 15) { // Override if it's a 2
                            playCards(player);
                        }
                        if (play_pile.getStackSize() <= selected_cards.getStackSize()) {
                            if (play_pile.getStackRank() < selected_cards.getStackRank()) {
                                playCards(player);
                            }
                        }
                    }
                }
            }// play card action

            if (action instanceof CollapseCardAction) {

                PlayerInfo player_info = getPlayerData(player.getPlayerNum());

                player_info.setCollapse(!player_info.getCollapse());
                Log.i("President","Set collpase to " + player_info.getCollapse());
                sendUpdatedStateTo(player);
            }// collapse card action

            if (action instanceof PassAction) {

                if (isPlayerTurn(action.getPlayer())) {
                    nextTurn();
                    this.golden_state.addPass();
                    sendAllUpdatedState();

                    // If everyone has passed (no one can play), reset the playpile.
                    if (this.golden_state.getPasses() >= NUM_PLAYERS-1) {
                        startNewRound();
                    }
                }
            }// pass action
        }
    }

    /**
     * Adds the player's cards to the PlayPile, updates their deck, and sends out a new updated
     * game state.
     *
     * Also checks if a player's hand is empty. If it is, then the game is over.
     * @param player
     */
    public void playCards(GamePlayer player) {

        Log.i("President","Play cards method called");

        // Remove all of the selected cards from their deck
        PlayerInfo player_info = getPlayerData(player.getPlayerNum());
        this.golden_state.getPlayPile().clear();
        this.golden_state.getPlayPile().add(player_info.getSelectedCards().getCards());
        for (Card c : player_info.getSelectedCards().getCards()) {
            player_info.getDeck().removeCard(
                c
            );
        }

        // If they have no cards left, they've won
        if (player_info.getDeck().getCards().size() == 0) {
            this.golden_state.setGameOver(true);
        } else {
            nextTurn();
        }
        this.golden_state.setPasses(0); // Consecutive passes has been reset since someone played a
                                        // card

        player_info.getSelectedCards().clear();
        sendAllUpdatedState();
    }

    /**
     * Determines whether or not it's a player's turn
     * @param player the player being checked
     * @return TRUE if player's turn; FALSE otherwise
     */
    public boolean isPlayerTurn(GamePlayer player) {

        if (player.getPlayerNum() == this.golden_state.getTurn()) {
            return true;
        }
        return false;
    }

    public GamePlayer getCurrPlayer() {
        return this.players[this.golden_state.getTurn()];
    }

    public PlayerInfo getPlayerData(int idx) {
        return this.golden_state.getPlayerData(idx);
    }

    /**
     * This method is called at the end of a game. It assigns the scores to players based on
     * a some rules:
     *
     * 1: The player with 0 cards gets 3 points.
     * 2: The player with the next lowest amount gets 2 points.
     * 3: The player with the 3rd lowest amount gets 1 point.
     * 4: Everybody else gets 0 points.
     */
    private void assignScores() {
        // Create a list of playerinfo sorted by deck size
        PlayerInfo[] sorted_pinfo = new PlayerInfo[NUM_PLAYERS];

        // selection sort
        for (int i = 0; i < NUM_PLAYERS; i++) {
            int min = getPlayerData(i).getDeck().getCards().size();
            PlayerInfo min_pinfo = getPlayerData(i);
            for (int j = i+1; j < NUM_PLAYERS; j++) {
                PlayerInfo compare = getPlayerData(j);

                if (compare.getDeck().getCards().size() < min) {
                    min = compare.getDeck().getCards().size();
                    min_pinfo = compare;
                }

            }
            sorted_pinfo[i] = min_pinfo;
        }

        // Now that the list is sorted based on card size, add points accordingly
        sorted_pinfo[0].addScore(3);
        sorted_pinfo[1].addScore(2);
        sorted_pinfo[2].addScore(1);
    }

    private void nextTurn() {
        this.golden_state.nextTurn();
        if (getCurrPlayer().supportsGui()) {
            getCurrPlayer().setAsGui(this.activity);
        }
    }

    public void startNewRound() {
        // TODO info msg for player
        this.golden_state.getPlayPile().clear();
        sendAllUpdatedState();
    }

    /**
     * This method is called when the game is over and the player selects a choice from the
     * message box.
     */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // -2 for no, -1 for yes
        if (i == -2) {
            this.activity.recreate();
        } else {
            assignScores();
            start(this.players);
        }
    }
}
