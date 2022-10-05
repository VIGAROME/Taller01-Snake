package lab1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


/**
 * Simple classic game of snake. The snake is put in the middle of the game pane
 * and grows to the size of a constant. A fruit is placed at a random location.
 * If a fruit is eaten, a new one is randomly placed and the snake grows with
 * one tile. The game is over if the snake runs into its tail or hits the
 * game pane edges. 
 */
public class SnakeModel extends GameModel {
	public enum Directions {
		EAST(1, 0),
		WEST(-1, 0),
		NORTH(0, -1),
		SOUTH(0, 1),
		NONE(0, 0);

		private final int xDelta;
		private final int yDelta;

		Directions(final int xDelta, final int yDelta) {
			this.xDelta = xDelta;
			this.yDelta = yDelta;
		}

		public int getXDelta() {
			return this.xDelta;
		}

		public int getYDelta() {
			return this.yDelta;
		}
	}


	/*
	 * The following GameTile objects are used only
	 * to describe how to paint the specified item.
	 * 
	 * This means that they should only be used in
	 * conjunction with the get/setGameboardState()
	 * methods.
	 */

	/** Graphical representation of a coin. */
	private static final GameTile FRUIT_TILE = new RoundTile(new Color(180, 0, 0), Color.RED, 3.0);

	/** Graphical representation of the snake */
	private static final GameTile SNAKE_TILE = new RectangularTile(Color.darkGray);

	/** Graphical representation of the snake's head */
	private static final GameTile SNAKE_HEAD_TILE = new RectangularTile(Color.GRAY);
	
	/** Graphical representation of a blank tile. */
	private static final GameTile BLANK_TILE = new GameTile();

	/** A list containing the position of the snake (with it's tail). */
	private final LinkedList<Position> snake = new LinkedList<Position>();
	
	/** The starting length of the snake */
	private final int INITIAL_SNAKE_LENGTH = 20;
	
	/** The amount of fruit on the game pane */
	private final int FRUIT_AMOUNT = 1;
	
	/** The size of the board */
	private final int GAME_BOARD_SIZE = getGameboardSize().height * getGameboardSize().width;
	
	/*
	 * The declaration and object creation above uses the new language feature
	 * 'generic types'. It can be declared in the old way like this:
	 * private java.util.List coins = new ArrayList();
	 * This will however result in a warning at compilation
	 * "Position" in this case is the type of the objects that are going
	 * to be used in the List
	 */

	/** The position of the snake. */
	private Position snakePos;

	/** The direction of the snake. */
	private Directions direction = Directions.NORTH;

	/** The number of fruits found. */
	private int score;

	/**
	 * Create a new model for the snake game.
	 */
	public SnakeModel() {
		Dimension size = getGameboardSize();
		


		// Blank out the whole game board
		for (int i = 0; i < size.width; i++) {
			for (int j = 0; j < size.height; j++) {
				setGameboardState(i, j, BLANK_TILE);
			}
		}

		/*
		 * Grow the snake to the size of the constant and place the snake's
		 * head in the center of the game board.
		 */
		this.snakePos = new Position(size.width / 2, size.height / 2);
		for(int i = 0; i < INITIAL_SNAKE_LENGTH; i++){
			snake.push(this.snakePos);
			setGameboardState(this.snakePos, SNAKE_TILE);
		}


		// Insert the fruits to the game board.
		for(int i = 0; i < FRUIT_AMOUNT; i++){
			if(blankTilesExists()){
				addFruit();			
			}
		}

	}

	/**
	 * Add a fruit to the game board at a random position.
	 */
	private void addFruit() {
		Position newFruitPos;
		Dimension size = getGameboardSize();
		// Loop until a blank position is found and ...
		do {
			newFruitPos = new Position((int) (Math.random() * size.width),
										(int) (Math.random() * size.height));
		} while (!isPositionEmpty(newFruitPos));

		// ... add a new fruit to the empty tile.
		setGameboardState(newFruitPos, FRUIT_TILE);
	}
	
	

	/**
	 * Return whether the specified position is empty.
	 * 
	 * @param pos
	 *            The position to test.
	 * @return true if position is empty.
	 */
	private boolean isPositionEmpty(final Position pos) {
		return (getGameboardState(pos) == BLANK_TILE);
	}

	/**
	 * Update the direction of the worm according to the user's key press.
	 * Checks the current direction of the worm before changing to the
	 * new direction.
	 * 
	 * Example: the worm is moving right (EAST) and the player presses
	 * the left key. The worm would die, since it collides with itself.
	 * Below, we're preventing that.
	 */
	private void updateDirection(final int key) {
		switch (key) {
			case KeyEvent.VK_LEFT:
				if(this.direction != Directions.EAST){
					this.direction = Directions.WEST;
				}
				break;
			case KeyEvent.VK_UP:
				if(this.direction != Directions.SOUTH){
					this.direction = Directions.NORTH;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(this.direction != Directions.WEST){
					this.direction = Directions.EAST;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(this.direction != Directions.NORTH){
					this.direction = Directions.SOUTH;
				}
				break;
			default:
				// Don't change direction if another key is pressed
				break;
		}
	}
	
	
	/**
	 * Checks the game board to see if blank tiles exist.
	 * 
	 * @return True if blank tiles exist, otherwise false
	 */
	private boolean blankTilesExists(){
		Dimension gameBoard = getGameboardSize();
		
		for(int i = 0; i < gameBoard.width; i++){
			for(int j = 0; j < gameBoard.height; j++){
				if(getGameboardState(i, j) == BLANK_TILE){
					return true;
				}
			}
			
		}
		
		return false;
	}
	

	/**
	 * Get next position of the snake.
	 */
	private Position getNextSnakePos() {
		return new Position(
				this.snakePos.getX() + this.direction.getXDelta(),
				this.snakePos.getY() + this.direction.getYDelta());
	}

	/**
	 * This method is called repeatedly so that the
	 * game can update its state.
	 * 
	 * @param lastKey
	 *            The most recent keystroke.
	 */
	@Override
	public void gameUpdate(final int lastKey) throws GameOverException {
		
		updateDirection(lastKey);
		
		this.snakePos = getNextSnakePos();
		
		// Checks if the snake hits a wall
		if (isOutOfBounds(this.snakePos)) {
			throw new GameOverException(this.score);
		}
		
		// The snake ate a fruit!
		if(getGameboardState(this.snakePos) == FRUIT_TILE) {						
			if(!blankTilesExists()){
				throw new GameOverException(getScore());
			}
			
			addFruit();
			score++;
		}
		
		else{
			// Erase the snake's tail.
			setGameboardState(this.snake.get(0), BLANK_TILE);
			this.snake.remove(0);
		}
		
		/* 
		 * Checks if the snake hits its tale. This is not done near the wall
		 * collision check above because the tiles need to be updated first.
		 * Otherwise the snake wouldn't be able to "chase its tail" because the
		 * last snake tile (the end of the tail) from the previous update would
		 * remain.
		*/
		if (getGameboardState(this.snakePos) == SNAKE_TILE) {
			throw new GameOverException(this.score);
		}
		
		// Changes the last head-tile to a tail-tile
		setGameboardState(this.snake.getLast(), SNAKE_TILE);
		
		// Updates the snake list with the new head position.
		this.snake.add(this.snakePos);
		
		// "Draws" the snake's head at the new position
		setGameboardState(this.snake.getLast(), SNAKE_HEAD_TILE);
	}
	
	/**
	 *  @return the current score 
	 */
	public int getScore(){
		return this.score;
	}
	

	/**
	 * 
	 * @param pos The position to test.
	 * @return <code>false</code> if the position is outside the playing field, <code>true</code> otherwise.
	 */
	private boolean isOutOfBounds(Position pos) {
		return pos.getX() < 0 || pos.getX() >= getGameboardSize().width
				|| pos.getY() < 0 || pos.getY() >= getGameboardSize().height;
	}

	public int getGAME_BOARD_SIZE() {
		return GAME_BOARD_SIZE;
	}

}
