package com.example.presidentasshole;

import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.actionMsg.GameAction;

public class CollapseCardAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CollapseCardAction(GamePlayer player) {
        super(player);
    }
}
