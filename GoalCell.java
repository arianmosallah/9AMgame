package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Goal cell for each level to progress the player to the next level.
 * 
 * @author William Conroy
 * @version 1.0
 *
 */

public class GoalCell extends BasicCell {

	private static final Image GOAL_IMAGE = new Image("Goal.png");

	/**
	 * Constructs a goal cell that only a player can move onto.
	 * 
	 * @param x x-value position of the goal cell on the map
	 * @param y y-value position of the goal cell on the map
	 */
	GoalCell(int x, int y) {
		super(1, x, y);
	}

	/**
	 * Draws the goal cell onto the map.
	 * @param gc The Graphics Context.
	 */
	public void draw(GraphicsContext gc) {
		gc.drawImage(GOAL_IMAGE, positionX * size, positionY * size);
	}

}
