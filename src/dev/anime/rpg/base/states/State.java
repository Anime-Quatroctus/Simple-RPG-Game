package dev.anime.rpg.base.states;

import java.awt.Graphics;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.gfx.ui.UI;

public abstract class State {
	
	protected GameHandler handler;
	
	protected UI currentUI;
	
	public State(GameHandler handler) {
		this.handler = handler;
	}
	
	public abstract void renderState(Graphics g);
	
	public abstract void tickState();
	
	public UI getCurrentUI() {
		return currentUI;
	}
	
	public void setCurrentUI(UI newUI) {
		this.currentUI = newUI;
	}
	
	public void mouseClicked(int button, int mouseX, int mouseY) {}
	
	public void keyPressed(int key) {}
	
}
