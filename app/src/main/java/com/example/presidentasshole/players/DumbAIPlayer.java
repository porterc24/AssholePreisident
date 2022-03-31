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

public class DumbAIPlayer extends Player {

    public DumbAIPlayer(PresidentGame game) {
        super(game);
    }

    @Override
    public void receiveInfo(GameAction action) {
        super.receiveInfo(action);

        // TODO AI behavior
        if (action instanceof PromptAction) {

            Random rand = new Random();
            long time = 500 + rand.nextInt(500);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    decide();
                }
            }, time);

        }
    }

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
        if (!playCards() || min_stack > 1) {
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

    private Card pickRandCard() {

        Random rand = new Random();
        int rand_card = rand.nextInt(this.deck.getCards().size()-1);
        for (int i = 0; i < this.deck.getCards().size(); i++) {
            if (i == rand_card) {
                return this.deck.getCards().get(i);
            }
        }
        return this.deck.getCards().get(0);
    }

    private Card pickMinCard() {

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

    private ArrayList<Card> pickSimilarCards(int rank) {
        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i < this.deck.getCards().size(); i++) {
            Card card = this.deck.getCards().get(i);

            if (card.getRank() == rank) {
                cards.add(card);
            }
        }
        return cards;
    }

    private CardStack pickMinCardStack() {

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
        CardStack best_stack = stacks.get(0);
        for (int i = 0; i < stacks.size(); i++) {
            CardStack stack = stacks.get(i);
            if (stack.getStackRank() < best_stack.getStackRank()
            &&  stack.getStackRank() > min_rank
            &&  stack.getStackSize() >= min_stack) {
                best_stack = stack;
            }
        }
        return best_stack;
    }

    @Override
    public void selectCard(Card card) {
        this.selectedCards.add(card);
    }
}
