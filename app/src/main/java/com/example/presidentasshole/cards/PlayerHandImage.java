package com.example.presidentasshole.cards;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.presidentasshole.R;

import java.lang.reflect.Field;

/**
 * Used to render other player's hands. Used in the PresidentHumanPlayer class.
 */
public class PlayerHandImage extends AppCompatImageView {

    public PlayerHandImage(Context context, int num_cards) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        params.weight = 2f;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        this.setAdjustViewBounds(false);
        this.setLayoutParams(params);

        if (num_cards != 0) {
            this.setImageResource(getResId("backofcard_" + num_cards));
        }
    }

    private int getResId(String resName) {

        try {
            Field idField = R.drawable.class.getField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
