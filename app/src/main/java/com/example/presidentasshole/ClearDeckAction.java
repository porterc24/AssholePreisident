package com.example.presidentasshole;

/**
 * @author Max Woods
 *
 * Sent by GameState class to a player. Clears their deck.
 */
public class ClearDeckAction extends GameAction {
    public ClearDeckAction(HumanPlayer sender) {
        super(sender);
    }
}
