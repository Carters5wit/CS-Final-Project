package ludoGame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	// Set title and create window frame root
    	primaryStage.setTitle("Ludo Game");
        final StackPane root = new StackPane();
        
        // Create a Board object and add it to the root
        Board board = new Board(750, 750);
        GraphicsContext gc = board.getGraphicsContext();
        root.getChildren().add(board.getCanvas());
        
        // Create a Pane for pawns and overlay it on top of the board
        Pane pawnPane = new Pane();
        root.getChildren().add(pawnPane);
        
        // Set scene size and show to screen
        Scene sc = new Scene(root, 750, 750);
        primaryStage.setScene(sc);
        primaryStage.show();
        
        
        // Get spots on the board
        Spot[][] spots = board.getSpots();
        int[][][] homes = board.getHomes();
        int[][] bluePath = board.getPath(0);
        
        // Initialize pawn arrays
        Pawn[] bluePawns = new Pawn[4];
        Pawn[] orangePawns = new Pawn[4];
        Pawn[] yellowPawns = new Pawn[4];
        Pawn[] greenPawns = new Pawn[4];
        
        // Populate pawn arrays, and place pawns on proper homes
        for (int z = 0; z < homes.length; z++) {
            int[][] teamHomes = homes[z];
            Pawn[] currentPawns = null;

            switch (z) {
                case 0:
                    currentPawns = bluePawns;
                    break;
                case 1:
                    currentPawns = orangePawns;
                    break;
                case 2:
                    currentPawns = yellowPawns;
                    break;
                case 3:
                    currentPawns = greenPawns;
                    break;
            }

            for (int i = 0; i < teamHomes.length; i++) {
                int[] coords = teamHomes[i];
                currentPawns[i] = new Pawn(spots[coords[1]][coords[0]], z + 1, pawnPane);
            }
        }
        
        Pawn[][] pawns = {bluePawns, orangePawns, yellowPawns, greenPawns};
        
        // Set up event handler to detect clicks on pawns
        for (Pawn[] teamPawns : pawns) {
        	for (int i = 0; i < teamPawns.length; i++) {
        		final int index = i; // Final variable capturing the current value of i
                teamPawns[i].getImageView().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Pawn clicked! Player: " + teamPawns[index].pawnColor() + " | ID: " + (index+1));
                        // TODO: Perform pawn click logic
                    }
                });
            }
        }
        
    }
}
