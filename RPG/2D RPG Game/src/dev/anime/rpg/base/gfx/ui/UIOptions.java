package dev.anime.rpg.base.gfx.ui;

import java.awt.Graphics;

import dev.anime.rpg.base.GameHandler;
import dev.anime.rpg.base.states.Button;

public class UIOptions extends UI {

	private final UI pastUI;
	
	public UIOptions(GameHandler handler, UI pastUI) {
		super(handler);
		this.pastUI = pastUI;
	}

	public void createButtons() {
		
	}
	
	@Override
	public void applyButtonEffect(int ID) {
		Button button = buttons.get(ID);
		if (button instanceof ScrollableButton) {
//			ScrollableButton scrollButton = ((ScrollableButton) button);
			switch (ID) {
			case 0: {
				// TODO Create need for scrollable buttons
			}
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public void tickUI() {
		
	}

	@Override
	public void renderUI(Graphics g) {
		
	}

	@Override
	public boolean pausesEntities() {
		return true;
	}

	@Override
	public boolean pausesState() {
		return true;
	}

}
