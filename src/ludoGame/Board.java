package ludoGame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Responsible for drawing the primary Ludo board to the window and handling board-related
 * logic.
 * 
 * @author Reggie Andrade
 */
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
    private int[][][] stretches = {stretchBlue, stretchOrange, stretchYellow, stretchGreen};
    
    // Coordinates for each player's home base
    private int[][] homesBlue = {{2,2}, {2,4}, {4,2}, {4,4}};
    private int[][] homesOrange = {{2,10}, {2,12}, {4,10}, {4,12}};
    private int[][] homesYellow = {{10,2}, {10,4}, {12,2}, {12,4}};
    private int[][] homesGreen = {{10,10}, {10,12}, {12,10}, {12,12}};
    private int[][][] homes = {homesBlue, homesOrange, homesYellow, homesGreen};
    
    // Coordinates for each player's start
    private int[] startBlue = {6,1};
    private int[] startOrange = {1,8};
    private int[] startGreen = {8,13};
    private int[] startYellow = {13,6};
    
    // Color for each player
    private Color blue = Color.rgb(54, 103, 181);
    private Color orange = Color.rgb(237, 117, 19);
    private Color yellow = Color.rgb(245, 223, 27);
    private Color green = Color.rgb(55, 138, 45);
    
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
    
    
    
    /**
     * Creates and draws a new board object, 
     * which can be added to a window root.
     * 
     * @param width the width of the board
     * @param height the height of the board
     */
    public Board(double width, double height) {
        canvas = new Canvas(width, height);
        drawBoard();
    }
    
    /**
     * Returns the canvas object of the board.
     * 
     * @return canvas object
     */
    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     * Returns the GraphicsContext object of the board.
     * 
     * @return graphicscontext object
     */
    public GraphicsContext getGraphicsContext() {
    	return gc;
    }
    
    /**
     * Draws all elements of the Ludo board onto the canvas. Internal use only.
     */
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
        
        // Change color of home stretch spots
        for (int z = 0; z < stretches.length; z++) {
        	int[][] stretch = stretches[z];
        	for (int[] coord : stretch) {
            	int i = coord[0];
            	int j = coord[1];
            	
            	if (z == 0) {
            		spots[i][j].setHomeStrechColor(blue);
            	} else if (z == 1) {
            		spots[i][j].setHomeStrechColor(orange);
            	} else if (z == 2) {
            		spots[i][j].setHomeStrechColor(yellow);
            	} else if (z == 3) {
            		spots[i][j].setHomeStrechColor(green);
            	}
            	
            }
        }
        
        // Create circles on homes
        for (int i = 0; i < 4; i++) {
        	Color c = Color.WHITE;
        	double centerX = 0;
        	double centerY = 0;
        	
        	if (i == 0) {
        		c = blue;
        		centerX = 3 * gapX;
        		centerY = 3 * gapY;
        	} else if (i == 1) {
        		c = orange;
        		centerX = 11 * gapX;
        		centerY = 3 * gapY;
        	} else if (i == 2) {
        		c = yellow;
        		centerX = 3 * gapX;
        		centerY = 11 * gapY;
        	} else if (i == 3) {
        		c = green;
        		centerX = 11 * gapX;
        		centerY = 11 * gapY;
        	}
        	
            gc.setFill(c);
            gc.setStroke(Color.BLACK);
            double radius = 75;
            double size = 200;
            gc.fillOval(centerX - radius, centerY - radius, size, size);
            gc.strokeOval(centerX - radius, centerY - radius, size, size);
        }
        
        // Create home spots
        for (int z = 0; z < homes.length; z++) {
        	int[][] homeSpots = homes[z];
        	for (int[] coords : homeSpots) {
        		double x = coords[0] * gapX;
                double y = coords[1] * gapY;
        		spots[coords[0]][coords[1]] = new Spot(x, y, "home", gc);
        	}
        }
        

    }
}
