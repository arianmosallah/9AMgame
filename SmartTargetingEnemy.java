package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This a enemy that will find the best route though the map to a give target
 * and move to it.
 * 
 * @author William Conroy
 * @version 1.3
 */
public class SmartTargetingEnemy extends Enemy {
	private static final String FRONT_LOCATION = "SmartTargetingEnemyFront.png";
	private static final String BACK_LOCATION = "SmartTargetingEnemyBack.png";
	private static final String LEFT_LOCATION = "SmartTargetingEnemyLeftSide.png";
	private static final String RIGHT_LOCATION = "SmartTargetingEnemyRightSide.png";
	
	private ArrayList<CellWeight> pathToPlayerList;
	private AStar aStar;
	private Character target;

	/**
	 * Creates a smart targeting enemy.
	 * @param location The location of the enemy.
	 * @param gc The graphics context.
	 * @param mapLevel The map.
	 * @param target The player.
	 */
	public SmartTargetingEnemy(BasicCell location, GraphicsContext gc, Map mapLevel, Character target) {
		super(location, gc, mapLevel, 0);

		// setting other attributes
		// this.target = target;
		this.setTarget(target);
		this.aStar = new AStar(this, this.target, mapLevel);
		this.setPathToPlayerList(aStar.findPath());
		setImages(FRONT_LOCATION, BACK_LOCATION, LEFT_LOCATION, RIGHT_LOCATION);
	}

	/**
	 * Moves the enemy.
	 */
	public void move() {
		BasicCell move = this.aStar.getMove();
		this.directionFacing = this.findDir(move);
		this.setLocation(move);
		this.showPath(this.aStar.findPath());
	}

	/**
	 * Finds the direction to move in
	 * @param move The next cell.
	 * @return the integer that represents the direction
	 */
	private int findDir(BasicCell move) {

		if (move.getX() < this.location.getX()) {
			return DIRECTION_LEFT;
		} else if (move.getX() > this.location.getX()) {
			return DIRECTION_RIGHT;
		}

		if (move.getY() > this.location.getY()) {
			return DIRECTION_DOWN;
		} else {
			return DIRECTION_UP;
		}
	}

	/**
	 * Redraws the enemy.
	 */
	public void update() {
		this.move();
		this.draw();
	}

	// setters and getters
	/**
	 * Gets the path to player.
	 * @return the arraylist of cell weight
	 */
	public ArrayList<CellWeight> getPathToPlayerList() {
		return pathToPlayerList;
	}

	/**
	 * Gets the character the enemy is targeting.
	 * @return the target
	 */
	public Character getTarget() {
		return target;
	}

	/**
	 * Sets the path to the player.
	 * @param pathToPlayerList The path.
	 */
	public void setPathToPlayerList(ArrayList<CellWeight> pathToPlayerList) {
		this.pathToPlayerList = pathToPlayerList;
	}

	/**
	 * Sets the target if the enemy.
	 * @param target The target.
	 */
	public void setTarget(Character target) {
		this.target = target;
		// this.aStar.setPlayer(target);
	}

	/**
	 * Show the path of the smart enemy.
	 * @param path The path of the enemy.
	 */
	private void showPath(ArrayList<CellWeight> path) {
		for (int i = 0; i < path.size() - 1; i++) {
			gc.setFill(Color.GREEN);
			gc.fillOval(path.get(i).getCell().getX() * size, path.get(i).getCell().getY() * size, 40, 40);
		}
	}

}