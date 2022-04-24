package com.example.presidentasshole;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.presidentasshole.game.GameMainActivity;
import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.LocalGame;
import com.example.presidentasshole.game.config.GameConfig;
import com.example.presidentasshole.game.config.GamePlayerType;
import com.example.presidentasshole.players.PresidentDumbComputerPlayer;
import com.example.presidentasshole.players.PresidentHumanPlayer;
import com.example.presidentasshole.players.PresidentSmartComputerPlayer;

import java.util.ArrayList;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * This is the main for the PresidentAsshole game. It initializes all of the configuarion files, the
 * Game, and the players.
 */
public class PresidentMainActivity extends GameMainActivity {

    @Override
    public GameConfig createDefaultConfig() {
        ArrayList<GamePlayerType> avail_types = new ArrayList<>();
        avail_types.add(new GamePlayerType("Human Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PresidentHumanPlayer(name);
            }
        });

        avail_types.add(new GamePlayerType("Smart Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PresidentSmartComputerPlayer(name);
            }
        });

        avail_types.add(new GamePlayerType("Dumb Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PresidentDumbComputerPlayer(name);
            }
        });


        GameConfig config = new GameConfig(
           avail_types,
           4,
           4,
           "President (Asshole)",
           69
        );

        config.addPlayer("Bob",0);
        config.addPlayer("Computer #1",1);
        config.addPlayer("Computer #2",1);
        config.addPlayer("Computer #3",1);

        return config;
    }

    @Override
    public LocalGame createLocalGame() {
        return new PresidentGame(this.config, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_main, menu);
        return true;
    }
}