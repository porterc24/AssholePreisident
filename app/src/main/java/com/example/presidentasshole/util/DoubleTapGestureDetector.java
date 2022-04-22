package com.example.presidentasshole.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.players.PresidentHumanPlayer;

public class DoubleTapGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private PresidentHumanPlayer player;

    public DoubleTapGestureDetector(PresidentHumanPlayer player) {
        this.player = player;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.player.collapse();
        return super.onDoubleTap(e);
    }
}
