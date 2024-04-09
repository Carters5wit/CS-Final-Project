package ludoGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
                       		 lastPawn.moveTo(row[index]);
                       		 System.out.println("Moved pawn!");
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
       // Create a background image
       BackgroundImage backgroundImg = new BackgroundImage(backgroundImage,
               BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
               new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
       // Create a background with the image
       Background background = new Background(backgroundImg);
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
       	twoPlayersButton.setTranslateY(35);
           twoPlayersButton.setPrefSize(80, 5);
           Button threePlayersButton = new Button("3 Players");
           threePlayersButton.setTranslateX(100);
       	threePlayersButton.setTranslateY(-10);
           threePlayersButton.setPrefSize(80, 5);
           Button fourPlayersButton = new Button("4 Players");
           fourPlayersButton.setTranslateX(-100);
       	fourPlayersButton.setTranslateY(10);
           fourPlayersButton.setPrefSize(80, 5);
           Button backButton = new Button("Back");
           backButton.setTranslateX(100);
       	backButton.setTranslateY(-35);
           backButton.setPrefSize(80, 5);
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
           
   		//exit button is pressed...
   		Scene playerSelectionScene = new Scene(playerSelectionLayout, 400, 200);
           playerSelectionStage.setScene(playerSelectionScene);
           playerSelectionStage.show();
   		});
   	
   		helpButton.setOnAction(e -> {
   			menu.close();
   			Stage helpStage = new Stage();
   			helpStage.setTitle("Help");
   			VBox helpLayout = new VBox(10);
   			helpLayout.setAlignment(Pos.CENTER);
   			ScrollPane scrollPane = new ScrollPane();
   			scrollPane.setContent(helpLayout);
   			scrollPane.setFitToWidth(true);
   			scrollPane.setFitToHeight(true);
   			Scene helpScene = new Scene(scrollPane, 400, 300);
   			helpStage.setScene(helpScene);
   			helpStage.show();
   			Label instruction1 = new Label("1. Place your 4 pieces in the corner of the same color");
   			Label instruction2 = new Label("2. Blue always goes first (Blue, Orange, Green, Yellow)");
   			Label instruction3 = new Label("3. Roll a 6 to move a piece out of your base onto the main track(End your turn after moving a piece if you didn’t roll a 6)");
   			Label instruction4 = new Label("4. Roll the die to move a piece during each of your turns");
   			Label instruction5 = new Label("5. Land in a space with an opponent's piece to send it back to their base");
   			Label instruction6 = new Label("6. Move onto the same space as one of your pieces to make a block");
   			Label instruction7 = new Label("7. Try to land on marked safe spaces");
   			Label instruction8 = new Label("8. Move your pieces into your home column once you go around the board");
   			Label instruction9 = new Label("9. Roll the exact number you need to get pieces into the finishing square");
   			Label instruction10 = new Label("10. Win the game by moving all 4 of your pieces to the finishing square");
          
           // Add instructions to layout
           helpLayout.getChildren().addAll(instruction1, instruction2, instruction3, instruction4, instruction5, instruction6, instruction7, instruction8, instruction9, instruction10);
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
   	 
    	// Set up event handler to detect clicks on pawns and spots
    	clickConnectPawns(pawns);
    	clickConnectSpots(spots);
   	 
	}
    
	public static void main(String[] args) {
    	launch(args);
	}
}


