package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Creates a coloured door.
 * 
 * @author Jolene Foong
 * @version 1.0
 * @since 25/11/19
 *
 */

public class ColouredDoor extends BasicCell {
	private static final Image RED_DOOR = new Image("RedDoor.png");
	private static final Image GREEN_DOOR = new Image("GreenDoor.png");
	private static final Image BLUE_DOOR = new Image("BlueDoor.png");
	private String doorColour;

	/**
	 * Constructs a coloured door.
	 * 
	 * @param x          x-value position of the door on the map
	 * @param y          y-value position of the door on the map
	 * @param doorColour colour of the door
	 */
	public ColouredDoor(int x, int y, String doorColour) {
		super(3, x, y);
		this.doorColour = doorColour;
	}

	/**
	 * Draws a coloured door with image.
	 * 
	 * @param gc draws the door with image and position
	 */
	public void draw(GraphicsContext gc) {
		if (doorColour.equals(Key.RED)) {
			gc.drawImage(RED_DOOR, positionX * Game.ELEMENT_SIZE, positionY 
					* Game.ELEMENT_SIZE);
		} else if (doorColour.equals(Key.GREEN)) {
			gc.drawImage(GREEN_DOOR, positionX * Game.ELEMENT_SIZE, positionY 
					* Game.ELEMENT_SIZE);
		} else if (doorColour.equals(Key.BLUE)) {
			gc.drawImage(BLUE_DOOR, positionX * Game.ELEMENT_SIZE, positionY 
					* Game.ELEMENT_SIZE);
		}
	}

	/**
	 * Gets the door colour.
	 * 
	 * @return door colour
	 */
	public String getDoorColour() {
		return doorColour;
	}
}
