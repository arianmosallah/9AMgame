package application;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This a character that the user can control and is used to interact with the
 * level.
 *
 * @author William Conroy
 * @version 1.3
 */
public class Player extends Character {
	private static final Image FRONT_IMAGE = new Image("PlayerFront.png");
	private static final Image BACK_IMAGE = new Image("PlayerBack.png");
	private static final Image LEFT_IMAGE = new Image("PlayerLeftSide.png");
	private static final Image RIGHT_IMAGE = new Image("PlayerRightSide.png");

	private boolean hasShoes;
	private boolean hasFlippers;
	private int tokenCount;
	private ArrayList<Key> keys;


	public Player(BasicCell location, GraphicsContext gc, Map map, int token, boolean hasShoes, boolean hasFlippers,
			ArrayList<Key> keys) {
		super(location, gc, map, 0);
		this.hasShoes = hasShoes;
		if(this.hasShoes ) {
				DeadlyCell.makeSafeWind();
		}
		this.hasFlippers = hasFlippers;
		if(this.hasFlippers ) {
				DeadlyCell.makeSafeWater();
		}
		this.tokenCount = token;
		this.keys = keys;

	}

	/**
	 * Give a key press it will try to move onto cell, in that direction.
	 *
	 * @param key The KeyCode of the pressed key
	 */
	public void move(int key) {
		//Get the cell it trying to move to
		BasicCell cell = getCell(key);

		//Make the player face the direction they are moving
		this.directionFacing = key;
		//Have to check doors and teleporters first as you might be able to open the doors
		if (cell instanceof Teleporter) {
			Sound.playTeleporterSound(Game.getLevelStage());
			cell = getCell(key, ((Teleporter) cell).getLink());
		} else if (cell instanceof ColouredDoor) {
			this.unlockColourDoor((ColouredDoor) cell);
			cell = getCell(key);
		} else if (cell instanceof TokenDoor) {
			this.unlockTokenDoor((TokenDoor) cell);
			cell = getCell(key);
		}

		//check if they can legal move onto the square
		if (canMove(cell)) {
			//check if the new square has any items
			if (cell instanceof ItemCell) {
				this.pickUp((ItemCell) cell);
			} else if (cell instanceof KeyCell) {
				this.pickUpKey((KeyCell) cell);
			}
			this.location = cell;
		}
		draw();

	}

	protected void draw() {
		switch (this.directionFacing) {
			case 0:
				gc.drawImage(BACK_IMAGE, this.location.getX() * size, this.location.getY() * size);
				break;
			case 1:
				gc.drawImage(RIGHT_IMAGE, this.location.getX() * size, this.location.getY() * size);
				break;
			case 2:
				gc.drawImage(FRONT_IMAGE, this.location.getX() * size, this.location.getY() * size);
				break;
			case 3:
				gc.drawImage(LEFT_IMAGE, this.location.getX() * size, this.location.getY() * size);
				break;

		}
	}

	/**
	 * checks if the player can move onto a cell.
	 *
	 * @param cell the cell that you want to move on
	 * @return true if you can
	 */
	protected boolean canMove(BasicCell cell) {

		if (cell.getSafeLevel() < BasicCell.WALL_SAFELEVEL) {
			return true;
		} else {
			Sound.playDoorErrorSound(Game.getLevelStage());
			return false;
		}

	}

	/**
	 * Collects the item in an item cell.
	 * @param cell The cell with an item on it.
	 */
	private void pickUp(ItemCell cell) {
		String item = cell.pickUpItem();
		this.map.setToBasic(cell.getX(), cell.getY());
		if (item == ItemCell.FLIPPERS) {
			this.hasFlippers = true;
			DeadlyCell.makeSafeWater();
		} else if (item == ItemCell.IRON_BOOTS) {
			this.hasShoes = true;
			DeadlyCell.makeSafeWind();
		} else if (item == ItemCell.TOKEN) {
			this.tokenCount++;
		}

	}

	/**
	 * Collects the key in a KeyCell.
	 * @param cell The cell with a key on it.
	 */
	private void pickUpKey(KeyCell cell) {
		this.keys.add(cell.getKey());
		this.map.setToBasic(cell.getX(), cell.getY());
	}


	/**
	 * Tries to unlock a TokenDoor.
	 * @param cell the token door.
	 */
	private void unlockTokenDoor(TokenDoor cell) {
		if (tokenCount == cell.getNumTokensForDoor()) {
			this.map.setToBasic(cell.getX(), cell.getY());
		} else {
			Sound.playDoorErrorSound(Game.getLevelStage());
		}
	}

	/**
	 * Tries to unlock a colour door.
	 * @param cell The coloured door.
	 */
	private void unlockColourDoor(ColouredDoor cell) {
		boolean openedDoor = false;
		int count = 0;
		int remove = 0;
		for (Key i : keys) {
			if (i.getColour().equalsIgnoreCase(cell.getDoorColour())) {

				this.map.setToBasic(cell.getX(), cell.getY());
				openedDoor = true;
				remove = count;
			}
			count++;
		}
		if (!openedDoor) {
			Sound.playDoorErrorSound(Game.getLevelStage());
		}else {
			keys.remove(remove);
		}
	}


	//-------------- Setters - and - Getters ------------------//

	/**
	 * Returns whether a player possess flippers.
	 *
	 * @return true if the player has it, false if not.
	 */
	public boolean isHasFlippers() {
		return hasFlippers;
	}

	/**
	 * Determines whether a player possess flippers.
	 *
	 * @param hasFlippers The boolean to chh=eck if player has flippers.
	 */
	public void setHasFlippers(boolean hasFlippers) {
		this.hasFlippers = hasFlippers;
	}

	/**
	 * Returns whether a player possess shoes/boots.
	 *
	 * @return true if the player has shoes
	 */
	public boolean isHasShoes() {
		return hasShoes;
	}

	/**
	 * Determines if a player has shoes.
	 *
	 * @param hasShoes true when the player has shoes
	 */
	public void setHasShoes(boolean hasShoes) {
		this.hasShoes = hasShoes;
	}

	/**
	 * Gets the number of tokens the player has.
	 *
	 * @return number of tokens on player
	 */
	public int getTokenCount() {
		return this.tokenCount;
	}

	/**
	 * Shows the colour and number of keys the player has.
	 *
	 * @return an arraylist of the key types and numbers
	 */
	public ArrayList<Key> getKeys() {
		return this.keys;
	}

}
