package PuzzleGame.SourceFiles.Graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	private static Tile tile;
	private final BufferedImage texture;
	private final boolean solid;
	private final float tileWidth, tileHeight;
	
	Tile(final BufferedImage texture, final boolean solid) {

		this.texture = texture;
		this.solid = solid;
		tileWidth = tileHeight = 50;
		tile = this;

	}

	public void render(final Graphics image, final float x, final float y) {
		
		image.drawImage(texture, (int) x, (int) y, (int) tileWidth, (int) tileHeight, null);
		
	}

	public static Tile getStaticInstance() {

		return tile;

	}
	
	public boolean getSolid() {
		
		return solid;
		
	}
	
	public float getTileWidth() {
		
		return tileWidth;
		
	}

	public float getTileHeight() {
	
		return tileHeight;
	
	}
	
}