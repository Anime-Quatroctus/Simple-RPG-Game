package dev.anime.rpg.base.characters;

public class BattleObject {
	
	protected BattleObjectStats boStats;
	
	protected String name;
	
	protected int screenPosition;
	
	public BattleObject(String name, BattleObjectStats stats) {
		this.name = name;
		this.boStats = stats;
	}
	
	public String getCharacterName() {
		return name;
	}
	
}
