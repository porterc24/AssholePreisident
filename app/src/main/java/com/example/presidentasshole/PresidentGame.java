package com.example.presidentasshole;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.example.presidentasshole.actions.GameAction;
import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.players.Player;

import java.util.ArrayList;

/**
 * @author Max Woods
 * This game class manages basically everything. It handles the sending of information between
 * GameStates and players using the sendInfo() method.
 *
 */
public class PresidentGame implements View.OnClickListener {

    private ArrayList<Player> players;
    private PresidentGameState game_state;

    private RelativeLayout card_view; // This is the stack of cards on the GUI

    private boolean collapse;

    public PresidentGame(RelativeLayout card_view) {
        this.card_view = card_view;
    }

    /**
     * Re-renders the cards at the bottom of the screen given a Player object.
     * @param player
     */
    public void renderCards(Player player) {
        this.card_view.removeAllViews();
        // TODO make cards more easily differentiable in collapse mode (perhaps alternating shading)?
        // Filler is my shitty way of preventing the first two cards from being on top of each other
        Space filler = new Space(this.card_view.getContext().getApplicationContext());
        filler.setId(0);
        ArrayList<Card> cards = player.getDeck().getCards();
        for (int i = 1; i < cards.size(); i++) {
            Card c = cards.get(i);
            CardImage new_card = new CardImage(
                    this.card_view.getContext().getApplicationContext(),
                    c,
                    this,
                    i,
                    this.collapse
            );

            this.card_view.addView(new_card);
        }

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

    @Override
    public void onClick(View view) {

        // If collapse has been pressed:
        if (view.getId() == R.id.collapse_button) {
            Button button = (Button) view;
            this.collapse = !collapse;
            renderCards(game_state.getPlayerFromTurn());

            // Changing text to show appropriate action
            if (this.collapse) {
                button.setText("Expand");
            } else {
                button.setText("Collapse");
            }
        }
    }
}
