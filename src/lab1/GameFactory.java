package lab1;

/**
 * Factory class for available games.
 */
public class GameFactory implements IGameFactory {

	/**
	 * Returns an array with names of games this factory can create. Used by GUI
	 * list availible games.
	 */
	@Override
	public String[] getGameNames() {
		return new String[] { "Gold", "Snake" };
	}

	/**
	 * Returns a new model object for the game corresponding to its Name.
	 * 
	 * @param gameName
	 *            The name of the game as given by getGameNames()
	 * @throws IllegalArgumentException
	 *             if no such game
	 */
	@Override
	public GameModel createGame(final String gameName) {
		if (gameName.equals("Gold")) {
			return new GoldModel();
		}
		
		else if (gameName.equals("Snake")) {
			return new SnakeModel();
		}

		throw new IllegalArgumentException("No such game: " + gameName);
	}
}
