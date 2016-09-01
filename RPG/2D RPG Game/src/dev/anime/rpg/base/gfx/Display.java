package dev.anime.rpg.base.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import dev.anime.rpg.base.Game;

public class Display {
	
	private Game game;
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private Dimension displayDimension;
	
	public Display(Game game, String title, int width, int height) {
		this.game = game;
		this.title = title;
		displayDimension = new Dimension(width, height);
		
		this.createJFrame();
	}
	
	private void createJFrame() {
		frame = new JFrame(title);
		frame.setSize(displayDimension.width, displayDimension.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				game.saveGameData();
				super.windowClosing(e);
			}
		});
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(displayDimension);
		canvas.setMaximumSize(displayDimension);
		canvas.setMinimumSize(displayDimension);
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
}
