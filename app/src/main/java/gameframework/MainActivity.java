package gameframework;

import gameframework.gameConfiguration.GameConfig;
import gameframework.infoMessage.GameState;


public class MainActivity extends gameframework.GameMainActivity {


    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return null;
    }
}
