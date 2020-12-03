package PuzzleGame.SourceFiles.States;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;
import PuzzleGame.SourceFiles.Graphics.Tile;
import PuzzleGame.SourceFiles.Levels.Level;
import PuzzleGame.SourceFiles.Levels.LevelTimer;
import PuzzleGame.SourceFiles.UserInterface.ClickListener;
import PuzzleGame.SourceFiles.UserInterface.TextButton;

public class GameState extends State {
	
	private Level level;
	private int levelNum;
	// private int levelNotCompleted;
	private final Tile tile = Tile.getStaticInstance();

	private final LevelTimer levelTimer;
	private boolean running;

	private final TextButton[] textButtons;

	public GameState(final Game game) {

		super(game);

		levelNum = 1;
		level = new Level(game, levelNum);
		levelTimer = new LevelTimer(game);

		final int textButtonsSize = 3;
		textButtons = new TextButton[textButtonsSize];
		addTextButtons();

	}

	@Override
	public void update() {

		if (!running) {
			levelTimer.startTimer();
			running = true;
		}

		for (final TextButton textButton : textButtons) {
			textButton.update();
		}

		if (game.getKeyManager().getInputs().get("ResetLevel")) {
			level = new Level(game, levelNum);
			level.getEntityManager().getPlayer().resetCountMoves();
			levelTimer.resetTimer();
		}
		
		level.update();

	}

	@Override
	public void render(final Graphics image) {
		
		for (final TextButton textButton : textButtons) {
			textButton.render(image);
		}

		displayText(image);

		image.translate((int) (game.getWidth() - level.getWidth() * tile.getTileWidth()) / 2, (int) (game.getHeight() - level.getHeight() * tile.getTileHeight()) / 2);
		level.render(image);
		
		displayPaused(image);

	}

	private void addTextButtons() {

		final TextButton nextButton = new TextButton(game, game.getAssets().getFont("Impact50"), "NEXT", game.getWidth() - level.getWidth() * tile.getTileWidth() / 4, spacing,

			new ClickListener() {

				@Override
				public void onClick() {

					final int lastLevelNum = 20;

					if (levelNum + 1 <= lastLevelNum) {
						// if (level.getLevelSolved() || levelNum < levelNotCompleted) {
							levelNum++;
							// if (levelNotCompleted < levelNum) {
								// levelNotCompleted = levelNum;
							// }
							level = new Level(game, levelNum);
							level.getEntityManager().getPlayer().resetCountMoves();
							levelTimer.resetTimer();
						// }
					}

				}

			}

		);

		final TextButton backButton = new TextButton(game, game.getAssets().getFont("Impact50"), "BACK", level.getWidth() * tile.getTileWidth() / 4, spacing,

			new ClickListener() {

				@Override
				public void onClick() {
					
					if (levelNum - 1 > 0) {
						levelNum--;
						level = new Level(game, levelNum);
						level.getEntityManager().getPlayer().resetCountMoves();
						levelTimer.resetTimer();
					}

				}

			}

		);

		final TextButton exitButton = new TextButton(game, game.getAssets().getFont("Impact50"), "EXIT", game.getWidth() / 2, game.getHeight() - spacing,
		
			new ClickListener() {

				@Override
				public void onClick() {
				
					System.exit(0);
				
				}
			
			}
		
		);

		textButtons[0] = nextButton;
		textButtons[1] = backButton;
		textButtons[2] = exitButton;

	}

	private void displayText(final Graphics image) {

		final String levelNumString = "LEVEL " + levelNum;
		final String countMoves = "MOVES: " + level.getEntityManager().getPlayer().getCountMoves();
		final FontMetrics metrics = image.getFontMetrics();

		game.getAssets().drawString(image, levelTimer.getTimerString(), game.getWidth() / 2, spacing, true, Color.white);
		game.getAssets().drawString(image, levelNumString, spacing / 2, game.getHeight() - spacing / 2, false, Color.white);
		game.getAssets().drawString(image, countMoves, game.getWidth() - metrics.stringWidth(countMoves) - spacing / 2, game.getHeight() - spacing / 2, false, Color.white);

	}

	private void displayPaused(final Graphics image) {

		if (game.getPaused()) {
			image.setFont(game.getAssets().getFont("Impact100"));
			game.getAssets().drawString(image, "PAUSED", (int) (level.getWidth() * tile.getTileWidth() / 2), (int) (level.getHeight() * tile.getTileHeight() / 2), true, Color.red);
			if (game.getKeyManager().getInputs().get("Pause")) {
				game.setPausedToFalse();
				game.getKeyManager().getInputs().replace("Pause", false);
			}
		}
		else if (game.getKeyManager().getInputs().get("Pause") && !level.getLevelSolved()) {
			game.setPausedToTrue();
			game.getKeyManager().getInputs().replace("Pause", false);
		}

	} 

	public Level getLevel() {

		return level;

	}

}