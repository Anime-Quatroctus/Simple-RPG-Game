package dev.anime.rpg.base.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import dev.anime.rpg.base.states.StateManager;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private int mouseX = 0, mouseY = 0;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	// TODO Pause game when mouse leaves and un pause game when mouse enters.
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (StateManager.getInstance().getCurrentState() != null) StateManager.getInstance().getCurrentState().mouseClicked(e.getButton(), e.getX(), e.getY());
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
}
