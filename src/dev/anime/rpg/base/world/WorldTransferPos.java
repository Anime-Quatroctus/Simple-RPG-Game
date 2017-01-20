package dev.anime.rpg.base.world;

import java.awt.Rectangle;

public class WorldTransferPos {
	
	private String worldKey;
	
	private Rectangle rect;
	
	private int newX, newY;
	
	public WorldTransferPos(String worldKey, int x, int y, int width, int height, int newX, int newY) {
		this.worldKey = worldKey;
		this.rect = new Rectangle(x, y, width, height);
		this.newX = newX;
		this.newY = newY;
	}
	
	public Rectangle getTransferCollision() {
		return rect;
	}
	
	public String getWorldKey() {
		return worldKey;
	}
	
	public int getNewX() {
		return newX;
	}
	
	public int getNewY() {
		return newY;
	}
	
}
