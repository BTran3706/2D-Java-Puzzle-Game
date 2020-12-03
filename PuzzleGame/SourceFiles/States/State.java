package PuzzleGame.SourceFiles.States;

import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;

public abstract class State {

	final Game game;
	final int spacing;

	State(final Game game) {
		
		this.game = game;
		spacing = 50;
		
	}
	
	public abstract void update();
	public abstract void render(final Graphics image);

}