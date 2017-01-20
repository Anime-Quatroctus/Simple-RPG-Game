package dev.anime.rpg.base;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import dev.anime.rpg.base.characters.Characters;
import dev.anime.rpg.base.data.SaveManager;
import dev.anime.rpg.base.gfx.Assets;
import dev.anime.rpg.base.gfx.Display;
import dev.anime.rpg.base.states.StateManager;
import dev.anime.rpg.base.world.WorldManager;
import dev.anime.rpg.base.world.tile.Tiles;

public class Game implements Runnable {
	
	/** How many ticks and frames per second. **/
	private static final int MAX_UPS = 30;
	
	/** Whether this game is running. **/
	private boolean running = false;
	
	/** The games Thread. **/
	private Thread thread;
	/** The Display for the game. **/ 
	private Display display;
	/** The BufferStrategy for the display. **/
	private BufferStrategy renderBuffer;
	/** The graphics object that is used to render objects to the display. **/
	private Graphics g;
	
	/** Title of the game. **/
	private String title;
	/** Screen width and height. **/
	private int width, height;
	
	/** GameHandler object for this game. **/
	private GameHandler handler;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	/** Used to initialize the games "assets". **/
	private void initResources() {
		display = new Display(this, title, width, height);
		Assets.initializeImages();
		Tiles.initializeTiles();
		
		StateManager.initializeStates(handler = new GameHandler(this));
		StateManager.getInstance().setCurrentState(StateManager.menuState);
	}
	
	/** The main game loop calls both {@link baseRender() render} and {@link gameTick() tick} **/
	@Override
	public void run() {
		initResources();
		double timePerTick = 1000000000 / MAX_UPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if(delta >= 1) {
				gameTick();
				baseRender();
				ticks++;
				delta--;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		saveGameData();
		stop();
	}
	
	/** Starts the thread. **/
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/** Saves game data when closed. **/
	public void saveGameData() {
		SaveManager.saveSaveables();
		SaveManager.save(handler.getKeyManager());
		SaveManager.save(WorldManager.getInstance().getCurrentWorld().getManager().getPlayer());
		SaveManager.save(WorldManager.getInstance().getCurrentWorld());
		SaveManager.save(Characters.MAIN_PROTANGONIST);
		System.out.println("Data saved on exit.");
	}
	
	/** Calld to terminate the thread. **/
	public synchronized void stop() {
		if (!running) return;
		try {
			running = false;
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** The base render method all rendering happens here. **/
	private void baseRender() {
		renderBuffer = display.getCanvas().getBufferStrategy();
		if (renderBuffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = renderBuffer.getDrawGraphics();
		
		// Clears screen to be render again.
		g.clearRect(0, 0, handler.getScreenWidth(), handler.getScreenHeight());
		
		if (StateManager.getInstance().getCurrentState() != null) StateManager.getInstance().getCurrentState().renderState(g);
		
		renderBuffer.show();
		g.dispose();
	}
	
	/** The base ticking method for this game all logic calculations happen here. **/
	private void gameTick() {
		if (StateManager.getInstance().getCurrentState() != null) StateManager.getInstance().getCurrentState().tickState();
	}
	
	//******************************************************Getters******************************************************//
	public int getScreenWidth() {
		return width;
	}
	
	public int getScreenHeight() {
		return height;
	}
	
	public JFrame getFrame() {
		return display.getFrame();
	}
	
	public Canvas getCanvas() {
		return display.getCanvas();
	}
	
}
