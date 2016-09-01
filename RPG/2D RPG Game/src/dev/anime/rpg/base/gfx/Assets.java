package dev.anime.rpg.base.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dev.anime.rpg.base.entity.Entity.EntityConstants;

public class Assets {
	
	private static final String WORLD_MAP_PATH = "/assets/game/maps/";
	private static final String CHARACTER_PATH = "/assets/game/textures/characters/";
	private static final String TILE_PATH = "/assets/game/textures/tiles/";
	
	private static final String IMAGE_TYPE = ".png";
	
	private static SpriteSheet CHARACTER_SPRITES = new SpriteSheet(loadImage(CHARACTER_PATH + "playable_characters"));
	
	public static BufferedImage[] CHARACTER_IMAGES= new BufferedImage[12];
	
	public static BufferedImage VOID;
	public static BufferedImage GRASS;
	
	public static void initializeImages() {
		CHARACTER_IMAGES = CHARACTER_SPRITES.split(CHARACTER_IMAGES.length, EntityConstants.HUMANOID_WIDTH, EntityConstants.HUMANOID_HEIGHT);
		
		VOID = loadImage(TILE_PATH + "void");
		GRASS = loadImage(TILE_PATH + "grass");
	}
	
	public static BufferedImage getMap(String path) {
		return loadImage(WORLD_MAP_PATH + path);
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Assets.class.getResource(path + IMAGE_TYPE));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
