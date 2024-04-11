package ludoGame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
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
    private int[][] starts = {startBlue, startOrange, startGreen, startYellow};
    
    private int[] finalBlue = {7,6};
    private int[] finalOrange = {6,7};
    private int[] finalGreen = {7,8};
    private int[] finalYellow = {8,7};
    private int[][] finals = {finalBlue, finalOrange, finalGreen, finalYellow};
    
    private int[][] safes = {{8,2},{2,6},{6,12},{12,8}};
    
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
     * Helper method to combine several board segment arrays into one array, internal use only.
     * 
     * @param segments
     * @return combined 2D array
     */
    private int[][] combineSegments(int[][]... segments) {
        // Calculate total length
        int totalLength = 0;
        for (int[][] segment : segments) {
            totalLength += segment.length;
        }
        // Exclude the last element of the last segment
        totalLength--;

        int[][] combinedSegments = new int[totalLength][2];
        int index = 0;

        // Combine segments
        for (int[][] segment : segments) {
            for (int i = 0; i < segment.length - 1; i++) {
                combinedSegments[index++] = segment[i];
            }
        }

        return combinedSegments;
    }
    
    private int[][] bluePath = combineSegments(segment1, segment2, segment3, segment4, stretchBlue);
    private int[][] orangePath = combineSegments(segment2, segment3, segment4, segment1, stretchOrange);
    private int[][] greenPath = combineSegments(segment3, segment4, segment1, segment2, stretchGreen);
    private int[][] yellowPath = combineSegments(segment4, segment1, segment2, segment3, stretchYellow);
    
    /**
     * Creates and draws a new board object, 
     * which can be added to a window root.
     * 
     * @param width The width of the board
     * @param height The height of the board
     * @param p Pane object, used for drawBoard()
     */
    public Board(double width, double height, Pane p) {
        canvas = new Canvas(width, height);
        drawBoard(p);
    }
    
    /**
     * Returns the canvas object of the board.
     * 
     * @return Canvas object
     */
    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     * Returns the GraphicsContext object of the board. May be depricated.
     * 
     * @return GraphicsContext object
     */
    public GraphicsContext getGraphicsContext() {
    	return gc;
    }
    
    /**
     * Returns a two-dimensional array of all spots on the board.
     * 
     * @return Spot 2D array
     */
    public Spot[][] getSpots() {
    	return spots;
    }
    
    /**
     * Returns an array of coordinates which define the order which
     * each team's pawn should move.
     * 
     * @param team The team (player) path to get (1 = blue, 2 = orange, 3 = green, 4 = yellow)
     * @return team path
     */
    public int[][] getPath(int team) {
    	switch (team) {
    		case 1:
    			return bluePath;
    		case 2:
    			return orangePath;
    		case 3:
    			return greenPath;
    		case 4:
    			return yellowPath;
    		default:
    			return null;
    	}
    }
    
    /**
     * Get the home stretch for each team (player)
     * 
     * @return The home stretches for each team (player), in order within a 3D int array
     */
    public int[][][] getStretches() {
    	return stretches;
    }
    
    /**
     * Get the homes for each team (player)
     * 
     * @return Home positions for each player, in order within a 3D int array
     */
    public int[][][] getHomes() {
    	return homes;
    }
    
    /**
     * Get the starts for each team (player)
     * 
     * @return Start positions for each player, in order within a 2D array
     */
    public int[][] getStarts() {
    	return starts;
    }
    
    /**
     * Draws all elements of the Ludo board onto the canvas. Internal use only.
     * 
     * @param p Pane, used for 
     */
    public void drawBoard(Pane p) {
        gc = canvas.getGraphicsContext2D();
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
                
                spots[i][j] = new Spot(x, y, p);
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
        
        // Change start spots
        for (int i = 0; i < starts.length; i++) {
        	int[] start = starts[i];
        	Color clr = null;
        	int ang = 0;
        	
        	switch (i) {
        		case 0:
        			clr = blue;
        			ang = 270;
        			break;
        		case 1:
        			clr = orange;
        			break;
        		case 2:
        			clr = green;
        			ang = 90;
        			break;
        		case 3:
        			clr = yellow;
        			ang = 180;
        			break;
        	}
        	
        	spots[start[0]][start[1]].setHomeStrechColor(clr);
        	spots[start[0]][start[1]].addArrow(ang);
        	spots[start[0]][start[1]].setType("start");
        }
        
        // Change safe spots
        for (int i = 0; i < safes.length; i++) {
        	int[] safe = safes[i];
 
        	spots[safe[0]][safe[1]].addStar();
        	spots[safe[0]][safe[1]].setType("safe");
        }
        
        // Draw the center image
        Image center = new Image("file:src/images/midboard.png");
	    gc.save(); // Save the current state of the graphics context
	    gc.translate(375, 375);
	    double scale = 0.3;
	    double scaledWidth = center.getWidth() * scale;
	    double scaledHeight = center.getHeight() * scale;
	    gc.drawImage(center, -scaledWidth / 2, -scaledHeight / 2, scaledWidth, scaledHeight); // Draw the scaled image
	    gc.restore(); // Restore the saved state (removes the translation and rotation)

	    // Making invisible final spots
        for (int i = 0; i < finals.length; i++) {
        	int[] coords = finals[i];
        	
        	double x = coords[0] * gapX;
            double y = coords[1] * gapY;
        	
        	spots[coords[0]][coords[1]] = new Spot(x, y, "final", gc);
        }
    }
}
