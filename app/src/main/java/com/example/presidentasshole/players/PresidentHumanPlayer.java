package com.example.presidentasshole.players;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.presidentasshole.cards.PlayerHandImage;
import com.example.presidentasshole.PresidentGame;
import com.example.presidentasshole.PresidentGameState;
import com.example.presidentasshole.PresidentMainActivity;
import com.example.presidentasshole.R;
import com.example.presidentasshole.actions.CollapseCardAction;
import com.example.presidentasshole.actions.PassAction;
import com.example.presidentasshole.actions.PlayCardAction;
import com.example.presidentasshole.actions.SelectCardAction;
import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.game.GameHumanPlayer;
import com.example.presidentasshole.game.GameMainActivity;
import com.example.presidentasshole.game.infoMsg.GameInfo;
import com.example.presidentasshole.info.PlayerInfo;
import com.example.presidentasshole.info.UpdateDeckInfo;
import com.example.presidentasshole.info.UpdateGUIInfo;
import com.example.presidentasshole.info.UpdatePlayPileInfo;
import com.example.presidentasshole.util.DoubleTapGestureDetector;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PresidentHumanPlayer extends GameHumanPlayer
        implements View.OnClickListener, View.OnTouchListener {

    private int num_players;

    private RelativeLayout card_layout;
    private RelativeLayout play_layout;

    private TextView[] player_text_views;
    private TextView turn_view;

    private GestureDetector gesture_detector; // Used for when the player double taps a card

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public PresidentHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (info instanceof UpdateDeckInfo) {
            ArrayList<Card> cards = ((UpdateDeckInfo) info).getCards();
            renderCards(cards, ((UpdateDeckInfo) info).getCollapse());
            for (Card c : cards) {
                Log.i("Test",c.toString());
            }
        }

        if (info instanceof UpdatePlayPileInfo) {
            UpdatePlayPileInfo upi = (UpdatePlayPileInfo) info;
            renderPlayPile(upi.getPlayPile());
            Log.i("President","Playpile: " + upi.getPlayPile().toString());
        }

        if (info instanceof UpdateGUIInfo) {
            UpdateGUIInfo p_info = (UpdateGUIInfo) info;
            int turn = p_info.getTurn();
            this.num_players = p_info.getPlayerScores().length;
            PresidentMainActivity activity = p_info.getActivity();

            if (turn == this.getPlayerNum()) {
                this.turn_view.setText("Your turn!");
            } else {
                this.turn_view.setText("Player " + (turn+1) + "'s turn");
            }
            // TODO the way this is setup could have some real big issues for multiplayer
            this.player_text_views = new TextView[num_players];
            this.player_text_views[0] = activity.findViewById(R.id.player1_points_text);
            this.player_text_views[1] = activity.findViewById(R.id.player2_points_text);
            this.player_text_views[2] = activity.findViewById(R.id.player3_points_4p);
            this.player_text_views[3] = activity.findViewById(R.id.player3_points_text);

            //TEST
            for (int i = 0; i < num_players; i++) {

                if (player_text_views[i] != null) {
                    String msg = "Player " + (i+1) + " Points: " +
                            p_info.getPlayerScores()[i].getScore();

                    this.player_text_views[i].setText(msg);
                }
            }

            // TODO fix boiler plate garbage
            // Play card display
            LinearLayout player2_layout = (LinearLayout) activity.findViewById(R.id.player2_layout_p4);
            player2_layout.removeView(player2_layout.getChildAt(1));
            player2_layout.addView(
                    new PlayerHandImage(activity.getApplicationContext(),
                            p_info.getPlayerScores()[1].getGUIDeckSize())
            );

            LinearLayout player3_layout = (LinearLayout) activity.findViewById(R.id.player3_layout_p4);
            player3_layout.removeAllViews();
            player3_layout.addView(
                    new PlayerHandImage(activity.getApplicationContext(),
                            p_info.getPlayerScores()[2].getGUIDeckSize())
            );

            LinearLayout player4_layout = (LinearLayout) activity.findViewById(R.id.player4_layout_p4);
            player4_layout.removeAllViews();
            player4_layout.removeView(player4_layout.getChildAt(1));
            player4_layout.addView(
                    new PlayerHandImage(activity.getApplicationContext(),
                            p_info.getPlayerScores()[3].getGUIDeckSize())
            );

        }// peripheral game info

        if (info instanceof PresidentGameState) {
            if (((PresidentGameState) info).isGameOver()) {
                PresidentGame real_game = (PresidentGame) this.game;
                this.turn_view.setText(real_game.checkIfGameOver());
            }
        }
    }

    // This method is called by the DoubleTapGestureDetector upon double tapping a card
    public void collapse() {
        this.game.sendAction(new CollapseCardAction(this));
    }

    @Override
    public void setAsGui(GameMainActivity activity) {

        activity.setTheme(R.style.Theme_MyApplication);
        activity.setContentView(R.layout.president_asshole);

        this.card_layout = (RelativeLayout) activity.findViewById(R.id.PlayerCardScrollViewLayout);
        this.play_layout = (RelativeLayout) activity.findViewById(R.id.PlayPileLayout);
        this.turn_view = (TextView) activity.findViewById(R.id.turn_text);
        activity.findViewById(R.id.collapse_button).setOnClickListener(this);

        this.gesture_detector = new GestureDetector(
                this.card_layout.getContext().getApplicationContext(),
                new DoubleTapGestureDetector(this)
        );
    }

    private int getResId(String resName) {

        try {
            Field idField = R.id.class.getField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void renderCards(ArrayList<Card> cards, boolean collapse) {
        this.card_layout.removeAllViews();
        addCardsToLayout(cards, this.card_layout, 100, collapse, false);
    }

    public void renderPlayPile(CardStack play_pile) {
        this.play_layout.removeAllViews();

        // Initializing back of card object, incase playpile has 0 cards
        CardImage back_card = new CardImage(this.card_layout.getContext().getApplicationContext(),
                new Card(-99,-99),
                0,
                false,
                true);
        back_card.setOnClickListener(this);

        if (play_pile.isEmpty()) {
            this.play_layout.addView(back_card);
        } else {
            addCardsToLayout(play_pile.getCards(),
                    this.play_layout,
                    200,
                    false,
                    true);
        }

    }

    /**
     * Adds the given list of cards to the layout.
     * @param cards
     * @param layout
     * @param index the starting ID of the cards added to the layout
     * @param is_collapsed whether or not the cards are displayed on top of each other or fanned
     * @param is_play_pile whether or not the card being rendered is inside of the play pile.
     *                     if it is, it gets special onClickListener rules
     */
    private void addCardsToLayout(ArrayList<Card> cards,
                                  RelativeLayout layout,
                                  int index,
                                  boolean is_collapsed,
                                  boolean is_play_pile) {

        // This is a dumb and lazy patch I made for a card overlay bug
        Space filler = new Space(layout.getContext().getApplicationContext());
        filler.setId(index);
        layout.addView(filler);
        for (int i = 1; i < cards.size()+1; i++) {
            Card c = cards.get(i-1);
            CardImage new_card = new CardImage(
                    this.card_layout.getContext().getApplicationContext(),
                    c,
                    i+index,
                    is_collapsed,
                    is_play_pile
            );
            new_card.setOnClickListener(this);
            if (!is_play_pile) {
                new_card.setOnTouchListener(this);
            }

            layout.addView(new_card);
        }
    }

    /**
     * Called whenever a CardImage is selected.
     * @param view
     */
    @Override
    public void onClick(View view) {

        if (view instanceof CardImage) {
            CardImage ci = (CardImage) view;

            /* If the CardImage is inside of the PlayPile, then call Play/Pass depending on
             * the context
             *
             * - If I've selected cards, then play the cards I have
             * - If I haven't selected anything, then pass
             */
            if (ci.isPlayPile()) {
                // Asking the game for my selected cards...
                PlayerInfo my_info = ((PresidentGame) this.game).requestInfo(this);

                if (my_info != null) {
                    if (my_info.getSelectedCards().isEmpty()) {
                        this.game.sendAction(new PassAction(this));
                    } else {
                        this.game.sendAction(new PlayCardAction(this));
                    }
                } else {
                    Log.i("President","requestInfo returned null!");
                }
            } else {
                Log.i("President", "Selected card");

                if (!ci.isSelected()) {
                    this.game.sendAction(new SelectCardAction(
                            this, ci, true
                    ));
                } else {
                    this.game.sendAction(new SelectCardAction(
                            this, ci, false
                    ));
                }
            }
        }

        if (view.getId() == R.id.collapse_button) {
            Log.i("President","Pressed collapse button");
            this.game.sendAction(new CollapseCardAction(this));
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.gesture_detector.onTouchEvent(motionEvent);
        return false;
    }
}
