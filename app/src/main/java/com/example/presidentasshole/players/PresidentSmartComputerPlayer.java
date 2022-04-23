package com.example.presidentasshole.players;

import android.os.Handler;
import android.util.Log;

import com.example.presidentasshole.PresidentGameState;
import com.example.presidentasshole.actions.AISelectCardAction;
import com.example.presidentasshole.actions.PassAction;
import com.example.presidentasshole.actions.PlayCardAction;
import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;
import com.example.presidentasshole.cards.CardValues;
import com.example.presidentasshole.cards.Deck;
import com.example.presidentasshole.game.GameComputerPlayer;
import com.example.presidentasshole.game.infoMsg.GameInfo;

import java.util.ArrayList;
import java.util.Random;

public class PresidentSmartComputerPlayer extends GameComputerPlayer {

    private CardStack selected_cards; // Used to keep track of cards that have been selected
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PresidentSmartComputerPlayer(String name) {
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
            time = 1; // TODO change this

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

    /**
     * Dumb AI logic for deciding which card to play at their turn. It does this by
     * simply picking the lowest value card/stack of cards it can possibly play.
     *
     * If it can't play any of those cards, it tries to play a 2.
     *
     * If none of the options work, it passes.
     *
     * Uses helper methods pickRandCard, pickMinCard, pickMinCardStack
     */
    private void decide(PresidentGameState game_state) {

        int min_stack = game_state.getPlayPile().getStackSize();

        if (min_stack == 0) {
            Card rand_card = pickRandCard(game_state.getPlayerData(this.playerNum).getDeck());
            if (rand_card != null) {
                selectCard(rand_card);
                playCards();
            }
        }

        if (min_stack == 1) {
            Card min_card = pickMinCard(game_state);

            if (min_card == null) {
                pass();
                return;
            }
            selectCard(min_card);
        }

        if (min_stack > 1) {
            this.selected_cards.clear();
            CardStack best_stack = pickMinCardStack(game_state);
            if (best_stack != null) {
                this.selected_cards.add(best_stack.getCards());
            }
        }

        playCards();
        // Last resort methods. If it can't find a better card, try to play a 2.
        playTwoCard(game_state.getPlayerData(this.playerNum).getDeck());
        // If no other moves worked, just pass anyway
        pass();

    }

    private void playTwoCard(Deck deck) {

        Card twocard = null;
        for (Card c : deck.getCards()) {
            if (c.getRank() == CardValues.TWO.value) {
                twocard = c;
                Log.i("President","Found 2 for player " + this.playerNum);
            }
        }

        if (twocard != null) {
            this.selected_cards.clear();
            this.selected_cards.add(twocard);
        }

        playCards();
    }

    /**
     * Picks a random card out of the deck. Used when there are no cards on the playpile.
     * @return a random card
     */
    private Card pickRandCard(Deck deck) {

        if (deck.getCards().size() == 1) {
            return deck.getCards().get(0);
        } else if (deck.getCards().size() == 0) {
            return null;
        }

        Random rand = new Random();
        int rand_card = rand.nextInt(deck.getCards().size()-1);
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (i == rand_card) {
                return deck.getCards().get(i);
            }
        }
        return deck.getCards().get(0);
    }

    /**
     * Picks the smallest value card that can be played.
     * @return smallest card value
     */
    private Card pickMinCard(PresidentGameState game_state) {

        Deck deck = game_state.getPlayerData(this.getPlayerNum()).getDeck();
        if (deck.getCards().size() == 1) {
            return deck.getCards().get(0);
        } else if (deck.getCards().size() == 0) {
            return null;
        }

        int min_rank = game_state.getPlayPile().getStackRank();

        if (min_rank == 15) {
            min_rank = 3;
        }
        int min_flag = 100;

        Card min_card = null;

        // Looking for smallest value of card
        for (int i = 0; i < deck.getCards().size(); i++) {
            Card card = deck.getCards().get(i);

            if (card.getRank() > min_rank && card.getRank() < min_flag) {
                min_flag = card.getRank();
                min_card = card;
            }
        }
        return min_card;
    }

    /**
     * Picks the smallest rank of a stack of cards > 1 that can be played.
     * It does this by first finding all of the cards of the same rank in the deck and putting
     * them in card stacks. The card stacks are then all compared to find the minimum.
     * @return minimum stack of cards that can be played
     */
    private CardStack pickMinCardStack(PresidentGameState game_state) {

        Deck deck = game_state.getPlayerData(this.getPlayerNum()).getDeck();
        if (deck.getCards().size() == 0) {
            return null;
        }

        ArrayList<CardStack> stacks = new ArrayList<>();
        int min_rank = game_state.getPlayPile().getStackRank();
        if (min_rank == 15) {
            min_rank = 3;
        }
        int min_stack = game_state.getPlayPile().getStackSize();
        int searching = 0;

        // Searching for potential CardStacks
        for (int i = 0; i < deck.getCards().size(); i++) {
            searching = deck.getCards().get(i).getRank();
            CardStack stack = new CardStack();

            // Looking for cards of same rank and adding them to a card stack
            for (int j = i+1; j < deck.getCards().size(); j++) {
                Card searching_card = deck.getCards().get(j);

                if (searching_card.getRank() == searching) {
                    stack.add(searching_card);
                }
            }
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }

        // Now check which stack has the best rank and size to play
        if (!stacks.isEmpty()) {
            CardStack best_stack = stacks.get(0);
            for (int i = 0; i < stacks.size(); i++) {
                CardStack stack = stacks.get(i);
                if (stack.getStackRank() < best_stack.getStackRank()
                        && stack.getStackRank() > min_rank
                        && stack.getStackSize() >= min_stack) {
                    best_stack = stack;
                }
            }
            return best_stack;
        }
        return null;
    }

    public void selectCard(Card card) {
        this.selected_cards.add(card);
    }

    public void playCards() {
        this.game.sendAction(new AISelectCardAction(this,this.selected_cards));
        this.game.sendAction(new PlayCardAction(this));
    }

    public void pass() {
        this.game.sendAction(new PassAction(this));
    }
}
