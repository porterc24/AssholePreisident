package presidentasshole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import presidentasshole.cards.Card;
import presidentasshole.cards.CardImage;
import presidentasshole.players.HumanPlayer;
import presidentasshole.players.Player;
import java.util.ArrayList;
import presidentasshole.R;

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
        PresidentGame game = new PresidentGame((RelativeLayout) findViewById(R.id.PlayerCardScrollViewLayout));
        ArrayList<Player> players = new ArrayList<>();

        players.add(new HumanPlayer(game));
        players.add(new HumanPlayer(game));

        PresidentGameState gameState = new PresidentGameState(players,game);
        game.setGameState(gameState);
        gameState.dealCards();
        Log.i("GameStateInfo", gameState.toString());

        HumanPlayer player1 = (HumanPlayer) gameState.getPlayerFromTurn();
        game.renderCards(player1);

        // Button assignment
        Button collapse_button = findViewById(R.id.collapse_button);
        collapse_button.setOnClickListener(game);

    }
}