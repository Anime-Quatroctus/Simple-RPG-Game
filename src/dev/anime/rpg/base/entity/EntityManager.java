package dev.anime.rpg.base.entity;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;

import dev.anime.rpg.base.GameHandler;

public class EntityManager {
	
	private Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
	
	private GameHandler handler;
	
	public EntityManager(GameHandler handler, EntityPlayer player) {
		entities.put(entities.size(), player);
		this.handler = handler;
	}
	
	public void addEntity(Entity newEntity) {
		entities.put(entities.size(), newEntity);
	}
	
	public void tickEntities() {
		Iterator<Entry<Integer, Entity>> itr = entities.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<Integer, Entity> entry = itr.next();
			Entity entity = entry.getValue();
			if (entity.isDead()) {
				itr.remove();
				continue;
			} else {
				entity.tickEntity();
			}
		}
	}
	
	public void renderEntities(Graphics g) {
		for (Entry<Integer, Entity> entry : entities.entrySet()) {
			entry.getValue().renderEntity(g);
		}
	}
	
	public EntityPlayer getPlayer() {
		for (Entry<Integer, Entity> entry : entities.entrySet()) {
			if (entry.getValue() instanceof EntityPlayer) return (EntityPlayer) entry.getValue();
		}
		return null;
	}
	
	public Object[] getEntityData() {
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (Entry<Integer, Entity> entry : entities.entrySet()) {
			Entity entity = entry.getValue();
			if (!(entity instanceof EntityPlayer)) {
				data.add(entity.getEntitySaveData());
			}
		}
		return data.toArray();
	}
	
	/** Used to load entities from world save JSON. **/
	public void setEntityData(Object[] data) {
		for (Object obj : data) {
			if (obj instanceof Object[]) {
				Object[] rawData = (Object[]) obj;
				for (Object rawObj : rawData) {
					if (rawObj instanceof JSONArray) {
						Object[] entData = ((JSONArray)rawObj).toArray();
						if (entData[0] instanceof Integer || entData[0] instanceof Long) {
						if (entData[0] instanceof Long) entData[0] = ((Long)entData[0]).intValue();
						if (entData[1] instanceof Long) entData[1] = ((Long)entData[1]).intValue();
						if (entData[2] instanceof Long) entData[2] = ((Long)entData[2]).intValue();
						Class<? extends Entity> entClass = Entities.getEntityClass((int) entData[0]);
						if (entClass != null) {
							try {
								Constructor<?> entConstructor = entClass.getDeclaredConstructor(GameHandler.class, int.class, int.class, int.class);
								if (entConstructor != null) {
									Entity entity = (Entity) entConstructor.newInstance(handler, (int) entData[0], (int) entData[1], (int) entData[2]);
									if (entity != null) {
										entity.setSpecialEntityData(entData);
										addEntity(entity);
									}
								}
							} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
						}
					}
				}
			}
		}
	}
	
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Entity entity : this.entities.values()) {
			if (!(entity instanceof EntityPlayer)) entities.add(entity);
		}
		return entities;
	}
	
	public int getAmountOfEntities() {
		return entities.size();
	}
	
}
