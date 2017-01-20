package dev.anime.rpg.base.entity;

import java.awt.image.BufferedImage;

import dev.anime.rpg.base.GameHandler;

public abstract class EntityAlive extends Entity {

	public EntityAlive(GameHandler handler) {
		super(handler);
	}
	
	public EntityAlive(GameHandler handler, int x, int y) {
		super(handler, x, y);
	}
	
	public EntityAlive(GameHandler handler, int ID, int x, int y) {
		super(handler, ID, x, y);
	}
	
	@Override
	public int getWidth() {
		return EntityConstants.HUMANOID_WIDTH;
	}

	@Override
	public int getHeight() {
		return EntityConstants.HUMANOID_HEIGHT;
	}
	
	/** Returns the still sprite from the facing. **/
	public BufferedImage getStillSprite() {
		return getSprites()[facing.getID()];
	}
	
}
