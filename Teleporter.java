package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Transports a player onto a paired teleporter when moved onto.
 * 
 * @author William Conroy
 * @version 1.2
 */
public class Teleporter extends BasicCell {
	private static final Image TELE_IMAGE = new Image("Teleporter.png");

	private Teleporter link;

	/**
	 * Constructs a teleporter - only the player can move onto.
	 * 
	 * @param x    x-value position of the teleporter on the map
	 * @param y    y-value position of the teleporter on the map
	 * @param link the teleporter it is linked to
	 */
	Teleporter(int x, int y, Teleporter link) {
		super(1, x, y);
		this.link = link;
	}

	/**
	 * Constructs a teleporter that is linked to another.
	 * 
	 * @param x     x-value position of the teleporter on the map
	 * @param y     y-value position of the teleporter on the map
	 * @param xLink x-value position of the linked teleporter on the map
	 * @param yLink y-value position of the linked teleporter on the map
	 */
	Teleporter(int x, int y, int xLink, int yLink) {
		super(1, x, y);
		this.link = new Teleporter(xLink, yLink, this);
	}

	/**
	 * Gets the cell the teleporter teleports the player to.
	 * 
	 * @return the cell to teleport to
	 */
	public BasicCell getLink() {
		return link;
	}

	/**
	 * Draw the cell onto the gc.
	 * 
	 * @param gc The graphics context.
	 */
	public void draw(GraphicsContext gc) {
		gc.drawImage(TELE_IMAGE, positionX * size, positionY * size);
	}

}
