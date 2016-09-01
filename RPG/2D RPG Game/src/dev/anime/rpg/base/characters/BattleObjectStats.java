package dev.anime.rpg.base.characters;

import java.util.Random;

public class BattleObjectStats {
	
	private Random rand = new Random();
	
	private int level = 1, xp = 0, totalXP = 0, levelUpXP = 15;
	
	private static int maxLevel = 99;
	
	private Integer[] baseStats = new Integer[]{0, 0, 0, 0, 0, 0, 0}, maxIncrements = new Integer[]{0, 0, 0, 0, 0, 0, 0}, minIncrements = new Integer[]{0, 0, 0, 0, 0, 0, 0}, currentStats = new Integer[]{0, 0, 0, 0, 0, 0, 0}, bonusStats = new Integer[]{0, 0, 0, 0, 0, 0, 0};
	
	private Double[] incrementChances = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	
	public BattleObjectStats(int[] baseStats, int[] maxStatIncrements, int[] minStatIncrements, double[] incrementChances) {
		for (int i = 0; i < StatConstants.NUM_OF_STATS; i++) {
			this.baseStats[i] = baseStats[i];
			this.currentStats[i] = baseStats[i];
			this.maxIncrements[i] = maxStatIncrements[i];
			this.minIncrements[i] = minStatIncrements[i];
			this.incrementChances[i] = incrementChances[i];
		}
	}
	
	private int getAmountOfCalculations(double chance) {
		if (chance >= .20) return 2;
		if (chance >= .40) return 3;
		if (chance >= .60) return 4;
		if (chance >= .80) return 5;
		return 1;
	}
	
	/** Used to increase stat that is not health or psi. **/
	private void incrementStat(int ID) {
		int statIncrement = 0;
		for (int i = 0 ; i < getAmountOfCalculations(incrementChances[ID]); i++) {
			statIncrement += rand.nextInt(maxIncrements[ID] - minIncrements[ID]) + minIncrements[ID];
		}
		statIncrement /= 3;
		this.addStat(ID, statIncrement);
		double oldChance = incrementChances[ID];
		double temp = 0;
		this.incrementChances[ID] += (double) (temp = rand.nextDouble() > oldChance ? oldChance / 4 : temp / 2);
		if (incrementChances[ID] > 1.0) incrementChances[ID] = 1.0;
	}
	
	private void handleHealthAndPSIIncrement(int ID) {
		if (ID == StatConstants.MAX_HEALTH) { // Handling "Max Health" increase.
			int statIncrement = 0;
			int baseHealth = baseStats[ID];
			int currentMaxHealth = currentStats[ID];
			double multiplier = (currentMaxHealth / 1000) + (baseHealth / 10);
			for (int i = 0; i < getAmountOfCalculations(incrementChances[ID]); i++) {
				statIncrement += rand.nextInt(maxIncrements[ID] - minIncrements[ID]) + minIncrements[ID];
			}
			if (multiplier > 1) statIncrement *= multiplier;
			this.addStat(ID, statIncrement);
			double oldChance = incrementChances[ID];
			double temp = 0;
			this.incrementChances[ID] += (double) (temp = rand.nextDouble() > oldChance ? oldChance / 4 : temp / 2);
		} else if (ID == 1) { // Handling "Max PSI" increase.
			if (maxIncrements[ID] == 0) return;
			int statIncrement = 0;
			int basePSI = baseStats[ID];
			int currentMaxPSI = currentStats[ID];
			double multiplier = (currentMaxPSI / 100) + (basePSI / 10);
			for (int i = 0 ; i < getAmountOfCalculations(incrementChances[ID]); i++) {
				statIncrement += rand.nextInt(maxIncrements[ID] - minIncrements[ID]) + minIncrements[ID];
			}
			if (multiplier > 1) statIncrement *= multiplier;
			this.addStat(ID, statIncrement);
			double oldChance = incrementChances[ID];
			double temp = 0;
			this.incrementChances[ID] += (double) (temp = rand.nextDouble() > oldChance ? oldChance / 4 : temp / 2);
		}
		if (incrementChances[ID] > 1.0) incrementChances[ID] = 1.0;
	}
	
	public boolean levelUp() {
		if (!isMaxLevel()) {
			for (int i = 0; i < StatConstants.NUM_OF_STATS; i++) {
				if (i < 2) handleHealthAndPSIIncrement(i);
				if (i >= 2) incrementStat(i);
			}
			level++;
			if (level >= maxLevel) {
				levelUpXP = 0;
			} else levelUpXP += ((int) Math.round(Math.pow(level, 1.7)) + (level >= 50 ? ((int) Math.round(Math.pow(level - 50, 1.6))) : 0));
			xp = 0;
			return true;
		}
		return false;
	}
	
	public void addStat(int ID, int amount) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			currentStats[ID] += amount;
		}
	}
	
	public void addXP(int xp) {
		if (!isMaxLevel()) {
			this.xp += xp;
			this.totalXP += xp;
		}
	}
	
	public void subtractStat(int ID, int amount) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			currentStats[ID] -= amount;
		}
	}
	
	public void setStat(int ID, int amount) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			currentStats[ID] = amount;
		}
	}
	
	public void setBonusStat(int ID, int amount) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			bonusStats[ID] = amount;
		}
	}
	
	public void setChance(int ID, double amount) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			incrementChances[ID] = amount;
		}
	}
	
	public int getCurrentStat(int ID) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			return currentStats[ID];
		}
		return 0;
	}
	
	public int getBonusStat(int ID) {
		if (ID >= 0 && ID < StatConstants.NUM_OF_STATS) {
			return bonusStats[ID];
		}
		return 0;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int newLevel) {
		if (newLevel >= maxLevel) {
			level = maxLevel;
		} else level = newLevel;
	}
	
	public int getXP() {
		return xp;
	}
	
	public void setXP(int newXP) {
		this.xp = newXP;
		if (xp > levelUpXP) levelUp();
	}
	
	public int getTotalXP() {
		return totalXP;
	}
	
	public void setTotalXP(int newTotal) {
		this.totalXP = newTotal;
	}
	
	public int getRequiredXP() {
		return levelUpXP;
	}
	
	public void setRequiredXP(int newRequiredXP) {
		this.levelUpXP = newRequiredXP;
	}
	
	public Double[] getChances() {
		return incrementChances;
	}
	
	public Integer[] getCurrentStats() {
		return currentStats;
	}
	
	public Integer[] getBonusStats() {
		return bonusStats;
	}
	
	public boolean isMaxLevel() {
		return level == maxLevel;
	}
	
	public class StatConstants {
		public static final int MAX_HEALTH = 0, MAX_PSI = 1, STRENGTH = 2, MAGIC_STRENGTH = 3, PHYSICAL_DEFENSE = 4, MAGIC_DEFENSE = 5, SPEED = 6, NUM_OF_STATS = 7;
	}
	
}
