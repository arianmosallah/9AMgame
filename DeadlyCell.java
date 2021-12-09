package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A cell that kills the player if they do not have the required item to pass.
 * 
 * @author William Conroy
 * @version 1.3
 */
public class DeadlyCell extends BasicCell {
	
	private static final Image WIND_IMAGE = new Image("WindTile.png");
	private static final Image WATER_IMAGE = new Image("WaterTile.png");
	
	// Safe level for all wind cells.
	private static int globalSafeLevelWind;
	// Safe level for all water cells.
	private static int globalSafeLevelWater;
	// True for water cell, false for wind cell.
	private boolean isWater;

	/**
	 * Constructs a deadly cell that only the player with the right item 
	 * can pass through.
	 * 
	 * @param x       x-value position of the cell on the map
	 * @param y       y-value position of the cell on the map
	 * @param isWater determine if water or wind cell
	 */
	DeadlyCell(int x, int y, boolean isWater) {
		super(2, x, y);
		this.isWater = isWater;

		// Sets the safe level for water or wind cell.
		if (this.isWater) {
			setGlobalSafeLevelWater(2);
		} else {
			setGlobalSafeLevelWind(2);
		}
	}

	/**
	 * Draw the cell onto the gc.
	 * 
	 * @param gc The graphics context.
	 */
	public void draw(GraphicsContext gc) {
		
		// Check if water or wind cell and draws the correct image.
		if (isWater) {
			gc.drawImage(WATER_IMAGE, positionX * size, positionY * size);
		} else {
			gc.drawImage(WIND_IMAGE, positionX * size, positionY * size);
		}
	}

	/**
	 * Makes all water cells passable.
	 */
	public static void makeSafeWater() {
		setGlobalSafeLevelWater(1);
	}

	/**
	 * Makes all wind cells passable.
	 */
	public static void makeSafeWind() {
		setGlobalSafeLevelWind(1);
	}

	/**
	 * Gets safe level of all water cells.
	 * @return The safelevel of the water cell.
	 */
	public static int getGlobalSafeLevelWater() {
		return globalSafeLevelWater;
	}

	/**
	 * Gets safe level of all wind cells.
	 * @return The safelevel of the wind cell.
	 */
	public static int getGlobalSafeLevelWind() {
		return globalSafeLevelWind;
	}

	/**
	 * Sets safe level of all water cells.
	 * @param globalSafeLevel The safe level.
	 */
	public static void setGlobalSafeLevelWater(int globalSafeLevel) {
		DeadlyCell.globalSafeLevelWater = globalSafeLevel;
	}

	/**
	 * Sets safe level of all wind cells.
	 * @param globalSafeLevel The safelevel.
	 */
	public static void setGlobalSafeLevelWind(int globalSafeLevel) {
		DeadlyCell.globalSafeLevelWind = globalSafeLevel;
	}

	/**
	 * Gets the safe level of deadly cells.
	 * @return The safe level.
	 */
	public int getSafeLevel() {
		//Returns the safe level depending on water or wind cell.
		if (this.isWater) {
			return getGlobalSafeLevelWater();
		} else {
			return getGlobalSafeLevelWind();
		}
	}

	/**
	 * Checks if deadly cell is a water or wind cell.
	 * @return true if water cell, false if wind cell
	 */
	public boolean isWater() {
		return this.isWater;
	}

}
