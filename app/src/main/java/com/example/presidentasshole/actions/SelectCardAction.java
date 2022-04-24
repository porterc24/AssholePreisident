package com.example.presidentasshole.actions;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardImage;
import com.example.presidentasshole.game.GamePlayer;
import com.example.presidentasshole.game.actionMsg.GameAction;

public class SelectCardAction extends GameAction {

    private CardImage card;
    private boolean select;

    public SelectCardAction(GamePlayer player, CardImage card, boolean select) {
        super(player);
        this.card = card;
        this.select = select;
    }

    public CardImage getCardImage() {
        return card;
    }

    public boolean isSelection() {
        return this.select;
    }

    public Card getCard() {
        return this.card.getCard();
    }
}
