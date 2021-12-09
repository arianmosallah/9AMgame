package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * this class saves the current state of the game into a text file.
 * @author Scarpati
 *
 */

public class LevelSaver {
	private Level level;
	private String mapComposition;
	private String levelInfo;
	private String saveFileText;
	private String charactersInfo;
	
	/**
	 * constructs an instance of LevelSaver.
	 * @param level the level that is being played.
	 */
	public LevelSaver(Level level) {
		this.setLevel(level);
		this.updateSave();
	}

	/**
	 * set the characterInfo to a new value.
	 * @param charactersInfo the new value of characters info.
	 */
	public void setCharactersInfo(String charactersInfo) {
		this.charactersInfo = charactersInfo;
	}
	
	/**
	 * set the levelInfo to a new value.
	 * @param levelInfo the new value of levelInfo.
	 */
	public void setLevelInfo(String levelInfo) {
		this.levelInfo = levelInfo;
	}

	/**
	 * get the content of the save file.
	 * @return the content of the save file.
	 */
	public String getSaveFileText() {
		return saveFileText;
	}

	/**
	 * set the content of the save file.
	 * @param saveFileText the content of the save file.
	 */
	public void setSaveFileText(String saveFileText) {
		this.saveFileText = saveFileText;
		
		this.saveFileText = this.getLevelInfo() + this.getMap() + this.getCharactersInfo();
	}

	/**
	 * get the composition of the map.
	 * @return the composition of the map.
	 */
	public String getMapComposition() {
		return mapComposition;
	}

	/**
	 * set the map composition to a new value.
	 * @param mapComposition the new value of mapComposition.
	 */
	public void setMapComposition(String mapComposition) {
		this.mapComposition = mapComposition;
	}

	/**
	 * get the level that is being played.
	 * @return the level that is being played.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * set the level reference to a new value.
	 * @param level the new value of the level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * save the game into a txt file.
	 */
	public void saveGame() {
		
		String username = this.level.getUser();
		int levelnum = this.level.getLevelNum();
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(Game.SAVE_PATH + username + "-" + levelnum + ".txt", "UTF-8");
			this.updateSave();
			writer.println(this.getSaveFileText());
			System.out.println(username + "-" + levelnum + ".txt");
		} catch (IOException e) {
			System.out.println("File Not Found");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	/**
	 * get the map composition of the current level.
	 * @return the map composition.
	 */
	private String getMap() {
		Map map = this.level.getMap();
		BasicCell[][] mapGrid = map.getTheMap();
		
		ArrayList<Teleporter> teleporterList = new ArrayList<Teleporter>();
		
		for (int i = 0; i < map.getMapHeight(); i++) {
			for (int j = 0; j < map.getMapWidth(); j++) {
				this.isUnwalkable(mapGrid[j][i]);
				this.isBasicLevelCell(mapGrid[j][i]);
				this.isSafeCell(mapGrid[j][i], teleporterList);
				this.isDeadlyCell(mapGrid[j][i]);
			}
			this.mapComposition += "\r\n";
		}
		
		for (int i = 0; i < teleporterList.size(); i++) {
			this.mapComposition += "TELEPORTER,";
			
			int teleporterX = teleporterList.get(i).getX();
			int teleporterY = teleporterList.get(i).getY();
			int teleporterLinkX = teleporterList.get(i).getLink().getX();
			int teleporterLinkY = teleporterList.get(i).getLink().getY();
			
			this.mapComposition += teleporterX + ",";
			this.mapComposition += teleporterY + ",";
			this.mapComposition += teleporterLinkX + ",";
			this.mapComposition += teleporterLinkY + "\r\n";
		}
		
		return this.mapComposition;
	}
	
	/**
	 * get information on the level.
	 * @return the information on the current level.
	 */
	private String getLevelInfo() {
		this.levelInfo += this.level.getUser() + "\r\n";
		this.levelInfo += this.level.getLevelTimer().getTime() + "\r\n";
		this.levelInfo += this.level.getMap().getMapWidth() + "," + this.level.getMap().getMapHeight() + "\r\n";
		
		return this.levelInfo;
	}
	
	/**
	 * get the characters' info.
	 * @return the characters' info.
	 */
	private String getCharactersInfo() {
		Enemy[] enemyList = this.level.getEnemyList();
		
		int playerPosX = this.level.getPlayer().getLocation().getX();
		int playerPosY = this.level.getPlayer().getLocation().getY();
		int numOfTokens = this.level.getPlayer().getTokenCount();
		boolean hasFlippers = this.level.getPlayer().isHasFlippers();
		boolean hasBoots = this.level.getPlayer().isHasShoes();
		String keys = this.amountOfKeys();
		
		this.charactersInfo += playerPosX + "," + playerPosY + ",";
		this.charactersInfo += numOfTokens + ",";
		this.charactersInfo += hasFlippers + ",";
		this.charactersInfo += hasBoots + ",";
		this.charactersInfo += keys + "\r\n";
		
		for (int i = 0; i < enemyList.length; i++){
			int positionX = enemyList[i].getLocation().getX();
			int positionY = enemyList[i].getLocation().getY();
			this.charactersInfo += positionX + "," + positionY + ",";
			
			if (enemyList[i] instanceof StraightLineEnemy) {
				this.charactersInfo += "STRAIGHT,";
				
				if (((StraightLineEnemy) enemyList[i]).isDir()) {
					this.charactersInfo += "UP\r\n";
				} else {
					this.charactersInfo += "DOWN\r\n";
				}
				
			} else {
				if (enemyList[i] instanceof WallFollowEnemy) {
					this.charactersInfo += "WALL,";
				} else if (enemyList[i] instanceof DumbTargetingEnemy) {
					this.charactersInfo += "DUMB,";
				} else if (enemyList[i] instanceof SmartTargetingEnemy) {
					this.charactersInfo += "SMART,";
				}
				
				switch(enemyList[i].getDirectionFacing()){
					case 0:
						this.charactersInfo += "UP\r\n";
						break;
					case 1:
						this.charactersInfo += "RIGHT\r\n";
						break;
					case 2:
						this.charactersInfo += "DOWN\r\n";
						break;
					case 3:
						this.charactersInfo += "LEFT\r\n";
						break;
					default:
						System.out.println(enemyList[i].getDirectionFacing());
						break;
				}
			}
		}
		
		return this.charactersInfo;
	}
	
	private String amountOfKeys() {
		ArrayList<Key> keys = this.level.getPlayer().getKeys();
		String result = "";
		
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).getColour().equals(Key.RED)) {
				result += Key.RED;
			}
			
			if (keys.get(i).getColour().equals(Key.BLUE)) {
				result += Key.BLUE;
			}
			
			if (keys.get(i).getColour().equals(Key.GREEN)) {
				result += Key.GREEN;
			}
		}
		
		return result;
	}
	
	/**
	 * saves 0-level cells into the mapComposition attribute.
	 * @param cell the cell that is currently being studied.
	 */
	private void isBasicLevelCell(BasicCell cell) {

		if (cell.getSafeLevel() == BasicCell.BASIC_CELL_SAFELEVEL) {

			if (cell instanceof ItemCell) {
				String item = ((ItemCell) cell).pickUpItem();

				if (item.equals(ItemCell.FLIPPERS)) {
					this.mapComposition += LevelLoader.FLIPPERS;
				} else if (item.equals(ItemCell.IRON_BOOTS)) {
					this.mapComposition += LevelLoader.BOOTS;
				} else if (item.equals(ItemCell.TOKEN)) {
					this.mapComposition += LevelLoader.TOKEN;
				}

				// key cell
			} else if (cell instanceof KeyCell) {
				String keyColour = ((KeyCell) cell).getKey().getColour();

				if (keyColour.equals(Key.BLUE)) {
					this.mapComposition += LevelLoader.BLUE_KEY;
				} else if (keyColour.equals(Key.GREEN)) {
					this.mapComposition += LevelLoader.GREEN_KEY;
				} else if (keyColour.equals(Key.RED)) {
					this.mapComposition += LevelLoader.RED_KEY;
				}
			} else {
				this.mapComposition += LevelLoader.FLOOR;
			}
		}
	}
	
	/**
	 * saves 1-level cells into the mapCompositionAttribute.
	 * 
	 * @param cell
	 *            the cell that is currently being studied.
	 * @param teleporterList
	 *            list of teleporters in the level.
	 */
	private void isSafeCell(BasicCell cell, ArrayList<Teleporter> teleporterList) {

		if (cell.getSafeLevel() == BasicCell.SAFE_CELL_SAFELEVEL) {

			if (cell instanceof Teleporter) {

				this.mapComposition += LevelLoader.FLOOR;
				teleporterList.add((Teleporter) cell);

			} else if (cell instanceof GoalCell) {
				this.mapComposition += LevelLoader.GOAL;
			}
		}
	}
	
	/**
	 * saves 2-level cells into the mapCompositionAttribute.
	 * 
	 * @param cell
	 *            the cell that is currently being studied.
	 */
	private void isDeadlyCell(BasicCell cell) {
		if (cell instanceof DeadlyCell) {

			if (((DeadlyCell) cell).isWater()) {
				this.mapComposition += LevelLoader.WATER;
			} else {
				this.mapComposition += LevelLoader.WIND;
			}

		}
	}
	
	/**
	 * saves 3-level cells into the mapCompositionAttribute.
	 * 
	 * @param cell
	 *            the cell that is currently being studied.
	 */
	private void isUnwalkable(BasicCell cell) {
		if (cell.getSafeLevel() == BasicCell.WALL_SAFELEVEL) {

			if (cell instanceof ColouredDoor) {
				ColouredDoor door = (ColouredDoor) cell;

				if (door.getDoorColour().equals(Key.GREEN)) {
					this.mapComposition += LevelLoader.GREEN_DOOR;
				} else if (door.getDoorColour().equals(Key.RED)) {
					this.mapComposition += LevelLoader.RED_DOOR;
				} else if (door.getDoorColour().equals(Key.BLUE)) {
					this.mapComposition += LevelLoader.BLUE_DOOR;
				}
			} else if (cell instanceof TokenDoor) {
				this.mapComposition += ((TokenDoor) cell).getNumTokensForDoor();
			} else {
				this.mapComposition += LevelLoader.WALL;
			}
		}
	}
	
	private void updateSave() {
		this.setMapComposition("");
		this.setLevelInfo("");
		this.setCharactersInfo("");
		this.setSaveFileText("");
	}
}
