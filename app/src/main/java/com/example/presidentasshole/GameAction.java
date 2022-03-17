package com.example.presidentasshole;

/**
 * @author Max Woods
 *
 * This is the parent class for all game actions. The only instance variable is the
 * player who is sending the information. It must be initialized when making a new GameAction (or
 * set to null if the action is sent by the GameState).
 *
 * GameActions are sent using the sendInfo method in the PresidentGame class. Every Player object
 * contains a reference to the PresidentGame object, and can call the sendInfo method using that
 * PresidentGame object.
 */
public abstract class GameAction {

    private final HumanPlayer sender;

    public GameAction(HumanPlayer sender) {
        this.sender = sender;
    }

    public HumanPlayer getSender() {
        return sender;
    }
}
