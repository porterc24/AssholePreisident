package presidentasshole;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;

import gameframework.players.GamePlayer;
import gameframework.utilities.GameTimer;
import presidentasshole.actions.GameAction;
import presidentasshole.cards.Card;
import presidentasshole.cards.CardImage;
import presidentasshole.cards.CardStack;
import presidentasshole.players.Player;

import java.util.ArrayList;

/**
 * @author Claire Porter
 * This game class manages basically everything. It handles the sending of information between
 * GameStates and players using the sendInfo() method.
 *
 */
public class PresidentLocalGame extends gameframework.LocalGame {

    /**
     * Basic Constructor
     */
    public PresidentLocalGame(){
        super();
        super.state = new PresidentGameState(4);

   }



    /**
     * Contructor from a Gamestate object
     * @param gamestate
     */
   public PresidentLocalGame(PresidentGameState gamestate){
       super();
       super.state = new PresidentGameState(gamestate);
   }




    /**
     * Sends a GameInfo message to a player, a copy of the gameState
     * @param p
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        PresidentGameState gameCopy = new PresidentGameState((PresidentGameState) state);
        p.sendInfo(gameCopy);
    }


    //checks if it is a players turn so they can play actions
    @Override
    protected boolean canMove(int playerIdx) {
        if ((((PresidentGameState) state).getPlayerTurn()) == playerIdx) return true;
        return false;
    }

    //takes an action object to then make the corresponding play
    @Override
    protected boolean makeMove(gameframework.actionMessage.GameAction action) {
        return false;
    }

    //checks if the game is over, if so, prints the winning player.
    @Override
    protected String checkIfGameOver() {
        for (int i = 0; i < players.length; i++) {
            if (((PresidentGameState) state).gameOver() != -1) {
                return "Player " + ((PresidentGameState) state).gameOver() + " wins!";
            }
        }
        return null;
    }
}
