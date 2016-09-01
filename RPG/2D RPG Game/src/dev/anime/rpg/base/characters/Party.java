package dev.anime.rpg.base.characters;

import dev.anime.rpg.base.data.ISaveable;

public class Party implements ISaveable {
	
	private Character[] currentParty = new Character[5];
	
	private String[] saveKeys = new String[]{};
	
	public Party() {
		currentParty[0] = Characters.MAIN_PROTANGONIST;
	}
	
	public Party(Character[] party) {
		currentParty = party;
	}
	
	/** Adds a character to the {@link currentParty party}. **/
	public boolean addCharacter(Character newCharacter) {
		for (int i = 0; i < currentParty.length; i++) {
			if (currentParty[i] == newCharacter) return false;
			if (currentParty[i] == null) {
				// TODO: Display textbox saying newCharacter has Joined the party or something.
				currentParty[i] = newCharacter;
				return true;
			}
		}
		return false;
	}
	
	/** Moves characterPositionInitial to newCharacterPosition and vice versa. **/
	public boolean switchCharacters(int characterPositionInitial, int newCharacterPosition) {
		if (characterPositionInitial != newCharacterPosition) {
			Character character1 = currentParty[characterPositionInitial];
			Character character2 = currentParty[newCharacterPosition];
			currentParty[newCharacterPosition] = character1;
			currentParty[characterPositionInitial] = character2;
			return true;
		}
		return false;
	}
	
	/** Removes a Character from the {@link currentParty party}. **/
	public boolean removeCharacter(Character oldCharacter) {
		for (int i = 0; i < currentParty.length; i++) {
			if (currentParty[i] == oldCharacter) {
				currentParty[i] = null;
				return true;
			}
		}
		return false;
	}
	
	/** Grabs a list of all party members names. **/
	public String[] getPartyCharacterNames() {
		String[] names = new String[currentParty.length];
		for (int i = 0; i < names.length; i++) {
			if (currentParty[i] != null) {
				names[i] = currentParty[i].getCharacterName();
			} else {
				names[i] = "null";
			}
		}
		return names;
	}

	@Override
	public boolean isGlobal() {
		return false;
	}

	@Override
	public String getFileName() {
		return "party";
	}
	
	@Override
	public String getFileDir() {
		return "/characters/";
	}

	@Override
	public String[] getSaveTags() {
		return saveKeys;
	}

	@Override
	public Object[] getSaveData() {
		return getPartyCharacterNames();
	}

	@Override
	public void setDataFromLoad(Object[] newData) {
		for (int i = 0; i < currentParty.length; i++) {
			if (newData[i] != null) currentParty[i] = Characters.getCharacterByName((String) newData[i]);
		}
	}
	
}
