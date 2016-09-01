package dev.anime.rpg.base.gfx.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.characters.Characters;
import dev.anime.rpg.base.gfx.RenderUtils;
import dev.anime.rpg.base.input.KeyManager;
import dev.anime.rpg.base.input.KeyManager.KeyConstants;
import dev.anime.rpg.base.states.Button;
import dev.anime.rpg.base.states.StateManager;

// TODO: Complete UI for ingame use.
// TODO: Finish main menu, add Character stats screen, add items/inventory screen, add equipment/equipment screen, add keybinds screen, add options screen, add Skills screen.
public class UIInGameMenu extends UI {

	private int ticks;
	
	private int index = -1;
	
	public UIInGameMenu(GameHandler handler) {
		super(handler);
	}
	
	@Override
	protected void createButtons() {
		this.addButton(0, new Button("Exit", new Rectangle(handler.getScreenWidth() - 125, 265, 100, 26), true));
		this.addButton(1, new Button("Test", new Rectangle(handler.getScreenWidth() - 125, 40, 100, 26), true));
	}
	
	@Override
	public void applyButtonEffect(int ID) {
		switch (ID) {
		case 0: {
			// TODO Add saving effect.
			StateManager.getInstance().getCurrentState().setCurrentUI(null);
		}
		default:
			break;
		}
	}
	
	@Override
	public void keyPressed(int key) {
		if (KeyManager.PRESSED_KEYBINDS[KeyConstants.UP]) {
			if (index >= 0) buttons.get(index).setKeyed(false);
			index--;
			if (index < 0) index = buttons.size() - 1;
			buttons.get(index).setKeyed(true);
		}
		if (KeyManager.PRESSED_KEYBINDS[KeyConstants.DOWN]) {
			if (index >= 0) buttons.get(index).setKeyed(false);
			index++;
			if (index >= buttons.size()) index = 0;
			buttons.get(index).setKeyed(true);
		}
		if (KeyManager.PRESSED_KEYBINDS[KeyConstants.ACTIVATE] && index >= 0) applyButtonEffect(index); 
	}
	
	@Override
	public void tickUI() {
		ticks++;
		if (ticks >= 30) {
			Characters.MAIN_PROTANGONIST.levelUP();
			ticks = 0;
		}
		boolean isMouseOverButton = false;
		for (Button button : buttons.values()) {
			if (Utils.isWithin(button.getPosition(), handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
				isMouseOverButton = true;
				break;
			}
		}
		
		if (isMouseOverButton) {
			for (Button button : buttons.values()) button.setKeyed(false);
		}
		
	}

	@Override
	public void renderUI(Graphics g) {
//		Graphics2D TDG = (Graphics2D) g;
//		g.setColor(new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 180));
//		g.fillRect(0, 0, handler.getScreenWidth(), handler.getScreenHeight());
		
		RenderUtils.drawRoundedBoxWithBorder(g, new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 180), Color.WHITE, handler.getScreenWidth() - 160, -1, 160, 320, 12);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 0, 20));
		g.drawString("Character: " + Characters.MAIN_PROTANGONIST.getCharacterName(), 50, 50);
		g.drawString("Level: " + Characters.MAIN_PROTANGONIST.getStats().getLevel(), 50, 70);
		g.drawString("Max Health: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(0), 50, 90);
		g.drawString("Max PSI: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(1), 50, 110);
		g.drawString("Strength: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(2), 50, 130);
		g.drawString("Magic Potency: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(3), 50, 150);
		g.drawString("Physical Defense: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(4), 50, 170);
		g.drawString("Magic Defense: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(5), 50, 190);
		g.drawString("Speed: " + Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(6), 50, 210);
		g.drawString("XP: " + Characters.MAIN_PROTANGONIST.getStats().getXP() + " Total XP: " + Characters.MAIN_PROTANGONIST.getStats().getTotalXP() + " Required XP: " + Characters.MAIN_PROTANGONIST.getStats().getRequiredXP(), 50, 230);
		Double[] chances = Characters.MAIN_PROTANGONIST.getStats().getChances();
		RenderUtils.drawWrappedString(g, "Chances: " + chances[0] + ", " + chances[1] + ", " + chances[2] + ", " + chances[3] + ", " + chances[4] + ", " + chances[5] + ", " + chances[6], 50, 250, 560);
		
//		TDG.setStroke(new BasicStroke(24));
//		TDG.drawRect(0, 0, handler.getScreenWidth(), handler.getScreenHeight());
//		TDG.setColor(Color.GRAY);
//		TDG.setStroke(new BasicStroke(6));
//		TDG.drawRoundRect(3, 3, handler.getScreenWidth() - 6, handler.getScreenHeight() - 6, 1, 1);
//		TDG.setColor(Color.DARK_GRAY);
//		TDG.setStroke(new BasicStroke(2));
//		TDG.drawRoundRect(2, 2, handler.getScreenWidth() - 4, handler.getScreenHeight() - 4, 1, 1);
		
		for (Button button : buttons.values()) {
			button.renderButton(g, new Color(0, 0, 0, 0), Color.WHITE, new Font("arial", 0, 19), handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 4);
		}
		
	}

	@Override
	public boolean pausesEntities() {
		return true;
	}

	@Override
	public boolean pausesState() {
		return false;
	}

}
