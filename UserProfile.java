package application;

import java.io.IOException;

/**
 * This class represents a user.
 * 
 * @author Gayathri Praveen
 * @version 1.3
 */
public class UserProfile {
	private int[] times = new int[3];
	private String name;

	/**
	 * Creates a user profile.
	 * 
	 * @param name
	 *            The name of the user.
	 */
	public UserProfile(String name) {
		this.name = name;
		try {
			this.times = FileHandler.getUserTimes(name);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the times for the user and writes them to their file.
	 * 
	 * @param newTimes
	 *            The new times to replace the old times.
	 * @throws IOException
	 *             in case another process interrupts writing to the user's
	 *             file.
	 */
	public void setTimes(int[] newTimes) throws IOException {
		for (int i = 0; i < newTimes.length; i++) {
			if (times[i] > newTimes[i] || times[i] == 0) {
				times[i] = newTimes[i];
			}
		}
		FileHandler.writeToUserFile(name, times);
	}

	/**
	 * Gets the name of the user.
	 * 
	 * @return The name of the user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the time that the user took to complete level 1.
	 * 
	 * @return The time the user took to complete level 1.
	 */
	public int getLevel1Time() {
		return times[0];
	}

	/**
	 * Gets the time that the user took to complete level 2.
	 * 
	 * @return The time the user took to complete level 2.
	 */
	public int getLevel2Time() {
		return times[1];
	}

	/**
	 * Gets the time that the user took to complete level 3.
	 * 
	 * @return The time the user took to complete level 3.
	 */
	public int getLevel3Time() {
		return times[2];
	}

	/**
	 * Gets the level that the user is on currently.
	 * 
	 * @return The level the user is currently on.
	 */
	public int getUserLevel() {
		int levelNum = 1;
		for (int i : times) {
			if (i != 0) {
				levelNum++;
			}
		}
		return levelNum;
	}

}
