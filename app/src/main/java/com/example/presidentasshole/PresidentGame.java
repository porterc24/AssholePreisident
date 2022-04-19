package com.example.presidentasshole;

import android.content.DialogInterface;
import android.os.Message;
import android.util.Log;
import android.view.View;

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

    @Override
    public void start(GamePlayer[] players) {
        super.start(players);

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

    // Assumes that the players array has been initialized
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
        p.sendInfo(new UpdatePeripheralInfo(
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

    @Override
    protected String checkIfGameOver() {

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

    @Override
    protected void receiveMessage(Message msg) {
        super.receiveMessage(msg);

        if (msg.obj instanceof GameAction) {

            GameAction action = (GameAction) msg.obj;
            GamePlayer player = action.getPlayer();

            if (action instanceof SelectCardAction) {

                if (this.golden_state.isGameOver()) {
                    return;
                }

                SelectCardAction sca = (SelectCardAction) action;
                CardImage card = sca.getCardImage();

                if (sca.isSelection()) {
                    if (this.golden_state.selectCard(card.getCard(), this.golden_state.getTurn())) {
                        card.setSelected(true);
                        Log.i("President", "Selected card action was valid");
                    }
                } else {
                    if (this.golden_state.deselectCard(card.getCard(), this.golden_state.getTurn())) {
                        card.setSelected(false);
                        Log.i("President","De-selected card action was valid");
                    }
                }
            }// select card action

            if (action instanceof AISelectCardAction) {

                AISelectCardAction aisca = (AISelectCardAction) action;

                PlayerInfo player_info = getPlayerData(player.getPlayerNum());

                player_info.getSelectedCards().set(aisca.getCardStack().getCards());
            }// aiselectcard action

            if (action instanceof PlayCardAction) {

                if (this.golden_state.isGameOver()) {
                    return;
                }

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

                if (this.golden_state.isGameOver()) {
                    return;
                }

                PlayerInfo player_info = getPlayerData(player.getPlayerNum());

                player_info.setCollapse(!player_info.getCollapse());
                Log.i("President","Set collpase to " + player_info.getCollapse());
                sendUpdatedStateTo(player);
            }// collapse card action

            if (action instanceof PassAction) {

                if (this.golden_state.isGameOver()) {
                    return;
                }

                if (isPlayerTurn(action.getPlayer())) {
                    nextTurn();
                    this.golden_state.addPass();
                    sendAllUpdatedState();

                    // If everyone has passed (no one can play), reset the playpile.
                    if (this.golden_state.getPasses() >= NUM_PLAYERS) {
                        startNewRound();
                    }
                }
            }// pass action
        }
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

    // TODO fix this
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

        sorted_pinfo[0].addScore(3);
        sorted_pinfo[1].addScore(2);
        sorted_pinfo[2].addScore(1);
    }

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

    //TODO send copy of config
    public GameConfig getConfig() {
        return this.config;
    }
}
