package com.example.presidentasshole.cards;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.presidentasshole.PresidentGameDeprecated;
import com.example.presidentasshole.R;

/**
 * @author Max Woods
 *
 * Visual representation of a card object on the screen.
 */
public class CardImage extends androidx.appcompat.widget.AppCompatImageButton {

    private Card card_model;
    private boolean selected;

    public CardImage(@NonNull Context context, Card card_model, int id, boolean collapse) {
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
        this.setId(id);
        this.selected = false;

        int margins = 5;

        // This && check is to prevent the card from going out of bounds
        // TODO fix this
        if (collapse && id > 1) {
            margins = -90;
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
        this.setImageResource(this.card_model.toResourceID());
        this.setWillNotDraw(false);

        if (this.card_model.isSelected()) {
            this.setAlpha(0.5f);
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            this.setAlpha(0.5f);
            this.selected = true;
        } else {
            this.setAlpha(1.0f);
            this.selected = false;
        }
    }

    public Card getCard() {
        return this.card_model;
    }

    public boolean isSelected() {
        return this.selected;
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
