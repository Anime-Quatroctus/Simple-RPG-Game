package dev.anime.rpg.base.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.anime.rpg.base.data.ISaveable;
import dev.anime.rpg.base.data.SaveManager;
import dev.anime.rpg.base.states.StateManager;

public class KeyManager implements KeyListener, ISaveable {
	
	private String[] saveKeys = new String[]{"UP", "DOWN", "LEFT", "RIGHT", "ACTIVATE"};
	private Integer[] keyBindings = new Integer[]{KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER};
	
	/** Array of booleans determining if a key was pressed. **/
	public static boolean[] PRESSED_KEYBINDS = new boolean[KeyConstants.TOTAL_KEYBINDS];
	
	public KeyManager() {
		SaveManager.load(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < keyBindings.length; i++) {
			if (e.getKeyCode() == keyBindings[i]) {
				PRESSED_KEYBINDS[i] = true;
			}
		}
		if (StateManager.getInstance().getCurrentState() != null) StateManager.getInstance().getCurrentState().keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < keyBindings.length; i++) {
			if (e.getKeyCode() == keyBindings[i]) {
				PRESSED_KEYBINDS[i] = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	public void setKeyBind(int position, int newKey) {
		this.keyBindings[position] = newKey;
		SaveManager.save(this);
	}
	
	public static String getKeyName(int keyCode) {
		return KeyEvent.getKeyText(keyCode);
	}
	
	@Override
	public boolean isGlobal() {
		return true;
	}
	
	@Override
	public String getFileName() {
		return "keybinds";
	}
	
	@Override
	public String getFileDir() {
		return "";
	}

	@Override
	public String[] getSaveTags() {
		return saveKeys;
	}

	@Override
	public Object[] getSaveData() {
		return keyBindings;
	}

	@Override
	public void setDataFromLoad(Object[] newData) {
		for (int i = 0; i < newData.length; i++) {
			if (newData[i] != null) keyBindings[i] = (Integer) newData[i];
		}
	}
	
	public class KeyConstants {
		
		/** Positions in the KeyManager.PRESSED_KEYBINDS. **/
		public static final int TOTAL_KEYBINDS = 5, UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, ACTIVATE = 4;
		
	}
}
