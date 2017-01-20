package dev.anime.rpg.base.states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.gfx.RenderUtils;

public class Button {
	
	public String displayString;
	private boolean isRounded, isKeyed, renders = true;
	
	private Rectangle rect;
	
	public Button(int xStart, int yStart, int width, int height, boolean isRounded) {
		this.rect = new Rectangle(xStart, yStart, width, height);
		this.isRounded = isRounded;
	}
	
	public Button(String displayString, int xStart, int yStart, int width, int height, boolean isRounded) {
		this.displayString = displayString;
		this.rect = new Rectangle(xStart, yStart, width, height);
		this.isRounded = isRounded;
	}
	
	public Button(String displayString, Rectangle rect, boolean isRounded) {
		this.displayString = displayString;
		this.rect = rect;
		this.isRounded = isRounded;
	}
	
	public Button(Rectangle rect, boolean isRounded) {
		this.rect = rect;
		this.isRounded = isRounded;
	}
	
	public Button(Rectangle rect) {
		this.rect = rect;
		this.renders = false;
	}
	
	/**
	 * Draws this button.
	 * @param g The Graphics object used to draw the button.
	 * @param backGroundColor The Color of the background.
	 * @param borderColor The Color of the border
	 * @param mouseX The mouses current x position.
	 * @param mouseY The mouses current y position.
	 */
	public void renderButton(Graphics g, Color backGroundColor, Color borderColor, Font f, int mouseX, int mouseY, int thickness) {
		if (renders) {
			Color originalColor = g.getColor();
			if (Utils.isWithin(rect, mouseX, mouseY)) setKeyed(false);
			if (Utils.isWithin(rect, mouseX, mouseY) || isKeyed) {
				backGroundColor = backGroundColor.darker();
				borderColor = borderColor.darker();
			}
			if (isRounded) {
				RenderUtils.drawRoundedBoxWithBorder(g, backGroundColor, borderColor, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), thickness);
			} else {
				Graphics2D TDG = (Graphics2D) g;
				TDG.setStroke(new BasicStroke(thickness));
				TDG.setColor(backGroundColor);
				TDG.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
				TDG.setColor(borderColor);
				TDG.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getWidth());
			}
			if (displayString != null && !displayString.isEmpty()) {
				g.setFont(f);
				int xStringStart = (int) (rect.getX() + ((rect.getWidth() - g.getFontMetrics().stringWidth(displayString)) / 2));
				int yStringStart = (int) (rect.getY() + ((rect.getHeight() - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent());
				g.drawString(displayString, xStringStart, yStringStart);
			}
			g.setColor(originalColor);
		}
	}
	
	public Rectangle getPosition() {
		return rect;
	}
	
	public boolean isKeyed() {
		return isKeyed;
	}
	
	public void setKeyed(boolean isKeyed) {
		this.isKeyed = isKeyed;
	}
	
}
