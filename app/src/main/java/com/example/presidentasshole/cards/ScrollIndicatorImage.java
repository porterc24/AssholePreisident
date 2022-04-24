package com.example.presidentasshole.cards;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.presidentasshole.R;

public class ScrollIndicatorImage extends androidx.appcompat.widget.AppCompatImageButton {

    private int direction;

    public ScrollIndicatorImage(@NonNull Context context, int direction) {
        super(context);
        this.direction = direction;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );



        if (direction == 1) {
            this.setImageResource(R.drawable.left_scroll_indicator);
            params.gravity = Gravity.RIGHT;
        } else {
            this.setImageResource(R.drawable.right_scroll_indicator);
            params.gravity = Gravity.LEFT;
        }
        this.setScaleX(0.35f);
        this.setScaleY(0.35f);
        this.setAdjustViewBounds(false);
        this.setLayoutParams(params);
        this.setAlpha(0f);
    }

    public int getDirection() {
        return direction;
    }
}
