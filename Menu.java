package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * This class represents the GUI for the game.
 * 
 * @author Gayathri Praveen
 * @author Will Conroy
 * @author Zhihao Chen
 * @version 1.5
 */
public class Menu {
	// The dimension of the windows of the all menu
	public static final int MENU_WIDTH = 400;
	public static final int MENU_HEIGHT = 400;

	/**
	 * Creates a Menu window.
	 * 
	 * @param stage
	 *            Used to display the Menu window to screen.
	 * @throws IOException
	 *             For Message of the Day in case another process closes the
	 *             stream or it is disconnected.
	 */
	public void mainMenu(Stage stage) throws IOException {
		StackPane r = new StackPane();
		BorderPane root = new BorderPane();
		Image bg = new Image("menuImage.png");
		ImageView img = new ImageView(bg);
		img.toBack();
		Button newGameB = new Button("New Game");
		Button howToPlayB = new Button("How To Play");
		Button loadGameB = new Button("Load Game");
		Button leaderboardB = new Button("Leaderboards");
		Button showProfileB = new Button("See Profiles");
		Button exitB = new Button("Exit");

		newGameB.setPrefWidth(150);
		howToPlayB.setPrefWidth(150);
		loadGameB.setPrefWidth(150);
		leaderboardB.setPrefWidth(150);
		showProfileB.setPrefWidth(150);
		exitB.setPrefWidth(150);
		newGameB.setPrefHeight(40);
		howToPlayB.setPrefHeight(40);
		loadGameB.setPrefHeight(40);
		leaderboardB.setPrefHeight(40);
		showProfileB.setPrefHeight(40);
		exitB.setPrefHeight(40);

		newGameB.setStyle("-fx-font: 15 arial;");
		howToPlayB.setStyle("-fx-font: 15 arial;");
		loadGameB.setStyle("-fx-font: 15 arial;");
		leaderboardB.setStyle("-fx-font: 15 arial;");
		showProfileB.setStyle("-fx-font: 15 arial;");
		exitB.setStyle("-fx-font: 15 arial;");

		newGameB.setOnAction(e -> {
			stage.close();
			try {
				selectProfileMenu();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

		howToPlayB.setOnAction(e -> {
			stage.close();
			howToPlayMenu();
		});

		loadGameB.setOnAction(e -> {
			stage.close();
			try {
				loadGameMenu();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		leaderboardB.setOnAction(e -> {
			stage.close();
			try {
				showLeaderboard();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		showProfileB.setOnAction(e -> {
			stage.close();
			try {
				showProfiles();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

		exitB.setOnAction(e -> {
			stage.close();
		});

		Label msg = new Label(MessageOfDay.getMsg());
		msg.setTextFill(Color.WHITE);
		msg.setWrapText(true);
		msg.setAlignment(Pos.CENTER);
		msg.setPadding(new Insets(10));

		VBox menuButtons = new VBox(newGameB, howToPlayB, loadGameB, leaderboardB, showProfileB, exitB);
		menuButtons.setSpacing(5);
		menuButtons.setAlignment(Pos.CENTER);
		menuButtons.setPadding(new Insets(100, 0, 0, 0));

		Scene scene = new Scene(r, 600, 492);
		stage.setTitle("9AM");
		root.setCenter(menuButtons);
		root.setBottom(msg);
		stage.setScene(scene);
		r.getChildren().addAll(img, root);
		stage.show();
	}

	/**
	 * Creates a window describing how to play.
	 */
	public void howToPlayMenu() {
		Stage stage = new Stage();
		stage.setTitle("How To Play");
		BorderPane root = new BorderPane();
		Text title = new Text("How To Play");
		title.setFill(Color.BLUE);
		title.setFont(Font.font(null, FontWeight.BOLD, 20));
		Text arrowKeys = new Text("Arrow keys to move.");
		Text sKey = new Text("Press S to save.");
		Text escKey = new Text("Press ESC to return to menu.");
		VBox howToPlayB = new VBox(arrowKeys, sKey, escKey);
		root.setTop(title);
		root.setCenter(howToPlayB);
		root.setBottom(this.createBackButton(stage));
		Scene scene = new Scene(root, 300, 200);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Allows user to select profile.
	 * @throws FileNotFoundException In case a user's file is not found.
	 */
	public void selectProfileMenu() throws FileNotFoundException {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		stage.setTitle("Select Profile");
		Text selectT = new Text("Select Profile:");
		selectT.setTextAlignment(TextAlignment.CENTER);
		root.setTop(selectT);
		VBox buttons = new VBox();
		
		ArrayList<UserProfile> users = FileHandler.getUsers();
		for (UserProfile u : users) {
			Button user = new Button(u.getName());
			user.setOnAction(e -> {
				stage.close();
				Game.newGame(((Button) e.getSource()).getText());
			});
			buttons.getChildren().add(user);
		}
		
		Button createB = new Button("Create new profile");
		createB.setOnAction(e -> {
			stage.close();
			enterNameMenu();
		});
		buttons.getChildren().add(createB);
		root.setCenter(buttons);
		root.setBottom(this.createBackButton(stage));
		Scene scene = new Scene(root, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Creates a window where the user enters their name.
	 */
	public void enterNameMenu() {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Label nameL = new Label("Enter name: ");
		TextField textField = new TextField();
		HBox nameEntry = new HBox(nameL, textField);
		nameEntry.setAlignment(Pos.CENTER);
		Button ok = new Button("OK");
		textField.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				stage.close();
				String name = textField.getText();
				Game.newGame(name);
			}
		});

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				stage.close();
				String name = textField.getText();
				Game.newGame(name);
			}
		};

		ok.setOnAction(event);

		HBox buttons = new HBox(this.createBackButton(stage), ok);
		buttons.setSpacing(206);

		root.setCenter(nameEntry);
		root.setBottom(buttons);
		Scene scene = new Scene(root, 300, 200);
		stage.setTitle("Enter Name");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Displays the leaderboard.
	 * 
	 * @throws IOException
	 *             in case another process interrupts the reading of a user's
	 *             file.
	 */
	private void showLeaderboard() throws IOException {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		stage.setTitle("Leaderboards");

		Text title = new Text("Leaderboard");
		title.setFill(Color.BLUE);
		title.setFont(Font.font(null, FontWeight.BOLD, 20));

		Text level1 = new Text("Level 1");
		level1.setFont(Font.font(null, FontWeight.BOLD, 15));
		Text level2 = new Text("Level 2");
		level2.setFont(Font.font(null, FontWeight.BOLD, 15));
		Text level3 = new Text("Level 3");
		level3.setFont(Font.font(null, FontWeight.BOLD, 15));
		VBox leaderboardB = new VBox();
		leaderboardB.setSpacing(10);
		ArrayList<UserProfile> users = FileHandler.getUsers();
		VBox l1Names = new VBox(level1);

		users = FileHandler.sortByLevelTimes(1, users);
		int count = 0;
		for (UserProfile u : users) {
			if (u.getLevel1Time() != 0) {
				Text name = new Text(u.getName() + " - Time: " + u.getLevel1Time());
				count++;
				if (count <= 3) {
					l1Names.getChildren().add(name);
				}
			}
		}

		leaderboardB.getChildren().add(l1Names);

		VBox l2Names = new VBox(level2);
		users = FileHandler.sortByLevelTimes(2, users);
		count = 0;
		for (UserProfile u : users) {
			if (u.getLevel2Time() != 0) {
				Text name = new Text(u.getName() + " - Time: " + u.getLevel2Time());
				count++;
				if (count <= 3) {
					l2Names.getChildren().add(name);
				}
			}
		}

		leaderboardB.getChildren().add(l2Names);

		VBox l3Names = new VBox(level3);
		users = FileHandler.sortByLevelTimes(3, users);
		count = 0;
		for (UserProfile u : users) {
			if (u.getLevel3Time() != 0) {
				Text name = new Text(u.getName() + " - Time: " + u.getLevel3Time());
				count++;
				if (count <= 3) {
					l3Names.getChildren().add(name);
				}
			}
		}

		leaderboardB.getChildren().add(l3Names);

		root.setTop(title);
		root.setCenter(leaderboardB);
		root.setBottom(this.createBackButton(stage));
		Scene scene = new Scene(root, MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Displays the saved games of the users.
	 * 
	 * @throws FileNotFoundException
	 *             in case a saved game file is not found.
	 */
	public void loadGameMenu() throws FileNotFoundException {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		stage.setTitle("Load Game");
		Text msgT = new Text("Select your saved game: ");
		msgT.setFont(new Font(15));
		HBox msgBox = new HBox(msgT);

		VBox buttons = new VBox();
		ArrayList<String> games = getSavedGames();
		for (int i = 0; i < games.size(); i++) {
			Button gameButton = new Button(games.get(i));
			gameButton.setPrefWidth(200);
			gameButton.setStyle("-fx-font: 14 arial;");
			gameButton.setOnAction(e -> {
				stage.close();
				Game.loadSavedLevel(((Button) e.getSource()).getText());
			});
			buttons.getChildren().add(gameButton);

		}
		buttons.setSpacing(5);
		buttons.setAlignment(Pos.TOP_CENTER);
		msgBox.setAlignment(Pos.CENTER);
		root.setTop(msgBox);
		root.setCenter(buttons);
		root.setBottom(this.createBackButton(stage));
		Scene scene = new Scene(root, MENU_WIDTH, MENU_HEIGHT);
		stage.setTitle("Load Game");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Displays the users' names and the current level they are on.
	 * 
	 * @throws FileNotFoundException
	 *             in case the file of a user is not found.
	 */
	public void showProfiles() throws FileNotFoundException {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		stage.setTitle("Profiles");

		Text profileT = new Text("Profiles");
		profileT.setFill(Color.BLUE);
		profileT.setFont(Font.font(null, FontWeight.BOLD, 20));

		ArrayList<UserProfile> users = FileHandler.getUsers();
		VBox profiles = new VBox();
		for (UserProfile u : users) {
			String levelNum = " - Level: " + u.getUserLevel();
			if (levelNum.equals(" - Level: 4")) {
				levelNum = " - Game Completed";
			}
			Text userLevel = new Text(u.getName() + levelNum);
			profiles.getChildren().add(userLevel);
		}
		profiles.setSpacing(10);

		root.setTop(profileT);
		root.setCenter(profiles);
		root.setBottom(this.createBackButton(stage));
		Scene scene = new Scene(root, 400, 300);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Creates button that takes you to the main menu.
	 * 
	 * @param stage
	 *            To display the button.
	 * @return A back button.
	 */
	private Button createBackButton(Stage stage) {
		Button back = new Button("Go Back");
		back.setOnAction(e -> {
			stage.close();
			try {
				this.mainMenu(stage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		back.setAlignment(Pos.TOP_LEFT);
		return back;
	}

	/**
	 * Gets the users' saved games.
	 * 
	 * @return The list of the names of the saved games files.
	 * @throws FileNotFoundException
	 *             in case a file is not found.
	 */
	private ArrayList<String> getSavedGames() throws FileNotFoundException {

		File folder = new File(Game.SAVE_PATH);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> fileList = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			fileList.add(listOfFiles[i].getName().replace(".txt", ""));
		}
		return fileList;
	}

}
