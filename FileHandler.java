package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class handles the files for users.
 * 
 * @author Gayathri Praveen
 * @version 1.3
 *
 */
public class FileHandler {
	public static final String USERS_PATH = System.getProperty("user.dir") + "\\source\\Users\\";

	/**
	 * Gets the users' information from their respective files.
	 * 
	 * @return The users.
	 * @throws FileNotFoundException
	 *             in case the file does not exist.
	 */
	public static ArrayList<UserProfile> getUsers() throws FileNotFoundException {
		File folder = new File(USERS_PATH);
		File[] listOfFiles = folder.listFiles();
		ArrayList<UserProfile> users = new ArrayList<UserProfile>();

		for (File file : listOfFiles) {
			UserProfile user = new UserProfile(file.getName().replace(".txt", ""));
			users.add(user);
		}

		return users;
	}

	/**
	 * Sorts the list of users by the time they have taken to complete the
	 * level.
	 * 
	 * @param levelNum
	 *            The level to sort the times of.
	 * @param users
	 *            The list of users to sort.
	 * @return The list of sorted users.
	 */
	public static ArrayList<UserProfile> sortByLevelTimes(int levelNum, ArrayList<UserProfile> users) {
		if (levelNum == 1) {
			users.sort(Comparator.comparing(UserProfile::getLevel1Time));
		} else if (levelNum == 2) {
			users.sort(Comparator.comparing(UserProfile::getLevel2Time));
		} else if (levelNum == 3) {
			users.sort(Comparator.comparing(UserProfile::getLevel3Time));
		}
		return users;
	}

	/**
	 * Gets the time a user has completed each level.
	 * 
	 * @param user
	 *            The user to get the times for.
	 * @return A list of times the user has taken to complete each level.
	 * @throws IOException
	 *             in case it gets interrupted by another process.
	 */
	public static int[] getUserTimes(String user) throws IOException {
		int[] times = new int[3];
		String filename = USERS_PATH + user + ".txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		String st;
		int count = 0;
		try {
			while ((st = br.readLine()) != null) {
				int time = Integer.parseInt(st);
				times[count] = time;
				count++;
			}
		} catch (IOException e) {
			return times;
		}
		br.close();
		return times;
	}

	/**
	 * Writes the times the user has taken to complete each level to their 
	 * file.
	 * 
	 * @param name
	 *            The name of the user.
	 * @param times
	 *            The times the user has taken to complete each level.
	 * @throws IOException
	 *             in case the reading of the file gets interrupted by 
	 *             another process.
	 */
	public static void writeToUserFile(String name, int[] times) throws IOException {
		String filename = FileHandler.USERS_PATH + name + ".txt";

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write("" + times[0]);
		writer.newLine();
		writer.write("" + times[1]);
		writer.newLine();
		writer.write("" + times[2]);
		writer.close();

	}
}
