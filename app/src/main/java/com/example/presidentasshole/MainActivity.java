package com.example.presidentasshole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.presidentasshole.players.DumbAIPlayer;
import com.example.presidentasshole.players.HumanPlayer;
import com.example.presidentasshole.players.Player;
import java.util.ArrayList;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // THIS IS FOR TESTING
        PresidentGame game = new PresidentGame((RelativeLayout) findViewById(R.id.PlayerCardScrollViewLayout),
                (RelativeLayout) findViewById(R.id.PlayPileLayout));
        ArrayList<Player> players = new ArrayList<>();

        players.add(new HumanPlayer(game));
        players.add(new DumbAIPlayer(game));
        players.add(new DumbAIPlayer(game));
        players.add(new DumbAIPlayer(game));

        PresidentGameState gameState = new PresidentGameState(players,game);
        game.setGameState(gameState);
        gameState.dealCards();
        Log.i("GameStateInfo", gameState.toString());

        HumanPlayer player1 = (HumanPlayer) gameState.getPlayerFromTurn();
        game.renderCards(player1);
        game.renderPlayPile();
        game.setTester(player1);
        game.setTurnText(findViewById(R.id.turn_text));
        game.updateTurnText(2);

        // Button assignment
        Button collapse_button = findViewById(R.id.collapse_button);
        Button play_button = findViewById(R.id.play_button);
        Button pass_button = findViewById(R.id.pass_button);

        collapse_button.setOnClickListener(game);
        play_button.setOnClickListener(game);
        pass_button.setOnClickListener(game);

    }
}