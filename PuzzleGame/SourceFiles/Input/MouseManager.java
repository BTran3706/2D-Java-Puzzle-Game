package PuzzleGame.SourceFiles.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

	private boolean leftPressed;
	private int mouseX, mouseY;
	
	@Override
	public void mousePressed(final MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
		}

	}

	@Override
	public void mouseMoved(final MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();

	}

	public boolean getLeftPressed() {

		return leftPressed;

	}

	public void setLeftPressedToFalse() {

		leftPressed = false;

	}

	public int getMouseX() {

		return mouseX;

	}

	public int getMouseY() {

		return mouseY;

	}

	@Override
	public void mouseDragged(final MouseEvent e) {

	}

	@Override
	public void mouseClicked(final MouseEvent e) {

	}

	@Override
	public void mouseEntered(final MouseEvent e) {

	}

	@Override
	public void mouseExited(final MouseEvent e) {
		
	}
	
}