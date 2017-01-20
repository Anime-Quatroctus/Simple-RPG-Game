package dev.anime.rpg.base.gfx.ui;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.states.Button;

//TODO Make text boxes.
public abstract class UI {
	
	protected GameHandler handler;
	
	protected Map<Integer, Button> buttons = new HashMap<Integer, Button>();
	
	public UI(GameHandler handler) {
		this.handler = handler;
		createButtons();
	}
	
	/** Used to take in player input. Or update variables. **/
	public abstract void tickUI();
	
	/** Used to render text boxes or other things. **/
	public abstract void renderUI(Graphics g);
	
	/** Whether entities tick when displayed. **/
	public abstract boolean pausesEntities();
	
	/** Whether the state ticks when displayed. **/
	public abstract boolean pausesState();
	
	/** Override if your state has buttons. **/
	protected void createButtons() {}
	
	/**
	 * Use this to have the button do something when pressed. For cleaner code use a switch statement.
	 * @param ID The position of the button in the Map.
	 */
	public void applyButtonEffect(int ID) {}
	
	/** Do not override. **/
	protected void addButton(int ID, Button button) {
		buttons.put(ID, button);
	}

	public void mouseClicked(int mouseButton, int mouseX, int mouseY) {
		for (int i = 0; i < buttons.size(); i++) {
			if (Utils.isWithin(buttons.get(i).getPosition(), mouseX, mouseY)) applyButtonEffect(i);
		}
	}
	
	public void keyPressed(int key) {}
	
}
