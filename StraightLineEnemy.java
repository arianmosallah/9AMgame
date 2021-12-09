package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * This a enemy that walk into a straight line until it hits a wall then turns
 * around.
 * 
 * @author William Conroy
 * @version 1.0
 */
public class StraightLineEnemy extends Enemy {
	// what direction it is moving in
	private boolean dir;
	// 0: moving up and down 1: moving left and right
	private int type;

	/**
	 * Creates a straight-line enemy.
	 * @param location The location of the enemy.
	 * @param gc The graphics context.
	 * @param map The map.
	 * @param type 0: moving up and down 1: moving left and right 
	 */
	public StraightLineEnemy(BasicCell location, GraphicsContext gc, Map map, int type) {
		super(location, gc, map, type);
		this.type = type;
	}

	/**
	 * Moves and draws the enemy.
	 */
	public void update() {
		move();
		draw();
	}

	/**
	 * Move the enemy in a straight line.
	 */
	private void move() {
		BasicCell cell = getCell(dir);
		// tries to move forward
		if (canMove(cell)) {
			this.location = cell;
		} else {
			// turns around
			dir = !dir;
			// tries again to move in the new direction
			cell = getCell(dir);
			if (canMove(cell)) {
				this.location = cell;
			}
		}
		draw();

	}

	/**
	 * Gets the cell in front of the enemy.
	 * @param dir The direction.
	 * @return The cell.
	 */
	private BasicCell getCell(boolean dir) {
		if (dir) {
			this.directionFacing = type;
			return super.getCell(type);

		} else {
			this.directionFacing = type + 2;
			return super.getCell(type + 2);
		}
	}

	/**
	 * Checks the direction of the enemy.
	 * @return The direction
	 */
	public boolean isDir() {
		return this.dir;
	}

}
