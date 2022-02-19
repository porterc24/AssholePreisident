package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // THIS IS FOR TESTING
        ArrayList<HumanPlayer> players = new ArrayList<HumanPlayer>();
        players.add(new HumanPlayer());
        players.add(new HumanPlayer());

        PresidentGameState gameState = new PresidentGameState(players);

    }
}