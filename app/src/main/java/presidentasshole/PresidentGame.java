package presidentasshole;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;

import gameframework.players.GamePlayer;
import gameframework.utilities.GameTimer;
import presidentasshole.actions.GameAction;
import presidentasshole.cards.Card;
import presidentasshole.cards.CardImage;
import presidentasshole.cards.CardStack;
import presidentasshole.players.Player;

import java.util.ArrayList;

/**
 * @author Max Woods
 * This game class manages basically everything. It handles the sending of information between
 * GameStates and players using the sendInfo() method.
 *
 */
public class PresidentGame extends gameframework.LocalGame implements View.OnClickListener{

    private ArrayList<Player> players;
    private PresidentGameState game_state;

    private RelativeLayout card_layout; // This is the stack of cards on the current player's hand
    private RelativeLayout play_layout; // This is the stack of cards on the play pile

    private boolean collapse;

    public PresidentGame(RelativeLayout card_view, RelativeLayout play_view) {
        this.card_layout = card_view;
        this.play_layout = play_view;
    }

    /**
     * Re-renders the cards at the bottom of the screen given a Player object.
     * @param player
     */
    public void renderCards(Player player) {
        this.card_layout.removeAllViews();
        // TODO make cards more easily differentiable in collapse mode (perhaps alternating shading)?
        ArrayList<Card> cards = player.getDeck().getCards();

        addCardsToLayout(cards, this.card_layout,0,collapse);
    }

    /**
     * Renders the currently played cards on the playpile.
     */
    public void renderPlayPile() {
        this.play_layout.removeAllViews();

        // Initializing back of card object, incase playpile has 0 cards
        CardStack play_pile = this.game_state.getPlayPile();
        ImageView back_card = new ImageView(this.card_layout.getContext().getApplicationContext());
        back_card.setImageResource(R.drawable.backofcard);

        if (play_pile.isEmpty()) {
            this.play_layout.addView(back_card);
        } else {
            addCardsToLayout(play_pile.getCards(),this.play_layout,100,false);
        }

    }

    /**
     * Adds the given list of cards to the layout.
     * @param cards
     * @param layout
     * @param index the starting ID of the cards added to the layout
     * @param is_collapsed whether or not the cards are displayed on top of each other or fanned
     */
    private void addCardsToLayout(ArrayList<Card> cards, RelativeLayout layout,
                                  int index, boolean is_collapsed) {

        // This is a dumb and lazy patch I made for a card overlay bug
        Space filler = new Space(layout.getContext().getApplicationContext());
        filler.setId(index);
        layout.addView(filler);
        for (int i = 1; i < cards.size()+1; i++) {
            Card c = cards.get(i-1);
            CardImage new_card = new CardImage(
                    this.card_layout.getContext().getApplicationContext(),
                    c,
                    this,
                    i+index,
                    is_collapsed
            );
            layout.addView(new_card);
        }
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

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(gameframework.actionMessage.GameAction action) {
        return false;
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

            //TODO remove this line
            renderPlayPile();
        }
    }

    @Override
    public void tick(GameTimer timer) {

    }
}
