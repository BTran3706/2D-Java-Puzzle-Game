package PuzzleGame.SourceFiles.Entities;

import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;

public class Star extends Entity {

	public Star(final Game game, final float x, final float y) {

		super(game, x, y);

	}

	@Override
	void render(final Graphics image) {

		image.drawImage(game.getAssets().getEntityImage("Star"), (int) x, (int) y, (int) entityWidth, (int) entityHeight, null);
		
	}
	
}