package dev.anime.rpg.base.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.data.SaveManager;
import dev.anime.rpg.base.entity.Entity;
import dev.anime.rpg.base.entity.EntityNPC;

public class Worlds {
	
	private static Map<String, World> WORLDS = new HashMap<String, World>();
	
	private static Map<String, List<Entity>> WORLD_ENTITIES = new HashMap<String, List<Entity>>();
	private static Map<String, List<WorldTransferPos>> WORLD_TRANSFER_POSITIONS = new HashMap<String, List<WorldTransferPos>>();
	
	private GameHandler handler;
	
	public Worlds(GameHandler handler) {
		this.handler = handler;
		init();
	}
	
	@SuppressWarnings("unchecked")
	/** Adds the world transfer positions and entities to a list so it can be loaded later. **/
	private void init() {
		List<Entity> worldEntities = new ArrayList<Entity>();
		List<WorldTransferPos> worldTransferList = new ArrayList<WorldTransferPos>();
		worldTransferList.add(new WorldTransferPos("/map1", 0, 50, 32, 100, 0, 0));
		// Remember to copy the list otherwise when you clear it it clears the stored one aswell.
		WORLD_TRANSFER_POSITIONS.put("/map2", (List<WorldTransferPos>) Utils.copyList(worldTransferList));
		worldEntities.add(new EntityNPC(handler, 20, 10));
		WORLD_ENTITIES.put("/map2", (List<Entity>) Utils.copyList(worldEntities));
		// Clearing list for next world.
		worldEntities.clear();
		worldTransferList.clear();
	}
	
	/** Used to put the updated world once it has been saved and switched from {@link WorldManager#currentWorld}. **/
	public static void putUpdatedWorld(World world) {
		String key = world.getFileDir() + world.getFileName();
		if (WORLDS.containsKey(key)) WORLDS.put(key, world);
	}
	
	/** If the world has already been loaded this play time loads the world from the List.
	 * If the key is not null and is not empty trys to load the world from image/key.
	 * If the key is empty or null returns a new world loaded from the error image.
	 **/
	public static World getWorld(GameHandler handler, String key) {
		if (handler != null) {
			if (WORLDS.containsKey(key)) {
				World world = WORLDS.get(key);
				SaveManager.load(world);
				return world;
			} else if (key != null && !key.isEmpty()) {
				String[] splitString = key.split("\\/+");
				WORLDS.put(key, new World(handler, key) {
					@Override
					public String getWorldName() {
						return splitString[splitString.length - 1];
					}
					@Override
					public String getWorldLocation() {
						String location = "";
						for (int i = 0; i < splitString.length; i++) if (i < splitString.length - 1) location += "/" + splitString[i];
						if (splitString.length == 1) location = "/";
						return location;
					}
					@Override
					public void addEntities() {
						if (WORLD_ENTITIES.containsKey(key)) {
							for (Entity entity : WORLD_ENTITIES.get(key)) {
								manager.addEntity(entity);
							}
						}
					}
					@Override
					public List<WorldTransferPos> transferPositions() {
						if (WORLD_TRANSFER_POSITIONS.containsKey(key)) return WORLD_TRANSFER_POSITIONS.get(key);
						return null;
					}
				});
				SaveManager.load(WORLDS.get(key));
				return WORLDS.get(key);
			} else {
				return new World(handler, "map1") {
					@Override
					public String getWorldName() {
						return "overworld";
					}
					@Override
					public String getWorldLocation() {
						return "/";
					}
					@Override
					public void addEntities() {}
					@Override
					public List<WorldTransferPos> transferPositions() {
						return null;
					}
				};
			}
		}
		return null;
	}
	
}
