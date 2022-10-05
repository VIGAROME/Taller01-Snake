package lab1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * The controller class of the framework. Listens to user keystrokes and
 * passes them on to the current game.
 * </ul>
 */
public class GameController implements Runnable {

	/** The view this controller is connected to. */
	private final GameView view;

	/** The game model describes the running game. */
	private GameModel gameModel;

	/** The timeout interval between each update. (millis) */
	private final int updateInterval;

	/** True when game is running. */
	private boolean isRunning;

	/** Listener for key events to the game. */
	private final KeyListener keyListener;

	/** A queue for all keypresses which so far haven't been processed */
	private final List<Integer> keypresses;

	/*
	 * The declaration of keypresses above uses the language feature 'generic
	 * types'. It can be declared in the old way like this: private
	 * java.util.List keypresses This will however result in a warning at
	 * compilation "Integer" in this case is the type of the objects that are
	 * going to be used in the List
	 */

	/** The thread which the game runs in. */
	private Thread gameThread;

	/**
	 * Creats a new GameContoller associated with supplied view.
	 */
	public GameController(final GameView view) {
		this.view = view;
		this.gameModel = null;
		this.isRunning = false;
		this.updateInterval = 150;

		this.keypresses = new LinkedList<Integer>();

		this.gameThread = null;

		// Create the key listener which will listen for gamekeys
		this.keyListener = new KeyAdapter() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void keyPressed(final KeyEvent event) {
				enqueueKeyPress(event.getKeyCode());
			}
		};

	}

	/**
	 * Add a key press to the end of the queue
	 */
	private synchronized void enqueueKeyPress(final int key) {
		this.keypresses.add(Integer.valueOf(key));
	}

	/**
	 * Get a key press, and remove it from the queue. Returns 0 if no key press
	 * is available.
	 * 
	 * 
	 * @return 0 or next unprocessed key press.
	 */
	private synchronized int nextKeyPress() {
		if (this.keypresses.isEmpty()) {
			return 0;
		}
		return this.keypresses.remove(0).intValue();
	}

	/**
	 * Starts a new game.
	 * 
	 * @param gameModel
	 *            Game to start
	 */
	public void startGame(final GameModel gameModel) {
		if (this.isRunning) {
			throw new IllegalStateException("Game is already running");
		}

		// Start listening for key events
		this.view.addKeyListener(this.keyListener);

		// Tell the view what to paint...
		this.view.setModel(gameModel);

		// Actually start the game
		this.gameModel = gameModel;
		this.isRunning = true;

		// Create the new thread and start it...
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}

	/**
	 * Stops the currently running game, if any.
	 */
	public void stopGame() {
		// Setting isRunning to false will
		// make the thread stop (see run())
		this.isRunning = false;

		// Unset the game model...
		this.view.setModel(null);

		// Stop listening for events
		this.view.removeKeyListener(this.keyListener);

		// Make sure we wait until the thread has stopped...
		if (this.gameThread != null) {
			while (this.gameThread.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) {
					// Pass the call on.
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	/**
	 * This code runs the game in a different thread.
	 */
	@Override
	public void run() {
		while (this.isRunning) {
			try {
				// Tell model to update, send next key press.
				// or 0 if no new keypress since last update.
				this.gameModel.gameUpdate(nextKeyPress());

				this.view.repaint();

				Thread.sleep(this.updateInterval);
			} catch (GameOverException e) {
				// we got a game over signal, time to exit...
				// The current implementation ignores the game score
				this.isRunning = false;
				System.out.println("Game over: " + e.getScore());
			} catch (InterruptedException e) {
				// if we get this exception, we're asked to terminate ourselves
				this.isRunning = false;
			}
		}
	}
}
