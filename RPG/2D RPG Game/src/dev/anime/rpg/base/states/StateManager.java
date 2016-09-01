package dev.anime.rpg.base.states;

import dev.anime.rpg.base.GameHandler;

public class StateManager {
	
	
//	public static MenuState menuState;
	public static GameState gameState;
	
	private static StateManager stateManager;
	
	private State currentState;
	
	public static StateManager getInstance() {
		if (stateManager == null) stateManager = new StateManager();
		return stateManager;
	}
	
	public static void initializeStates(GameHandler handler) {
		gameState = new GameState(handler);
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(State newState) {
		currentState = newState;
	}
	
}
