package ludoGame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {
    private Canvas canvas;
    private GraphicsContext gc;
    private final int rows = 15; // Number of rows
    private final int columns = 15; // Number of columns
    private Spot[][] spots = new Spot[rows][columns]; // Array to store spots

    public Board(double width, double height) {
        canvas = new Canvas(width, height);
        drawBoard();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void drawBoard() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Draw the board here
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Example: Drawing a border
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Calculate the gap between each spot
        double gapX = canvas.getWidth() / columns;
        double gapY = canvas.getHeight() / rows;

        // Create spots and store the6m in the array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double x = j * gapX;
                double y = i * gapY;
                
                // Skip center spots
                if (i == 6 || i == 7 || i == 8) {
                	if (j == 6 || j == 7 || j == 8) {
                		continue;
                	}
                }
                
                // Skip corner spots
                if ((i != 6 && i != 7 && i != 8) && (j != 6 && j != 7 && j != 8)) {
                	continue;
                }
                
                spots[i][j] = new Spot(x, y, gc);
            }
        }
    }
    
    public GraphicsContext getGraphicsContext() {
    	return gc;
    }
}
