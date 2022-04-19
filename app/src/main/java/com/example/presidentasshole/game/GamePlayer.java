package com.example.presidentasshole.game;

import com.example.presidentasshole.game.infoMsg.GameInfo;

/**
 * A player who plays a (generic) game. Each class that implements a player for
 * a particular game should implement this interface.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */

public abstract class GamePlayer {

	protected int playerNum;
	protected String name;
	protected Game game;
	
	// sets this player as the GUI player (implemented as final in the
	// major player classes)
	public abstract void gameSetAsGui(GameMainActivity activity);
	
	// sets this player as the GUI player (overrideable)
	public abstract void setAsGui(GameMainActivity activity);
	
	// sends a message to the player
	public abstract void sendInfo(GameInfo info);
	
	// start the player
	public abstract void start();
	
	// whether this player requires a GUI
	public abstract boolean requiresGui();
	
	// whether this player supports a GUI
	public abstract boolean supportsGui();

	public int getPlayerNum() {
		return this.playerNum;
	}

	public String getName() {
		return name;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
}// interface GamePlayer
