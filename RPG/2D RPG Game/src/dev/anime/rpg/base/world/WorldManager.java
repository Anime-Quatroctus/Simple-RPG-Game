package dev.anime.rpg.base.world;

import dev.anime.rpg.base.data.SaveManager;

public class WorldManager {
	
	private World lastWorld;
	private World currentWorld;
	
	private static WorldManager worldManager;
	
	public static WorldManager getInstance() {
		if (worldManager == null) worldManager = new WorldManager();
		return worldManager;
	}
	
	public World getCurrentWorld() {
		return currentWorld;
	}
	
	public World getLastWorld() {
		return lastWorld;
	}
	
	public void setCurrentWorld(World newWorld) {
		if (currentWorld != null) {
			SaveManager.save(currentWorld);
			Worlds.putUpdatedWorld(currentWorld);
		}
		this.lastWorld = currentWorld;
		this.currentWorld = newWorld;
	}
	
}
