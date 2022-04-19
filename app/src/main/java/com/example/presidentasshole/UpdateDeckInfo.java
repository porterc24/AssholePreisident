package com.example.presidentasshole;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.cards.Deck;
import com.example.presidentasshole.game.infoMsg.GameInfo;

import java.util.ArrayList;

public class UpdateDeckInfo extends GameInfo {

    private ArrayList<Card> cards;
    private boolean collapse;

    public UpdateDeckInfo(PlayerInfo playerData, boolean collapse) {
        super();
        this.cards = playerData.getDeck().getCards();
        this.collapse = collapse;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }
    public boolean getCollapse() {
        return this.collapse;
    }
}
