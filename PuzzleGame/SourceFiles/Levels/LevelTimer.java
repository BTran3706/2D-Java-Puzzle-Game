package PuzzleGame.SourceFiles.Levels;

import java.util.Timer;
import java.util.TimerTask;

import PuzzleGame.SourceFiles.Game;
import PuzzleGame.SourceFiles.States.GameState;

public class LevelTimer {
	
	private final Game game;
	private final Timer levelTimer;
	private TimerTask updateTime;
	private int secs;

	public LevelTimer(final Game game) {

		this.game = game;
		levelTimer = new Timer();
		
	}

	public void startTimer() {

		updateTime = new TimerTask() {
			
			public void run() {

				if (!((GameState) game.getCurrentState()).getLevel().getLevelSolved()) {
					if (!game.getPaused()) {
						secs++;
					}
				}
				else {
					updateTime.cancel();
				}

			}

		};

		final int secToMilli = 1000;
		levelTimer.schedule(updateTime, secToMilli, secToMilli);
		
	}

	public void resetTimer() {
		
		secs = 0;
		updateTime.cancel();
        startTimer();

	}

	public String getTimerString() {
		
		final int secToMin = 60, secToHour = 3600;
		
		return String.format("%02d:%02d:%02d", secs / secToHour, secs % secToHour / secToMin, secs % secToMin);
		
	}

}