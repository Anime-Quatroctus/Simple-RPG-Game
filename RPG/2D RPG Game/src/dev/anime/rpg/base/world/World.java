package dev.anime.rpg.base.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.data.ISaveable;
import dev.anime.rpg.base.data.SaveManager;
import dev.anime.rpg.base.entity.Entity;
import dev.anime.rpg.base.entity.EntityManager;
import dev.anime.rpg.base.entity.EntityPlayer;
import dev.anime.rpg.base.gfx.Assets;
import dev.anime.rpg.base.states.StateManager;
import dev.anime.rpg.base.world.tile.Tile;
import dev.anime.rpg.base.world.tile.TileTicker;
import dev.anime.rpg.base.world.tile.Tiles;

public abstract class World implements ISaveable {
	
	private String[] saveKeys = new String[]{"Entities"};
	
	/** Current instance of GameHandler. **/
	protected GameHandler handler;
	
	/** The current camera centered on an Entity. **/
	protected WorldCamera camera;
	
	/** This worlds instance of an EntityManager. **/
	protected EntityManager manager;
	
	/** The array containing the IDs of the tiles in the map **/
	protected int[][] tiles;
	
	/** The Tile tickers for this map. **/
	protected List<TileTicker> tickers = new ArrayList<TileTicker>();
	
	/** The width and height in tiles **/
	protected int width, height;
	
	/** The players default spawn point. **/
	protected int spawnX = 0, spawnY = 0;
	
	/** The maximum steps it will take before the player will enter a battle. **/
	protected int maxBattleSteps = 15;
	
	public World(GameHandler handler, String saveFilePath) {
		this.handler = handler;
		final BufferedImage map = Assets.getMap(saveFilePath);
		
		this.manager = new EntityManager(handler, getPlayerFromLastWorld());
		
		camera = new WorldCamera(handler, manager.getPlayer());
		
		this.width = map.getWidth();
		this.height = map.getHeight();
		loadLevel(map);
		SaveManager.load(this);
		if (manager.getAmountOfEntities() == 1) addEntities();
	}
	
	/** Loads the level from a image file. **/
	protected void loadLevel(BufferedImage map) {
		tiles = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color c = new Color(map.getRGB(x, y));
				String h = String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
				Tile tile = Tiles.getTileFromColor(h);
				tiles[x][y] = tile.getID();
				if (tile.hasTileTicker()) tickers.add(tile.createNewTileTicker(this, x, y));
			}
		}
	}
	
	/** Ticks the entities if it is not paused. **/
	public void tickWorld() {
		if (StateManager.getInstance().getCurrentState() != null && StateManager.getInstance().getCurrentState() == StateManager.gameState) {
			if ((StateManager.getInstance().getCurrentState().getCurrentUI() != null && !StateManager.getInstance().getCurrentState().getCurrentUI().pausesState()) || StateManager.getInstance().getCurrentState().getCurrentUI() == null) {
				if ((StateManager.getInstance().getCurrentState().getCurrentUI() != null && !StateManager.getInstance().getCurrentState().getCurrentUI().pausesEntities()) || StateManager.getInstance().getCurrentState().getCurrentUI() == null) {
					manager.tickEntities();
					handleTransferCollisions();
				}
				camera.centerOnEntity();
			}
		}
	}
	
	/** Renders the Tiles and Entities within the world. **/
	public void renderWorld(Graphics g) {
		if (StateManager.getInstance().getCurrentState() != null && StateManager.getInstance().getCurrentState() == StateManager.gameState) {
			int xStart = (int) Math.max(0, getWorldCamera().getXOffset() / Tile.DEFAULT_WIDTH);
			int xEnd = (int) Math.min(width, (getWorldCamera().getXOffset() + handler.getScreenWidth()) / Tile.DEFAULT_WIDTH + 1);
			int yStart = (int) Math.max(0, getWorldCamera().getYOffset() / Tile.DEFAULT_HEIGHT);
			int yEnd = (int) Math.min(height, (getWorldCamera().getYOffset() + handler.getScreenHeight()) / Tile.DEFAULT_HEIGHT + 1);
			for(int y = yStart; y < yEnd; y++) {
				for(int x = xStart; x < xEnd; x++) {
					getTileAt(x, y).renderTile(g, (int) (x * Tile.DEFAULT_WIDTH - handler.getWorldCamera().getXOffset()), (int) (y * Tile.DEFAULT_HEIGHT - handler.getWorldCamera().getYOffset()));
				}
			}
			manager.renderEntities(g);
		}
	}
	
	/** Checks if the player collides with a transfer positions collision box. **/
	public void handleTransferCollisions() {
		if (transferPositions() != null) {
			for (WorldTransferPos pos : transferPositions()) {
				if (Utils.isWithin(pos.getTransferCollision(), manager.getPlayer().getX(), manager.getPlayer().getY())) {
					manager.getPlayer().setPosition(pos.getNewX(), pos.getNewY());
					WorldManager.getInstance().setCurrentWorld(Worlds.getWorld(handler, pos.getWorldKey()));
				}
			}
		}
	}
	
	/** Returns a list of ICollideable if they collide at x and y. **/
	public List<ICollidedable> getCollidablesAt(int x, int y) {
		List<ICollidedable> collidables = new ArrayList<ICollidedable>();
		for (Entity entity : manager.getEntities()) {
			if (entity.getCollisionBox() != null) {
				if (Utils.isWithin(entity.getCollisionBox(), x, y)) collidables.add(entity);
			}
		}
		Tile tile = getTileAt(x, y);
		if (tile.isCollideable()) {
			ICollidedable tileCollider = (ICollidedable) tile;
			if (tileCollider.getCollisionBox() != null) collidables.add(tileCollider);
		}
		return collidables;
	}
	
	/** Returns a tile at the given x and y tile position. **/
	public Tile getTileAt(int x, int y) {
		if(x < 0.0 || y < 0.0 || x > width - 1 || y > height - 1) 
			return Tiles.VOID_TILE;
		return Tiles.getTileFromID(tiles[x][y]);
	}
	
	public int getWorldWidth() {
		return width;
	}
	
	public int getWorldHeight() {
		return height;
	}
	
	public EntityManager getManager() {
		return manager;
	}
	
	public WorldCamera getWorldCamera() {
		return camera;
	}
	
	/** Override if you do not want player to spawn at {@link spawnX} and {@link spawnY} **/
	public int[] getPlayerPositionFromEntrancePosition() {
		int[] position = new int[2];
		position[0] = spawnX;
		position[1] = spawnY;
		return position;
	}
	
	public EntityPlayer getPlayerFromLastWorld() {
		if (WorldManager.getInstance().getLastWorld() == null) return new EntityPlayer(handler);
		int[] playerPosition = getPlayerPositionFromEntrancePosition();
		return (EntityPlayer) WorldManager.getInstance().getLastWorld().getPlayerFromLastWorld().setPosition(playerPosition[0], playerPosition[1]);
		
	}
	
	/** A unique name for this world when paired with the {@link getWorldLocation()} method. **/
	public abstract String getWorldName();
	
	/** A unique location name for this world when paired with the {@link getWorldName()} method. **/
	public abstract String getWorldLocation();
	
	/** Called to add entities to the world. When there is nothing to load them from. **/
	public abstract void addEntities();
	
	/** A list of collision boxes that contain what world to send the player to and what position. **/
	public abstract List<WorldTransferPos> transferPositions();
	
	@Override
	public boolean isGlobal() {
		return false;
	}
	
	@Override
	public String getFileName() {
		return getWorldName();
	}
	
	@Override
	public String getFileDir() {
		return "/worlds/" + getWorldLocation() + "/";
	}

	@Override
	public String[] getSaveTags() {
		return saveKeys;
	}

	@Override
	public Object[] getSaveData() {
		return new Object[]{manager.getEntityData()};
	}

	@Override
	public void setDataFromLoad(Object[] newData) {
		manager.setEntityData(newData);
	}
	
}
