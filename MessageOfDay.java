package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Gets the message of the day.
 * @author Gayathri Praveen
 * @author Jolene Foong
 * @version 1.0
 *
 */
public class MessageOfDay {
	
	/**
	 * Retrieves contents of URL.
	 * 
	 * @param readUrl URL to get request
	 * @return contents of the URL
	 * @throws IOException if another process closes the stream or is 
	 * disconnected
	 */
	private static String getReq(String readUrl) throws IOException {
		URL url = new URL(readUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		
		// When 
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		return content.toString();
	}
	
	/**
	 * Solves the puzzle.
	 * @return the solved puzzle.
	 * @throws IOException in case another process closes the stream or it is 
	 * disconnected.
	 */
	private static String solvePuzzle() throws IOException {
		//Gets the puzzle as a string.
		String puzzle = getReq("http://cswebcat.swan.ac.uk/puzzle");
		//Puts each character into an array.
		char[] arrayPuzzle = puzzle.toCharArray();

		//Goes through the string to set each letter correctly.
		for (int i = 0; i < arrayPuzzle.length; i++) {
			
			//Selects every other character starting from the first.
			if (i % 2 == 0) {
				//Goes to the front of the alphabet.
				if (arrayPuzzle[i] == 'Z') {
					arrayPuzzle[i] = 'A';
				} else {
					//Shift the letter one alphabet forward.
					arrayPuzzle[i] = ++arrayPuzzle[i];
				}
			} else { //Selects every other character starting from the second.
				
				//Goes to the back of the alphabet.
				if (arrayPuzzle[i] == 'A') {
					arrayPuzzle[i] = 'Z';
				} else {
					//Shift the letter one alphabet back.
					arrayPuzzle[i] = --arrayPuzzle[i];
				}
			}
		}

		StringBuilder outString = new StringBuilder("");
		outString.append(arrayPuzzle);
		return outString.toString();
	}
	
	/**
	 * Gets the message of the day.
	 * @return the message of the day.
	 * @throws IOException in case another process closes the stream or it is 
	 * disconnected.
	 */
	public static String getMsg() throws IOException {
		String solved = solvePuzzle();
		String url = "http://cswebcat.swan.ac.uk/message?solution="+solved;
		return getReq(url);
	}

}
