package com.example.presidentasshole;

import com.example.presidentasshole.game.GameMainActivity;
import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.LocalGame;
import com.example.presidentasshole.game.config.GameConfig;
import com.example.presidentasshole.game.config.GamePlayerType;

import java.util.ArrayList;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * Beware of bugs! This is a pretty sloppily made version of the game. The game framework
 * implementation leaves a lot to be desired (many features of it are in the wrong place/missing).
 * These will certainly be fixed by the next project due-date.
 *
 * For now, however, it works :^).
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
           8,
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
}