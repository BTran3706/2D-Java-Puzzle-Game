package PuzzleGame.SourceFiles;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import PuzzleGame.SourceFiles.Graphics.Assets;
import PuzzleGame.SourceFiles.Input.KeyManager;
import PuzzleGame.SourceFiles.Input.MouseManager;
import PuzzleGame.SourceFiles.States.ControlState;
import PuzzleGame.SourceFiles.States.CreditState;
import PuzzleGame.SourceFiles.States.GameState;
import PuzzleGame.SourceFiles.States.LoadingState;
import PuzzleGame.SourceFiles.States.MenuState;
import PuzzleGame.SourceFiles.States.State;

public class Game {
	
	public static void main(final String[] args) {

		final Game game = new Game();
		game.start();

	}

	private final String gameName;
	private final Canvas canvas;
	private final JFrame frame;
	private final KeyManager keyManager;
	private final MouseManager mouseManager;

	private final Assets assets;
	private final Map<String, State> states;
	private State currentState;
	private boolean paused;

	private Game() {
		
		gameName = "STARPUSHER";
		canvas = new Canvas();
		frame = new JFrame(gameName);
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		createWindow();
		
		assets = new Assets();
		states = new HashMap<String, State>();
		addStates();
		currentState = states.get("Loading");

		playMusic("Background.wav");

	}

	public void start() {

		boolean running = true;
		
		final int maxFPS = 120;
		final int targetTime = 1000000000 / maxFPS;
		int updateTime = 0;
		long beforeUpdate = System.nanoTime();

		while (running) {
			final long afterUpdate = System.nanoTime();
			updateTime += afterUpdate - beforeUpdate;
			while (updateTime >= targetTime) {
				if (!paused) {
					update();
				}
				updateTime -= targetTime;
			}
			render();
			beforeUpdate = afterUpdate;
		}

	}

	private void update() {

		currentState.update();

	}

	private void render() {

		final BufferStrategy buffer = canvas.getBufferStrategy();
		final Graphics image = buffer.getDrawGraphics();
		image.setColor(Color.blue);
		image.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		currentState.render(image);
		buffer.show();
		image.dispose();

	}

	private void addStates() {

		final State loadingState = new LoadingState(this);
		final State menuState = new MenuState(this);
		final State gameState = new GameState(this);
		final State controlState = new ControlState(this);
		final State creditState = new CreditState(this);

		states.put("Loading", loadingState);
		states.put("Menu", menuState);
		states.put("Game", gameState);
		states.put("Controls", controlState);
		states.put("Credits", creditState);

	}
	
	private void createWindow() {

		canvas.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		canvas.setFocusable(false);
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true); 
		frame.setVisible(true);
		frame.add(canvas);
		frame.pack();
		frame.addKeyListener(keyManager);
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);

		final int bufferNum = 2;
		canvas.createBufferStrategy(bufferNum);

	}

	public void playMusic(final String musicName) {

		final File musicFile = new File("PuzzleGame/ResourceFiles/Audio/" + musicName);

		try {
			final AudioInputStream audio = AudioSystem.getAudioInputStream(musicFile);
			final Clip clip = AudioSystem.getClip();
			clip.open(audio);
			if (musicName.equals("Background.wav")) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} 
			else {
				clip.start();
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		
	}

	public String getGameName() {

		return gameName;

	}

	public int getWidth() {

		return frame.getWidth();

	}

	public int getHeight() {

		return frame.getHeight();

	}

	public KeyManager getKeyManager() {

		return keyManager;

	}

	public MouseManager getMouseManager() {

		return mouseManager;

	}

	public Assets getAssets() {

		return assets;

	}

	public State getState(final String key) {

		return states.get(key);

	}

	public State getCurrentState() {

		return currentState;

	}

	public void setState(final State state) {

		currentState = state;

	}

	public boolean getPaused() {

		return paused;

	}

	public void setPausedToTrue() {

		paused = true;

	}

	public void setPausedToFalse() {

		paused = false;

	}

}