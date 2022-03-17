package com.example.presidentasshole.cards;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.presidentasshole.PresidentGame;

/**
 * @author Max Woods
 *
 * Visual representation of a card object on the screen.
 */
public class CardImage extends androidx.appcompat.widget.AppCompatImageButton implements View.OnClickListener {

    private Card card_model;
    private PresidentGame game;

    public CardImage(@NonNull Context context, Card card_model, PresidentGame game, boolean collapse) {
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        // TODO might wanna replace this with display pixels
        // TODO make card collapse actually work
        params.setMargins(5,5,5,5);
        params.gravity = 3;
        this.card_model = card_model;
        this.game = game;

        this.setAdjustViewBounds(true);
        this.setLayoutParams(params);
        this.setImageResource(this.card_model.toResourceID());
        this.setWillNotDraw(false);
        this.setOnClickListener(this);

        if (this.card_model.isSelected()) {
            this.setAlpha(0.5f);
        }
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

    @Override
    public void onClick(View view) {
        Log.i("CardClick",card_model.toString());
        this.game.getGameState().getPlayerFromTurn().selectCard(this.card_model);
    }
}
