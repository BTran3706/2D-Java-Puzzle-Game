package PuzzleGame.SourceFiles.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyManager implements KeyListener {
	
	private final Map<String, Boolean> inputs;

	public KeyManager() {

		inputs = new HashMap<String, Boolean>();
		addInputs();

	}

	@Override
	public void keyPressed(final KeyEvent e) {

		setInputToTrue(e);

	}

	@Override
	public void keyReleased(final KeyEvent e) {

		setInputToFalse(e);

	}

	private void addInputs() {

		inputs.put("Up", false);
		inputs.put("Down", false);
		inputs.put("Left", false);
		inputs.put("Right", false);
		inputs.put("W", false);
		inputs.put("A", false);
		inputs.put("S", false);
		inputs.put("D", false);
		inputs.put("ResetLevel", false);
		inputs.put("UndoMove", false);
		inputs.put("Pause", false);

	}

	private void setInputToTrue(final KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				inputs.replace("Up", true);
				break;
			case KeyEvent.VK_DOWN:
				inputs.replace("Down", true);
				break;
			case KeyEvent.VK_LEFT:
				inputs.replace("Left", true);
				break;
			case KeyEvent.VK_RIGHT:
				inputs.replace("Right", true);
				break;
			case KeyEvent.VK_W:
				inputs.replace("W", true);
				break;
			case KeyEvent.VK_A:
				inputs.replace("A", true);
				break;
			case KeyEvent.VK_S:
				inputs.replace("S", true);
				break;
			case KeyEvent.VK_D:
				inputs.replace("D", true);
				break;
			case KeyEvent.VK_SPACE:
				inputs.replace("ResetLevel", true);
				break;
			case KeyEvent.VK_BACK_SPACE:
				inputs.replace("UndoMove", true);
				break;
			case KeyEvent.VK_ESCAPE:
				inputs.replace("Pause", true);
				break;
		}

	}

	private void setInputToFalse(final KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				inputs.replace("Up", false);
				break;
			case KeyEvent.VK_DOWN:
				inputs.replace("Down", false);
				break;
			case KeyEvent.VK_LEFT:
				inputs.replace("Left", false);
				break;
			case KeyEvent.VK_RIGHT:
				inputs.replace("Right", false);
				break;
			case KeyEvent.VK_W:
				inputs.replace("W", false);
				break;
			case KeyEvent.VK_A:
				inputs.replace("A", false);
				break;
			case KeyEvent.VK_S:
				inputs.replace("S", false);
				break;
			case KeyEvent.VK_D:
				inputs.replace("D", false);
				break;
			case KeyEvent.VK_SPACE:
				inputs.replace("ResetLevel", false);
				break;
			case KeyEvent.VK_BACK_SPACE:
				inputs.replace("UndoMove", false);
				break;
			case KeyEvent.VK_ESCAPE:
				inputs.replace("Pause", false);
				break;
		}

	}

	public Map<String, Boolean> getInputs() {

		return inputs;

	}

	@Override
	public void keyTyped(final KeyEvent e) {

	}
	
}