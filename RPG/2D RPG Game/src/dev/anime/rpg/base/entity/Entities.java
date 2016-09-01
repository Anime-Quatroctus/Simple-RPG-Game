package dev.anime.rpg.base.entity;

import java.util.HashMap;
import java.util.Map;

public class Entities {
	
	private static final Map<Integer, Class<? extends Entity>> ENTITY_CLASSES = new HashMap<Integer, Class<? extends Entity>>();
	
	static {
		ENTITY_CLASSES.put(0, EntityNPC.class);
		// TODO: Add more Entites
	}
	
	public static void registerEntityClass(int ID, Class<? extends Entity> entityClass) {
		if (ENTITY_CLASSES.containsKey(ID)) return;
		ENTITY_CLASSES.put(ID, entityClass);
	}
	
	public static Class<? extends Entity> getEntityClass(int ID) {
		return ENTITY_CLASSES.get(ID);
	}
	
}
