package PuzzleGame.SourceFiles.Entities;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

import PuzzleGame.SourceFiles.Game;
import PuzzleGame.SourceFiles.Graphics.Animation;
import PuzzleGame.SourceFiles.Graphics.Tile;
import PuzzleGame.SourceFiles.States.GameState;

public class Player extends Entity {

	private final Deque<EntityCoordinates> playerAndStarXY;
	private final Animation animUp, animDown, animLeft, animRight;

	private final Tile tile = Tile.getStaticInstance();
	private final float speed;
	private float xMove, yMove, lastXMove, lastYMove;
	private int countMoves;

	private Star collidedStar;
	private boolean starCollision, starBlocked;

	public Player(final Game game, final float x, final float y) {

		super(game, x, y);

		final Point2D playerXY = new Point2D.Float(x, y);
		final EntityCoordinates coordinates = new EntityCoordinates(playerXY, null, null);
		playerAndStarXY = new ArrayDeque<EntityCoordinates>();
		playerAndStarXY.push(coordinates);

		animUp = new Animation(game.getAssets().getPlayerAnim("Up"));
		animDown = new Animation(game.getAssets().getPlayerAnim("Down"));
		animLeft = new Animation(game.getAssets().getPlayerAnim("Left"));
		animRight = new Animation(game.getAssets().getPlayerAnim("Right"));
		
		speed = 2;

	}

	@Override
	void render(final Graphics image) {

		image.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, (int) entityWidth, (int) entityHeight, null);

	}

	void update() {

		if (!((GameState) game.getCurrentState()).getLevel().getLevelSolved()) {
			getInput();
			if (xMove != 0 || yMove != 0) {
				move();
				if (yMove < 0) {
					animUp.update();
				} 
				else if (yMove > 0) {
					animDown.update();
				} 
				else if (xMove < 0) {
					animLeft.update();
				}
				else {
					animRight.update();
				}
			}
		}

	}

	private BufferedImage getCurrentAnimationFrame() {

		if (xMove != 0 || yMove != 0) {
			if (yMove < 0) {
				lastYMove = yMove;
				lastXMove = 0;
				return animUp.getCurrentFrame();
			}
			else if (yMove > 0) {
				lastYMove = yMove;
				lastXMove = 0;
				return animDown.getCurrentFrame();
			}
			else if (xMove < 0) {
				lastXMove = xMove;
				lastYMove = 0;
				return animLeft.getCurrentFrame();
			}
			else {
				lastXMove = xMove;
				lastYMove = 0;
				return animRight.getCurrentFrame();
			}
		}
		else if (lastXMove != 0 || lastYMove != 0) {
			if (lastYMove < 0) {
				return animUp.resetIndex();
			}
			else if (lastYMove > 0) {
				return animDown.resetIndex();
			}
			else if (lastXMove < 0) {
				return animLeft.resetIndex();
			}
			else {
				return animRight.resetIndex();
			}
		}
		
		return animDown.getCurrentFrame();
		
	}

	private void getInput() {
		
		if (x % tile.getTileWidth() == 0 && y % tile.getTileHeight() == 0) {
			xMove = yMove = 0;
			if (game.getKeyManager().getInputs().get("Up") || game.getKeyManager().getInputs().get("W")) {
				yMove = -speed;
			} 
			else if (game.getKeyManager().getInputs().get("Left") || game.getKeyManager().getInputs().get("A")) {
				xMove = -speed;
			}
			else if (game.getKeyManager().getInputs().get("Down") || game.getKeyManager().getInputs().get("S")) {
				yMove = speed;
			} 
			else if (game.getKeyManager().getInputs().get("Right") || game.getKeyManager().getInputs().get("D")) {
				xMove = speed;
			}
			else if (game.getKeyManager().getInputs().get("UndoMove") && playerAndStarXY.size() > 1) {
				undoMove();
			}
		}

	}

	private void move() {

		checkStarCollisionAndBlocked();
		
		if (xMove > 0) {
			if (!starCollision) {
				final int playerXIfMove = (int) ((x + xMove + entityWidth) / tile.getTileWidth());
				movePlayerX(playerXIfMove);
			}
			else {
				final int starXIfMove = (int) ((collidedStar.x + xMove + entityWidth) / tile.getTileWidth());
				movePlayerAndStarX(starXIfMove);
			}
		} 
		else if (xMove < 0) {
			if (!starCollision) {
				final int playerXIfMove = (int) ((x + xMove) / tile.getTileWidth());
				movePlayerX(playerXIfMove);
			} 
			else {
				final int starXIfMove = (int) ((collidedStar.x + xMove) / tile.getTileWidth());
				movePlayerAndStarX(starXIfMove);
			}
		} 
		else if (yMove < 0) {
			if (!starCollision) {
				final int playerYIfMove = (int) ((y + yMove) / tile.getTileHeight());
				movePlayerY(playerYIfMove);
			} 
			else {
				final int starYIfMove = (int) ((collidedStar.y + yMove) / tile.getTileHeight());
				movePlayerAndStarY(starYIfMove);
			}
		} 
		else {
			if (!starCollision) {
				final int playerYIfMove = (int) ((y + yMove + entityHeight) / tile.getTileHeight());
				movePlayerY(playerYIfMove);
			} 
			else {
				final int starYIfMove = (int) ((collidedStar.y + yMove + entityHeight) / tile.getTileHeight());
				movePlayerAndStarY(starYIfMove);
			}
		}

	}

	private void undoMove() {
		
		if (playerAndStarXY.peek().getStar() != null) {
			collidedStar = playerAndStarXY.peek().getStar();
			collidedStar.x = (float) playerAndStarXY.peek().getStarXY().getX();
			collidedStar.y = (float) playerAndStarXY.peek().getStarXY().getY();
		}

		playerAndStarXY.pop();
		x = (float) playerAndStarXY.peek().getPlayerXY().getX();
		y = (float) playerAndStarXY.peek().getPlayerXY().getY();

		countMoves--;
		lastYMove = speed;
		game.getKeyManager().getInputs().replace("UndoMove", false);

	}

	private void checkStarCollisionAndBlocked() {

		starCollision = starBlocked = false;
		
		for (final Star star : ((GameState) game.getCurrentState()).getLevel().getEntityManager().getStars()) {
			if (!starCollision && boundsIfMove(xMove, yMove).intersects(star.boundsIfMove(0, 0))) {
				collidedStar = star;
				starCollision = true;
			}
			else if (!starBlocked && x + xMove / speed * tile.getTileWidth() * 2 == star.x && y + yMove / speed * tile.getTileHeight() * 2 == star.y) {
				starBlocked = true;
			}
		}

	}

	private void movePlayerX(final int playerXIfMove) {

		if (!tileCollision(playerXIfMove, (int) (y / tile.getTileHeight())) && !tileCollision(playerXIfMove, (int) ((y + entityHeight) / tile.getTileHeight()))) {
			x += xMove;
			if (x % tile.getTileWidth() == 0) {
				final Point2D playerXY = new Point2D.Float(x, y);
				final EntityCoordinates coordinates = new EntityCoordinates(playerXY, null, null);
				playerAndStarXY.push(coordinates);
				countMoves++;
			}
		}

	}

	private void movePlayerAndStarX(final int starXIfMove) {

		if (!starBlocked && !tileCollision(starXIfMove, (int) (collidedStar.y / tile.getTileHeight())) && 
			!tileCollision(starXIfMove, (int) ((collidedStar.y + entityHeight) / tile.getTileHeight()))) {
			x += xMove;
			collidedStar.x += xMove;
			if (x % tile.getTileWidth() == 0) {
				final Point2D playerXY = new Point2D.Float(x, y);
				final Point2D starBeforeMove = new Point2D.Float(collidedStar.x - xMove / speed * tile.getTileWidth(), collidedStar.y);
				final EntityCoordinates coordinates = new EntityCoordinates(playerXY, collidedStar, starBeforeMove);
				playerAndStarXY.push(coordinates);
				countMoves++;
			}
		}

	}

	private void movePlayerY(final int playerYIfMove) {

		if (!tileCollision((int) (x / tile.getTileWidth()), playerYIfMove) && !tileCollision((int) ((x + entityWidth) / tile.getTileWidth()), playerYIfMove)) {
			y += yMove;
			if (y % tile.getTileHeight() == 0) {
				final Point2D playerXY = new Point2D.Float(x, y);
				final EntityCoordinates coordinates = new EntityCoordinates(playerXY, null, null);
				playerAndStarXY.push(coordinates);
				countMoves++;
			}
		}

	}

	private void movePlayerAndStarY(final int starYIfMove) {

		if (!starBlocked && !tileCollision((int) (collidedStar.x / tile.getTileWidth()), starYIfMove) && 
			!tileCollision((int) ((collidedStar.x + entityWidth) / tile.getTileWidth()), starYIfMove)) {
			y += yMove;
			collidedStar.y += yMove;
			if (y % tile.getTileHeight() == 0) {
				final Point2D playerXY = new Point2D.Float(x, y);
				final Point2D starBeforeMove = new Point2D.Float(collidedStar.x, collidedStar.y - yMove / speed * tile.getTileHeight());
				final EntityCoordinates coordinates = new EntityCoordinates(playerXY, collidedStar, starBeforeMove);
				playerAndStarXY.push(coordinates);
				countMoves++;
			}
		}

	}

	private boolean tileCollision(final int x, final int y) {

		return ((GameState) game.getCurrentState()).getLevel().getTile(x, y).getSolid();

	}

	public int getCountMoves() {
		
		return countMoves;
		
	}
	
	public void resetCountMoves() {
		
		countMoves = 0;
		
	}

}