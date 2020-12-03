package PuzzleGame.SourceFiles.States;

import java.awt.Color;
import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;
import PuzzleGame.SourceFiles.UserInterface.ClickListener;
import PuzzleGame.SourceFiles.UserInterface.TextButton;

public class MenuState extends State {

	private final TextButton[] textButtons;

	public MenuState(final Game game) {

		super(game);

		final int textButtonsSize = 4;
		textButtons = new TextButton[textButtonsSize];
		addTextButtons();

	}

	@Override
	public void update() {

		for (final TextButton textButton : textButtons) {
			textButton.update();
		}

	}

	@Override
	public void render(final Graphics image) {
		
		image.setFont(game.getAssets().getFont("Impact75"));
		game.getAssets().drawString(image, game.getGameName(), game.getWidth() / 2, game.getHeight() / 2 - spacing * 3, true, Color.green);

		for (final TextButton textButton : textButtons) {
			textButton.render(image);
		}

	}

	private void addTextButtons() {

		final TextButton playButton = new TextButton(game, game.getAssets().getFont("Impact50"), "PLAY", game.getWidth() / 2, game.getHeight() / 2,

			new ClickListener() {

				@Override
				public void onClick() {

					game.setState(game.getState("Game"));

				}

			}

		);

		final TextButton controlsButton = new TextButton(game, game.getAssets().getFont("Impact50"), "CONTROLS", game.getWidth() / 2, game.getHeight() / 2 + spacing * 2,

			new ClickListener() {

				@Override
				public void onClick() {

					game.setState(game.getState("Controls"));

				}

			}

		);

		final TextButton creditsButton = new TextButton(game, game.getAssets().getFont("Impact50"), "CREDITS", game.getWidth() / 2, game.getHeight() / 2 + spacing * 4,

			new ClickListener() {

				@Override
				public void onClick() {

					game.setState(game.getState("Credits"));

				}

			}

		);

		final TextButton exitButton = new TextButton(game, game.getAssets().getFont("Impact50"), "EXIT", game.getWidth() / 2, game.getHeight() / 2 + spacing * 6,
		
			new ClickListener() {

				@Override
				public void onClick() {
				
					System.exit(0);
				
				}
			
			}
		
		);
		
		textButtons[0] = playButton;
		textButtons[1] = controlsButton;
		textButtons[2] = creditsButton;
		textButtons[3] = exitButton;

	}

}