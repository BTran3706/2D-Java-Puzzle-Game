package PuzzleGame.SourceFiles.States;

import java.awt.Color;
import java.awt.Graphics;

import PuzzleGame.SourceFiles.Game;

public class LoadingState extends State {
		
	private String text;
	private int updateTime, index;
	private long beforeUpdate;
		
	public LoadingState(final Game game) {

		super(game);

	}

	@Override
	public void update() {

		final int waitToAddLetter = 150;

		updateTime += System.currentTimeMillis() - beforeUpdate;

		if (updateTime >= waitToAddLetter) {
			final String name = "DEVELOPED BY BRIAN TRAN";
			if (index <= name.length()) {
				text = name.substring(0, index);
				index++;
				updateTime = 0;
			}
			else {
				changeToMenuState();
			}
		}

		beforeUpdate = System.currentTimeMillis();

	}

	@Override
	public void render(final Graphics image) {
		
		image.setFont(game.getAssets().getFont("Impact75"));
		game.getAssets().drawString(image, text, game.getWidth() / 2, game.getHeight() / 2, true, Color.green);

	}

	private void changeToMenuState() {

		final int waitToChangeState = 1000;

		updateTime = 0;
		beforeUpdate = System.currentTimeMillis();

		while (updateTime <= waitToChangeState) {
			final long afterUpdate = System.currentTimeMillis();
			updateTime += afterUpdate - beforeUpdate;
			beforeUpdate = afterUpdate;
		}

		game.setState(game.getState("Menu"));

	}

}