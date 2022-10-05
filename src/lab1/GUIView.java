package lab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * This panel is meant to be the base of a window or applet. It will add a new
 * GameView with a corresponding GameController to itself. It will also provide
 * a gui for choosing a new game. The list of games will be aquired from
 * a GameFactory.
 */
public class GUIView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3394100357075618465L;

	/** The "Start Game" button */
	private final JButton startGameButton;

	/** The chooser (also called drop-down menu) with names of different games */
	private final JComboBox<Object> gameChooser;

	/** The game controller associated with the GameView */
	private final GameController gameController;

	/** The game view on this panel */
	private final GameView gameView;

	/** The panel with the gui-gadgets on this panel */
	private final JPanel guiPanel;

	/** This is the factory which creates GameModels for us */
	private final IGameFactory gameFactory;

	/**
	 * Create a new GUIView. This will create a GameView and a GameController.
	 * @param factory The factory to use for creating games.
	 */

	public GUIView(IGameFactory factory) {
		// Create a new GameView
		this.gameView = new GameView();

		// Create a new GameController connected to the GameView
		this.gameController = new GameController(this.gameView);

		// Create a new GameFactory
		this.gameFactory = factory;

		// Set the background on the GameView
		this.gameView.setBackground(Color.white);

		// Set the layout on myself
		setLayout(new BorderLayout());

		// Make a new panel containing the GUI
		this.guiPanel = new JPanel();

		// Set the background on that panel
		this.guiPanel.setBackground(Color.lightGray);

		// Create a new button on that panel and add a StartGameListener as
		// listener on that button
		this.startGameButton = new JButton("Start Game");
		this.startGameButton.addActionListener(new StartGameListener());
		this.guiPanel.add(this.startGameButton);

		// Create a new choice on the panel, and add all available games
		this.gameChooser = new JComboBox<Object>(this.gameFactory.getGameNames());
		this.guiPanel.add(this.gameChooser);

		// Add both the new panel and the GameView to myself
		add(this.gameView, BorderLayout.CENTER);
		add(this.guiPanel, BorderLayout.SOUTH);
	}

	/**
	 * Get a reference to the game controller. Useful if game needs to be
	 * stopped by some other means, like in stop() in Applet.
	 */
	public GameController getGameController() {
		return this.gameController;
	}

	/**
	 * This inner class will listen for presses on the "Start Game" button.
	 * It will respond by creating a new game model and starting it using
	 * the game controller.
	 */
	private class StartGameListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			Object source = e.getSource();

			if (source == GUIView.this.startGameButton) {
				// Get the name of the game selected in the Choice
				String gameName =
						GUIView.this.gameChooser.getSelectedItem().toString();
				GameModel gameModel =
						GUIView.this.gameFactory.createGame(gameName);

				// Stop current game (if any) and start a new game with the
				// new game model
				GUIView.this.gameController.stopGame();
				GUIView.this.gameController.startGame(gameModel);
				GUIView.this.gameView.requestFocus();
			}
		}
	}
}
