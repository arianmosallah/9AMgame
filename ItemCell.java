package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a cell with an item on it.
 * @author Gayathri Praveen
 * @version 1.3
 */
public class ItemCell extends BasicCell {
	public static final String IRON_BOOTS = "ironBoots";
	public static final String FLIPPERS = "flippers";
	public static final	String TOKEN = "token";
	public static final String NULL_ITEM = "null";
	private static final Image TOKEN_IMAGE = new Image("TokenTile.png");
	private static final Image BOOTS_IMAGE = new Image("BootsTile.png");
	private static final Image FLIPPERS_IMAGE = new Image("FlippersTile.png");
	private String item;
	
	/**
	 * Creates a cell with an item on it.
	 * @param item The item on the cell
	 * @param x The x position of the cell.
	 * @param y The y position of the cell.
	 */
	public ItemCell(String item, int x, int y) {
		super(0, x, y);
		this.item = item;
	}
	
	/**
	 * Gets the item that is on the cell.
	 * @return The item.
	 */
	public String pickUpItem() {
		return this.item;
	}
	
	/**
	 * Draws the cell with the item onto the map.
	 * @param gc The graphics context.
	 */
	public void draw(GraphicsContext gc) {
		if (item.equals(IRON_BOOTS)) {
			gc.drawImage(BOOTS_IMAGE, positionX * size, positionY * size);
		} else if (item.equals(FLIPPERS)) {
			gc.drawImage(FLIPPERS_IMAGE, positionX * size, positionY * size);
		} else if (item.equals(TOKEN)) {
			gc.drawImage(TOKEN_IMAGE, positionX * size, positionY * size);
		}
	}
}
