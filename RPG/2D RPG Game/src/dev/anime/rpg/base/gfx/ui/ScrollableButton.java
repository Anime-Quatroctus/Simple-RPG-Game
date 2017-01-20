package dev.anime.rpg.base.gfx.ui;

import java.awt.Rectangle;

import dev.anime.rpg.base.states.Button;

public class ScrollableButton extends Button {
	
	private int current, max;
	
	public ScrollableButton(String displayString, int xStart, int yStart, int width, int height, boolean isRounded, int current, int max) {
		super(displayString, xStart, yStart, width, height, isRounded);
		this.current = current;
		this.max = max;
	}
	
	public ScrollableButton(String displayString, Rectangle collision, boolean isRounded, int current, int max) {
		super(displayString, collision, isRounded);
		this.current = current;
		this.max = max;
	}
	
	public void onMouseDrag(int mouseX, int mouseY) {
		
	}
	
	public void onMouseClicked(int mouseX, int mouseY) {
		
	}
	
	public int getCurrent() {
		return current;
	}
	
	public int getMax() {
		return max;
	}
	
}
