package application;

import java.util.ArrayList;
import java.lang.Math;

/**
 * This class implements functionality for a pathfinding algorithm based on the
 * A* algorithm. This class will allow instances of SmartTargetingEnemy to find
 * the path to the Player.
 * 
 * @author Scarpati, Conroy.
 * @version 1.3
 *
 */
public class AStar {
	private SmartTargetingEnemy enemy;
	private Character player;
	private Map mapLevel;
	private CellWeight[][] cellWeightGrid;
	private ArrayList<CellWeight> openSet;
	private ArrayList<CellWeight> closedSet;

	/**
	 * This constructor method initialises class attributes.
	 * 
	 * @param enemy
	 *            an instance of the Enemy.
	 * @param player
	 *            an instance of the Player.
	 * @param mapLevel
	 *            an instance of the Map.
	 */
	public AStar(SmartTargetingEnemy enemy, Character player, Map mapLevel) {

		this.setEnemy(enemy);
		this.setPlayer(player);
		this.setMapLevel(mapLevel);
		this.setCellWeightGrid(mapLevel.getMapWidth(), mapLevel.getMapHeight());
		this.setOpenSet();
		this.setClosedSet();
	}

	/**
	 * This method finds the shortest path between the Enemy and the Player.
	 * 
	 * @return an ArrayList containing all the steps required to reach the
	 *         Player.
	 */
	public ArrayList<CellWeight> findPath() {

		ArrayList<CellWeight> finalPath = new ArrayList<CellWeight>();
		this.updateData();
		this.setOpenSet();
		this.closedSet.clear();

		boolean foundPlayer = false;
		while (!this.openSet.isEmpty()) {
			CellWeight current = this.findLowestFCost();

			if (current.getCell().getX() == this.player.getLocation().getX()
					&& current.getCell().getY() == this.player.getLocation().getY()) {
				foundPlayer = true;
				CellWeight temp = current;
				finalPath.add(temp);

				while (temp.getPreviousCell() != null) {
					finalPath.add(temp.getPreviousCell());
					CellWeight temp2 = temp;

					temp = temp.getPreviousCell();
					temp2.setPreviousCell(null);
				}

				this.clearPrevious();

				return finalPath;
			}

			this.openSet.remove(current);
			this.closedSet.add(current);

			this.setNeighbours(current);

			ArrayList<CellWeight> neighbours = current.getNeighbourSet();

			for (int i = 0; i < neighbours.size(); i++) {
				CellWeight neighbour = neighbours.get(i);

				float tempGCost = current.getgCost() + 1;

				if (this.openSet.contains(neighbour)) {
					if (tempGCost < neighbour.getgCost()) {
						neighbour.setgCost(tempGCost);
					}
				} else {
					neighbour.setgCost(tempGCost);
					if (!closedSet.contains(neighbour)) {
						this.openSet.add(neighbour);
					}

				}

				if (current.getPreviousCell() != neighbour) {
					neighbour.setPreviousCell(current);
				}
			}
		}
		return getRandomMoves();

	}

	/**
	 * This method determines the next cell to move to. if the cell is not a
	 * neighbour of the current cell, the enemy moves randomly.
	 * 
	 * @return the next cell to move towards.
	 */
	public BasicCell getMove() {
		ArrayList<CellWeight> temp = this.findPath();

		int pos = temp.size() - 2;
		if (pos < 0) {
			pos = 0;
		}

		boolean isContained = false;

		CellWeight current = this.getLocationCellWeight();

		for (int i = 0; i < current.getNeighbourSet().size(); i++) {
			if (current.getNeighbourSet().get(i) == temp.get(pos)) {
				isContained = true;
			}
		}

		if (isContained) {
			return temp.get(pos).getCell();
		} else {
			return current.getCell();
		}

	}

	/**
	 * This method determines the neighbour set of all cells in the grid.
	 */
	public void setAllNeighbours() {
		for (int i = 0; i < this.cellWeightGrid.length; i++) {
			for (int j = 0; j < this.cellWeightGrid[0].length; j++) {
				this.cellWeightGrid[i][j].addNeighbours(this.cellWeightGrid, this.closedSet);
			}
		}
	}

	/**
	 * This method determines the neighbour set of a certain cell.
	 * 
	 * @param cell
	 *            the CellWeight whose neighbour set is to be determined.
	 */
	public void setNeighbours(CellWeight cell) {
		int x = cell.getCell().getX();
		int y = cell.getCell().getY();

		this.cellWeightGrid[x][y].addNeighbours(this.cellWeightGrid, this.closedSet);
	}

	/**
	 * get the mapLevel.
	 * 
	 * @return the mapLevel.
	 */
	public Map getMapLevel() {
		return mapLevel;
	}

	/**
	 * set the mapLevel.
	 * 
	 * @param mapLevel
	 *            an instance to the mapLevel.
	 */
	public void setMapLevel(Map mapLevel) {
		this.mapLevel = mapLevel;
	}

	/**
	 * get the Enemy.
	 * 
	 * @return the enemy.
	 */
	public SmartTargetingEnemy getEnemy() {
		return enemy;
	}

	/**
	 * get the player.
	 * 
	 * @return the instance of player.
	 */
	public Character getPlayer() {
		return player;
	}

	/**
	 * get the cellWeightGrid.
	 * 
	 * @return the instance of cellWeightGrid.
	 */
	public CellWeight[][] getCellWeightGrid() {
		return cellWeightGrid;
	}

	/**
	 * get the openSet.
	 * 
	 * @return the instance of openSet.
	 */
	public ArrayList<CellWeight> getOpenSet() {
		return openSet;
	}

	/**
	 * get the closedSet.
	 * 
	 * @return the instance of closedSet.
	 */
	public ArrayList<CellWeight> getClosedSet() {
		return closedSet;
	}

	/**
	 * set the enemy.
	 * 
	 * @param enemy
	 *            the new Enemy instance.
	 */
	public void setEnemy(SmartTargetingEnemy enemy) {
		this.enemy = enemy;
	}

	/**
	 * set the player.
	 * 
	 * @param player
	 *            the new Player instance.
	 */
	public void setPlayer(Character player) {
		this.player = player;

	}

	/**
	 * set the cellWeightGrid.
	 * 
	 * @param rows
	 *            the number of rows in the grid.
	 * @param cols
	 *            the number of columns in the grid.
	 */
	public void setCellWeightGrid(int rows, int cols) {
		this.cellWeightGrid = new CellWeight[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.cellWeightGrid[i][j] = new CellWeight(this.enemy, this.player, this.mapLevel,
						this.mapLevel.getCell(i, j));
			}
		}

	}

	/**
	 * initialises the openSet and adds the enemy location to it.
	 */
	public void setOpenSet() {
		int startX = this.enemy.getLocation().getX();
		int startY = this.enemy.getLocation().getY();

		this.openSet = new ArrayList<CellWeight>();
		this.openSet.add(this.cellWeightGrid[startX][startY]);
	}

	/**
	 * initialises the closedSet.
	 */
	public void setClosedSet() {
		this.closedSet = new ArrayList<CellWeight>();
	}

	/**
	 * this method generates a random integer between 2 bounds.
	 * 
	 * @param min
	 *            the min bound.
	 * @param max
	 *            the max bound.
	 * @return the random integer.
	 */
	public int getRandomInt(int min, int max) {
		int x = (int) (Math.random() * ((max - min) + 1)) + min;
		return x;
	}

	/**
	 * find the cell in the openSet with the lowest F Cost.
	 * 
	 * @return the cell with the lowest F Cost.
	 */
	private CellWeight findLowestFCost() {
		CellWeight minNode = this.openSet.get(0);
		float minValue = this.openSet.get(0).calculateFCost();

		for (int i = 0; i < this.openSet.size(); i++) {
			CellWeight currentNode = this.openSet.get(i);
			float currentValue = this.openSet.get(i).calculateFCost();

			if (currentValue < minValue) {
				minValue = currentValue;
				minNode = currentNode;
			}
		}

		return minNode;
	}

	/**
	 * this method picks a valid but random move for the enemy.
	 * 
	 * @return an ArrayList containing the chosen move.
	 */
	private ArrayList<CellWeight> getRandomMoves() {
		ArrayList<CellWeight> randomMoveArray = new ArrayList<CellWeight>();
		randomMoveArray.add(this.getLocationCellWeight());
		int randomNumber = this.getRandomInt(0, 3);
		CellWeight move = this.getLocationCellWeight().getNeighbourSet().get(randomNumber);
		while (!this.enemy.canMove(move.getCell())) {
			randomNumber = this.getRandomInt(0, 3);
			move = this.getLocationCellWeight().getNeighbourSet().get(randomNumber);

		}
		randomMoveArray.add(move);
		randomMoveArray.add(move);
		return randomMoveArray;

	}

	/**
	 * updates the data of the grid (G Cost and H Cost).
	 */
	private void updateData() {
		for (int i = 0; i < this.cellWeightGrid.length; i++) {
			for (int j = 0; j < this.cellWeightGrid[0].length; j++) {
				this.cellWeightGrid[i][j].updateData();
			}
		}
	}

	/**
	 * this method gets the CellWeight correspondent to the cell in which 
	 * the enemy is standing on.
	 * 
	 * @return the Enemy's CellWeight.
	 */
	private CellWeight getLocationCellWeight() {
		return this.cellWeightGrid[this.enemy.getLocation().getX()][this.enemy.getLocation().getY()];
	}

	/**
	 * sets the previous attributes of cellWeight instances to null.
	 */
	private void clearPrevious() {
		for (int i = 0; i < this.cellWeightGrid.length; i++) {
			for (int j = 0; j < this.cellWeightGrid[0].length; j++) {
				this.cellWeightGrid[i][j].setPreviousCell(null);
			}
		}
	}
}
