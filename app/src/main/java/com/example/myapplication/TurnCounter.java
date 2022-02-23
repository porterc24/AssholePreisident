package com.example.myapplication;

/**
 * This class keeps track of what turn it is. When the turn counter is greater than
 * the number of players, it resets back to 1.
 */
public class TurnCounter {
    int turn;
    int max_players;

    public TurnCounter(int max_players) {
        this.max_players = max_players;
        this.turn = 1;
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

}
