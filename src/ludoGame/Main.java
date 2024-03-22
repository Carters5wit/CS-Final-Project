package ludoGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ludo Game");
        final StackPane root = new StackPane();
        
        // Create a Board object
        Board board = new Board(900, 900);
        
        // Add the canvas from Board to the root pane
        root.getChildren().add(board.getCanvas());

        primaryStage.setScene(new Scene(root, 900, 900));
        primaryStage.show();
    }
}
