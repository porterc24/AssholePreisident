package com.example.presidentasshole.actions;

import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.actionMsg.GameAction;

public class PlayCardAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PlayCardAction(GamePlayer player) {
        super(player);
    }

}
