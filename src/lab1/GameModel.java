package lab1;

import java.awt.Dimension;

/**
 * Common superclass for all game model classes.
 * 
 * Constructors of subclasses should initiate matrix elements and additional,
 * game-dependent fields.
 */
public abstract class GameModel {

	/** A Matrix containing the state of the gameboard. */
	private final GameTile[][] gameboardState;

	/** The size of the state matrix. */
	private final Dimension gameboardSize = Constants.getGameSize();

	/**
	 * Create a new game model. As GameModel is an abstract class, this is only
	 * intended for subclasses.
	 */
	protected GameModel() {
		this.gameboardState =
				new GameTile[this.gameboardSize.width][this.gameboardSize.height];
	}

	/**
	 * Set the tile on a specified position in the gameboard.
	 * 
	 * @param pos
	 *            The position in the gameboard matrix.
	 * @param tile
	 *            The type of tile to paint in specified position
	 */
	protected void setGameboardState(final Position pos, final GameTile tile) {
		setGameboardState(pos.getX(), pos.getY(), tile);
	}

	/**
	 * Set the tile on a specified position in the gameboard.
	 * 
	 * @param x
	 *            Coordinate in the gameboard matrix.
	 * @param y
	 *            Coordinate in the gameboard matrix.
	 * @param tile
	 *            The type of tile to paint in specified position
	 */
	protected void setGameboardState(final int x, final int y,
			final GameTile tile) {
		this.gameboardState[x][y] = tile;
	}

	/**
	 * Returns the GameTile in logical position (x,y) of the gameboard.
	 * 
	 * @param pos
	 *            The position in the gameboard matrix.
	 */
	public GameTile getGameboardState(final Position pos) {
		return getGameboardState(pos.getX(), pos.getY());
	}

	/**
	 * Returns the GameTile in logical position (x,y) of the gameboard.
	 * 
	 * @param x
	 *            Coordinate in the gameboard matrix.
	 * @param y
	 *            Coordinate in the gameboard matrix.
	 */
	public GameTile getGameboardState(final int x, final int y) {
		return this.gameboardState[x][y];
	}

	/**
	 * Returns the size of the gameboard.
	 */
	public Dimension getGameboardSize() {
		return this.gameboardSize;
	}

	/**
	 * This method is called repeatedly so that the game can update it's state.
	 * 
	 * @param lastKey
	 *            The most recent keystroke.
	 */
	public abstract void gameUpdate(int lastKey) throws GameOverException;
	
	
	/**
	 * Returns the current score.
	 */
	public abstract int getScore();
}
