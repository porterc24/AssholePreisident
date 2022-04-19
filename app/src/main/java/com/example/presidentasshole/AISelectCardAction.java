package com.example.presidentasshole;

import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.actionMsg.GameAction;

public class AISelectCardAction extends GameAction {

    private CardStack cards;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public AISelectCardAction(GamePlayer player, CardStack cards) {
        super(player);
        this.cards = cards;
    }

    public CardStack getCardStack() {
        return cards;
    }
}
