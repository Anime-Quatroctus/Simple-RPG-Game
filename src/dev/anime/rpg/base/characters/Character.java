package dev.anime.rpg.base.characters;

import dev.anime.rpg.base.Utils;
import dev.anime.rpg.base.characters.BattleObjectStats.StatConstants;
import dev.anime.rpg.base.data.ISaveable;
import dev.anime.rpg.base.data.SaveManager;

public class Character extends BattleObject implements ISaveable {
	
	private final String[] dataKeys = new String[]{"MAX HEALTH", "MAX PSI", "STRENGTH", "MAGIC STRENGTH", "PHYSICAL DEFENSE", "MAGIC DEFENSE", "SPEED", "XP", "TOTAL XP", "LEVEL UP XP", "LEVEL", "HEALTH", "PSI", "HCHANCE", "PSICHANCE", "STRCHANCE", "MSTRCHANCE", "PDEFCHANCE", "MDEFCHANCE", "SCHANCE"};
	
	private CharacterClass characterClass;
	
	public Character(String name, BattleObjectStats stats, CharacterClass characterClass) {
		super(name, stats);
		this.characterClass = characterClass;
		SaveManager.load(this);
	}
	
	public BattleObjectStats getStats() {
		return boStats;
	}
	
	public void levelUP() {
		boStats.addXP(boStats.getRequiredXP());
		boStats.levelUp();
	}
	
	public CharacterClass getCharacterClass() {
		return characterClass;
	}
	
	@Override
	public boolean isGlobal() {
		return false;
	}
	
	@Override
	public String getFileName() {
		return getCharacterName().toLowerCase();
	}
	
	@Override
	public String getFileDir() {
		return "characters/";
	}

	@Override
	public String[] getSaveTags() {
		return dataKeys;
	}

	@Override
	public Object[] getSaveData() {
		return Utils.addArrays(Utils.addArrays(boStats.getCurrentStats(), new Integer[]{boStats.getXP(), boStats.getTotalXP(), boStats.getRequiredXP(), boStats.getLevel(), boStats.getHealth(), boStats.getPSI()}), boStats.getChances());
	}

	@Override
	public void setDataFromLoad(Object[] newData) {
		int i;
		for (i = 0; i < StatConstants.NUM_OF_STATS; i++) {
			if (newData[i] != null) boStats.setStat(i, (int) newData[i]);
		}
		if (newData[StatConstants.NUM_OF_STATS] != null) boStats.setXP((int) newData[StatConstants.NUM_OF_STATS]);
		if (newData[StatConstants.NUM_OF_STATS + 1] != null) boStats.setTotalXP((int) newData[StatConstants.NUM_OF_STATS + 1]);
		if (newData[StatConstants.NUM_OF_STATS + 2] != null) boStats.setRequiredXP((int) newData[StatConstants.NUM_OF_STATS + 2]);
		if (newData[StatConstants.NUM_OF_STATS + 3] != null) boStats.setLevel((int) newData[StatConstants.NUM_OF_STATS + 3]);
		if (newData[StatConstants.NUM_OF_STATS + 4] != null) boStats.setHealth((int) newData[StatConstants.NUM_OF_STATS + 4]);
		if (newData[StatConstants.NUM_OF_STATS + 5] != null) boStats.setPSI((int) newData[StatConstants.NUM_OF_STATS + 5]);
		for (i = 0; i < StatConstants.NUM_OF_STATS; i++) {
			if (newData[StatConstants.NUM_OF_STATS + 6 + i] != null)  boStats.setChance(i, (double) newData[i + (StatConstants.NUM_OF_STATS + 6)]);
		}
		
	}
	
	public static enum CharacterClass {
		SWORD_FENCER(false), GUNNER(true), PSI_USER(false), LOLI(false), NINJA(true);
		
		private boolean canDuelEquip;
		
		CharacterClass(boolean canDuelEquip) {
			this.canDuelEquip = canDuelEquip;
		}
		
		public boolean canDuelEquip() {
			return canDuelEquip;
		}
		
	}
	
}
