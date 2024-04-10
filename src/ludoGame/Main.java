package ludoGame;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Pawn lastPawn;
    private static Spot lastSpot;
    
    public static <T extends Node> void addToParent(T node, StackPane r) {
    	r.getChildren().add(node);
	}
    
    public static void populate(Spot[][] spots, int[][][] homes, Pawn[][] pawns, Pane p) {
   	 for (int z = 0; z < homes.length; z++) {
        	int[][] teamHomes = homes[z];
        	Pawn[] currentPawns = null;

        	switch (z) {
            	case 0:
                	currentPawns = pawns[0];
                	break;
            	case 1:
                	currentPawns = pawns[1];
                	break;
            	case 2:
                	currentPawns = pawns[2];
                	break;
            	case 3:
                	currentPawns = pawns[3];
                	break;
        	}

        	for (int i = 0; i < teamHomes.length; i++) {
            	int[] coords = teamHomes[i];
            	currentPawns[i] = new Pawn(spots[coords[1]][coords[0]], z + 1, p);
        	}
    	}
    }
    
    public static void clickConnectPawns(Pawn[][] pawns) {
   	 for (Pawn[] teamPawns : pawns) {
   		 for (int i = 0; i < teamPawns.length; i++) {
   			 final int index = i; // Final variable capturing the current value of i
            	teamPawns[i].getImageView().setOnMouseClicked(new EventHandler<MouseEvent>() {
                	@Override
                	public void handle(MouseEvent event) {
               		 lastPawn = teamPawns[index];
                    	System.out.println("Pawn clicked! Player: " + teamPawns[index].pawnColor() + " | ID: " + (index+1));
                    	// TODO: Perform pawn click logic, remove debug print
                	}
            	});
        	}
    	}
    }
    
    public static void clickConnectSpots(Spot[][] spots) {
   	 for (Spot[] row : spots) {
   		 for (int i = 0; i < row.length; i++) {
   			 final int index = i; // Final variable capturing the current value of i
   			 if (row[i] != null && row[i].getRectangle() != null) {
   				 row[i].getRectangle().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    	@Override
                    	public void handle(MouseEvent event) {
                   		 lastSpot = row[index];
                        	System.out.println("Spot clicked! " + row[index]);
                        	if (lastPawn != null) {
                       		 lastPawn.moveTo(lastSpot);
                       		 System.out.println("Moved pawn to " + lastSpot);
                        	}
                    	}
                	});
   			 }
        	}
    	}
    }
    
   public static void createMenu(Stage primaryStage, Scene sc) {
	   	// Window setup
			Stage menu = new Stage();
	   	menu.setTitle("Ludo Game");
	   	StackPane menuRoot = new StackPane();
	   	Scene menuScene = new Scene(menuRoot, 600, 350);
	   	menu.setScene(menuScene);
	   	menu.show();
       // Load the background image
       Image backgroundImage = new Image("file:src/images/ludo2.png"); // Adjust the path as needed
       Image backgroundImage2 = new Image("file:src/images/ludo instructionz2.png");
       // Create a background image
       BackgroundImage backgroundImg = new BackgroundImage(backgroundImage,
               BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
               new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
       BackgroundImage backgroundImg2 = new BackgroundImage(backgroundImage2,
    		   BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    		   new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
       // Create a background with the image
       Background background = new Background(backgroundImg);
       Background background2 = new Background(backgroundImg2);
       // Set the background to the menuRoot
       menuRoot.setBackground(background);
	   	// Button creation
	   	Button playButton = new Button("Play Ludo!");
	   	addToParent(playButton, menuRoot); // Add button to menuRoot
	   	playButton.setTranslateX(0); // Change X
	   	playButton.setTranslateY(-100); // Change Y
	   	playButton.setPrefSize(100, 50); // Change size (all buttons should be this size)
	   	//Help Button
	   	Button helpButton = new Button("Help");
	   	addToParent(helpButton, menuRoot); // Add button to menuRoot
	   	helpButton.setTranslateX(0); // Change X
	   	helpButton.setTranslateY(-25); // Change Y
	   	helpButton.setPrefSize(100, 50); // Change size (all buttons should be this size)
	   	//Exit Button
	   	Button exitButton = new Button("Exit");
	   	addToParent(exitButton, menuRoot);
	   	exitButton.setTranslateX(0);
	   	exitButton.setTranslateY(50);
	   	exitButton.setPrefSize(100, 50);
	   	
//	   	Button playPauseButton = new Button();
//	   	Polygon playIcon = new Polygon(0.0, 0.0, 0.0, 20.0, 7.0, 20.0, 7.0, 0.0, 0.0, 0.0); // Two vertical lines as pause icon
//        playIcon.setFill(Color.BLACK);
//        playPauseButton.setGraphic(playIcon);
//        addToParent(playPauseButton, menuRoot);
//        playPauseButton.setTranslateX(-270); // Change X
//        playPauseButton.setTranslateY(95);
//        playPauseButton.setPrefSize(1, 1);
        
//        ImageView playIcon = new ImageView(new Image("file:src/images/play2.png"));
//        ImageView pauseIcon = new ImageView(new Image("file:src/images/pause2.png"));
//        ImageView icon = playIcon;
//        ImageView playPauseButton = new ImageView(icon.getImage());
//        addToParent(playPauseButton, menuRoot);
//        playPauseButton.setTranslateX(-270); // Adjust position as needed
//        playPauseButton.setTranslateY(135); // Adjust position as needed
//        playPauseButton.setFitWidth(100); // Adjust size as needed
//        playPauseButton.setFitHeight(70);
//        
//	   	
//	   	String musicFile = "allthat.mp3";     // For example
//
//	   	Media sound = new Media(new File(musicFile).toURI().toString());
//	   	MediaPlayer mediaPlayer = new MediaPlayer(sound);
//	   	mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        
//        // Add event handler to play/pause the music when the button is clicked
//        playPauseButton.setOnMouseClicked(event -> {
//            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
//                mediaPlayer.pause();
//                playPauseButton.setImage(playIcon.getImage());
//                
//            } else {
//                mediaPlayer.play();
//                playPauseButton.setImage(pauseIcon.getImage());
//            }
//        });
   	
   	// When play button is pressed...
   	playButton.setOnAction(e -> {
   		menu.close(); // Close the main menu
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
//    		primaryStage.setScene(sc); // Set the Ludo game scene
//    		primaryStage.show(); // Show the Ludo game stage
   		
           twoPlayersButton.setOnAction(event -> {
           	menu.close();
           	playerSelectionStage.close();
           	primaryStage.setScene(sc);
           	primaryStage.show();
           });
          
           threePlayersButton.setOnAction(event -> {
           	menu.close();
           	playerSelectionStage.close();
           	primaryStage.setScene(sc);
           	primaryStage.show();
           });
          
           fourPlayersButton.setOnAction(event -> {
           	menu.close();
           	playerSelectionStage.close();
           	primaryStage.setScene(sc);
           	primaryStage.show();
           });
          
           backButton.setOnAction(event -> {
           	playerSelectionStage.close();
           	menu.show();
           });
           
   		//exit button is pressed...400,200
   		Scene playerSelectionScene = new Scene(playerSelectionLayout, 600, 350);
           playerSelectionStage.setScene(playerSelectionScene);
           playerSelectionStage.show();
   		});
   	
   		helpButton.setOnAction(e -> {
   			menu.close();
   			Stage helpStage = new Stage();
   			helpStage.setTitle("Help");
   			VBox helpLayout = new VBox(10);
   			helpLayout.setAlignment(Pos.CENTER);
   			helpLayout.setBackground(background2);
   			
   			ScrollPane scrollPane = new ScrollPane();
   			scrollPane.setContent(helpLayout);
   			scrollPane.setFitToWidth(true);
   			scrollPane.setFitToHeight(true);
   			Scene helpScene = new Scene(scrollPane, 600, 350);
   			helpStage.setScene(helpScene);
   			helpStage.show();
   			
   			Label instruction1 = new Label("1. Place your 4 pieces in the corner of the same color");
   			Label instruction2 = new Label("2. Blue always goes first (Blue, Orange, Green, Yellow)");
   			Label instruction3 = new Label("3. Roll a 6 to move a piece out of your base onto the main track");
   			Label instruction = new Label("(End your turn after moving a piece if you didn’t roll a 6)");
   			Label instruction4 = new Label("4. Roll the die to move a piece during each of your turns");
   			Label instruction5 = new Label("5. Land in a space with an opponent's piece to send it back to their base");
   			Label instruction6 = new Label("6. Move onto the same space as one of your pieces to make a block");
   			Label instruction7 = new Label("7. Try to land on marked safe spaces");
   			Label instruction8 = new Label("8. Move your pieces into your home column once you go around the board");
   			Label instruction9 = new Label("9. Roll the exact number you need to get pieces into the finishing square");
   			Label instruction10 = new Label("10. Win the game by moving all 4 of your pieces to the finishing square");
   			instruction1.setTextFill(Color.WHITE);
            instruction2.setTextFill(Color.WHITE);
            instruction3.setTextFill(Color.WHITE);
            instruction.setTextFill(Color.WHITE);
            instruction4.setTextFill(Color.WHITE);
            instruction5.setTextFill(Color.WHITE);
            instruction6.setTextFill(Color.WHITE);
            instruction7.setTextFill(Color.WHITE);
            instruction8.setTextFill(Color.WHITE);
            instruction9.setTextFill(Color.WHITE);
            instruction10.setTextFill(Color.WHITE);
            
   		 Button helpBackButton = new Button("Back");
         helpLayout.getChildren().add(helpBackButton);
         helpBackButton.setOnAction(event -> {
             helpStage.close();
             menu.show();
         });
          
           // Add instructions to layout
           helpLayout.getChildren().addAll(instruction1, instruction2, instruction3, instruction, instruction4, instruction5, instruction6, instruction7, instruction8, instruction9, instruction10);
   	});
   		
   	exitButton.setOnAction(event -> Platform.exit());
   	
   	
   	// TODO: Create exit button to close the main menu and exit the program
   	// TODO: Add the ludo.png image to the right of the main menu
   	// TODO: Create help button to show game instructions (depends if we have time)
	}

    
	@Override
	public void start(Stage primaryStage) {
   	 primaryStage.setTitle("Ludo"); // Set title if main game
   	 
   	 // Game Window variables
    	final StackPane root = new StackPane();
    	final Pane clickablePane = new Pane();
    	final Board board = new Board(750, 750, clickablePane);
    	final GraphicsContext gc = board.getGraphicsContext();
    	final Scene sc = new Scene(root, 750, 750);
   	 
    	// Board variables
    	Spot[][] spots = board.getSpots();
    	int[][][] homes = board.getHomes();
    	int[][] bluePath = board.getPath(1);
    	int[][] orangePath = board.getPath(2);
    	int[][] greenPath = board.getPath(3);
    	int[][] yellowPath = board.getPath(4);
   	 
    	// Pawn variables
    	Pawn[] bluePawns = new Pawn[4];
    	Pawn[] orangePawns = new Pawn[4];
    	Pawn[] yellowPawns = new Pawn[4];
    	Pawn[] greenPawns = new Pawn[4];
    	Pawn[][] pawns = {bluePawns, orangePawns, yellowPawns, greenPawns};
   	 
    	// Show the main menu
    	createMenu(primaryStage, sc);
   	 
    	// Add board to StackPane
    	addToParent(board.getCanvas(), root);
   	 
    	// Add clickable pane to StackPane
    	addToParent(clickablePane, root);
   	 
    	// Populate pawn arrays (create and place pawns on home spots)
    	populate(spots, homes, pawns, clickablePane);
    	
    	for (Pawn[] teamPawns : pawns) {
    		for (Pawn pawn : teamPawns) {
    			// Set cursor to hand when mouse enters the ImageView
    	        pawn.getImageView().setOnMouseEntered(e -> {
    	            sc.setCursor(Cursor.HAND);
    	        });

    	        // Set cursor to default when mouse exits the ImageView
    	        pawn.getImageView().setOnMouseExited(e -> {
    	            sc.setCursor(Cursor.DEFAULT);
    	        });
    		}
    	}
   	 
    	// Set up event handler to detect clicks on pawns and spots
    	clickConnectPawns(pawns);
    	clickConnectSpots(spots);
   	 
	}
    
	public static void main(String[] args) {
    	launch(args);
	}
}


