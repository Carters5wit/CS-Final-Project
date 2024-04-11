package ludoGame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Pawn lastPawn;
    private static Spot lastSpot;
    
    /**
     * Helper method to add a JavaFX node to any StackPane
     * 
     * @param <T> Generic variable for any class which extends Node
     * @param node Any class which extends node, to be added to a StackPane (parent)
     * @param r StackPane to add element to
     */
    public static <T extends Node> void addToParent(T node, StackPane r) {
    	r.getChildren().add(node);
	}
    
    /**
     * Method to populate the board with player pawns.
     * 
     * @param players Number of players (1, 2, 3, or 4)
     * @param spots 2D array of spots to place pawns on
     * @param homes Coordinate 3D array of home spots
     * @param pawns 2D array of pawns for each team
     * @param p Pane object to place pawns on
     */
    public static void populate(int players, Spot[][] spots, int[][][] homes, Pawn[][] pawns, Pane p) {
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
    
    /**
     * Method to connect pawn click events. Should be initialized at the start of the code.
     * 
     * @param pawns 2D array of pawns with each team's pawns
     */
    public static void clickConnectPawns(Pawn[][] pawns) {
   	 	for (Pawn[] teamPawns : pawns) {
   	 		for (int i = 0; i < teamPawns.length; i++) {
 				final int index = i;
 				teamPawns[i].getImageView().setOnMouseClicked(new EventHandler<MouseEvent>() {
 						@Override
 						public void handle(MouseEvent event) {
 							/*
 							 * This function is where code to tell the game what should happen
 							 * after a pawn is clicked should be put.
 							 * 
 							 * The variable 'lastPawn' can be used to get the last pawn that was clicked.
 							 */
 							
   	 						Pawn pawn = teamPawns[index];
                			if (pawn.isClickable()) {
                				// Logic to perform if pawn is clickable
                				lastPawn = pawn;
                			} else {
                				// Logic to perform if pawn cannot be clicked
                				lastPawn = null;
                			}
                			
 						}
 				});
   	 		}
   	 	}
    }
    
    /**
     * Method to connect spot click events. May be depricated.
     * 
     * @param spots 2D array of spots on board
     * @param sc Scene to toggle pawn clicks on, may be depricated
     */
    public static void clickConnectSpots(Spot[][] spots, Scene sc) {
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
                       		 lastPawn.toggleClick(sc);
                       		 lastPawn = null;
                       		 System.out.println("Moved pawn to " + lastSpot);
                        	}
                    	}
                	});
   			 }
        	}
    	}
    }
    
	@Override
	public void start(Stage primaryStage) {
   	 	primaryStage.setTitle("Ludo"); // Set title if main game
   	 
   	 	// Game Window variables
    	final StackPane root = new StackPane();
    	final Pane clickablePane = new Pane();
    	final Board board = new Board(750, 750, clickablePane);
    	final Scene sc = new Scene(root, 750, 750);
    	primaryStage.setResizable(false);
   	 
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
   	 
    	// Create the main menu
    	new Menu(primaryStage, sc);
    	
    	// Add board to StackPane
    	addToParent(board.getCanvas(), root);
   	 
    	// Add clickable pane to StackPane
    	addToParent(clickablePane, root);
   	 
    	// Populate pawn array with 4 players (create and place pawns on home spots)
    	populate(4, spots, homes, pawns, clickablePane);
    	
    	// Temporary debug code to make every pawn clickable
    	for (Pawn[] teamPawns : pawns) {
    		for (Pawn pawn : teamPawns) {
    			pawn.toggleClick(sc);
    		}
    	}
   	 
    	// Set up event handler to detect clicks on pawns and spots
    	clickConnectPawns(pawns);
    	clickConnectSpots(spots, sc);
    	
   	 	/**
   	 	 * TODOS:
   	 	 * 
   	 	 * 1. TODO: Open Dice class window when game is opened, and make this function have the ability to store lastRoll in a variable
   	 	 * 2. TODO: Let each player take a turn rolling the dice (MUST USE A QUEUE TO MEET PROJECT REQUIREMENTS!!)
   	 	 * 3. TODO: Give a player an extra turn if they roll a 6
   	 	 * 4. TODO: Allow the player to move a pawn out of their home by clicking it if they roll a 6 (pawn should go to player start point)
   	 	 * 5. TODO: Make the pawn move a certain amount of spaces based on roll, and let the player click which pawn they want to move
   	 	 * 	Tip: Use board.getPath(teamID) to get the path a pawn should move based on the player color
   	 	 * 6. TODO: Make the pawn move onto the home stretch and final spot after completing their path
   	 	 * 7. TODO: Implement pawn capturing, so if a pawn lands directly on another pawn, the pawn must return to their home
   	 	 * 8. TODO: Disable pawn capturing on safe spots
   	 	 * 9. TODO: Add win check logic (if player gets all 4 pawns to final spot, let that player win and end the game)
   	 	 */
	}
    
	public static void main(String[] args) {
    	launch(args);
	}
}


