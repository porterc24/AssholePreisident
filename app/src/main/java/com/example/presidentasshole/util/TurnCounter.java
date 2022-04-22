package com.example.presidentasshole.util;

/**
 * @author Max Woods
 * This class keeps track of what turn it is. When the turn counter is greater than
 * the number of players, it resets back to 0.
 */
public class TurnCounter {
    private int turn;
    private int max_players;

    public TurnCounter(int max_players) {
        this.max_players = max_players;
        this.turn = 0;
    }

    // Copy ctor for TurnCounter
    public TurnCounter(TurnCounter orig) {
        this.max_players = orig.max_players;
        this.turn = orig.turn;
    }

    public void nextTurn() {
        this.turn++;
        if (this.turn > max_players-1) {
            this.turn = 0;
        }
    }

    public int getPrevTurn() {
        int prev_turn = this.turn - 1;
        if (prev_turn < 0) {
            prev_turn = 0;
        }
        return prev_turn;
    }

    public void reset() {
        this.turn = 0;
    }

    public int getTurn() {
        return this.turn;
    }

    @Override
    public String toString() {
        return "" + this.turn;
    }
}
