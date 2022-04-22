package com.example.presidentasshole.actions;

import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.actionMsg.GameAction;

public class PassAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PassAction(GamePlayer player) {
        super(player);
    }
}
