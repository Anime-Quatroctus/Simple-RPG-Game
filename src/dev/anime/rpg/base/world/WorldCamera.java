package dev.anime.rpg.base.world;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.entity.Entity;
import dev.anime.rpg.base.world.tile.Tile;

public class WorldCamera {
	
	private GameHandler handler;
	private Entity entity;
	
	private int xOffset, yOffset;
	
	public WorldCamera(GameHandler handler, Entity center) {
		this.handler = handler;
		this.entity = center;
	}
	
	private void checkBlankSpace() {
		World world = WorldManager.getInstance().getCurrentWorld();
		if (xOffset < 0) {
			xOffset = 0;
		} else if (xOffset > world.getWorldWidth() * Tile.DEFAULT_WIDTH - handler.getScreenWidth()) {
			xOffset = world.getWorldWidth() * Tile.DEFAULT_WIDTH - handler.getScreenWidth();
		}
		if (yOffset < 0) {
			yOffset = 0;
		} else if (yOffset > world.getWorldHeight() * Tile.DEFAULT_HEIGHT - handler.getScreenHeight()) {
			yOffset = world.getWorldHeight() * Tile.DEFAULT_HEIGHT - handler.getScreenHeight();
		}
	}
	
	public void centerOnEntity() {
		xOffset = entity.getX() - handler.getScreenWidth() / 2 + entity.getWidth() / 2;
		yOffset = entity.getY() - handler.getScreenHeight() / 2 + entity.getHeight() / 2;
		checkBlankSpace();
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public void setXOffset(int newXOffset) {
		this.xOffset = newXOffset;
		checkBlankSpace();
	}
	
	public void setYOffset(int newYOffset) {
		this.yOffset = newYOffset;
		checkBlankSpace();
	}
	
	public void setOffsets(int newXOffset, int newYOffset) {
		this.setXOffset(newXOffset);
		this.setYOffset(newYOffset);
	}
	
}
