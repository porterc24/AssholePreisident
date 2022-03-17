package com.example.presidentasshole;

import androidx.annotation.NonNull;

/**
 * @author Max Woods
 * This class keeps track of what turn it is. When the turn counter is greater than
 * the number of players, it resets back to 1.
 *
 * IMPORTANT: Turn counter starts at 1!!!
 */
public class TurnCounter {
    int turn;
    int max_players;

    public TurnCounter(int max_players) {
        this.max_players = max_players;
        this.turn = 1;
    }

    // Copy ctor for TurnCounter
    public TurnCounter(TurnCounter orig) {
        this.max_players = orig.max_players;
        this.turn = orig.turn;
    }

    public void nextTurn() {
        this.turn++;
        if (this.turn > max_players) {
            this.turn = 1;
        }
    }

    public void reset() {
        this.turn = 1;
    }

    public int getTurn() {
        return this.turn;
    }

    @Override
    public String toString() {
        return "" + this.turn;
    }
}
