package dev.anime.rpg;

import dev.anime.rpg.base.Game;

/** Just creates a new Game instance and starts it's thread. **/
public class GameLauncher {
	
	public static void main(String[] args) {
		Game game = new Game("Simple RPG.", 672, 480);
		game.start();
	}

}
