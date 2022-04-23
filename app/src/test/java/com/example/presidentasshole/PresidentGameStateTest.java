package com.example.presidentasshole;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.Deck;
import com.example.presidentasshole.game.config.GameConfig;
import com.example.presidentasshole.info.PlayerInfo;
import com.example.presidentasshole.util.TurnCounter;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PresidentGameStateTest extends TestCase {

    private PresidentGameState test_state;

    /** Setups game config and game state */
    @Before
    public void setUp() throws Exception {
        super.setUp();

        PresidentMainActivity pma = new PresidentMainActivity();
        GameConfig config = pma.createDefaultConfig();
        PresidentGame game = new PresidentGame(config, pma);

        this.test_state = new PresidentGameState(config);
    }

    /** Testing that the turn counter properly loops back around */
    @Test
    public void testTurnCounter() {

        TurnCounter sentinel = new TurnCounter(4);
        sentinel.nextTurn();
        for (int i = 0; i < 50; i++) {
            this.test_state.nextTurn();

            assertEquals(this.test_state.getTurn(), sentinel.getTurn());
            sentinel.nextTurn();
        }
    }

    /** Testing that the copy constructor copies the correct values */
    @Test
    public void testCopyCtor() {

        // First, test that names are copied succesfully:
        this.test_state.registerPlayer("Bob",0);
        this.test_state.registerPlayer("Billy",1);
        this.test_state.registerPlayer("Foo",2);
        this.test_state.registerPlayer("Bar",3);

        dealCards(); // Ensuring that all players have cards

        PresidentGameState copy = new PresidentGameState(this.test_state);

        for (int i = 0; i < 4; i++) {
            assertEquals(copy.getPlayerData(i).getName(),
                    this.test_state.getPlayerData(i).getName()
            );
        }

        // Now make sure that all players have the correct cards
        for (int i = 0; i < 4; i++) {

            ArrayList<Card> orig_cards = this.test_state.getPlayerData(i).getDeck().getCards();
            ArrayList<Card> copy_cards = copy.getPlayerData(i).getDeck().getCards();

            for (int j = 0; j < orig_cards.size(); j++) {
                assertTrue(orig_cards.get(j).cardEquals(copy_cards.get(j)));
            }
        }


    }

    /** Testing that registerPlayer properly registers new players, and that extra
     * players cannot be added beyond the limit */
    @Test
    public void testRegisterPlayer() {

        assertNull(this.test_state.getPlayerData(0));
        assertNull(this.test_state.getPlayerData(1));
        assertNull(this.test_state.getPlayerData(2));
        assertNull(this.test_state.getPlayerData(3));

        this.test_state.registerPlayer("Bob",0);
        this.test_state.registerPlayer("Billy",1);
        this.test_state.registerPlayer("Foo",2);
        this.test_state.registerPlayer("Bar",3);

        assertNotNull(this.test_state.getPlayerData(0));
        assertNotNull(this.test_state.getPlayerData(1));
        assertNotNull(this.test_state.getPlayerData(2));
        assertNotNull(this.test_state.getPlayerData(3));

        this.test_state.registerPlayer("Error",4);
        this.test_state.registerPlayer("Error",-4);
        this.test_state.registerPlayer("Overwrite",2);

        assertEquals(this.test_state.getPlayerData(2).getName(),"Overwrite");
    }

    /** Tests that the correct information is retrieved from each player */
    @Test
    public void testGetPlayerData() {

        this.test_state.registerPlayer("Dylan",2,-42);

        assertEquals(-42,this.test_state.getPlayerData(2).getScore(),-42);
        assertNull(this.test_state.getPlayerData(0));
    }

    // Helper method for assigning cards to every PlayerInfo object in the game state.
    // Identical to method in PresidentGame.dealCards()
    private void dealCards() {
        Deck masterDeck = new Deck();
        masterDeck.generateMasterDeck();


        for (int i = 0; i < 4; i++) {

            PlayerInfo player_data = this.test_state.getPlayerData(i);
            player_data.getDeck().getCards().clear();
            for (int j = 0; j < (52 / 4); j++) {
                //selects a random card of the 52 in masterDeck
                Card randomCard = (masterDeck.getCards().get((int) Math.random() * masterDeck.MAX_CARDS));


                player_data.addCard(randomCard);
                masterDeck.getCards().remove(randomCard);
            }
        }
    }
}