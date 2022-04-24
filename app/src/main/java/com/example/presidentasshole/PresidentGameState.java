package com.example.presidentasshole;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.game.config.GameConfig;
import com.example.presidentasshole.game.infoMsg.GameState;
import com.example.presidentasshole.info.PlayerInfo;
import com.example.presidentasshole.util.TurnCounter;

public class PresidentGameState extends GameState {

    private final int NUM_PLAYERS;

    private int pass_counter;
    private boolean isGameOver;

    private GameConfig config;
    private PlayerInfo[] player_info;
    private TurnCounter turn_counter;
    private CardStack play_pile;

    // default ctor, for JUnit
    public PresidentGameState() {
        this.config = null;
        this.NUM_PLAYERS = 4;
        this.turn_counter = new TurnCounter(NUM_PLAYERS);
        this.pass_counter = 0;
        this.play_pile = new CardStack();
        this.player_info = new PlayerInfo[NUM_PLAYERS];
        this.isGameOver = false;
    }

    public PresidentGameState(GameConfig config) {
        this.config = config;
        this.NUM_PLAYERS = config.getNumPlayers();
        this.turn_counter = new TurnCounter(NUM_PLAYERS);
        this.play_pile = new CardStack();
        this.player_info = new PlayerInfo[NUM_PLAYERS];
        this.isGameOver = false;
    }

    public PresidentGameState(PresidentGameState that) {
        this.config = that.config; // TODO is this ok?
        this.NUM_PLAYERS = that.NUM_PLAYERS;
        this.turn_counter = new TurnCounter(that.turn_counter);
        this.play_pile = new CardStack(that.play_pile);
        this.player_info = new PlayerInfo[NUM_PLAYERS];
        this.pass_counter = that.pass_counter;
        this.isGameOver = that.isGameOver;

        for (int i = 0; i < NUM_PLAYERS; i++) {
            this.player_info[i] = new PlayerInfo(that.player_info[i]);
        }
    }

    // Use this for when you only want one player to be kept.
    public PresidentGameState(PresidentGameState that, int idx) {
        this.config = that.config; // TODO this should be ok?
        this.NUM_PLAYERS = that.NUM_PLAYERS;
        this.turn_counter = new TurnCounter(that.turn_counter);
        this.play_pile = new CardStack(that.play_pile);
        this.player_info = new PlayerInfo[NUM_PLAYERS];
        this.pass_counter = that.pass_counter;
        this.isGameOver = that.isGameOver;

        this.player_info[idx] = that.player_info[idx];
    }

    public void registerPlayer(String name, int id) {

        if (id > player_info.length - 1 || id < 0) {
            return;
        }
        this.player_info[id] = new PlayerInfo(name, id);
    }

    public void registerPlayer(String name, int id, int score) {
        this.player_info[id] = new PlayerInfo(name, id);
        this.player_info[id].setScore(score);
    }

    public PlayerInfo getPlayerData(int idx) {
        return this.player_info[idx];
    }

    public PlayerInfo[] getPlayerData() {
        return this.player_info;
    }

    public int getTurn() {
        return this.turn_counter.getTurn();
    }

    public boolean selectCard(Card card, int id) {
        return this.player_info[id].selectCard(card);
    }

    public boolean deselectCard(Card card, int id) {
        return this.player_info[id].deselectCard(card);
    }

    public void nextTurn() {
        this.turn_counter.nextTurn();
    }

    public CardStack getPlayPile() {
        return play_pile;
    }

    public int getPasses() {
        return pass_counter;
    }

    public void addPass() {
        this.pass_counter++;
    }

    public void setPasses(int pass_counter) {
        this.pass_counter = pass_counter;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

}
