package dev.anime.rpg.base.gfx.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.characters.BattleObjectStats.StatConstants;
import dev.anime.rpg.base.characters.Character;
import dev.anime.rpg.base.characters.Characters;
import dev.anime.rpg.base.characters.Party;
import dev.anime.rpg.base.data.SaveManager;
import dev.anime.rpg.base.gfx.RenderUtils;
import dev.anime.rpg.base.input.KeyManager;
import dev.anime.rpg.base.input.KeyManager.KeyConstants;
import dev.anime.rpg.base.states.Button;
import dev.anime.rpg.base.states.StateManager;
import dev.anime.rpg.base.world.WorldManager;


public class UIInGameMenu extends UI {

	// TODO: Finish main menu, add Character stats screen, add items/inventory screen, add equipment/equipment screen, add keybinds screen, add options screen, add Skills screen.
	
	private int ticks, index = -1;
	
	private final int lineDisplacement = (handler.getScreenHeight()) / 5;
	
	private boolean displayCharacterStats = false;
	
	private Character currentStatCharacter;
	
	public UIInGameMenu(GameHandler handler) {
		super(handler);
		createButtons();
	}
	
	@Override
	protected void createButtons() {
		this.addButton(0, new Button("Items", new Rectangle(handler.getScreenWidth() - 125, 30, 100, 30), true));
		this.addButton(1, new Button("Skills", new Rectangle(handler.getScreenWidth() - 125, 90, 100, 30), true));
		this.addButton(2, new Button("Spells", new Rectangle(handler.getScreenWidth() - 125, 150, 100, 30), true));
		this.addButton(3, new Button("Options", new Rectangle(handler.getScreenWidth() - 125, 210, 100, 30), true));
		this.addButton(4, new Button("Save", new Rectangle(handler.getScreenWidth() - 125, 270, 100, 30), true));
		this.addButton(5, new Button("Exit", new Rectangle(handler.getScreenWidth() - 125, 330, 100, 30), true));
		
		this.addButton(6, new Button(new Rectangle(12, 12, handler.getScreenWidth() - 172, lineDisplacement - 12)));
	}
	
	@Override
	public void applyButtonEffect(int ID) {
		switch (ID) {
		case 0: {
			// TODO Inventory
		}
			break;
		case 1: {
			// TODO Skills and skill viewer.
		}
			break;
		case 2: {
			//TODO Spells and spell viewer.
		}
			break;
		case 3: {
			StateManager.getInstance().getCurrentState().setCurrentUI(new UIOptions(handler, this));
		}
			break;
		case 4: {
			SaveManager.saveSaveables();
		}
			break;
		case 5: {
			StateManager.getInstance().getCurrentState().setCurrentUI(null);
		}
			break;
		case 6: {
			System.out.println("Pressed big button.");
			displayCharacterStats = true;
			currentStatCharacter = Characters.MAIN_PROTANGONIST;
		}
			break;
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
			Characters.MAIN_PROTANGONIST.getStats().setHealth(Characters.MAIN_PROTANGONIST.getStats().getCurrentStat(0));
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
		
		Color backgroundColor = new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 180);
		RenderUtils.drawRoundedBoxWithBorder(g, backgroundColor, Color.LIGHT_GRAY, 0, 0, handler.getScreenWidth() - 160, handler.getScreenHeight(), 12); //Character Data part
		RenderUtils.drawRoundedBoxWithBorder(g, backgroundColor, Color.LIGHT_GRAY, handler.getScreenWidth() - 160, 0, 160, 384, 12); // Actual menu part
		RenderUtils.drawRoundedBoxWithBorder(g, backgroundColor, Color.LIGHT_GRAY, handler.getScreenWidth() - 160, 384, 160, handler.getScreenHeight() - 384, 12); // Time and gold part.
		
		int lineDisplacement = (handler.getScreenHeight()) / 5;
		
		renderCharacterOnMainScreen(g, WorldManager.getInstance().getCurrentWorld().getManager().getPlayer().getParty(), lineDisplacement);
		
		if (displayCharacterStats)
			renderCharacterInformation(g, currentStatCharacter); // For later use
		
		
		g.setColor(Color.LIGHT_GRAY.darker().darker());
		Graphics2D TDG = (Graphics2D) g;
		TDG.setStroke(new BasicStroke(6));
		TDG.drawLine(0, lineDisplacement, handler.getScreenWidth() - 160, lineDisplacement);
		TDG.drawLine(0, lineDisplacement * 2, handler.getScreenWidth() - 160, lineDisplacement * 2);
		TDG.drawLine(0, lineDisplacement * 3, handler.getScreenWidth() - 160, lineDisplacement * 3);
		TDG.drawLine(0, lineDisplacement * 4, handler.getScreenWidth() - 160, lineDisplacement * 4);
		
		for (Button button : buttons.values()) {
			button.renderButton(g, new Color(0, 0, 0, 0), Color.WHITE, new Font("arial", 0, 19), handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 5);
		}
		
	}
	
	private void renderCharacterOnMainScreen(Graphics g, Party party, int displacment) {
		for (int partyPos = 0; partyPos < Party.PARTY_LENGTH; partyPos++) {
			Character currentCharacter = party.getCharacter(partyPos);
			if (currentCharacter != null) {
				//TODO Add facial avatar.
				g.setColor(Color.LIGHT_GRAY);
				g.setFont(new Font("arial", 0, 18));
				String name = currentCharacter.getCharacterName();
				int stringStart = 70 + (g.getFontMetrics().stringWidth(name) / 2);
				int stringStop = stringStart + g.getFontMetrics().stringWidth(name) + 8;
				int stringHeight = (g.getFontMetrics().getHeight() / 2) - g.getFontMetrics().getAscent();
				g.drawString(name, stringStart, 52 + displacment * (partyPos) - stringHeight);
				renderHorizonalScaledBar(g, "" + currentCharacter.getStats().getHealth(), currentCharacter.getStats().getCurrentStat(StatConstants.MAX_HEALTH), currentCharacter.getStats().getHealth(), null, stringStop, 45 + displacment * (partyPos), 75, 15);
				renderHorizonalScaledBar(g, "" + currentCharacter.getStats().getPSI(), currentCharacter.getStats().getCurrentStat(StatConstants.MAX_PSI), currentCharacter.getStats().getPSI(), new Color(157, 53, 255), stringStop + 80, 45 + displacment * (partyPos), 75, 15);
			}
		}
	}
	
	// TODO
	private void renderCharacterInformation(Graphics g, Character character) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 0, 20));
		g.drawString("Character: " + character.getCharacterName(), 50, 50);
		g.drawString("Level: " + character.getStats().getLevel(), 50, 70);
		g.drawString("Max Health: " + character.getStats().getCurrentStat(0) + " Health: " + character.getStats().getHealth(), 50, 90);
		g.drawString("Max PSI: " + character.getStats().getCurrentStat(1), 50, 110);
		g.drawString("Strength: " + character.getStats().getCurrentStat(2), 50, 130);
		g.drawString("Magic Potency: " + character.getStats().getCurrentStat(3), 50, 150);
		g.drawString("Physical Defense: " + character.getStats().getCurrentStat(4), 50, 170);
		g.drawString("Magic Defense: " + character.getStats().getCurrentStat(5), 50, 190);
		g.drawString("Speed: " + character.getStats().getCurrentStat(6), 50, 210);
		g.drawString("XP: " + character.getStats().getXP() + " Total XP: " + character.getStats().getTotalXP() + " Required XP: " + character.getStats().getRequiredXP(), 50, 230);
	}
	
	private void renderHorizonalScaledBar(Graphics g, String name, int max, int current, Color barColor, int x, int y, int width, int height) {
		double ratio = 0;
		int barWidth = 0;
		if (max > 0 && current > 0) {
			ratio = (((double) current) / ((double) max));
			barWidth = (int) (ratio * width);
		} else {
			barWidth = width;
			ratio = 1;
		}
		if (barColor == null) {
			if (ratio > (double) 1/2) barColor = Color.GREEN;
			if (ratio <= (double) 1/2 && ratio > (double) 1/3) barColor = Color.YELLOW;
			if (barColor == null) barColor = Color.RED;
		}
		RenderUtils.addRoundBackGroundRect(g, barColor, x, y, barWidth, height);
		RenderUtils.drawRoundedBorder(g, Color.WHITE, x, y, width, height, 2);
		g.setFont(new Font("arial", 0, 12));
		g.setColor(Color.BLACK);
		g.drawString(name, x + (width - g.getFontMetrics().stringWidth(name)) / 2, y + g.getFontMetrics().getHeight() / 2 + g.getFontMetrics().getAscent() / 2);
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
