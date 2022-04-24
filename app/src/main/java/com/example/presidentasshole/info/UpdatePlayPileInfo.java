package com.example.presidentasshole.info;

import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.game.infoMsg.GameInfo;

public class UpdatePlayPileInfo extends GameInfo {

    private CardStack play_pile;

    public UpdatePlayPileInfo(CardStack play_pile) {
        this.play_pile = play_pile;
    }

    public CardStack getPlayPile() {
        return play_pile;
    }
}
