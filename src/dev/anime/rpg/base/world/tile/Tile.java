package dev.anime.rpg.base.world.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.anime.rpg.base.gfx.RenderUtils;
import dev.anime.rpg.base.world.ICollidedable;
import dev.anime.rpg.base.world.World;

public abstract class Tile {
	
	public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;
	
	protected final int ID;
	
	protected BufferedImage texture;
	
	public Tile(int ID, BufferedImage texture) {
		this.ID = ID;
		this.texture= texture;
		Tiles.TILES.add(this);
	}
	
	public void renderTile(Graphics g, int x, int y) {
		RenderUtils.drawImage(g, texture, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public boolean isCollideable() {
		return this instanceof ICollidedable;
	}
	
	public boolean hasTileTicker() {
		return false;
	}
	
	public TileTicker createNewTileTicker(World world, int x, int y) {
		return null;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	public abstract String getHexColor();
	
	public int getID() {
		return ID;
	}
	
}
