package application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a cell with a key on it.
 * @author Gayathri Praveen
 * @version 1.0
 */
public class KeyCell extends BasicCell {
	private static final Image RED_KEY_IMAGE = new Image("RedKeyTile.png");
	private static final Image BLUE_KEY_IMAGE = new Image("KeyTile.png");
	private static final Image GREEN_KEY_IMAGE = new Image("GreenKeyTile.png");
	
	private Key key;
		
	/**
	 * Creates a cell with a key on it.
	 * @param x The position of the key on the x-axis.
	 * @param y The position of the key on the y-axis.
	 * @param key The key on the cell.
	 */
	public KeyCell(int x, int y, Key key) {
		super(0, x, y);
		this.key = key;
	}
	
	/**
	 * Get the key that is on the cell.
	 * @return The key that is on the cell.
	 */
	public Key getKey() {
		return key;
	}

		
	/**
	 * Draws the cell with the key onto the map.
	 * @param gc The graphics context.
	 */
	public void draw(GraphicsContext gc) {
		String colour = key.getColour();
		if (colour.equals(Key.RED)) {
			gc.drawImage(RED_KEY_IMAGE, positionX * Game.ELEMENT_SIZE, positionY *  Game.ELEMENT_SIZE);
		} else if (colour.equals(Key.GREEN)) {
			gc.drawImage(GREEN_KEY_IMAGE, positionX *  Game.ELEMENT_SIZE, positionY *  Game.ELEMENT_SIZE);
		} else if (colour.equals(Key.BLUE)) {
			gc.drawImage(BLUE_KEY_IMAGE, positionX *  Game.ELEMENT_SIZE, positionY *  Game.ELEMENT_SIZE);
		}
	}
	
	
}
