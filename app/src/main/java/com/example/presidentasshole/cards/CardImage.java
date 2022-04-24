package com.example.presidentasshole.cards;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.presidentasshole.R;
import com.example.presidentasshole.players.PresidentHumanPlayer;

/**
 * @author Max Woods
 *
 * Visual representation of a card object on the screen. This is used in the player's hand, as
 * well as the PlayPile. It functions as an image button. The HumanPlayer functions as its
 * onClickListener
 *
 * @see
 *  PresidentHumanPlayer
 */
public class CardImage extends androidx.appcompat.widget.AppCompatImageButton {

    private Card card_model;
    private boolean is_play_pile;

    public CardImage(@NonNull Context context,
                     Card card_model,
                     int id,
                     boolean collapse,
                     boolean is_play_pile) {
        super(context);
        /**
         * External Citation
         *  Date: 16 March 2022
         *  Problem: Couldn't figure out how to adjust the attributes
         *  of a custom view
         *
         * Resource:
         *      https://stackoverflow.com/questions/12728255/
         *      in-android-how-do-i-set-margins-in-dp-programmatically
         *
         * Solution: I used the LayoutParams class in the post
         */
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        // TODO might wanna replace this with display pixels
        this.card_model = card_model;
        this.is_play_pile = is_play_pile;
        this.setId(id);
        int margins = 5;

        // This && check is to prevent the card from going out of bounds
        // TODO fix this
        if (collapse && id > 1) {
            margins = -130;
        }

        // TODO Fix bug where the first two cards are on top of each other
        // The ID is used for aligning cards to be next to each other in the RelativeLayout
        if ((id - 1) >= 0) {
            params.addRule(RelativeLayout.RIGHT_OF, (id - 1));
            Log.i("Debug","" + (id - 1));
        } else {
            params.addRule(RelativeLayout.RIGHT_OF, R.id.PlayerCardScrollViewLayout);
        }
        params.setMargins(5,5,margins,5);

        this.setAdjustViewBounds(true);
        this.setLayoutParams(params);
        this.setWillNotDraw(false);

        // -99 means this is a back-facing card
        if (this.card_model.getRank() == -99) {
            this.setImageResource(R.drawable.backofcard);
        } else {
            this.setImageResource(this.card_model.toResourceID());
        }

        if (isSelected()) {
            this.setAlpha(0.5f);
        }
    }

    public Card getCard() {
        return this.card_model;
    }

    public boolean isSelected() {
        return this.card_model.isSelected();
    }

    public boolean isPlayPile() {
        return this.is_play_pile;
    }

    public void delete() {
        LinearLayout parent = (LinearLayout) this.getParent();
        parent.removeView(this);
        parent.invalidate();
    }

    public void setImage(int res) {
        this.setImageResource(res);
        this.invalidate();
    }
}
