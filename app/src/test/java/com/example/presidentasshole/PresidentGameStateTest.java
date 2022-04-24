package com.example.presidentasshole;

import static org.junit.Assert.*;
import com.example.presidentasshole.cards.Card;
import org.junit.Test;

public class PresidentGameStateTest {

    @Test
    public void registerPlayer() {
        PresidentGameState state = new PresidentGameState();
        state.registerPlayer("P1", 0);
        state.registerPlayer("P2", 1);
        assertNotSame(state.getPlayerData(0), state.getPlayerData(1));
    }

    @Test
    public void getPlayerData() {
        PresidentGameState state = new PresidentGameState();
        state.registerPlayer("P1", 0);
        state.registerPlayer("P2", 1);
        assertNotSame(state.getPlayerData(0), state.getPlayerData(1));
    }

    @Test
    public void getTurn() {
        PresidentGameState state = new PresidentGameState();
        int turn = state.getTurn();
        assertEquals(turn, 0);
    }

    @Test
    public void selectCard() {
        PresidentGameState state = new PresidentGameState();
        state.registerPlayer("P1", 0);
        state.registerPlayer("P2", 1);
        boolean b = state.selectCard(new Card(1, 3), 0);
        assertEquals(b, true);
    }

    @Test
    public void deselectCard() {
        PresidentGameState state = new PresidentGameState();
        state.registerPlayer("P1", 0);
        state.registerPlayer("P2", 1);
        Card test = new Card(1, 3);
        state.selectCard(test, 0);
        boolean b = state.deselectCard(test, 0);
        assertEquals(b, true);
    }

    @Test
    public void nextTurn() {
        PresidentGameState state = new PresidentGameState();
        state.nextTurn();
        int test = state.getTurn();
        assertEquals(test, 1);
    }

    @Test
    public void getPlayPile() {
        PresidentGameState state = new PresidentGameState();
        state.registerPlayer("P1", 0);
        state.registerPlayer("P2", 1);
        Card test = new Card(1, 3);
        state.getPlayPile().add(test);
        assertEquals(state.getPlayPile().getCard(0), test);
    }

    @Test
    public void getPasses() {
        PresidentGameState state = new PresidentGameState();
        assertEquals(state.getPasses(), 0);
    }

    @Test
    public void addPass() {
        PresidentGameState state = new PresidentGameState();
        state.addPass();
        assertEquals(state.getPasses(), 1);
    }

    @Test
    public void setPasses() {
        PresidentGameState state = new PresidentGameState();
        state.setPasses(2);
        assertEquals(state.getPasses(), 2);
    }

    @Test
    public void isGameOver() {
        PresidentGameState state = new PresidentGameState();
        boolean test = state.isGameOver();
        assertEquals(test, false);
    }

    @Test
    public void setGameOver() {
        PresidentGameState state = new PresidentGameState();
        state.setGameOver(true);
        assertEquals(state.isGameOver(), true);
    }
}