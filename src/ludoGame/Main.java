package ludoGame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    
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
                        System.out.println("Pawn clicked! Player: " + teamPawns[index].pawnColor() + " | ID: " + (index+1));
                        // TODO: Perform pawn click logic, remove debug print
                    }
                });
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
    	
    	// Button creation
    	Button playButton = new Button("Play Ludo!");
    	addToParent(playButton, menuRoot); // Add button to menuRoot
    	playButton.setTranslateX(-200); // Change X
    	playButton.setTranslateY(-100); // Change Y
    	playButton.setPrefSize(100, 50); // Change size (all buttons should be this size)
    	
    	// When play button is pressed...
    	playButton.setOnAction(e -> {
    		menu.close(); // Close the main menu
    		primaryStage.setScene(sc); // Set the Ludo game scene
    		primaryStage.show(); // Show the Ludo game stage
    	});
    	
    	// TODO: Create exit button to close the main menu and exit the program
    	// TODO: Add the ludo.png image to the right of the main menu
    	// TODO: Create help button to show game instructions (depends if we have time)
	}
	
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Ludo"); // Set title if main game
    	
    	// Game Window variables
        final StackPane root = new StackPane();
        final Board board = new Board(750, 750);
        final GraphicsContext gc = board.getGraphicsContext();
        final Pane pawnPane = new Pane();
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
        
        // Add pawn pane to StackPane
        addToParent(pawnPane, root);
        
        // Populate pawn arrays (create and place pawns on home spots)
        populate(spots, homes, pawns, pawnPane);
        
        // Set up event handler to detect clicks on pawns
        clickConnectPawns(pawns);
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
