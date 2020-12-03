package PuzzleGame.SourceFiles.Entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
	
	private final Player player;
	private final List<Target> targets;
	private final List<Star> stars;

	public EntityManager(final Player player) {

		this.player = player;
		targets = new ArrayList<Target>();
		stars = new ArrayList<Star>();

	}

	public void update() {

		player.update();

	}

	public void render(final Graphics image) {

		for (final Target target : targets) {
			target.render(image);
		}

		for (final Star star : stars) {
			star.render(image);
		}

		player.render(image);

	}

	public void addTarget(final Target target) {

		targets.add(target);

	}

	public void addStar(final Star star) {
		
		stars.add(star);
		
	}

	public Player getPlayer() {

		return player;

	}
	
	public List<Target> getTargets() {
		
		return targets;
		
	}
	
	public List<Star> getStars() {
		
		return stars;
		
	}

}