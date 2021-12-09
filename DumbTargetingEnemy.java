package application;

import java.lang.Math;
import javafx.scene.canvas.GraphicsContext;

/**
 * A dumb targeting enemy that always moves directly toward the player, 
 * regardless of impassable objects.
 * 
 * @author Will Conroy
 *
 */
public class DumbTargetingEnemy extends Enemy {
	private static final String FRONT_LOCATION = "DumbTargetingEnemyFront.png";
	private static final String BACK_LOCATION = "DumbTargetingEnemyBack.png";
	private static final String LEFT_LOCATION = "DumbTargetingEnemyLeftSide.png";
	private static final String RIGHT_LOCATION = "DumbTargetingEnemyRightSide.png";
	private Player target;

	/**
	 * Constructs a dumb enemy with the player as a target.
	 * 
	 * @param location cell on the map the enemy is on
	 * @param gc The Graphics Context.
	 * @param mapLevel level in the game
	 * @param target   the player
	 */
	public DumbTargetingEnemy(BasicCell location, GraphicsContext gc, Map mapLevel, Player target) {
		super(location, gc, mapLevel, 0);
		this.setTarget(target);
		setImages(FRONT_LOCATION, BACK_LOCATION, LEFT_LOCATION, RIGHT_LOCATION);
	}

	/**
	 * Moves and draws the enemy.
	 */
	public void update() {
		move();
		draw();
	}

	/**
	 * Moves the instance onto the next cell.
	 */
	public void move() {
		BasicCell destination = this.getCell(this.findDirection());

		// Checks if the enemy is able to move onto its intended cell.
		if (this.canMove(destination)) {
			this.setLocation(destination);
		}
	}

	/**
	 * Locates the player as a target.
	 * 
	 * @return the player
	 */
	public Player getTarget() {
		return this.target;
	}

	/**
	 * Determines the player as the character the enemies have to aim for.
	 * 
	 * @param target The player
	 */
	public void setTarget(Player target) {
		this.target = target;
	}

	/**
	 * Determines the direction to face based on the position of the target.
	 * 
	 * @return the direction the enemy should be facing
	 */
	private int findDirection() {
		int targetXPosition = target.getLocation().getX();
		int targetYPosition = target.getLocation().getY();

		int xDistance = targetXPosition - location.getX();
		int yDistance = targetYPosition - location.getY();

		/*
		 * The general direction the enemy faces horizontally or vertically is
		 * determined by the most number of steps it needs to ultimately take on a
		 * Cartesian pane
		 */
		if (Math.abs(xDistance) >= Math.abs(yDistance)) {
			// Face left if the largest distance to the player is in the -x direction
			if (xDistance < 0) {
				this.directionFacing = DIRECTION_LEFT;
				return directionFacing;
			} else {
				// Face right if the largest distance to the player is in the +x direction
				this.directionFacing = DIRECTION_RIGHT;
				return directionFacing;
			}	
		} else {
			// Face down if the largest distance to the player is in the +y direction
			if (yDistance > 0) {
				this.directionFacing = DIRECTION_DOWN;
				return directionFacing;
			} else {
				// Face up if the largest distance to the player is in the -y direction
				this.directionFacing = DIRECTION_UP;
				return directionFacing;
			}
		}
	}
}
