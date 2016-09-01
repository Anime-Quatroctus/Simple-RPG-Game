package dev.anime.rpg.base.world.tile;

import java.util.ArrayList;
import java.util.List;

import dev.anime.rpg.base.gfx.Assets;

public class Tiles {
	
	public static List<Tile> TILES = new ArrayList<Tile>();
	
	public static Tile VOID_TILE;
	public static Tile GRASS_TILE;
	
	public static void initializeTiles() {
		VOID_TILE = new TileVoid(0, Assets.VOID);
		GRASS_TILE = new TileGrass(1, Assets.GRASS);
	}
	
	public static Tile getTileFromID(int ID) {
		for (Tile tile : TILES) {
			if (tile.ID == ID) return tile;
		}
		return VOID_TILE;
	}
	
	public static Tile getTileFromColor(String hexColor) {
		for (Tile tile : TILES) {
			if (tile.getHexColor().equals(hexColor)) return tile;
		}
		return VOID_TILE;
	}
	
}
