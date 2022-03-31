package com.example.presidentasshole.players;

import static java.lang.Thread.sleep;

import android.util.Log;

import com.example.presidentasshole.PresidentGame;
import com.example.presidentasshole.actions.GameAction;
import com.example.presidentasshole.actions.PromptAction;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DumbAIPlayer extends Player {

    public DumbAIPlayer(PresidentGame game) {
        super(game);
    }

    @Override
    public void receiveInfo(GameAction action) {
        super.receiveInfo(action);

        // TODO AI behavior
        if (action instanceof PromptAction) {

            // TODO fix timer breaking shit

            Random rand = new Random();
            long time = 500 + rand.nextInt(500);

            try {
                timer_thread.sleep(time);
            } catch (InterruptedException e) {
                // dont care
            }

            timer_thread.run();
        }
    }
}
