package PuzzleGame.SourceFiles.Levels;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import PuzzleGame.SourceFiles.Game;
import PuzzleGame.SourceFiles.Entities.EntityManager;
import PuzzleGame.SourceFiles.Entities.Player;
import PuzzleGame.SourceFiles.Entities.Star;
import PuzzleGame.SourceFiles.Entities.Target;
import PuzzleGame.SourceFiles.Graphics.Tile;

public class Level {

	private final Game game;
	private final char[][] levelMap;
	private final EntityManager entityManager;
	private final Tile tile = Tile.getStaticInstance();
	private boolean levelSolved;
	
	public Level(final Game game, final int levelNum) {

		this.game = game;

		final StringBuilder seperateNums = new StringBuilder();
		final StringBuilder seperateChars = new StringBuilder();
		loadLevel(levelNum, seperateNums, seperateChars);

		final String[] widthHeightAndSpawn = seperateNums.toString().split("\\s+");
		final char[] levelFile = seperateChars.toString().replaceAll("\\s+", "").toCharArray();
		levelMap = createLevelMap(widthHeightAndSpawn);
		entityManager = createEntityManager(widthHeightAndSpawn);
		addTilesAndEntities(levelFile);

	}

	public void update() {

		entityManager.update();

	}

	public void render(final Graphics image) {

		for (int y = 0; y < levelMap[0].length; y++) {
			for (int x = 0; x < levelMap.length; x++) {
				getTile(x, y).render(image, x * tile.getTileWidth(), y * tile.getTileHeight());
			}
		}

		entityManager.render(image);
		
		if (checkLevelSolved()) {
			image.setFont(game.getAssets().getFont("Impact100"));
			game.getAssets().drawString(image, "SOLVED", (int) (levelMap.length * tile.getTileWidth() / 2), (int) (levelMap[0].length * tile.getTileHeight() / 2), true, Color.green);
		}

	}

	private void loadLevel(final int levelNum, final StringBuilder seperateNumbers, final StringBuilder seperateCharacters) {

		try {
			final BufferedReader reader = new BufferedReader(new FileReader("PuzzleGame/ResourceFiles/Levels/Level" + levelNum + ".txt"));
			final int rowsWithNums = 2;
			for (int i = 0; i < rowsWithNums; i++) {
				seperateNumbers.append(reader.readLine() + "\n");
			}
			String line;
			while ((line = reader.readLine()) != null) {
				seperateCharacters.append(line);
			}
			reader.close();
		} 
		catch (final IOException e) {
			e.printStackTrace();
		}

	}

	private char[][] createLevelMap(final String[] widthHeightAndSpawn) {

		final int widthIndex = 0, heightIndex = 1;
		final int width = Integer.parseInt(widthHeightAndSpawn[widthIndex]);
		final int height = Integer.parseInt(widthHeightAndSpawn[heightIndex]);
		
		return new char[width][height];

	}

	private EntityManager createEntityManager(final String[] widthHeightAndSpawn) {

		final int spawnXIndex = 2, spawnYIndex = 3;
		final int spawnX = Integer.parseInt(widthHeightAndSpawn[spawnXIndex]);
		final int spawnY = Integer.parseInt(widthHeightAndSpawn[spawnYIndex]);

		final Player player = new Player(game, spawnX * tile.getTileWidth(), spawnY * tile.getTileHeight());
		
		return new EntityManager(player);

	}

	private void addTilesAndEntities(final char[] levelFile) {

		for (int y = 0; y < levelMap[0].length; y++) {
			for (int x = 0; x < levelMap.length; x++) {
				final char starID = 'S', targetID = 'T', starAndTargetID = '&';
				final Star star;
				final Target target;
				levelMap[x][y] = levelFile[x + y * levelMap.length];
				switch (levelMap[x][y]) {
					case starID:
						star = new Star(game, x * tile.getTileWidth(), y * tile.getTileHeight());
						entityManager.addStar(star);
						break;
					case targetID:
						target = new Target(game, x * tile.getTileWidth(), y * tile.getTileHeight());
						entityManager.addTarget(target);
						break;
					case starAndTargetID:
						star = new Star(game, x * tile.getTileWidth(), y * tile.getTileHeight());
						target = new Target(game, x * tile.getTileWidth(), y * tile.getTileHeight());
						entityManager.addStar(star);
						entityManager.addTarget(target);
						break;
				}
			}
		}

	}

	private boolean checkLevelSolved() {
		
		if (!levelSolved) {
			int targetsCompleted = 0;
			for (int i = 0; i < entityManager.getStars().size(); i++) {
				for (int j = 0; j < entityManager.getTargets().size(); j++) {
					if (entityManager.getStars().get(i).getX() == entityManager.getTargets().get(j).getX() && 
					    entityManager.getStars().get(i).getY() == entityManager.getTargets().get(j).getY()) {
						targetsCompleted++;
					}
				}	
			}
			if (targetsCompleted == entityManager.getTargets().size()) {
				game.playMusic("LevelSolved.wav");
				levelSolved = true;
			}
		}

		return levelSolved;
		
	}

	public Tile getTile(final int x, final int y) {

		if (!game.getAssets().getTiles().containsKey(levelMap[x][y])) {
			final char floorKey = 'F';
			return game.getAssets().getTiles().get(floorKey);
		}

		return game.getAssets().getTiles().get(levelMap[x][y]);
		
	}
	
	public int getWidth() {
		
		return levelMap.length;
		
	}
	
	public int getHeight() {
		
		return levelMap[0].length;
		
	}

	public EntityManager getEntityManager() {
		
		return entityManager;
		
	}
	
	public boolean getLevelSolved() {
		
		return levelSolved;
		
	}

}