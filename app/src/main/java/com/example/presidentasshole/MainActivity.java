package com.example.presidentasshole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.players.HumanPlayer;
import com.example.presidentasshole.players.Player;
import java.util.ArrayList;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * TODO Idea: Add toggle 'collapse' button for cards which shows them evenly stacked on each other.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // THIS IS FOR TESTING
        PresidentGame game = new PresidentGame();
        ArrayList<Player> players = new ArrayList<>();

        players.add(new HumanPlayer(game));
        players.add(new HumanPlayer(game));

        PresidentGameState gameState = new PresidentGameState(players,game);
        gameState.dealCards();
        Log.i("GameStateInfo", gameState.toString());

        LinearLayout pcsvl = findViewById(R.id.PlayerCardScrollViewLayout);
        pcsvl.removeAllViews();

        Card card = new Card(14,3);
        HumanPlayer player1 = (HumanPlayer) gameState.getPlayerFromTurn();

        player1.getDeck().getCards().forEach(c -> {
            pcsvl.addView(new CardImage(
                    getApplicationContext(),
                    c.toResourceID()
            ));
        });

        pcsvl.invalidate();

    }
}