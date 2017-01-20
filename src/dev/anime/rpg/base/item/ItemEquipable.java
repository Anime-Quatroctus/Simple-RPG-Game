package dev.anime.rpg.base.item;

import java.awt.image.BufferedImage;

public abstract class ItemEquipable extends Item {

	public ItemEquipable(int ID, BufferedImage texture, int[] stats) {
		super(ID, texture);
		this.setMaxStackSize(99);
	}

	public abstract boolean canEquip(Character equiper);
	
	public abstract void onEquip(Character equiper);
	
}
