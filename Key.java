package application;
/** 
 * This class represents a key in the game.
 * @author Gayathri Praveen
 * @version 1.0
 */
public class Key {
	public static final String RED = "r";
	public static final String BLUE = "b";
	public static final String GREEN = "g";
	private String colour;

	/**
	 * Creates a key with a colour.
	 * @param colour The colour of the key.
	 */
	public Key(String colour) {
		this.colour = colour;
	}
	
	/**
	 * Gets the colour of the key.
	 * @return The colour.
	 */
	public String getColour() {
		return colour;
	}
	
	
}
