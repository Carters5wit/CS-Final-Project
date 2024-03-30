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
    
    // Coordinates for each player's home stretch
    private int[][] stretchBlue = {{7,1},{7,2},{7,3},{7,4},{7,5}};
    private int[][] stretchOrange = {{1,7},{2,7},{3,7},{4,7},{5,7}};
    private int[][] stretchYellow = {{13,7},{12,7},{11,7},{10,7},{9,7}};
    private int[][] stretchGreen = {{7,13},{7,12},{7,11},{7,10},{7,9}};
    
    // Coordinates for each player's start
    private int[] startBlue = {6,1};
    private int[] startOrange = {1,8};
    private int[] startGreen = {8,13};
    private int[] startYellow = {13,6};
    
    // Defined segments of the board
    
    // Runs from the front of blue start, to behind orange start
    private int[][] segment1 = {
    		{6,2}, {6,3}, {6,4}, {6,5},	
    		{5,6}, {4,6}, {3,6}, {2,6},	{1,6},
    		{0,6}, {0,7}, {0,8},
    };
    
    // Runs from the front of orange start, to behind green start
    private int[][] segment2 = {
    		{2,8}, {3,8}, {4,8}, {5,8},
    		{6,9}, {6,10}, {6,11}, {6,12}, {6,13},
    		{6,14}, {7,14}, {8,14},
    };
    
    // Runs from the front of green start, to behind yellow start
    private int[][] segment3 = {
    		{8,12}, {8,11}, {8,10},	{8,9},
    		{9,8}, {10,8}, {11,8}, {12,8}, {13,8},
    		{14,8}, {14,7}, {14,6},
    };
    
    // Runs from the front of yellow start, to behind blue start
    private int[][] segment4 = {
    		{12,6}, {11,6}, {10,6},	{9,6},
    		{8,5}, {8,4}, {8,3}, {8,2},	{8,1},
    		{8,0}, {7,0}, {6,0},
    };
    
    

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
        gc.setFill(Color.rgb(239, 193, 135));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Drawing a border
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Calculate the gap between each spot
        double gapX = canvas.getWidth() / columns;
        double gapY = canvas.getHeight() / rows;

        // Create spots and store them in the array
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
