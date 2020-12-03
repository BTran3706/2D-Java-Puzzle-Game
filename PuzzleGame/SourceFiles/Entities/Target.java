package PuzzleGame.SourceFiles.Entities;

import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;

public class Target extends Entity {

	public Target(final Game game, final float x, final float y) {

		super(game, x, y);

	}

	@Override
	void render(final Graphics image) {

		image.drawImage(game.getAssets().getEntityImage("Target"), (int) x, (int) y, (int) entityWidth, (int) entityHeight, null);
		
	}
	
}