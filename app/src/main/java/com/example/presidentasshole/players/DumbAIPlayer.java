package com.example.presidentasshole.players;

import com.example.presidentasshole.PresidentGame;
import com.example.presidentasshole.actions.GameAction;
import com.example.presidentasshole.actions.PromptAction;

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
