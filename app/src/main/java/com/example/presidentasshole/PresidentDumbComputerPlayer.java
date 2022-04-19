package com.example.presidentasshole;

import android.os.Handler;

import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.cards.Deck;
import com.example.presidentasshole.game.GameComputerPlayer;
import com.example.presidentasshole.game.infoMsg.GameInfo;

import java.util.Random;

public class PresidentDumbComputerPlayer extends GameComputerPlayer {

    private CardStack selected_cards; // Used to keep track of cards that have been selected
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PresidentDumbComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {

        if (info instanceof PresidentGameState) {

            PresidentGameState game_state = (PresidentGameState) info;

            if (game_state.isGameOver()) {
                return;
            }

            this.selected_cards = new CardStack();

            Random rand = new Random();
            long time = 500 + rand.nextInt(500);

            /**
             * External Citation
             *   Date: 31 March 2022
             *   Problem: Thread.sleep() freezes the GUI
             *
             *   Resource:
             *     https://stackoverflow.com/questions/1520887/
             *     how-to-pause-sleep-thread-or-process-in-android
             *   Solution: I used the example code from this post.
             */
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    decide((PresidentGameState) info);
                }
            }, time);
        }
    }

    private void decide(PresidentGameState info) {

        Deck deck = info.getPlayerData(this.getPlayerNum()).getDeck();
        for (int i = 0; i < deck.getCards().size(); i++) {
            playCards(deck.getCards().get(i));
        }
        this.game.sendAction(new PassAction(this));

    }

    private void playCards(Card card) {
        this.selected_cards.set(card);
        this.game.sendAction(new AISelectCardAction(this, this.selected_cards));
        this.game.sendAction(new PlayCardAction(this));

    }
}
