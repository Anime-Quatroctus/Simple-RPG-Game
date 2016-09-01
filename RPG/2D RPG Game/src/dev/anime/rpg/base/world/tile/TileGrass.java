package dev.anime.rpg.base.world.tile;

import java.awt.image.BufferedImage;

public class TileGrass extends Tile {

	public TileGrass(int ID, BufferedImage texture) {
		super(ID, texture);
	}
	
	public String getHexColor() {
		return "00ff00";
	}
	
}
