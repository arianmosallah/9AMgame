package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Creates a token door.
 * 
 * @author Jolene Foong
 * @version 1.0
 * @since 25/11/19
 *
 */
public class TokenDoor extends BasicCell {

	private int numTokensForDoor;
	private Image door = new Image("TokenDoor.png");

	/**
	 * Constructs a token door.
	 * 
	 * @param x                x-value position of the door on the map
	 * @param y                y-value position of the door on the map
	 * @param numTokensForDoor number tokens required to unlock door
	 */
	public TokenDoor(int x, int y, int numTokensForDoor) {
		super(3, x, y);
		this.numTokensForDoor = numTokensForDoor;
	}

	/**
	 * Gets the number of tokens for door.
	 * 
	 * @return number of tokens to unlock door
	 */
	public int getNumTokensForDoor() {
		return numTokensForDoor;
	}

	/**
	 * Draws a token door with image.
	 * 
	 * @param gc draws the door with image and position
	 */
	public void draw(GraphicsContext gc) {
		gc.drawImage(door, positionX * size, positionY * size);
		gc.setFill(Color.BLACK);
		gc.setFont(Font.font(40));
		gc.fillText("" + numTokensForDoor + "", (positionX + 0.45) 
				* size, (positionY + 0.55) * size);
	}
}
