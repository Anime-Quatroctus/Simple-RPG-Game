package dev.anime.rpg.base.item;

public class ItemStack {
	
	private Item item;
	
	private int stackSize;
	
	public ItemStack(Item item, int stackSize) {
		this.item = item;
		this.stackSize = stackSize;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getStackSize() {
		return stackSize;
	}
	
	
	
}
