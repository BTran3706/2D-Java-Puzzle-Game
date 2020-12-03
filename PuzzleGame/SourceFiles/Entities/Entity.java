package PuzzleGame.SourceFiles.Entities;

import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;

abstract class Entity {

	final Game game;
	float x, y;
	final float entityWidth, entityHeight;

	Entity(final Game game, final float x, final float y) {

		this.game = game;
		this.x = x;
		this.y = y;
		entityWidth = entityHeight = 49.9f;

	}

	abstract void render(final Graphics image);

	Rectangle2D boundsIfMove(final float xMove, final float yMove) {
		
		return new Rectangle2D.Float(x + xMove, y + yMove, entityWidth, entityHeight);
		
	}
	
	public float getX() {
		
		return x;
		
	}

	public float getY() {
		
		return y;
		
	}

}