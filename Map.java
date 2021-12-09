package application;

import javafx.scene.canvas.GraphicsContext;
/**
 * Store and interacts with the map the character interact with.
 *
 * @author William Conroy

 * @version 1.5
 */
public class Map {
	//The array the hold all the cells in the map
	private BasicCell[][] theMap;
	private int mapWidth;
	private int mapHeight;

	Map(BasicCell[][] map, int width, int height) {
		this.mapHeight = height;
		this.mapWidth = width;
		this.theMap = map;
	}
	
	/**
	 * Gets the map.
	 * @return A 2D array of cells which represents the map.
	 */
	public BasicCell[][] getTheMap() {
		return theMap;
	}

	/**
	 * Redraws all the cells in the map onto the canvas.
	 * @param gc the graphics context.
	 */
	public void update(GraphicsContext gc) {
		for (int x = 0; x < theMap.length; x++) {
			for (int y = 0; y < theMap[0].length; y++) {
				theMap[x][y].draw(gc);
			}
		}
	}
	
	/**
	 * Gets a cell from a position in  the map.
	 * @param x The x position
	 * @param y the y position
	 * @return The cell.
	 */
	public BasicCell getCell(int x, int y) {
		return theMap[x][y];
	}
	
	/**
	 * Turns a give cell in the map into a basic floor cell.
	 * @param x The x position
	 * @param y The y position
	 */
	public void setToBasic(int x, int y) {
		theMap[x][y] = new BasicCell(0, x, y);	
	}
	
	/**
	 * Gets the width of the map.
	 * @return The width of the map.
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * Gets the height of the map.
	 * @return The height of the map
	 */
	public int getMapHeight() {
		return mapHeight;
	}

}
