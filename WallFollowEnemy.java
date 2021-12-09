package application;

import javafx.scene.canvas.GraphicsContext;
/**
 * This a enemy that will follow a wall around the map.
 * @author William Conroy
 * @version 1.0
 */
public class WallFollowEnemy extends Enemy {
	public static final int UP_MOVE = 1;
	public static final int DOWN_MOVE = 5;
	public static final int LEFT_MOVE = 7;
	public static final int RIGHT_MOVE = 3;
	public static final int UP_LEFT_MOVE = 0;
	public static final int UP_RIGHT_MOVE = 2;
	public static final int DOWN_LEFT_MOVE = 6;
	public static final int DOWN_RIGHT_MOVE = 4;
	
	
	private static final String FRONT_LOCATION  = "WallFollowingEnemyFront.png";
	private static final String BACK_LOCATION = "WallFollowingEnemyBack.png";
	private static final String LEFT_LOCATION  = "WallFollowingEnemyLeftSide.png";
	private static final String RIGHT_LOCATION  = "WallFollowingEnemyRightSide.png";
	

	/**
	 * Constructs a wall-following enemy with its location.
	 * @param location location on map
	 * @param gc the graphics context
	 * @param map map it is loaded into
	 */
	public WallFollowEnemy(BasicCell location, GraphicsContext gc, Map map) {
		super(location, gc, map, 0);
		setImages(FRONT_LOCATION, BACK_LOCATION, LEFT_LOCATION, RIGHT_LOCATION);
		
	}
	
	/**
	 * Finds out the next cell it is supposed to move onto.
	 * @return the correct cell to move to
	 */
	private BasicCell getMove() {
		BasicCell[] neighbours = this.getNeighbours(this.location);
		
		if (this.isNotAgainstAWall(this.getCell(this.location))) {
			return neighbours[this.directionFacing];
		}
		
		if (this.isOpeningLeft()) {
			this.directionFacing = this.getLeft();
		} else if (!this.canMove(neighbours[this.directionFacing])
				|| this.isNotAgainstAWall(this.getCell(neighbours[this.directionFacing]))) {
			if (this.checkMove(neighbours[this.getRight()])) {
				this.directionFacing = this.getRight();
			} else {
				this.directionFacing = this.spin();
			}
		} else {
			// this.directionFacing = this.spin();
			return neighbours[this.directionFacing];
		}

		return neighbours[this.directionFacing];
	}

	/**
	 * Checks immediate surrounding cells for any floor cells.
	 * @return true if there are surrounding cells, false otherwise.
	 */
	private boolean isOpeningLeft() {
		BasicCell[] neighbours = this.getNeighbours(this.location);
		BasicCell[] diagonalNeigh = this.getNeighboursDiagonals(this.location);

		if (this.canMove(neighbours[this.getLeft()]) && !this.canMove(diagonalNeigh[this.getLeft()])) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the left direction.
	 * @return The integer that represents the direction
	 */
	private int getLeft() {
		if (this.directionFacing == DIRECTION_UP) {
			return DIRECTION_LEFT;
		} else {
			return this.directionFacing - 1;
		}
	}

	/**
	 * The right direction.
	 * @return The integer representing the direction
	 */
	private int getRight() {
		if (this.directionFacing == DIRECTION_LEFT) {
			return DIRECTION_UP;
		} else {
			return this.directionFacing + 1;
		}
	}
	
	/**
	 * Turns the enemy around when it comes up to a cell it 
	 * cannot move onto.
	 * @return The integer representing the direction
	 */
	private int spin() {
		switch (this.directionFacing) {
			case DIRECTION_UP:
				return DIRECTION_DOWN;
			case DIRECTION_DOWN:
				return DIRECTION_UP;
			case DIRECTION_LEFT:
				return DIRECTION_RIGHT;
			case DIRECTION_RIGHT:
				return DIRECTION_LEFT;
			default:
				return DIRECTION_UP;
		}
	}
	
	/**
	 * Checks if it can move onto the next cell.
	 * @param nextCell The next cell it moves onto
	 * @return true if it can move onto it false otherwise.
	 */
	private boolean checkMove(BasicCell nextCell) {
		return this.canMove(nextCell) && !this.isNotAgainstAWall(this.getCell(nextCell));
	}

	/**
	 * Gets the move of the enemy.
	 */
	private void move() {
		this.location = getMove();

	}

	/**
	 * Moves and redraws the enemy.
	 */
	public void update() {
		move();
		draw();
	}

	/**
	 * Gets a list of the cells
	 * @param cell The cell.
	 * @return The list of cells.
	 */
	private BasicCell[] getCell(BasicCell cell) {
		BasicCell[] cellList = new BasicCell[9];
		cellList[0] = map.getCell(cell.getX() - 1, cell.getY() - 1);
		cellList[1] = map.getCell(cell.getX(), cell.getY() - 1);
		cellList[2] = map.getCell(cell.getX() + 1, cell.getY() - 1);
		cellList[3] = map.getCell(cell.getX() + 1, cell.getY());
		cellList[4] = map.getCell(cell.getX() + 1, cell.getY() + 1);
		cellList[5] = map.getCell(cell.getX(), cell.getY() + 1);
		cellList[6] = map.getCell(cell.getX() - 1, cell.getY() + 1);
		cellList[7] = map.getCell(cell.getX() - 1, cell.getY());
		cellList[8] = map.getCell(cell.getX(), cell.getY());
		return cellList;
	}

	/**
	 * Gets the cells next to the cell that the enemy is on.
	 * @param cell the cell that the enemy is on.
	 * @return The list of cells.
	 */
	private BasicCell[] getNeighbours(BasicCell cell) {
		BasicCell[] cellList = new BasicCell[4];
		cellList[0] = map.getCell(cell.getX(), cell.getY() - 1);
		cellList[1] = map.getCell(cell.getX() + 1, cell.getY());
		cellList[2] = map.getCell(cell.getX(), cell.getY() + 1);
		cellList[3] = map.getCell(cell.getX() - 1, cell.getY());
		return cellList;
	}

	/**
	 * Gets the cells digonal to the cell that the enemy is on.
	 * @param cell The cell the enemy is on.
	 * @return The list of diagonal cells.
	 */
	private BasicCell[] getNeighboursDiagonals(BasicCell cell) {
		BasicCell[] cellList = new BasicCell[4];
		cellList[0] = map.getCell(cell.getX() - 1, cell.getY() - 1);
		cellList[1] = map.getCell(cell.getX() + 1, cell.getY() - 1);
		cellList[2] = map.getCell(cell.getX() + 1, cell.getY() + 1);
		cellList[3] = map.getCell(cell.getX() - 1, cell.getY() + 1);
		return cellList;
	}

	/**
	 * Checks if the enemy is against a wall.
	 * @param cells The list of cells next to the enemy.
	 * @return true if not, false otherwise.
	 */
	private boolean isNotAgainstAWall(BasicCell[] cells) {
		for (int i = 0; i < cells.length; i++) {
			if (!canMove(cells[i])) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
}
