package dev.anime.rpg.base.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	/**
	 * @param amount The maximum amount in the array.
	 * @param width The width of each new image cropped from the sheet.
	 * @param height The height of each new image cropped from the sheet.
	 * @return A new BufferedImage array with contents from the sheet.
	 */
	public BufferedImage[] split(int amount, int width, int height) {
		final int maxWidth = sheet.getWidth() / width;
		final int maxHeight = sheet.getHeight() / height;
		BufferedImage[] images = new BufferedImage[amount];
		for (int y = 0; y < maxHeight; y++) {
			for (int x = 0; x < maxWidth; x++) {
				if (y * x <= amount) {
					if (y == 0) images[x] = crop(width * x, height * y, width, height);
					if (y > 0) images[y * x] = crop(width * x, height * y, width, height);
				}
			}
		}
		return images;
	}
	
	/**
	 * @param x Starting x position on image.
	 * @param y Starting y position on image.
	 * @param width Distance to crop from x.
	 * @param height Distance to crop from y.
	 * @return A new BufferedImage cropped from the sheet.
	 */
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
	
}