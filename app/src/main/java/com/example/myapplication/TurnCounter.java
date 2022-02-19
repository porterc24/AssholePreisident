package com.example.myapplication;

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
