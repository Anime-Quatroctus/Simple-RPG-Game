package dev.anime.rpg.base.characters;

import dev.anime.rpg.base.characters.Character.CharacterClass;

public class Characters {
	
	public static final String PROTAGONIST_NAME = "James";
	
	/** The games main protagonist **/
	public static final Character MAIN_PROTANGONIST = new Character(PROTAGONIST_NAME, new BattleObjectStats(new int[]{35, 15, 3, 3, 2, 2, 2}, new int[]{15, 11, 5, 3, 4, 4, 7}, new int[]{5, 4, 1, 1, 1, 1, 2}, new double[]{0.02, 0.02, 0.10, 0.04, 0.06, 0.05, 0.10}), CharacterClass.SWORD_FENCER);
	
	public static Character getCharacterByName(String name) {
		switch (name.toLowerCase()) {
		case PROTAGONIST_NAME: return MAIN_PROTANGONIST;
		default: return null;
		}
	}
	
}
