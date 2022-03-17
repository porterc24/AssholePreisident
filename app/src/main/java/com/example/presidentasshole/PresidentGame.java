package com.example.presidentasshole;

import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.presidentasshole.actions.GameAction;
import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.players.Player;

import java.util.ArrayList;

/**
 * @author Max Woods
 * This game class manages basically everything. It handles the sending of information between
 * GameStates and players using the sendInfo() method.
 *
 */
public class PresidentGame {

    private ArrayList<Player> players;
    private PresidentGameState game_state;

    private LinearLayout card_view; // This is the stack of cards on the GUI

    public PresidentGame(LinearLayout card_view) {
        this.card_view = card_view;
    }

    /**
     * Re-renders the cards at the bottom of the screen given a Player object.
     * @param player
     * @param collapse whether or not the cards are rendered collapsed or expanded
     */
    public void renderCards(Player player, boolean collapse) {
        this.card_view.removeAllViews();
        //TODO make cards more easily differentiable in collapse mode (perhaps alternating shading)?
        player.getDeck().getCards().forEach(c -> {
            this.card_view.addView(new CardImage(
                    this.card_view.getContext().getApplicationContext(),
                    c,
                    this,
                    collapse
            ));
        });

        this.card_view.invalidate();
    }

    // This is for proj E testing
    private EditText editText;

    // For sending GameState info to players:
    public void sendInfo(GameAction action, Player player) {
        player.receiveInfo(action);
    }

    // For sending GameAction info to GameState:
    public boolean sendInfo(GameAction action) {
        return this.game_state.receiveInfo(action);
    }

    // For printing debug to the screen (used for ProjE)
    public void print(String msg) {
        Log.i("Game",msg);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
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
