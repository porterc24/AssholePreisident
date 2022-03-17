package com.example.presidentasshole.cards;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

/**
 * @author Max Woods
 *
 * Visual representation of a card object on the screen.
 */
public class CardImage extends androidx.appcompat.widget.AppCompatImageButton {

    public CardImage(@NonNull Context context, int res) {
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
        // TODO might wanna replace this with display pixels, also there's gotta be a better way
        params.setMargins(-180,5,-180,5);
        params.gravity = 3;

        this.setLayoutParams(params);
        this.setImageResource(res);
        this.setWillNotDraw(false);
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
