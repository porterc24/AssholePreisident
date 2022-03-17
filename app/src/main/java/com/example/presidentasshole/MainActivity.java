package com.example.presidentasshole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // THIS IS FOR TESTING
        PresidentGame game = new PresidentGame();
        ArrayList<Player> players = new ArrayList<Player>();

        players.add(new HumanPlayer(game));
        players.add(new HumanPlayer(game));

        PresidentGameState gameState = new PresidentGameState(players,game);
        Log.i("d", gameState.toString());

    }
}