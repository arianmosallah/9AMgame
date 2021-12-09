package application;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class contains the GUI of the game and can load levels.
 * 
 * @author Gayathri Praveen
 * @author William Conroy
 * @version 1.0
 */
public class Game extends Application {

	public static final String FILE_PATH = System.getProperty("user.dir") + "\\source\\";

	// The dimensions of the window that the level are played in
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 900;

	// The dimensions of the canvas
	public static final int CANVAS_WIDTH = 7000;
	public static final int CANVAS_HEIGHT = 7000;

	// The dimension of all elements
	public static final int ELEMENT_SIZE = 130;

	// All default levels file location
	public static final String LEVEL_ONE = FILE_PATH + "Levels\\Level1.txt";
	public static final String LEVEL_TWO = FILE_PATH + "Levels\\Level2.txt";
	public static final String LEVEL_THREE = FILE_PATH + "Levels\\Level3.txt";

	public static final String GRAPHIC_PATH = FILE_PATH + "Graphics\\";
	public static final String SOUND_PATH = FILE_PATH + "Sound\\";
	public static final String SAVE_PATH = FILE_PATH + "saves\\";

	// The current level which is being played
	private static Level currentLevel;

	// The canvas the level is shown on
	private static Canvas canvas;

	private static Stage levelStage;

	private static Menu menuSystem = new Menu();

	/**
	 * Displays the main menu.
	 * 
	 * @param stage
	 *            Used to display the menu to screen.
	 * @throws IOException
	 *             for Message of the Day in case another process closes the
	 *             stream or it is disconnected.
	 */
	public void start(Stage stage) throws IOException {
		menuSystem.mainMenu(stage);
	}

	/**
	 * Start a new game at level 1.
	 * 
	 * @param name
	 *            Name of the player.
	 */
	public static void newGame(String name) {
		// Creates a new user file if user has not played before.
		String filename = FileHandler.USERS_PATH + name + ".txt";
		if (!(new File(filename).exists())) {
			int times[] = new int[3];
			try {
				FileHandler.writeToUserFile(name, times);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Game.startLevel(LEVEL_ONE, name, 1);
	}

	/**
	 * Give a fileName it will load a saved game.
	 * 
	 * @param name
	 *            Name of the player.
	 */
	public static void loadSavedLevel(String name) {
		String[] nameSplit = name.split("-");
		String levelLocation = SAVE_PATH + name + ".txt";
		startLevel(levelLocation, nameSplit[0], Integer.parseInt(nameSplit[1]));
	}

	/**
	 * Build a new level in a new screen.
	 * 
	 * @param fileName
	 *            - what save is being loaded.
	 * @param user
	 *            - who is playing.
	 * @param levelNum
	 *            - which level they are on.
	 */
	public static void startLevel(String fileName, String user, int levelNum) {
		levelStage = new Stage();
		Pane root = buildGameRoot();
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Display the scene on the stage
		levelStage.setScene(scene);
		levelStage.setTitle(fileName);

		Sound.playOverworldSound();
		currentLevel = new Level(canvas, user, fileName, levelNum);

		// update the game when a button is pressed
		scene.setOnKeyPressed(e -> {
			KeyCode keyCode = e.getCode();
			try {
				updateGame(keyCode);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		levelStage.show();
	}

	/**
	 * Give a key press will update the level.
	 * 
	 * @param keyCode
	 * @throws IOException
	 */
	private static void updateGame(KeyCode keyCode) throws IOException {
		currentLevel.updateLevel(keyCode);
	}

	/**
	 * Builds the root that the level are displayed onto.
	 * 
	 * @return Pane
	 */
	private static Pane buildGameRoot() {
		BorderPane root = new BorderPane();
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);
		return root;

	}
	
	/**
	 * Gets the stage for the level.
	 * @return The stage.
	 */
	public static Stage getLevelStage() {
		return levelStage;
	}
	
	/**
	 * Gets the Main Menu.
	 * @return The main Menu.
	 */
	public static Menu getMenuSystem() {
		return menuSystem;
	}

	public static void main(String[] args) {
		launch(args);
	}
}