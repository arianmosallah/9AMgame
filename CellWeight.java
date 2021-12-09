package application;

import java.lang.Math;
import java.util.ArrayList;

/**
 * This class holds information and functionality about a specific cell on the
 * grid for the implementation of a pathfinding algorithm based on the A*
 * algorithm.
 * 
 * @author Scarpati.
 * @version 1.0
 */
public class CellWeight {
	public static final int H_WEIGHT_MULT = 1;
	private float gCost;
	private float hCost;
	private Character target;
	private Enemy enemy;
	private Map mapLevel;
	private BasicCell cell;
	private ArrayList<CellWeight> neighbourSet;
	private CellWeight previousCell;

	/**
	 * This constructor class instantiates the attributes of this class.
	 * 
	 * @param enemy
	 *            an instance of the enemy
	 * @param target
	 *            an instance of the player
	 * @param mapLevel
	 *            an instance of the map
	 * @param cell
	 *            an instance of the cell the instance of this class will
	 *            represent
	 */
	public CellWeight(Enemy enemy, Character target, Map mapLevel, BasicCell cell) {
		this.setEnemy(enemy);
		this.setTarget(target);
		this.setMapLevel(mapLevel);
		this.setCell(cell);
		this.setPreviousCell(null);
		this.setNeighbourSet();

		this.updateData();
	}

	/**
	 * It calculates the F Cost of the cell
	 * 
	 * @return
	 */
	public float calculateFCost() {
		return this.gCost + this.hCost;
	}

	/**
	 * it updates the hCost and GCost when the game state changes
	 */
	public void updateData() {
		this.cell = mapLevel.getTheMap()[this.cell.getX()][this.cell.getY()];
		this.hCost = this.calculateHCost();
		this.gCost = this.calculateGCost();

	}

	/**
	 * This methods adds neighbours to the neighbourSet if they are not
	 * outside the grid and they are not in the closedSet already.
	 * 
	 * @param cellWeightGrid
	 *            the grid of cellWeight instances representing the map
	 * @param closedSet
	 *            the closed set
	 */
	public void addNeighbours(CellWeight[][] cellWeightGrid, ArrayList<CellWeight> closedSet) {
		int posX = this.getCell().getX();
		int posY = this.getCell().getY();

		if (posX < cellWeightGrid.length - 1) {
			if (this.checkNeighbour(posX + 1, posY, closedSet, cellWeightGrid)) {
				this.neighbourSet.add(cellWeightGrid[posX + 1][posY]);
			}
		}

		if (posY < cellWeightGrid.length - 1) {
			if (this.checkNeighbour(posX, posY + 1, closedSet, cellWeightGrid)) {
				this.neighbourSet.add(cellWeightGrid[posX][posY + 1]);
			}
		}

		if (posX > 0) {
			if (this.checkNeighbour(posX - 1, posY, closedSet, cellWeightGrid)) {
				this.neighbourSet.add(cellWeightGrid[posX - 1][posY]);
			}
		}

		if (posY > 0) {
			if (this.checkNeighbour(posX, posY - 1, closedSet, cellWeightGrid)) {
				this.neighbourSet.add(cellWeightGrid[posX][posY - 1]);
			}
		}
	}

	/**
	 * This method calculates the GCost.
	 * 
	 * @return the GCost.
	 */
	private float calculateGCost() {
		return this.calculateDistance(this.cell, this.enemy.getLocation());
	}

	/**
	 * This method calculates the HCost.
	 * 
	 * @return the HCost.
	 */
	private float calculateHCost() {

		return this.calculateDistance(this.target.getLocation(), this.cell) * H_WEIGHT_MULT;

	}

	/**
	 * This method calculates the euclidean distance between two cells.
	 * 
	 * @param x
	 *            the first cell.
	 * @param y
	 *            the second cell.
	 * @return the distance between x and y.
	 */
	private float calculateDistance(BasicCell x, BasicCell y) {
		int distanceX = x.getX() - y.getX();
		int distanceY = y.getY() - x.getY();

		return (float) (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));
	}

	/**
	 * This method checks if a neighbour is can be added in the neighbour 
	 * set.
	 * 
	 * @param x
	 *            position x of neighbour.
	 * @param y
	 *            position y of neighbour.
	 * @param closedSet
	 *            the closed set.
	 * @param cellWeightGrid
	 *            the grid of cellWeights.
	 * @return true if neighbour is eligible.
	 */
	private boolean checkNeighbour(int x, int y, ArrayList<CellWeight> closedSet, CellWeight[][] cellWeightGrid) {
		BasicCell neighbour = cellWeightGrid[x][y].getCell();

		return (neighbour.getSafeLevel() <= this.enemy.getSafeLevel() && !closedSet.contains(cellWeightGrid[x][y]));
	}

	/**
	 * get an instance to the cell the path to the player is coming from.
	 * 
	 * @return the instance of previous cell.
	 */
	public CellWeight getPreviousCell() {
		return this.previousCell;
	}

	/**
	 * get the neighbour set of this cell.
	 * 
	 * @return the instance of the neighbour cell.
	 */
	public ArrayList<CellWeight> getNeighbourSet() {
		return this.neighbourSet;
	}

	/**
	 * get the gCost.
	 * 
	 * @return the gCost value.
	 */
	public float getgCost() {
		return this.gCost;
	}

	/**
	 * get the hCost.
	 * 
	 * @return the hCost value
	 */
	public float gethCost() {
		return this.hCost;
	}

	/**
	 * get the player that is being targeted.
	 * 
	 * @return the instance of player.
	 */
	public Character getTarget() {
		return this.target;
	}

	/**
	 * get the enemy that uses the information of this class.
	 * 
	 * @return the instance of enemy.
	 */
	public Enemy getEnemy() {
		return this.enemy;
	}

	/**
	 * get the map of this level.
	 * 
	 * @return the instance of mapLevel.
	 */
	public Map getMapLevel() {
		return this.mapLevel;
	}

	/**
	 * get the cell on the grid the instance of this class represents.
	 * 
	 * @return the cell.
	 */
	public BasicCell getCell() {
		return cell;
	}

	/**
	 * set the previous cell.
	 * 
	 * @param previousCell
	 *            the new previous cell instance.
	 */
	public void setPreviousCell(CellWeight previousCell) {
		this.previousCell = previousCell;
	}

	/**
	 * Instantiates the neighbour set.
	 */
	public void setNeighbourSet() {
		this.neighbourSet = new ArrayList<CellWeight>();
	}

	/**
	 * set the cell attribute.
	 * 
	 * @param cell
	 *            the new cell instance.
	 */
	public void setCell(BasicCell cell) {
		this.cell = cell;
	}

	/**
	 * set the G Cost.
	 * 
	 * @param gCost
	 *            the new value of G Cost.
	 */
	public void setgCost(float gCost) {
		this.gCost = gCost;
	}

	/**
	 * set the H Cost.
	 * 
	 * @param hCost
	 *            the new value of H Cost.
	 */
	public void sethCost(float hCost) {
		this.hCost = hCost;
	}

	/**
	 * set the target.
	 * 
	 * @param target
	 *            the new instance of Player to be targeted.
	 */
	public void setTarget(Character target) {
		this.target = target;
	}

	/**
	 * set the enemy.
	 * 
	 * @param enemy
	 *            the new instance of SmartTargetingEnemy.
	 */
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	/**
	 * set the map.
	 * 
	 * @param mapLevel
	 *            the new instance of Map.
	 */
	public void setMapLevel(Map mapLevel) {
		this.mapLevel = mapLevel;
	}

}