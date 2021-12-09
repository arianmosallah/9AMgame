package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A basic cell for the map which can be a wall or floor.
 * 
 * @author William Conroy
 *
 */
public class BasicCell {

	// All characters can move onto a cell with this integer.
	public static final int BASIC_CELL_SAFELEVEL = 0;
	// Only the player can move onto a cell with this integer.
	public static final int SAFE_CELL_SAFELEVEL = 1;
	// No characters can move onto a cell with this integer.
	public static final int WALL_SAFELEVEL = 3;

	// Coordinates on the map the cell is loaded.
	protected int positionX;
	protected int positionY;

	// Determines which characters are allowed to move onto a cell.
	protected int safeLevel;

	// In pixels the size which the cell takes up when displaced.
	protected int size;

	// Image for the cells.
	private static Image WALL_IMAGE = new Image("Wall.png");
	private static Image NORMAL_IMAGE = new Image("NormalTile.png");

	/**
	 * Constructs a basic cell.
	 * 
	 * @param level the safelevel of the cell
	 * @param x     x-value position of the cell on the map
	 * @param y     y-value position of the cell on the map
	 */
	BasicCell(int level, int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.safeLevel = level;
		this.size = Game.ELEMENT_SIZE;
	}

	/**
	 * Draws the cell onto the gc.
	 * 
	 * @param gc The Graphics Context.
	 */
	public void draw(GraphicsContext gc) {
		// Check if wall or normal floor cell and display the correct image.
		if (safeLevel == WALL_SAFELEVEL) {
			gc.drawImage(WALL_IMAGE, positionX * size, positionY * size);
		} else {
			gc.drawImage(NORMAL_IMAGE, positionX * size, positionY * size);
		}
	}

	/**
	 * Gets the x value position of the cell.
	 * 
	 * @return x-coordinate of the cell
	 */
	public int getX() {
		return positionX;
	}

	/**
	 * Gets the y value position of the cell.
	 * 
	 * @return y-coordinate of the cell
	 */
	public int getY() {
		return positionY;
	}

	/**
	 * Gets the cell's safelevel.
	 * 
	 * @return safelevel
	 */
	public int getSafeLevel() {
		return safeLevel;
	}

}
