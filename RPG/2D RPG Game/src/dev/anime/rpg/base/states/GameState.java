package dev.anime.rpg.base.states;

import java.awt.Graphics;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.gfx.ui.UIInGameMenu;
import dev.anime.rpg.base.input.KeyManager;
import dev.anime.rpg.base.input.KeyManager.KeyConstants;
import dev.anime.rpg.base.world.WorldManager;
import dev.anime.rpg.base.world.Worlds;

public class GameState extends State {

	public GameState(GameHandler handler) {
		super(handler);
		WorldManager.getInstance().setCurrentWorld(Worlds.getWorld(handler, "/map2"));
	}

	@Override
	public void renderState(Graphics g) {
		WorldManager.getInstance().getCurrentWorld().renderWorld(g);
		if (getCurrentUI() != null) getCurrentUI().renderUI(g);
	}

	@Override
	public void tickState() {
		if (KeyManager.PRESSED_KEYBINDS[KeyConstants.ACTIVATE] == true && StateManager.getInstance().getCurrentState().getCurrentUI() == null) {
			this.setCurrentUI(new UIInGameMenu(handler));
		}
		WorldManager.getInstance().getCurrentWorld().tickWorld();
		if (getCurrentUI() != null) getCurrentUI().tickUI();
	}
	
	@Override
	public void mouseClicked(int button, int mouseX, int mouseY) {
		if (getCurrentUI() != null) getCurrentUI().mouseClicked(button, mouseX, mouseY);
	}
	
	@Override
	public void keyPressed(int key) {
		if (getCurrentUI() != null) getCurrentUI().keyPressed(key);
	}
	
}
