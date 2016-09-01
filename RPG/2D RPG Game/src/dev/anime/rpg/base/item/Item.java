package dev.anime.rpg.base.item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Item {
	
	protected final int ID;
	
	protected int maxStackSize = 999;
	
	protected BufferedImage texture;
	
	public Item(int ID, BufferedImage texture) {
		this.ID = ID;
		this.texture = texture;
	}
	
	public void renderItem(Graphics g, DisplayType type) {
		switch (type) {
		case GUI: //TODO Render GUI.
			break;
		case WORLD: //TODO Render WORLD.
			break;
		case OTHER: break;
		default: break;
		}
	}
	
	/** Determines where the Item can be used. **/
	public ItemUseType getUseType() {
		return ItemUseType.BOTH;
	}
	
	/**
	 * @param user The user of the item null if not in battle.
	 * @param target The target of the item can be null if it is not used on a Character.
	 * @return Whether this Item can be used.
	 */
	public boolean canUse(Character user, Character target, ItemUseType type) {
		return ItemUseType.canBeUsed(getUseType(), type);
	}
	
	/**
	 * Called when the item is used either in the world or in a battle.
	 * @param user The user of the item is null if not in battle.
	 * @param target The target of the item can be null.
	 * @param type Either BATTLE or WORLD depending on where it is used.
	 */
	public abstract void useItem(Character user, Character target, ItemUseType type);
	
	public int getID() {
		return ID;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	public int getMaxStackSize() {
		return maxStackSize;
	}
	
	public Item setMaxStackSize(int amount) {
		this.maxStackSize = amount;
		return this;
	}
	
	public static enum DisplayType {
		GUI, WORLD, OTHER;
	}
	
	public static enum ItemUseType {
		BATTLE, WORLD, BOTH;
		
		public static boolean canBeUsed(ItemUseType type1, ItemUseType type2) {
			if (type1 == BOTH) return true;
			return type1 == type2;
		}
		
	}
	
}
