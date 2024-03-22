package ludoGame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {
    private Canvas canvas;

    public Board(double width, double height) {
        canvas = new Canvas(width, height);
        drawBoard();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void drawBoard() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Draw the board here
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Example: Drawing a border
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
