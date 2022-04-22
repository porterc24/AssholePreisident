package com.example.presidentasshole.info;

import com.example.presidentasshole.PresidentMainActivity;
import com.example.presidentasshole.game.infoMsg.GameInfo;

public class UpdatePeripheralInfo extends GameInfo {

    private int turn; // The current turn of the game
    private PresidentMainActivity activity;
    private PlayerInfo[] player_scores;
    // TODO other player's scores

    public UpdatePeripheralInfo(int turn, PlayerInfo[] player_scores,
                                PresidentMainActivity activity) {
        this.turn = turn;
        this.player_scores = player_scores;
        this.activity = activity;
    }

    public int getTurn() {
        return this.turn;
    }

    public PresidentMainActivity getActivity() {
        return activity;
    }

    public PlayerInfo[] getPlayerScores() {
        return player_scores;
    }
}
