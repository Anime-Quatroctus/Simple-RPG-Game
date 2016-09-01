package dev.anime.rpg.base.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.gfx.animation.AnimationBasic;
import dev.anime.rpg.base.world.ICollidedable;
import dev.anime.rpg.base.world.World;
import dev.anime.rpg.base.world.WorldManager;
import dev.anime.rpg.base.world.tile.Tile;

// TODO: Implement new Collision system completely aka Collision with all ICollidedable.
public abstract class Entity implements ICollidedable {

	/** The direction the entity is facing. **/
	protected EntityFacing facing = EntityFacing.DOWN;
	
	/** The animation used when moving. **/
	protected AnimationBasic movementAnimation;

	/** The entities current position. **/
	protected int x, y;
	
	/** Entity class ID used to load entity from JSON. **/
	protected final int ID;

	/** The distance the entity is moving in pixels the next motion tick. **/
	protected int motionX, motionY;

	/** How many pixels the entity should move every tick **/
	protected int movementSpeed = 4;

	/** Used to determine if the Entity should be removed from the map. **/
	protected boolean isDead = false;

	/** Current instance of GameHandler. **/
	protected GameHandler handler;

	public Entity(GameHandler handler) {
		this.handler = handler;
		this.ID = - 1;
	}

	public Entity(GameHandler handler, int x, int y) {
		this.handler = handler;
		this.ID = - 1;
		this.setPosition(x, y);
	}

	/** Used to allow Entity loading from JSON, using Reflection. {@link Entities entities} **/
	public Entity(GameHandler handler, int ID, int x, int y) {
		this.handler = handler;
		this.ID = ID;
		this.setPosition(x, y);
		Entities.registerEntityClass(ID, this.getClass());
	}

	/** Updates entity logic. **/
	public abstract void tickEntity();

	/** Renders entity to the screen should respect WorldCamera. **/
	public abstract void renderEntity(Graphics g);
	
	/** Corrects the entities facing dependant on its motion (if the entity is moving up or down defaults to that). **/
	protected void correctFacing() {
		EntityFacing tf = facing;
		if (motionX > 0) facing = EntityFacing.RIGHT;
		if (motionX < 0) facing = EntityFacing.LEFT;
		if (motionY > 0) facing = EntityFacing.DOWN;
		if (motionY < 0) facing = EntityFacing.UP;
		if (!tf.equals(facing)) move(false);
	}

	/** Moves the entity respecting collision. **/
	protected void move(boolean doNormalMove) {
		World world = WorldManager.getInstance().getCurrentWorld();
		if (world == null) {
			setDead();
			return;
		}
		if (checkHorizontalCollision(world) == 0) {
			if (doNormalMove) x += motionX;
		} else {
			System.out.println("Collision detected correcting position.");
			if (facing != EntityFacing.LEFT && checkHorizontalCollision(world) == 1) {
				int tx = (int) Math.floor((double) (x + motionX + getCollisionBox().getX() + getCollisionBox().getWidth()) / Tile.DEFAULT_WIDTH);
				x = (int) (tx * Tile.DEFAULT_WIDTH - getCollisionBox().getX() - getCollisionBox().getWidth() - 1);
			} else if (facing != EntityFacing.RIGHT && checkHorizontalCollision(world) == 2) {
//				if (x + motionX >= 0) {
					int tx = (int) Math.floor((double) ((x + motionX + getCollisionBox().getX()) / Tile.DEFAULT_WIDTH));
					x = (int) (tx * Tile.DEFAULT_WIDTH + Tile.DEFAULT_WIDTH - getCollisionBox().getX());
//				}
			} else move(doNormalMove);
		}
		if (checkVerticalCollision(world)) {
			if (doNormalMove) y += motionY;
		} else {
			if (facing == EntityFacing.DOWN) {
				int ty = (int) (y + motionY + getCollisionBox().getY() + getCollisionBox().getHeight()) / Tile.DEFAULT_HEIGHT;
				y = (int) (ty * Tile.DEFAULT_HEIGHT - getCollisionBox().getY() - getCollisionBox().getHeight() - 1);
			} else if (facing == EntityFacing.UP) {
				if (y + motionY >= 0) {
					int ty = (int) (y + motionY + getCollisionBox().getY()) / Tile.DEFAULT_HEIGHT;
					y = (int) (ty * Tile.DEFAULT_HEIGHT + Tile.DEFAULT_HEIGHT - getCollisionBox().getY());
				}
			}
		}
		correctFacing();
	}

	/** Checks the horizonal collision. 
	 * Returns 0 if no collision, 1 if a rightwards collision, and 2 if a leftwards collision was detected.
	**/
	protected int checkHorizontalCollision(World world) {
		Rectangle collision = getCollisionBox();
		if (facing != EntityFacing.LEFT) { // Moving Right
			int tx = (int) Math.floor((double) ((x + motionX + getCollisionBox().getX() + getCollisionBox().getWidth()) / Tile.DEFAULT_WIDTH));
			for (int ty = (int) collision.getY(); ty <= collision.getY() + collision.getHeight(); ty++) {
				if (collisionWithCollideable(world, tx, (int) Math.floor((double) ((y + ty) / Tile.DEFAULT_HEIGHT)))) return 1;
			}
		} 
		if (facing != EntityFacing.RIGHT) { // Moving Left
			int tx = (int) Math.floor((double) ((x + motionX + getCollisionBox().getX()) / Tile.DEFAULT_WIDTH));
			for (int ty = (int) collision.getY(); ty <= collision.getY() + collision.getHeight(); ty++) {
				if (collisionWithCollideable(world, tx, (int) Math.floor((double) (((double) y + (double) ty) / (double) Tile.DEFAULT_HEIGHT)))) return 2;
			}
		}
		return 0;
	}

	/** Checks vertical collission.
	 * Returns true if there was no collision detected.
	**/
	protected boolean checkVerticalCollision(World world) {
		Rectangle collision = getCollisionBox();
		if (facing == EntityFacing.DOWN) { // Moving Down
			int ty = (int) Math.floor((double) ((y + motionY + getCollisionBox().getY() + getCollisionBox().getHeight()) / Tile.DEFAULT_HEIGHT));
			for (int tx = (int) collision.getX(); tx <= collision.getX() + collision.getWidth(); tx++) {
				if (collisionWithCollideable(world, (int) Math.floor((double) ((x + tx) / Tile.DEFAULT_WIDTH)), ty)) return false;
			}
		} else if (facing == EntityFacing.UP) { // Moving Up
			int ty = (int) Math.floor((double) ((y + motionY + getCollisionBox().getY()) / Tile.DEFAULT_HEIGHT));
			for (int tx = (int) collision.getX(); tx <= collision.getX() + collision.getWidth(); tx++) {
				if (collisionWithCollideable(world, (int) Math.floor((double) ((x + tx) / Tile.DEFAULT_WIDTH)), ty)) return false;
			}
		}
		return true;
	}
	
	/** Asks the world if their is a collidable object at x and y. **/
	protected boolean collisionWithCollideable(World world, int x, int y) {
		// List<ICollidedable> collidables = world.getCollidablesAt(x, y);
		return world.getTileAt(x, y).isCollideable();
	}

	/** If this entities motion fields are greater than 0. **/
	public boolean isMoving() {
		return motionX > 0 || motionX < 0 || motionY > 0 || motionY < 0;
	}

	/** Returns if this entity should be removed from the Worlds EntityManager. **/
	public boolean isDead() {
		return isDead;
	}

	/** Sets the entit to be removed in the next tick. **/
	public void setDead() {
		this.isDead = false;
	}
	
	/** Sets the entities x y position. **/
	public Entity setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Object[] getEntitySaveData() {
		return new Object[]{ID, x, y, facing.getID()};
	}
	
	/** Used to set entity data on load that is not either its ID, x, y, or facing. WARNING 0-3 in the array are ID, x, y, and facing. **/
	public void setSpecialEntityData(Object[] specialData) {}

	/** Returns the entities width for rendering. **/
	public abstract int getWidth();

	/** Returns the entities height for rendering. **/
	public abstract int getHeight();

	/** Returns the entites possible textures usually used for animations. **/
	public abstract BufferedImage[] getSprites();

	public static class EntityConstants {
		
		/** The number of walking images used for animations. **/
		public static final int NUM_WALKING_SPRITES = 2;
		
		/** Humanoid width and height. **/
		public static final int HUMANOID_WIDTH = 32, HUMANOID_HEIGHT = 64;

		/** Entity Item width and height. **/
		public static final int ITEM_WIDTH = 16, ITEM_HEIGHT = 16;

		/** Default Collision box  **/
		public static final Rectangle DEFAULT_COLLISION_BOX = new Rectangle(HUMANOID_WIDTH, HUMANOID_HEIGHT);

		/** 0-facing: DOWN/UP 1-facing: LEFT 2-facing: RIGHT. **/
		public static final Rectangle[] HUMANOID_COLLISION_BOXES = new Rectangle[]{new Rectangle(1, 36, HUMANOID_WIDTH - 2, HUMANOID_HEIGHT - 36), new Rectangle(8, 36, HUMANOID_WIDTH - 8, HUMANOID_HEIGHT - 36), new Rectangle(0, 36, HUMANOID_WIDTH - 8, HUMANOID_HEIGHT - 36)};

	}

	public static enum EntityFacing {
		DOWN(0), UP(1), LEFT(2), RIGHT(3);

		private int ID;

		EntityFacing(int ID) {
			this.ID = ID;
		}

		public int getID() {
			return ID;
		}

		public static EntityFacing getFacingFromID(int ID) {
			switch (ID) {
			case 0: return DOWN;
			case 1: return UP;
			case 2: return LEFT;
			case 3: return RIGHT;
			default: return DOWN;
			}
		}

	}

}
