package presidentasshole.actions;

import presidentasshole.players.Player;

/**
 * @author Max Woods
 *
 * This should be used whenever a player passes. Used in the pass() method of the Player class.
 */
public class PassAction extends GameAction {

    public PassAction(Player sender) {
        super(sender);
        sender.getGame().print("Player " + sender.getId() + " attempted to pass");
    }

}
