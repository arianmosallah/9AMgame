package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A character that can kill the player.
 * 
 * @author William Conroy
 *
 */
public class Enemy extends Character {

	// The file location for the image for this basic enemy
	private static final String FRONT_LOCATION = "StraightLineEnemyFront.png";
	private static final String BACK_LOCATION = "StraightLineEnemyBack.png";
	private static final String LEFT_LOCATION = "StraightLineEnemyLeftSide.png";
	private static final String RIGHT_LOCATION = "StraightLineEnemyRightSide.png";

	protected Image frontImage;
	protected Image backImage;
	protected Image leftImage;
	protected Image rightImage;

	public Enemy(BasicCell location, GraphicsContext gc, Map map, int directionFacing) {
		super(location, gc, map, directionFacing);
		setImages(FRONT_LOCATION, BACK_LOCATION, LEFT_LOCATION, RIGHT_LOCATION);

	}

	/**
	 * Get the adjacent cell for a direction.
	 * 
	 * @param dir The direction in which you want the cell
	 * @return the cell in that direction.
	 */
	protected BasicCell getCell(int dir) {

		switch (dir) {
			case 0:
				return map.getCell(location.getX(), location.getY() - 1);
			case 1:
				return map.getCell(location.getX() + 1, location.getY());
			case 2:
				return map.getCell(location.getX(), location.getY() + 1);
			case 3:
				return map.getCell(location.getX() - 1, location.getY());
			default:
				System.out.println(dir);
				return map.getCell(location.getX(), location.getY());
		}

	}

	/**
	 * Creates and sets all images need to display the enemy.
	 * 
	 * @param front The name of the front image.
	 * @param back The name of the back image.
	 * @param left The name of the the left image
	 * @param right The name of the right image.
	 */
	protected void setImages(String front, String back, String left, String right) {
		frontImage = new Image(front);
		backImage = new Image(back);
		leftImage = new Image(left);
		rightImage = new Image(right);

	}

	/**
	 * draws the enemy onto a gc.
	 * 
	 */
	protected void draw() {
		switch (this.directionFacing) {
			case 0:
				gc.drawImage(backImage, this.location.getX() * size, this.location.getY() * size);
				break;
			case 1:
				gc.drawImage(rightImage, this.location.getX() * size, this.location.getY() * size);
				break;
			case 2:
				gc.drawImage(frontImage, this.location.getX() * size, this.location.getY() * size);
				break;
			case 3:
				gc.drawImage(leftImage, this.location.getX() * size, this.location.getY() * size);
				break;

		}
	}
	
	/**
	 * Redraws the enemy to face the right way.
	 */
	public void update() {
		this.draw();

	}
}
