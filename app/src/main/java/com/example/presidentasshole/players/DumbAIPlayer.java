package com.example.presidentasshole.players;

import static java.lang.Thread.sleep;

import android.os.Handler;

import com.example.presidentasshole.PresidentGame;
import com.example.presidentasshole.actions.GameAction;
import com.example.presidentasshole.actions.PromptAction;
import com.example.presidentasshole.cards.Card;
import com.example.presidentasshole.cards.CardStack;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * This guy simply picks the lowest value card he can on his turn. For more information about
 * the logic, see the decide() method.
 *
 * The LocalGame sends a PromptAction to a Player at the beginning of their turn, to let them know
 * that it's their turn to go. The Dumb AI has an artificial wait that occurs before it makes a
 * decision about what card to play.
 */
public class DumbAIPlayer extends Player {

    public DumbAIPlayer(PresidentGame game) {
        super(game);
    }

    @Override
    public void receiveInfo(GameAction action) {
        super.receiveInfo(action);

        if (action instanceof PromptAction) {

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
                    decide();
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
    private void decide() {

        int min_stack = this.game.getGameState().getPlayPile().getStackSize();

        if (min_stack == 0) {
            selectCard(pickRandCard());
            playCards();
        }

        if (min_stack == 1) {
            Card min_card = pickMinCard();

            if (min_card == null) {
                pass();
                return;
            }
            selectCard(min_card);
        }

        if (min_stack > 1) {
            this.selectedCards.clear();
            CardStack best_stack = pickMinCardStack();
            if (!best_stack.isEmpty()) {
                this.selectedCards.add(best_stack.getCards());
            }
        }

        if (!playCards()) {
            pass();
        }

    }

    /**
     * Picks a random card out of the deck. Used when there are no cards on the playpile.
     * @return a random card
     */
    private Card pickRandCard() {

        if (this.deck.getCards().size() == 1) {
            return this.deck.getCards().get(0);
        } else if (this.deck.getCards().size() == 0) {
            return null;
        }

        Random rand = new Random();
        int rand_card = rand.nextInt(this.deck.getCards().size()-1);
        for (int i = 0; i < this.deck.getCards().size(); i++) {
            if (i == rand_card) {
                return this.deck.getCards().get(i);
            }
        }
        return this.deck.getCards().get(0);
    }

    /**
     * Picks the smallest value card that can be played.
     * @return smallest card value
     */
    private Card pickMinCard() {

        if (this.deck.getCards().size() == 1) {
            return this.deck.getCards().get(0);
        } else if (this.deck.getCards().size() == 0) {
            return null;
        }

        int min_rank = this.game.getGameState().getPlayPile().getStackRank();

        if (min_rank == 15) {
            min_rank = 3;
        }
        int min_flag = 100;

        Card min_card = null;

        // Looking for smallest value of card
        for (int i = 0; i < this.deck.getCards().size(); i++) {
            Card card = this.deck.getCards().get(i);

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
    private CardStack pickMinCardStack() {

        if (this.deck.getCards().size() == 0) {
            return null;
        }

        ArrayList<CardStack> stacks = new ArrayList<>();
        int min_rank = this.game.getGameState().getPlayPile().getStackRank();
        if (min_rank == 15) {
            min_rank = 3;
        }
        int min_stack = this.game.getGameState().getPlayPile().getStackSize();
        int searching = 0;

        // Searching for potential CardStacks
        for (int i = 0; i < this.deck.getCards().size(); i++) {
            searching = this.deck.getCards().get(i).getRank();
            CardStack stack = new CardStack();

            // Looking for cards of same rank and adding them to a card stack
            for (int j = i+1; j < this.deck.getCards().size(); j++) {
                Card searching_card = this.deck.getCards().get(j);

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

    @Override
    public void selectCard(Card card) {
        this.selectedCards.add(card);
    }
}
