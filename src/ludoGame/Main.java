package ludoGame;

import java.util.ArrayList;

import javax.swing.JFrame;

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
     * @param homes 2D array of coordinates representing each team's homes
     * @param pawns 2D array of pawns for each team
     * @param p Pane object to place pawns on
     */
    public static void populate(int players, Spot[][] spots, XY[][] homes, Pawn[][] pawns, Pane p) {
    	switch (players) {
   	 		case 2:
   	 			homes[2] = null;
   	 			homes[3] = null;
   	 		case 3:
   	 			homes[3] = null;
   	 	}
    	
    	for (int z = 0; z < homes.length; z++) {
    		if (homes[z] != null) {
    			XY[] teamHomes = homes[z];
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
                	XY coords = teamHomes[i];
                	currentPawns[i] = new Pawn(spots[(int)coords.y][(int)coords.x], z + 1, p);
            	}
    		}
    	}
    }
    
    /**
     * Method to connect pawn click events. Should be initialized at the start of the code.
     * 
     * @param pawns 2D array of pawns with each team's pawns
     */
    public static void clickConnectPawns(Pawn[][] pawns, ArrayList<Player> plrs) {
   	 	for (Pawn[] teamPawns : pawns) {
   	 		for (int i = 0; i < teamPawns.length; i++) {
   	 			if (teamPawns[i] != null) {
   	 				
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
                				
//                				Player currentPlayer = plrs.get(index + 1);
//                    			
//                    			// After moving the pawn, switch to the next player's turn
//                                currentPlayer = (currentPlayer + 1) % 4;
//                                System.out.println("Player " + (currentPlayer + 1) + "'s turn");
//                                // Allow the next player to roll the dice
//                                clickablePane.setDisable(false);
                			} else {
                				// Logic to perform if pawn cannot be clicked
                				lastPawn = null;
                			}
                		
                			
 						}
 				});
   	 			}
 				
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
		final StackPane root = new StackPane();
		final Scene sc = new Scene(root, 750, 750);
    	
    	// Create the main menu
    	Menu m = new Menu(primaryStage, sc, root);
	}
	
	public static void gameplay(Stage primaryStage, Scene sc, StackPane root, int players) {
		primaryStage.setTitle("Ludo"); // Set title if main game
	   	 
   	 	// Game Window variables
    	final Pane clickablePane = new Pane();
    	final Board board = new Board(750, 750, clickablePane);
//    	primaryStage.setResizable(false);
   	 
    	// Board variables
    	Spot[][] spots = board.getSpots();
    	XY[][] homes = {board.getHomes(1), board.getHomes(2), board.getHomes(3), board.getHomes(4)};
    	XY[] bluePath = board.getPath(1);
    	XY[] orangePath = board.getPath(2);
    	XY[] greenPath = board.getPath(3);
    	XY[] yellowPath = board.getPath(4);
   	 
    	// Pawn variables
    	Pawn[] bluePawns = new Pawn[4];
    	Pawn[] orangePawns = new Pawn[4];
    	Pawn[] yellowPawns = new Pawn[4];
    	Pawn[] greenPawns = new Pawn[4];
    	Pawn[][] pawns = {bluePawns, orangePawns, yellowPawns, greenPawns};
    	
    	// Game variables
    	boolean won = false;
    	int playerIndex = 0;
    	
    	// Add board to StackPane
    	addToParent(board.getCanvas(), root);
   	 
    	// Add clickable pane to StackPane
    	addToParent(clickablePane, root);
   	 
    	// Populate pawn array with 4 players (create and place pawns on home spots)
    	populate(players, spots, homes, pawns, clickablePane);
   	 
    	// Set up event handler to detect clicks on pawns and spots
    	clickConnectPawns(pawns, Player.getPlayers());
    	clickConnectSpots(spots, sc);
    	
    	// Show boards
    	primaryStage.setScene(sc);
		primaryStage.show();
		
		// Allow the first player to roll the dice
        clickablePane.setDisable(false);
		
		// Create and show dice
		JFrame frame = new JFrame("Dice");
        Dice dice = new Dice();
        frame.add(dice);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(400,300);
        frame.setVisible(true);
        
        // Create players
        Player blue = new Player(1, bluePawns);
        Player orange = new Player(2, orangePawns);
        Player yellow = new Player(3, yellowPawns);
        Player green = new Player(4, greenPawns);
        ArrayList<Player> plrs = Player.getPlayers();
        
        // Primary game loop
        while (!won) {
        	Player currentPlr = plrs.get(playerIndex);
        	int roll = dice.getRoll();
        	
        	if (roll == 6) {
        		continue;
        	} else {
        		if (playerIndex == 3) {
        			playerIndex = 0;
        		} else {
        			playerIndex++; // Moves to the next player's turn
        		}
        	}
        	
        	// Allow player to click their pawns
        	for (Pawn pawn : currentPlr.getPawns()) {
    			if (pawn != null) {
    				pawn.toggleClick(sc);
    			}
    		}
        }
        
    	
        /**
   	 	 * TODOS:
   	 	 * 
   	 	 * 1. : Open Dice class window when game is opened, and make this function have the ability to store lastRoll in a variable
   	 	 * 2. TODO: Let each player take a turn rolling the dice (MUST USE A QUEUE TO MEET PROJECT REQUIREMENTS!!)
   	 	 * 3. TODO: Give a player an extra turn if they roll a 6
   	 	 * 4. TODO: Allow the player to move a pawn out of their home by clicking it if they roll a 6 (pawn should go to player start point)
   	 	 * 5. TODO: Make the pawn move a certain amount of spaces based on roll, and let the player click which pawn they want to move
   	 	 * 	Tip: Use board.getPath(teamID) to get the path a pawn should move based on the player color
   	 	 * 6. TODO: Make the pawn move onto the home stretch and final spot after completing their path. If the player rolls higher than the moves needed to enter the final spot, their turn is skipped if they cannot move any other pawns.
   	 	 * 7. TODO: Implement pawn capturing, so if a pawn lands directly on another pawn, the pawn must return to their home
   	 	 * 8. TODO: Disable pawn capturing on safe spots
   	 	 * 9. TODO: Add win check logic (if player gets all 4 pawns to final spot, let that player win and end the game)
   	 	 */
	}
    
	public static void main(String[] args) {
    	launch(args);
	}
}


