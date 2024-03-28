package ludoGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
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
        root.getChildren().add(board.getCanvas());
        
        // Set scene size and show to screen
        primaryStage.setScene(new Scene(root, 750, 750));
        primaryStage.show();
    }
}
