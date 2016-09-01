package dev.anime.rpg.base.world.tile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.anime.rpg.base.world.ICollidedable;

public class TileVoid extends Tile implements ICollidedable {

	public TileVoid(int ID, BufferedImage texture) {
		super(ID, texture);
	}
	
	public String getHexColor() {
		return "0000000";
	}
	
	@Override
	public Rectangle getCollisionBox() {
		return new Rectangle(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

}
