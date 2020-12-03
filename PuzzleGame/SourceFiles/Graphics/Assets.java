package PuzzleGame.SourceFiles.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Assets {

	private final Map<String, BufferedImage[]> playerAnims;
	private final Map<String, BufferedImage> entityImages;
	private final Map<Character, Tile> tiles;
	private final Map<String, Font> fonts;

	public Assets() {

		final BufferedImage spriteSheet = loadImage("PuzzleGame/ResourceFiles/Textures/Spritesheet.png");

		playerAnims = new HashMap<String, BufferedImage[]>();
		addAnimations(spriteSheet);

		entityImages = new HashMap<String, BufferedImage>();
		addEntityImages(spriteSheet);

		tiles = new HashMap<Character, Tile>();
		addTiles(spriteSheet);

		fonts = new HashMap<String, Font>();
		addFonts();

	}

	private BufferedImage loadImage(final String fileName) {

		try {
			return ImageIO.read(new File(fileName));
		} 
		catch (final IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	private void addAnimations(final BufferedImage spriteSheet) {

		final int playerFramesNum = 3;
		final int xWhiteSpace = 10, yStart = 65, width = 35, height = 36;

		final BufferedImage[] playerUp = new BufferedImage[playerFramesNum];
		playerUp[0] = spriteSheet.getSubimage(width + xWhiteSpace, yStart + 2 * height, width, height);
		playerUp[1] = spriteSheet.getSubimage(0, yStart + 2 * height, width, height);
		playerUp[2] = spriteSheet.getSubimage(2 * (width + xWhiteSpace), yStart + 2 * height, width, height);
		
		final BufferedImage[] playerDown = new BufferedImage[playerFramesNum];
		playerDown[0] = spriteSheet.getSubimage(width + xWhiteSpace, yStart, width, height);
		playerDown[1] = spriteSheet.getSubimage(0, yStart, width, height);
		playerDown[2] = spriteSheet.getSubimage(2 * (width + xWhiteSpace), yStart, width, height);

		final BufferedImage[] playerLeft = new BufferedImage[playerFramesNum];
		playerLeft[0] = spriteSheet.getSubimage(width + xWhiteSpace, yStart + 3 * height, width, height);
		playerLeft[1] = spriteSheet.getSubimage(0, yStart + 3 * height, width, height);
		playerLeft[2] = spriteSheet.getSubimage(2 * (width + xWhiteSpace), yStart + 3 * height, width, height);

		final BufferedImage[] playerRight = new BufferedImage[playerFramesNum];
		playerRight[0] = spriteSheet.getSubimage(width + xWhiteSpace, yStart + height, width, height);
		playerRight[1] = spriteSheet.getSubimage(0, yStart + height, width, height);
		playerRight[2] = spriteSheet.getSubimage(2 * (width + xWhiteSpace), yStart + height, width, height);

		playerAnims.put("Up", playerUp);
		playerAnims.put("Down", playerDown);
		playerAnims.put("Left", playerLeft);
		playerAnims.put("Right", playerRight);

	}

	private void addEntityImages(final BufferedImage spriteSheet) {
		
		final int yWhiteSpace = 25, width = 50, targetHeight = 40, starHeight = 50;
		
		final BufferedImage target = spriteSheet.getSubimage(width, yWhiteSpace, width, targetHeight);
		final BufferedImage star = spriteSheet.getSubimage(2 * width, 0, width, starHeight);

		entityImages.put("Target", target);
		entityImages.put("Star", star);

	}

	private void addTiles(final BufferedImage spriteSheet) {

		final int width = 50, height = 50;

		final BufferedImage floorImage = spriteSheet.getSubimage(0, 0, width, height);
		final BufferedImage grassImage = spriteSheet.getSubimage(3 * width,  2 * height, width, height);
		final BufferedImage brickImage = spriteSheet.getSubimage(3 * width, height, width, height);
		final BufferedImage woodImage = spriteSheet.getSubimage(3 * width, 0, width, height);

		final Tile floor = new Tile(floorImage, false);
		final Tile grass = new Tile(grassImage, false);
		final Tile brick = new Tile(brickImage, true);
		final Tile wood = new Tile(woodImage, true);

		tiles.put('F', floor);
		tiles.put('G', grass);
		tiles.put('B', brick);
		tiles.put('W', wood);

	}
 
	private void addFonts() {

		final Font impact50 = new Font("Impact", Font.BOLD, 50);
		final Font impact75 = new Font("Impact", Font.BOLD, 75);
		final Font impact100 = new Font("Impact", Font.BOLD, 100);

		fonts.put("Impact50", impact50);
		fonts.put("Impact75", impact75);
		fonts.put("Impact100", impact100);

	}

	public void drawString(final Graphics image, final String text, int x, int y, final boolean center, final Color color) {

		if (text != null) {
			if (center) {
				final FontMetrics metrics = image.getFontMetrics();
				x -= metrics.stringWidth(text) / 2;
				y = (y - metrics.getHeight() / 2) + metrics.getAscent();
			}
			image.setColor(color);
			image.drawString(text, x, y);
		}

	}

	public BufferedImage[] getPlayerAnim(final String key) {

		return playerAnims.get(key);

	}

	public BufferedImage getEntityImage(final String key) {

		return entityImages.get(key);

	}

	public Map<Character, Tile> getTiles() {

		return tiles;

	}

	public Font getFont(final String key) {

		return fonts.get(key);

	}

}