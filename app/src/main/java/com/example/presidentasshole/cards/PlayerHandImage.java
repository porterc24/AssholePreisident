package com.example.presidentasshole.cards;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.presidentasshole.R;

public class PlayerHandImage extends AppCompatImageView {

    public PlayerHandImage(Context context) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        params.weight = 2f;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        this.setImageResource(R.drawable.backofcard);
        this.setAdjustViewBounds(false);
        this.setLayoutParams(params);
        this.setRotation(90f);
    }


}
