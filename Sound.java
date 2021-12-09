package application;

import java.io.File;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Sounds for game.
 * 
 * @author Jolene Foong
 * @author Gayathri Praveen
 * @since 3/12/19
 * @version 1.0
 *
 */
public class Sound extends Application {
	private static Media overworld9am = new Media(new File(Game.SOUND_PATH 
			+ "overworld_9am.mp3").toURI().toString());
	private static MediaPlayer overworld9amPlayer = 
			new MediaPlayer(overworld9am);

	/**
	 * Plays the background music in a loop.
	 */
	public static void playOverworldSound() {
		overworld9amPlayer.setVolume(0.5);
		overworld9amPlayer.play();
		overworld9amPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}

	/**
	 * Stops the background music.
	 */
	public static void closeBGM() {
		overworld9amPlayer.stop();
	}

	/**
	 * Plays the teleporter sound.
	 * 
	 * @param stage allows the sound to be played
	 */
	public static void playTeleporterSound(Stage stage) {
		File teleporter = new File(Game.SOUND_PATH + "teleporter.mp3");
		Media teleporterSound = new Media(teleporter.toURI().toString());
		MediaPlayer teleporterPlayer = new MediaPlayer(teleporterSound);

		teleporterPlayer.setVolume(1.0);
		teleporterPlayer.play();
	}

	/**
	 * Plays error sound when the player makes an invalid move.
	 * 
	 * @param stage allows the sound to be played
	 */
	public static void playDoorErrorSound(Stage stage) {
		File doorError = new File(Game.SOUND_PATH + "doorError.mp3");
		Media doorErrorSound = new Media(doorError.toURI().toString());
		MediaPlayer doorErrorPlayer = new MediaPlayer(doorErrorSound);

		doorErrorPlayer.setVolume(1.0);
		doorErrorPlayer.play();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}
}
