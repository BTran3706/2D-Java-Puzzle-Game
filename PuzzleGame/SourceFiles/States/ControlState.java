package PuzzleGame.SourceFiles.States;

import java.awt.Color;
import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;
import PuzzleGame.SourceFiles.UserInterface.ClickListener;
import PuzzleGame.SourceFiles.UserInterface.TextButton;

public class ControlState extends State {

	private final TextButton textButton;

	public ControlState(final Game game) {

		super(game);

		textButton = new TextButton(game, game.getAssets().getFont("Impact50"), "BACK", game.getWidth() / 2, game.getHeight() - spacing, 
		
			new ClickListener() {

				@Override
				public void onClick() {

					game.setState(game.getState("Menu"));

				}

			}
			
		);

	}

	@Override
	public void update() {

		textButton.update();

	}

	@Override
	public void render(final Graphics image) {
		
		image.setFont(game.getAssets().getFont("Impact50"));
		game.getAssets().drawString(image, "HOW TO PLAY", game.getWidth() / 2, spacing, true, Color.white);
		textButton.render(image);
		
	}
	
}