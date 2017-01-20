package dev.anime.rpg.base.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.gfx.Assets;
import dev.anime.rpg.base.gfx.RenderUtils;

public class EntityNPC extends EntityAlive {
	
	public EntityNPC(GameHandler handler, int x, int y) {
		super(handler, x, y);
	}
	
	public EntityNPC(GameHandler handler, int ID, int x, int y) {
		super(handler, ID, x, y);
	}

	@Override
	public Rectangle getCollisionBox() {
		return EntityConstants.HUMANOID_COLLISION_BOXES[0];
	}

	@Override
	public void tickEntity() {

	}
	
	@Override
	public void renderEntity(Graphics g) {
		RenderUtils.drawImage(g, Assets.VOID, x - handler.getWorldCamera().getXOffset(), y - handler.getWorldCamera().getYOffset(), getWidth(), getHeight());
	}

	@Override
	public BufferedImage[] getSprites() {
		return null;
	}

}
