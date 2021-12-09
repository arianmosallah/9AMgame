package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A character that can interact with the map.
 * 
 * @author William Conroy
 * @version 1.0
 *
 */
public class Character {

	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;

	// A cell location.
	protected BasicCell location;

	protected GraphicsContext gc;

	// Map the character is on.
	protected Map map;

	// Size that it should be drawn.
	protected int size;

	// What direction it is facing.
	protected int directionFacing;

	// What cells a character can move onto.
	protected int safeLevel;

	/**
	 * Constructs a character with initial starting position.
	 * 
	 * @param location        starting position on map
	 * @param gc			  The Graphics Context.
	 * @param mapLevel        map level the character is on
	 * @param directionFacing direction the character faces
	 */
	Character(BasicCell location, GraphicsContext gc, Map mapLevel, int directionFacing) {
		this.location = location;
		this.gc = gc;
		this.map = mapLevel;
		this.size = Game.ELEMENT_SIZE;
		this.directionFacing = directionFacing;
		this.safeLevel = 0;
	}

	/**
	 * Draws the character.
	 */
	protected void draw() {
		gc.setFill(Color.BLUE);
		// Draw a circle at the coordinates
		gc.fillOval(location.getX() * size, location.getY() * size, size, size);
	}

	/**
	 * Check if the character can move onto a given cell.
	 * 
	 * @param nextCell the cell to move into
	 * @return true if can move onto cell
	 */
	protected boolean canMove(BasicCell nextCell) {
		return (nextCell.getSafeLevel() == 0);
	}

	// -------------- Setters - and - Getters ------------------//

	/**
	 * Find out the location of the cell.
	 * 
	 * @return location cell on the map
	 */
	public BasicCell getLocation() {
		return location;
	}

	/**
	 * Determines a spot on the map for character.
	 * 
	 * @param location the location of the cell
	 */
	public void setLocation(BasicCell location) {
		this.location = location;
	}

	/**
	 * To see what kind of character (eg. player, dumb targeting enemy, etc)
	 * 
	 * @return safelevel of the character
	 */
	public int getSafeLevel() {
		return safeLevel;
	}

	/**
	 * Returns the current position of the direction the character is facing.
	 * 
	 * @return the direction the character faces
	 */
	public int getDirectionFacing() {
		return directionFacing;
	}

	/**
	 * Given a key press it will get the cell, in that direction.
	 * 
	 * @param key the KeyCode of the pressed key
	 * @return a cell
	 */
	protected BasicCell getCell(int key) {

		return getCell(key, this.location);
	}

	/**
	 * Finds the next cell of the arrow keypress direction.
	 * 
	 * @param key      arrowkey press
	 * @param location the current cell
	 * @return the neighbouring cell of keypress direction
	 */
	protected BasicCell getCell(int key, BasicCell location) {

		switch (key) {
			case DIRECTION_UP:
				return map.getCell(location.getX(), location.getY() - 1);
			case DIRECTION_RIGHT:
				return map.getCell(location.getX() + 1, location.getY());
			case DIRECTION_DOWN:
				return map.getCell(location.getX(), location.getY() + 1);
			case DIRECTION_LEFT:
				return map.getCell(location.getX() - 1, location.getY());
			default:
				System.out.println(key);
				return map.getCell(location.getX(), location.getY());
		}
	}

}
