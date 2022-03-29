package presidentasshole.actions;

import presidentasshole.players.Player;

/**
 * @author Max Woods
 *
 * Sent by GameState class to a player. Clears their deck.
 */
public class ClearDeckAction extends GameAction {
    public ClearDeckAction(Player sender) {
        super(sender);
    }
}
