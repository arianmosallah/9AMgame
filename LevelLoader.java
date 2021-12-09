package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

/**
 * This class will load a level form a text file.
 *
 * @author William Conroy
 * @author Aryan Mosallah
 * @version 1.0
 */

public class LevelLoader {

	public static final char WALL = '#';
	public static final char FLOOR = ' ';
	public static final char TOKEN = 't';
	public static final char GOAL = '@';
	public static final char BOOTS = 'I';
	public static final char FLIPPERS = 'F';
	public static final char WATER = '~';
	public static final char WIND = 'W';
	public static final char RED_KEY = 'r';
	public static final char GREEN_KEY = 'g';
	public static final char BLUE_KEY = 'b';
	public static final char RED_DOOR = 'R';
	public static final char GREEN_DOOR = 'G';
	public static final char BLUE_DOOR = 'B';

	public static final int SIZE_LINE = 2;

	public static final String WALL_FOLLOW = "WALL";
	public static final String SMART_TARGETING = "SMART";
	public static final String DUMB_TARGETING = "DUMB";
	public static final String STRAIGHT_LINE = "STRAIGHT";

	// private PlayerData data;
	private String loadedFile;
	private String fileLocation;
	private String[] fileString;
	private Map map;
	private int width;
	private int height;
	private GraphicsContext gc;
	private Player thePlayer;
	private int playerLine;
	private int teleLine;
	private int time;

	/**
	 * Change directory to correct one Finds file then loads map.
	 *
	 * @param gc
	 *            The graphics context
	 * @param levelFile
	 *            the name of the save file.
	 */
	public LevelLoader(GraphicsContext gc, String levelFile) {
		this.gc = gc;
		this.fileLocation = levelFile;
		this.fileString = readMap(fileLocation).split("\n");
		String[] widthHeightString = fileString[SIZE_LINE].split(",");
		this.width = Integer.parseInt(widthHeightString[0]);
		this.height = Integer.parseInt(widthHeightString[1]);
		this.time = Integer.parseInt(fileString[1]);
		this.teleLine = height + SIZE_LINE + 1;
		this.playerLine = height + SIZE_LINE + 1;
		makeMap();
		this.thePlayer = makePlayer();

	}

	/**
	 * Finds file then loads map line by line to variable loadedFile.
	 *
	 * @param fileName
	 *            File directory.
	 * @return The loaded file.
	 */
	private String readMap(String fileName) {
		this.loadedFile = "";
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			try {
				for (String line; (line = br.readLine()) != null;) {
					loadedFile += line + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// line is not visible here.
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return loadedFile;
	}

	/**
	 * Makes the map based on the file.
	 */
	private void makeMap() {

		BasicCell[][] basicCellArray = new BasicCell[this.width][this.height];
		String[] mapRow = new String[this.height];

		// Make an array of strings where each element is a row of the level
		for (int y = 0; y < this.height; y++) {
			mapRow[y] = fileString[y + 1 + SIZE_LINE];
		}

		for (int y = 0; y < this.height; y++) {
			int x = 0;
			for (char ch : mapRow[y].toCharArray()) {
				int chInt = ch - 48;
				if (chInt > 0 && chInt < 10) {
					basicCellArray[x][y] = new TokenDoor(x, y, chInt);
				} else {
					basicCellArray[x][y] = makeCell(ch, x, y);
				}
				x++;
			}
		}
		int count = 0;
		String[] teleRow = new String[this.fileString.length - teleLine];
		for (int y = 0; y < this.fileString.length - teleLine; y++) {
			teleRow[y] = fileString[y + teleLine];

		}
		String[] temp = teleRow[0].split(",");

		while (temp[0].equals("TELEPORTER")) {
			int[] locations = new int[4];
			for (int i = 0; i < 4; i++) {
				locations[i] = Integer.parseInt(temp[i + 1]);
			}
			basicCellArray = this.loadTele(locations[0], locations[1], locations[2], locations[3], basicCellArray);
			count++;
			temp = teleRow[count].split(",");
		}
		this.playerLine = playerLine + count;

		this.map = new Map(basicCellArray, this.width, this.height);
	}

	/**
	 * Makes a cell based on its character in the save file.
	 *
	 * @param cell
	 *            The character of the cell.
	 * @param x
	 *            the x position of the cell
	 * @param y
	 *            the y position of the cell
	 * @return A BasicCell or any subclass
	 */
	private BasicCell makeCell(char cell, int x, int y) {
		switch (cell) {
			case WALL:
				return new BasicCell(3, x, y);
			case FLOOR:
				return new BasicCell(0, x, y);
			case GOAL:
				return new GoalCell(x, y);
			case BOOTS:
				return new ItemCell(ItemCell.IRON_BOOTS, x, y);
			case FLIPPERS:
				return new ItemCell(ItemCell.FLIPPERS, x, y);
			case TOKEN:
				return new ItemCell(ItemCell.TOKEN, x, y);
			case WATER:
				return new DeadlyCell(x, y, true);
			case WIND:
				return new DeadlyCell(x, y, false);
			case RED_KEY:
				return new KeyCell(x, y, new Key(Key.RED));
			case GREEN_KEY:
				return new KeyCell(x, y, new Key(Key.GREEN));
			case BLUE_KEY:
				return new KeyCell(x, y, new Key(Key.BLUE));
			case RED_DOOR:
				return new ColouredDoor(x, y, Key.RED);
			case GREEN_DOOR:
				return new ColouredDoor(x, y, Key.GREEN);
			case BLUE_DOOR:
				return new ColouredDoor(x, y, Key.BLUE);
			default:
				return new BasicCell(3, x, y);
		}

	}

	/**
	 * Gets the map.
	 * @return The map.
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Make an instance of a player with the correct items.
	 *
	 * @return a player
	 */
	private Player makePlayer() {
		String[] playerString = this.fileString[playerLine].split(",");
		int x = Integer.parseInt(playerString[0]);
		int y = Integer.parseInt(playerString[1]);
		int tokens = Integer.parseInt(playerString[2]);
		boolean hasFlippers = playerString[3].equals("true");


		boolean hasShoes = playerString[4].equals("true");
		ArrayList<Key> keys = new ArrayList<Key>();

		if (playerString.length == 6) {
			for (char ch : playerString[5].toCharArray()) {
				switch (ch) {
					case 'r':
						keys.add(new Key(Key.RED));
						break;
					case 'g':
						keys.add(new Key(Key.GREEN));
						break;
					case 'b':
						keys.add(new Key(Key.BLUE));
						break;
					default:
						break;

				}
			}

		}

		// return new Player(map.getCell(x, y),gc,map);
		return new Player(map.getCell(x, y), gc, map, tokens, hasShoes, hasFlippers, keys);
	}

	/**
	 * Creates instants of all the enemy in the level in an array.
	 *
	 * @return a list of enemies
	 */
	public Enemy[] makeEnemyList() {
		int numOfEnemy = this.fileString.length - (playerLine + 1);
		int count = 0;
		Enemy[] enemyList = new Enemy[numOfEnemy];
		for (int i = playerLine + 1; i < this.fileString.length; i++) {
			String[] enemyString = this.fileString[i].split(",");
			int x = Integer.parseInt(enemyString[0]);
			int y = Integer.parseInt(enemyString[1]);
			enemyList[count] = makeEnemy(enemyString[2], x, y, enemyString[3]);
			count++;

		}

		return enemyList;
	}

	/**
	 * create an instance of an enemy given its name.
	 *
	 * @param enemy
	 *            what type of enemy
	 * @param x The x position.
	 * @param y The y position
	 * @param extra
	 * @return an enemy
	 */
	private Enemy makeEnemy(String enemy, int x, int y, String extra) {
		switch (enemy) {
			case STRAIGHT_LINE:
				int dir = 0;
				if (extra == "UP") {
					dir = 1;
				}
				return new StraightLineEnemy(map.getCell(x, y), this.gc, this.map, dir);
			case WALL_FOLLOW:
				return new WallFollowEnemy(map.getCell(x, y), this.gc, this.map);
			case SMART_TARGETING:
				return new SmartTargetingEnemy(map.getCell(x, y), this.gc, this.map, thePlayer);
			case DUMB_TARGETING:
				return new DumbTargetingEnemy(map.getCell(x, y), this.gc, this.map, thePlayer);
			default:
				return new Enemy(map.getCell(x, y), this.gc, this.map, 0);
		}
	}

	/**
	 * Gets the player.
	 * @return The player
	 */
	public Player getThePlayer() {
		this.thePlayer = makePlayer();
		return this.thePlayer;
	}

	/**
	 * Creates a pair on teleporter and puts then into the map.
	 *
	 * @param x
	 *            x position of the first teleporter
	 * @param y
	 *            y position of the first teleporter
	 * @param xL
	 *            x position of the second teleporter
	 * @param yL
	 *            y position of the second teleporter
	 * @param map The map
	 * @return an 2d array of cells
	 */
	private BasicCell[][] loadTele(int x, int y, int xL, int yL, BasicCell[][] map) {
		map[x][y] = new Teleporter(x, y, xL, yL);
		map[xL][yL] = ((Teleporter) map[x][y]).getLink();
		return map;
	}

	/**
	 * Gets the time that the player was on when they last saved the level.
	 * @return the time
	 */
	public int getTime() {
		return this.time;
	}

}
