package dev.anime.rpg.base.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.gfx.RenderUtils;

public class MenuState extends State {
	
	private final Button[] menuButtons = new Button[]{new Button("Start", (handler.getScreenWidth() - 60) / 2, (handler.getScreenHeight() - 50) / 2, 60, 25, true), new Button("Options", (handler.getScreenWidth() - 75) / 2, (handler.getScreenHeight() + 35) / 2, 75, 25, true), new Button("Quit Game", (handler.getScreenWidth() - 80) / 2, (handler.getScreenHeight() + 117) / 2, 80, 25, true)};
	
	public MenuState(GameHandler handler) {
		super(handler);
	}
	
	@Override
	public void renderState(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, handler.getScreenWidth(), handler.getScreenHeight());
		RenderUtils.drawRoundedBoxWithBorder(g, Color.BLACK, Color.WHITE, (handler.getScreenWidth() - 212) / 2, 62, 212, 50, 6);
		g.setColor(Color.WHITE);
		RenderUtils.drawCenteredString(g, handler, "The RPG that changed the world.", 90, true, false);
		for (Button button : menuButtons) {
			button.renderButton(g, Color.BLACK, Color.WHITE, new Font("arial",  0,  10), handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 4);
		}
	}

	@Override
	public void tickState() {}

	@Override
	public void mouseClicked(int mouseButton, int mouseX, int mouseY) {
		if (mouseButton == 1) {
			for (int i = 0; i < menuButtons.length; i++) {
				Button button = menuButtons[i];
				if (Utils.isWithin(button.getPosition(), mouseX, mouseY)) applyButtonEffect(i);
			}
		}
	}
	
	private void applyButtonEffect(int ID) {
		switch (ID) {
		case 0: StateManager.getInstance().setCurrentState(StateManager.gameState);
			break;
		case 1: //TODO Make Options
			break;
		case 2: System.exit(0);
			break;
		}
	}
	
}
