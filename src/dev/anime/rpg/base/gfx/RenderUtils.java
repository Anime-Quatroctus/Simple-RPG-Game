package dev.anime.rpg.base.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dev.anime.rpg.base.GameHandler;

public class RenderUtils {
	
	/**
	 * Draws a string and wraps it.
	 * @param g Graphics object used to draw.
	 * @param text The text to draw.
	 * @param x The starting x Position.
	 * @param y The starting y position.
	 * @param width The width that it is allowed to draw.
	 */
	public static void drawWrappedString(Graphics g, String text, int x, int y, int width) {
		String[] splitString = text.split("\\s+");
		List<String> lines = new ArrayList<String>();
		String temp = "";
		for (int i = 0; i < splitString.length; i++) {
			String oldTemp = temp;
			if (temp.isEmpty()) {
				temp += splitString[i];
			} else temp += " " + splitString[i];
			if (g.getFontMetrics().stringWidth(temp) > width) {
				lines.add(oldTemp);
				temp = splitString[i];
			} else if (g.getFontMetrics().stringWidth(temp) == width) {
				lines.add(temp);
				temp = "";
			}
		}
		if (lines.isEmpty()) {
			lines.add(text);
		} else lines.add(splitString[splitString.length - 1]);
		for (int i = 0; i < lines.size(); i++) g.drawString(lines.get(i), x, y + (i * g.getFontMetrics().getHeight()));
	}
	
	public static void drawCompletlyCenteredString(Graphics g, GameHandler handler, String text) {
		int x = (handler.getScreenWidth() - g.getFontMetrics().stringWidth(text)) / 2;
		int y = ((handler.getScreenHeight() - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent();
		g.drawString(text, x, y);
	}
	
	public static void drawCenteredString(Graphics g, GameHandler handler, String text, int xy, boolean centerH, boolean centerV) {
		if (centerH && centerV) { drawCompletlyCenteredString(g, handler, text); return; }
		if (centerH) {
			int x = (handler.getScreenWidth() - g.getFontMetrics().stringWidth(text)) / 2;
			g.drawString(text, x, xy);
			return;
		}
		if (centerV) {
			int y = ((handler.getScreenHeight() - g.getFontMetrics().getHeight()) / 2) + g.getFontMetrics().getAscent();
			g.drawString(text, xy, y);
			return;
		}
	}
	
	public static void drawImage(Graphics g, BufferedImage image, int x, int y, int width, int height) {
		g.drawImage(image, x, y, width, height, null);
	}
	
	public static void drawRoundedBoxWithBorder(Graphics g, Color backgroundColor, Color borderColor, int x, int y, int width, int height, int thickness) {
		addRoundBackGroundRect(g, backgroundColor, x + thickness, y + thickness, width - thickness, height - thickness);
		drawRoundedBorder(g, borderColor, x, y, width, height, thickness);
	}
	
	public static void addRoundBackGroundRect(Graphics g, Color backgroundColor, int x, int y, int width, int height) {
		g.setColor(backgroundColor);
		g.fillRect(x, y, width, height);
	}
	
	public static void drawRoundedBorder(Graphics g, Color borderColor, int x, int y, int width, int height, int thickness) {
		Graphics2D TDG = (Graphics2D) g;
		TDG.setColor(borderColor);
		TDG.setStroke(new BasicStroke(thickness));
		TDG.drawRoundRect(x + (thickness / 2), y + (thickness / 2), width - thickness, height - thickness, 2, 2);
		TDG.setColor(borderColor.darker().darker());
		TDG.setStroke(new BasicStroke(thickness / 2));
		TDG.drawRoundRect(x + (int) (thickness / 3.1), y + (int) (thickness / 3.1), (int) (width - (thickness / 2)), (int) (height - (thickness / 2)), 2, 2);
	}
	
}
