package com.example.presidentasshole;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * @author Max Woods
 * This game class manages basically everything. It handles the sending of information between
 * GameStates and players using the sendInfo() method.
 *
 */
public class PresidentGame {

    private ArrayList<HumanPlayer> players;
    private PresidentGameState game_state;

    // This is for proj E testing
    private EditText editText;


    // For sending GameState info to players:
    public void sendInfo(GameAction action, HumanPlayer player) {
        player.receiveInfo(action);
    }

    // For sending GameAction info to GameState:
    public boolean sendInfo(GameAction action) {
        return this.game_state.receiveInfo(action);
    }

    // For printing debug to the screen (used for ProjE)
    public void print(String msg) {
        this.editText.setText(this.editText.getText() + "\n" + msg);
    }

    public ArrayList<HumanPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<HumanPlayer> players) {
        this.players = players;
    }

    public PresidentGameState getGameState() {
        return game_state;
    }

    public void setGameState(PresidentGameState game_state) {
        this.game_state = game_state;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public CharSequence getEditText() {
        return this.editText.getText();
    }
}
