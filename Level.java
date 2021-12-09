package application;

import java.io.IOException;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * This class represents and displays a level.
 * @author Will Conroy
 * @version 1.3
 */
public class Level {
	// What keys control the player
	private static final String UP_KEY = "Up";
	private static final String DOWN_KEY = "Down";
	private static final String LEFT_KEY = "Left";
	private static final String RIGHT_KEY = "Right";
	private static final String SAVE_KEY = "S";
	private static final String ESC_KEY = "Esc";
	// private static final String String = null;

	// All the enemies in the game.
	private Enemy[] enemyList;

	// The character the user controls
	private Player thePlayer;

	// The map the player and enemies are in
	private Map map;

	// Where the level is draw onto
	private Canvas canvas;

	private Timer levelTimer;

	// The classes for saving and loading the level
	private LevelLoader loader;
	private LevelSaver saver;

	private String levelFile;

	// Name of the user playing
	private String user;

	// What level they are on.
	private int levelNum;

	private BasicCell playerOldPosition;

	private boolean playerOnEmeny;
	
	/**
	 * Constructs the level.
	 * @param canvas the canvas the game is displayed on.
	 * @param user The name of the player.
	 * @param levelFile The name of the level file.
	 * @param levelNum The number of the level.
	 */
	public Level(Canvas canvas, String user, String levelFile, int levelNum) {

		this.levelNum = levelNum;
		this.canvas = canvas;
		this.levelFile = levelFile;

		this.loader = new LevelLoader(canvas.getGraphicsContext2D(), levelFile);

		this.loadLevel();

		this.user = user;
		this.saver = new LevelSaver(this);
	}

	/**
	 * Updates all elements of the game.
	 * 
	 * @param keyCode
	 *            The key that was pressed.
	 * @throws IOException 
	 * 				In case another process interrupts 
	 * 				the the saving of a game.
	 */
	public void updateLevel(KeyCode keyCode) throws IOException {
		// checks if an arrow key is pressed
		if (checkKeyPress(keyCode) < 4) {
			// draws the map first so that charaters are visable
			map.update(canvas.getGraphicsContext2D());
			this.playerOldPosition = this.thePlayer.getLocation();
			thePlayer.move(checkKeyPress(keyCode));
			this.playerOnEmeny = this.isPlayerOnEnemy();
			this.updateEnemies();
			// checks if the player is dead or has finish the level
			isGameOver();
			// move the canvas so the player is in the centre
			this.updateCanvas();
		} else if (keyCode.getName().equals(SAVE_KEY)) {
			this.saver.saveGame();

		} else if (keyCode.getName().equals(ESC_KEY)) {
			Game.getMenuSystem().mainMenu(new Stage());
			Game.getLevelStage().close();
		}

	}

	/**
	 * Move and redraws all the enemies.
	 */
	private void updateEnemies() {
		for (int i = 0; i < enemyList.length; i++) {
			enemyList[i].update();
		}
	}

	/**
	 * Takes in a key press and turns into a int dir.
	 * 
	 * @param key The key pressed.
	 * @return int 0 to 5
	 */
	private int checkKeyPress(KeyCode key) {
		String keyString = key.getName();
		switch (keyString) {
			case UP_KEY:
				return 0;
			case RIGHT_KEY:
				return 1;
			case DOWN_KEY:
				return 2;
			case LEFT_KEY:
				return 3;
			default:
				return 4;
		}
	}

	/**
	 * Moves the canvas to a new location.
	 * 
	 * @param x The x position
	 * @param y The y position
	 */
	private void moveCanvas(int x, int y) {
		canvas.setTranslateX(x);
		canvas.setTranslateY(y);
	}

	/**
	 * Draws the canvas so the player is in the centre.
	 */
	private void updateCanvas() {
		int[] newLocation = this.getNewCanvasLocation();
		moveCanvas(newLocation[0], newLocation[1]);
	}

	/**
	 * Works out where the canvas should be so the player is in the middle 
	 * of the screen.
	 * 
	 * @return int[2] [x,y]
	 */
	private int[] getNewCanvasLocation() {
		int[] newLocation = new int[2];
		int[] locationFix = new int[2];
		locationFix[0] = (Game.WINDOW_HEIGHT / Game.ELEMENT_SIZE) / 2;
		locationFix[1] = (Game.WINDOW_WIDTH / Game.ELEMENT_SIZE) / 2;
		newLocation[0] = -((thePlayer.getLocation().getX() - locationFix[0]) * (Game.ELEMENT_SIZE));
		newLocation[1] = -((thePlayer.getLocation().getY() - locationFix[1]) * (Game.ELEMENT_SIZE));
		return newLocation;
	}

	/**
	 * Check if the player is killed or has finished the level.
	 * 
	 * @throws IOException 
	 * 				In case another process interrupts 
	 * 				the saving a user.
	 */
	private void isGameOver() throws IOException {

		// Have they reached the end of the level
		if (thePlayer.getLocation() instanceof GoalCell) {
			this.finishLevel();
		}

		// Check if they on a deadly cell
		if (thePlayer.getLocation().getSafeLevel() == 2) {
			this.killPlayer();
		}

		if (isPlayerOnEnemy()) {
			this.killPlayer();
		}

		if (playerOnEmeny) {
			for (int i = 0; i < enemyList.length; i++) {
				if (enemyList[i].getLocation() == playerOldPosition) {
					this.killPlayer();
				}
			}

		}

	}

	/**
	 * Checks if the player is on the same cell as an enemy.
	 * @return True if the player is on the same cell, false otherwise.
	 */
	private boolean isPlayerOnEnemy() {
		for (int i = 0; i < enemyList.length; i++) {
			if (enemyList[i].getLocation() == thePlayer.getLocation()) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Ends and reloads the level.
	 */
	public void killPlayer() {
		Game.getLevelStage().close();
		Game.startLevel(levelFile, user, levelNum);

	}

	/**
	 * Ends the level and loads the next one and saves the time that the 
	 * user took to complete the level.
	 * 
	 * @throws IOException
	 *             In case another process interrupts writing to the user's
	 *             file.
	 */
	public void finishLevel() throws IOException {
		Game.getLevelStage().close();
		switch (levelNum) {
			case 1:
				UserProfile u = new UserProfile(user);
				int[] newTimes = {levelTimer.getTime(), u.getLevel2Time(), u.getLevel3Time()};
				u.setTimes(newTimes);
				Game.startLevel(Game.LEVEL_TWO, user, 2);
				break;
			case 2:
				UserProfile u1 = new UserProfile(user);
				int[] newTimes2 = {u1.getLevel1Time(), levelTimer.getTime(), u1.getLevel3Time()};
				u1.setTimes(newTimes2);
				Game.startLevel(Game.LEVEL_THREE, user, 3);
				break;
			default:
				UserProfile u2 = new UserProfile(user);
				int[] newTimes3 = {u2.getLevel1Time(), u2.getLevel2Time(), levelTimer.getTime()};
				u2.setTimes(newTimes3);
				Game.getMenuSystem().mainMenu(new Stage());
				break;
		}

		this.loadLevel();
	}

	/**
	 * Loads the correct Level.
	 */
	private void loadLevel() {
		this.levelTimer = new Timer(loader.getTime());
		this.map = loader.getMap();
		this.thePlayer = loader.getThePlayer();
		this.enemyList = loader.makeEnemyList();
		// this.user = loader.getUser();
		map.update(canvas.getGraphicsContext2D());
		thePlayer.draw();
		updateCanvas();

	}

	/**
	 * Gets the list of enemies.
	 * @return The list of enemies.
	 */
	public Enemy[] getEnemyList() {

		return this.enemyList;
	}

	/**
	 * Gets the map.
	 * @return The map.
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Gets the timer of the level.
	 * @return The level timer.
	 */
	public Timer getLevelTimer() {
		return levelTimer;
	}

	/**
	 * Gets the number of the level.
	 * @return The number of the level
	 */
	public int getLevelNum() {
		return levelNum;
	}

	/**
	 * Gets the player.
	 * @return the player.
	 */
	public Player getPlayer() {

		return this.thePlayer;
	}

	/**
	 * Gets the name of the player.
	 * @return The name of the player.
	 */
	public String getUser() {
		return this.user;
	}

}