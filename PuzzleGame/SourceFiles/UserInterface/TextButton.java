package PuzzleGame.SourceFiles.UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;

public class TextButton {
	
	private final Game game;
	private final Font font;
	private final String text;
	private final float x, y;
	private final ClickListener click;
	private Rectangle2D bounds;
	private boolean hover;

	public TextButton(final Game game, final Font font, final String text, final float x, final float y, final ClickListener click) {

		this.game = game;
		this.font = font;
		this.text = text;
		this.x = x;
		this.y = y;
		this.click = click;

	}

	public void update() {
		
		if (bounds.contains(game.getMouseManager().getMouseX(), game.getMouseManager().getMouseY())) {
			hover = true;
			if (game.getMouseManager().getLeftPressed()) {
				game.getMouseManager().setLeftPressedToFalse();
				click.onClick();
			}
		} 
		else {
			hover = false;
		}

	}

	public void render(final Graphics image) {

		image.setFont(font);
		final FontMetrics metrics = image.getFontMetrics();
		
		bounds = new Rectangle2D.Float(x - metrics.stringWidth(text) / 2, y - metrics.getHeight() / 2, metrics.stringWidth(text), metrics.getHeight());
		
		if (hover) {
			game.getAssets().drawString(image, text, (int) x, (int) y, true, Color.white);
		}
		else {
			game.getAssets().drawString(image, text, (int) x, (int) y, true, Color.red);
		}
		
	}
	
}