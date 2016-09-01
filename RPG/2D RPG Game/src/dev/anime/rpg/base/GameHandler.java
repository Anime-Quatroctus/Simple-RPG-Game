package dev.anime.rpg.base;

import java.awt.Canvas;

import javax.swing.JFrame;

import dev.anime.rpg.base.input.KeyManager;
import dev.anime.rpg.base.input.MouseManager;
import dev.anime.rpg.base.world.WorldCamera;
import dev.anime.rpg.base.world.WorldManager;
import dev.anime.rpg.base.world.Worlds;

public class GameHandler {

	private Game game;

	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public GameHandler(Game game) {
		this.game = game;
		getFrame().addKeyListener(keyManager = new KeyManager());
		getCanvas().addMouseListener(mouseManager = new MouseManager());
		getCanvas().addMouseMotionListener(mouseManager);
		getCanvas().addMouseWheelListener(mouseManager);
		new Worlds(this); // Used to initialize the WORLD_ENTITIES List as a GameHandler field is required.
	}

	public Game getGame() {
		return game;
	}

	public int getScreenWidth() {
		return game.getScreenWidth();
	}

	public int getScreenHeight() {
		return game.getScreenHeight();
	}

	public JFrame getFrame() {
		return game.getFrame();
	}

	public Canvas getCanvas() {
		return game.getCanvas();
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public WorldCamera getWorldCamera() {
		if (WorldManager.getInstance().getCurrentWorld() != null)
			return WorldManager.getInstance().getCurrentWorld().getWorldCamera();
		return null;
	}

}
