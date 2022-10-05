package lab1;

/**
 * Thrown by GameModel.doCommand at game termination. The exception contains
 * information about how many points the player got.
 */
public class GameOverException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6586608947928120931L;
	private final int score;

	/** Constructs a new exception with the final score.
	 * 
	 * @param score The final score of the game.
	 */
	public GameOverException(final int score) {
		this.score = score;
	}

	/** Get the score of the game */
	public int getScore() {
		return this.score;
	}
}
