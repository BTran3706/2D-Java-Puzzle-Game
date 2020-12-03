package PuzzleGame.SourceFiles.Graphics;

import java.awt.image.BufferedImage;

public class Animation {
	
	private final BufferedImage[] animFrames;
	private final int changeFrameSpeed;
	private int updateTime, index;
	private long beforeUpdate;

	public Animation(final BufferedImage[] animFrames) {
		
		this.animFrames = animFrames;
		changeFrameSpeed = 150;
		
	}
	
	public void update() {
		
		cycleThroughFrames();
		
	}

	private void cycleThroughFrames() {

		updateTime += System.currentTimeMillis() - beforeUpdate;
		
		if (updateTime >= changeFrameSpeed) {
			index++;
			if (index == animFrames.length) {
				index = 0;
			}
			updateTime = 0;
		}
		
		beforeUpdate = System.currentTimeMillis();

	}
	
	public BufferedImage getCurrentFrame() {
		
		return animFrames[index];
		
	}
	
	public BufferedImage resetIndex() {
		
		return animFrames[index = 0];
		
	}

}