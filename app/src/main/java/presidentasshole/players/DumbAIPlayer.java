package presidentasshole.players;

import presidentasshole.PresidentGame;
import presidentasshole.actions.GameAction;
import presidentasshole.actions.PromptAction;

public class DumbAIPlayer extends Player {

    public DumbAIPlayer(PresidentGame game) {
        super(game);
    }

    @Override
    public void receiveInfo(GameAction action) {
        super.receiveInfo(action);

        // TODO AI behavior
        if (action instanceof PromptAction) {

        }
    }
}
