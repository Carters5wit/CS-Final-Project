package ludoGame;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Responsible for creating the game menu
 * shows different buttons that leads to other windows
 * for example the help button leads to the help window
 * that displays the gae rules
 * 
 * @author Toby Dokyi
 */
public class Menu {
	Stage gameStage;
	Scene gameScene;
	int players;
	
	public static <T extends Node> void addToParent(T node, StackPane r) {
    	r.getChildren().add(node);
	}
	
	public Menu(Stage primaryStage, Scene sc, StackPane root) {
		gameScene = sc;
		gameStage = primaryStage;
		
	   	// Window setup
		Stage menu = new Stage();
	   	menu.setTitle("Ludo Game");
	   	StackPane menuRoot = new StackPane();
	   	Scene menuScene = new Scene(menuRoot, 600, 350);
	   	menu.setScene(menuScene);
	   	menu.show();
	   	
	   	// Load background images
	   	Image backgroundImage = new Image("file:src/images/ludo2.png");
	   	Image backgroundImage2 = new Image("file:src/images/ludo instructionz2.png");
	   	
	   	BackgroundImage backgroundImg = new BackgroundImage(backgroundImage,
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
	   	
	   	BackgroundImage backgroundImg2 = new BackgroundImage(backgroundImage2,
    	BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    	new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

	   	Background background = new Background(backgroundImg);
	   	Background background2 = new Background(backgroundImg2);
	   	
	   	// Set the background to the menuRoot
	   	menuRoot.setBackground(background);
	   	
	   	// Button creation
	   	Button playButton = new Button("Play Ludo!"); // Play Button
	   	addToParent(playButton, menuRoot);
	   	playButton.setTranslateX(0);
	   	playButton.setTranslateY(-100);
	   	playButton.setPrefSize(100, 50);

	   	Button helpButton = new Button("Help"); // Help Button
	   	addToParent(helpButton, menuRoot);
	   	helpButton.setTranslateX(0);
	   	helpButton.setTranslateY(-25);
	   	helpButton.setPrefSize(100, 50);
	   	
	   	Button exitButton = new Button("Exit"); // Exit Button
	   	addToParent(exitButton, menuRoot);
	   	exitButton.setTranslateX(0);
	   	exitButton.setTranslateY(50);
	   	exitButton.setPrefSize(100, 50);
	   	
	   	// Bind exit button
   		exitButton.setOnAction(event -> Platform.exit());

        // Create play button for music
        ImageView playIcon = new ImageView(new Image("file:src/images/play2.png"));
        ImageView pauseIcon = new ImageView(new Image("file:src/images/pause2.png"));
        ImageView icon = playIcon;
        ImageView playPauseButton = new ImageView(icon.getImage());
        addToParent(playPauseButton, menuRoot);
        playPauseButton.setTranslateX(-270); // Adjust position as needed
        playPauseButton.setTranslateY(135); // Adjust position as needed
        playPauseButton.setFitWidth(100); // Adjust size as needed
        playPauseButton.setFitHeight(70);
        
	   	// Add music file
	   	String musicFile = "allthat.mp3";
	   	Media sound = new Media(new File(musicFile).toURI().toString());
	   	MediaPlayer mediaPlayer = new MediaPlayer(sound);
	   	mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        
        // Add event handler to play/pause the music when the music button is clicked
        playPauseButton.setOnMouseClicked(event -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playPauseButton.setImage(playIcon.getImage());
                
            } else {
                mediaPlayer.play();
                playPauseButton.setImage(pauseIcon.getImage());
            }
        });
   	
        // When play game button is pressed...
        playButton.setOnAction(e -> {
        	menu.close(); // Close the main menu
        	
        	// Open player selection screen
        	Stage playerSelectionStage = new Stage();
        	playerSelectionStage.setTitle("Player Selection");
        	VBox playerSelectionLayout = new VBox(20);
        	playerSelectionLayout.setAlignment(Pos.CENTER);
           
        	// Create buttons for player selection
        	Button twoPlayersButton = new Button("2 Players");
        	twoPlayersButton.setTranslateX(-100);
       		twoPlayersButton.setTranslateY(85);
       		twoPlayersButton.setPrefSize(120, 80);
           
       		Button threePlayersButton = new Button("3 Players");
       		threePlayersButton.setTranslateX(100);
       		threePlayersButton.setTranslateY(-10);
       		threePlayersButton.setPrefSize(120, 80);
           
       		Button fourPlayersButton = new Button("4 Players");
       		fourPlayersButton.setTranslateX(-100);
       		fourPlayersButton.setTranslateY(57);
       		fourPlayersButton.setPrefSize(120, 80);
           
       		Button backButton = new Button("Back");
       		backButton.setTranslateX(100);
       		backButton.setTranslateY(-35);
       		backButton.setPrefSize(120, 80);
           
           
       		// Add buttons to the layout
       		playerSelectionLayout.getChildren().addAll(twoPlayersButton,threePlayersButton, fourPlayersButton, backButton);
       		playerSelectionLayout.setBackground(background);
   			
       		// Bind events to buttons
       		twoPlayersButton.setOnAction(event -> {
           		menu.close();
           		players = 2;
           		playerSelectionStage.close();
           		Main.gameplay(primaryStage, sc, root, players);
       		});
          
       		threePlayersButton.setOnAction(event -> {
       			menu.close();
       			players = 3;
       			playerSelectionStage.close();
       			Main.gameplay(primaryStage, sc, root, players);
       		});
          
       		fourPlayersButton.setOnAction(event -> {
           		menu.close();
           		players = 4;
           		playerSelectionStage.close();
           		Main.gameplay(primaryStage, sc, root, players);
       		});
          
       		backButton.setOnAction(event -> {
           		playerSelectionStage.close();
           		menu.show();
       		});
           
       		// Create scene for player selection window and show
       		Scene playerSelectionScene = new Scene(playerSelectionLayout, 600, 350);
       		playerSelectionStage.setScene(playerSelectionScene);
       		playerSelectionStage.show();
   		});
        
        // When help button is pressed...
   		helpButton.setOnAction(e -> {
   			// Open help window
   			menu.close();
   			Stage helpStage = new Stage();
   			helpStage.setTitle("Help");
   			VBox helpLayout = new VBox(10);
   			helpLayout.setAlignment(Pos.CENTER);
   			helpLayout.setBackground(background2);
   			
   			// Make a scroll pane
   			ScrollPane scrollPane = new ScrollPane();
   			scrollPane.setContent(helpLayout);
   			scrollPane.setFitToWidth(true);
   			scrollPane.setFitToHeight(true);
   			Scene helpScene = new Scene(scrollPane, 600, 350);
   			helpStage.setScene(helpScene);
   			helpStage.show();
   			
   			// Add text
   			String[] instructionTexts = {
   					("1. Place your 4 pieces in the corner of the same color"),
   					("2. Blue always goes first (Blue, Orange, Green, Yellow)"),
   					("3. Roll a 6 to move a piece out of your base onto the main track"),
   					("(End your turn after moving a piece if you didn’t roll a 6)"),
   					("4. Roll the die to move a piece during each of your turns"),
   					("5. Land in a space with an opponent's piece to send it back to their base"),
   					("6. Move onto the same space as one of your pieces to make a block"),
   					("7. Try to land on marked safe spaces"),
   					("8. Move your pieces into your home column once you go around the board"),
   					("9. Roll the exact number you need to get pieces into the finishing square"),
   					("10. Win the game by moving all 4 of your pieces to the finishing square")
   			};
   			
   			Label[] instructions = new Label[11];
   			
   			for (int i = 0; i < instructionTexts.length; i++) {
   				instructions[i] = new Label(instructionTexts[i]);
   				instructions[i].setTextFill(Color.WHITE);
   				helpLayout.getChildren().add(instructions[i]);
   			}
   			
            
   		 	Button helpBackButton = new Button("Back");
   		 	helpLayout.getChildren().add(helpBackButton);
   		 	helpBackButton.setOnAction(event -> {
   		 		helpStage.close();
   		 		menu.show();
   		 	});
          

   		});
	}
	
	public int getPlayers() {
		return players;
	}

}
