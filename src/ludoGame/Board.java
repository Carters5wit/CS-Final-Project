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
    private XY[] stretchBlue = {XY.form(7,1), XY.form(7,2), XY.form(7,3), XY.form(7,4), XY.form(7,5)};
    private XY[] stretchOrange = {XY.form(1,7), XY.form(2,7), XY.form(3,7), XY.form(4,7), XY.form(5,7)};
    private XY[] stretchYellow = {XY.form(13,7), XY.form(12,7), XY.form(11,7), XY.form(10,7), XY.form(9,7)};
    private XY[] stretchGreen = {XY.form(7,13), XY.form(7,12), XY.form(7,11), XY.form(7,10), XY.form(7,9)};
    private XY[][] stretches = {stretchBlue, stretchOrange, stretchYellow, stretchGreen};
    
    // Coordinates for each player's home base
    private XY[] homesBlue = {XY.form(2, 2), XY.form(2, 4), XY.form(4, 2), XY.form(4, 4)};
    private XY[] homesOrange = {XY.form(2, 10), XY.form(2, 12), XY.form(4, 10), XY.form(4, 12)};
    private XY[] homesYellow = {XY.form(10, 2), XY.form(10, 4), XY.form(12, 2), XY.form(12, 4)};
    private XY[] homesGreen = {XY.form(10, 10), XY.form(10, 12), XY.form(12, 10), XY.form(12, 12)};
    private XY[][] homes = {homesBlue, homesOrange, homesYellow, homesGreen};
    
    // Coordinates for each player's start
    private XY startBlue = new XY(6,1);
    private XY startOrange = new XY(1,8);
    private XY startGreen = new XY(8,13);
    private XY startYellow = new XY(13,6);
    private XY[] starts = {startBlue, startOrange, startYellow, startGreen};
    
    private XY finalBlue = new XY(7,6);
    private XY finalOrange = new XY(6,7);
    private XY finalGreen = new XY(7,8);
    private XY finalYellow = new XY(8,7);
    private XY[] finals = {finalBlue, finalOrange, finalYellow, finalGreen};
    
    private XY[] safes = {new XY(8,2),new XY(2,6),new XY(6,12),new XY(12,8)};
    
    // Color for each player
    private Color blue = Color.rgb(54, 103, 181);
    private Color orange = Color.rgb(237, 117, 19);
    private Color yellow = Color.rgb(245, 223, 27);
    private Color green = Color.rgb(55, 138, 45);
    
    // Defined segments of the board
    
    // Segment 1: Runs from the front of blue start to behind orange start
    private XY[] segment1 = {
        XY.form(6, 2), XY.form(6, 3), XY.form(6, 4), XY.form(6, 5),
        XY.form(5, 6), XY.form(4, 6), XY.form(3, 6), XY.form(2, 6), XY.form(1, 6),
        XY.form(0, 6), XY.form(0, 7), XY.form(0, 8)
    };

    // Segment 2: Runs from the front of orange start to behind green start
    private XY[] segment2 = {
        XY.form(2, 8), XY.form(3, 8), XY.form(4, 8), XY.form(5, 8),
        XY.form(6, 9), XY.form(6, 10), XY.form(6, 11), XY.form(6, 12), XY.form(6, 13),
        XY.form(6, 14), XY.form(7, 14), XY.form(8, 14)
    };

    // Segment 3: Runs from the front of green start to behind yellow start
    private XY[] segment3 = {
        XY.form(8, 12), XY.form(8, 11), XY.form(8, 10), XY.form(8, 9),
        XY.form(9, 8), XY.form(10, 8), XY.form(11, 8), XY.form(12, 8), XY.form(13, 8),
        XY.form(14, 8), XY.form(14, 7), XY.form(14, 6)
    };

    // Segment 4: Runs from the front of yellow start to behind blue start
    private XY[] segment4 = {
        XY.form(12, 6), XY.form(11, 6), XY.form(10, 6), XY.form(9, 6),
        XY.form(8, 5), XY.form(8, 4), XY.form(8, 3), XY.form(8, 2), XY.form(8, 1),
        XY.form(8, 0), XY.form(7, 0), XY.form(6, 0)
    };

    
    /**
     * Helper method to combine several board segment arrays into one array, internal use only.
     * 
     * @param segments
     * @return combined 2D array
     */
    private XY[] combineSegments(XY[]... segments) {
        // Calculate total length
        int totalLength = 0;
        for (XY[] segment : segments) {
            totalLength += segment.length;
        }
        // Exclude the last element of the last segment
        totalLength--;

        XY[] combinedSegments = new XY[totalLength];
        int index = 0;

        // Combine segments
        for (XY[] segment : segments) {
            for (int i = 0; i < segment.length - 1; i++) {
                combinedSegments[index++] = segment[i];
            }
        }

        return combinedSegments;
    }
    
    private XY[] bluePath = combineSegments(segment1, segment2, segment3, segment4, stretchBlue);
    private XY[] orangePath = combineSegments(segment2, segment3, segment4, segment1, stretchOrange);
    private XY[] yellowPath = combineSegments(segment4, segment1, segment2, segment3, stretchYellow);
    private XY[] greenPath = combineSegments(segment3, segment4, segment1, segment2, stretchGreen);
    
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
    public XY[] getPath(int team) {
    	switch (team) {
    		case 1:
    			return bluePath;
    		case 2:
    			return orangePath;
    		case 3:
    			return yellowPath;
    		case 4:
    			return greenPath;
    		default:
    			return null;
    	}
    }
    
    /**
     * Get the home stretch for a certain team (player)
     * 
     * @return The home stretches for each team (player), in order within a 3D int array
     */
    public XY[] getStretch(int team) {
	    switch (team) {
			case 1:
				return stretchBlue;
			case 2:
				return stretchOrange;
			case 3:
				return stretchYellow;
			case 4:
				return stretchGreen;
			default:
				return null;
		}
    }
    
    /**
     * Get the homes for each team (player)
     * 
     * @return Home positions for each player, in order within a 3D int array
     */
    public XY[] getHomes(int team) {
	    switch (team) {
			case 1:
				return homesBlue;
			case 2:
				return homesOrange;
			case 3:
				return homesYellow;
			case 4:
				return homesGreen;
			default:
				return null;
		}
    }
    
    /**
     * Get the starts for each team (player)
     * 
     * @return Start positions for each player, in order within a 2D array
     */
    public XY getStart(int team) {
    	switch (team) {
			case 1:
				return startBlue;
			case 2:
				return startOrange;
			case 3:
				return startYellow;
			case 4:
				return startGreen;
			default:
				return null;
    	}
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
                
                spots[i][j] = new Spot(new XY(x,y), p);
            }
        }
        
        // Change color of home stretch spots
        for (int z = 0; z < stretches.length; z++) {
        	XY[] stretch = stretches[z];
        	for (XY coord : stretch) {
            	int i = (int) coord.x;
            	int j = (int) coord.y;
            	
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
        	XY[] homeSpots = homes[z];
        	for (XY coords : homeSpots) {
        		double x = coords.x * gapX;
                double y = coords.y * gapY;
        		spots[(int)coords.x][(int)coords.y] = new Spot(new XY(x,y), "home", gc);
        	}
        }
        
        // Change start spots
        for (int i = 0; i < starts.length; i++) {
        	XY start = starts[i];
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
        			clr = yellow;
        			ang = 180;
        			break;
        		case 3:
        			clr = green;
        			ang = 90;
        			break;
        	}
        	
        	spots[(int)start.x][(int)start.y].setHomeStrechColor(clr);
        	spots[(int)start.x][(int)start.y].addArrow(ang);
        	spots[(int)start.x][(int)start.y].setType("start");
        }
        
        // Change safe spots
        for (int i = 0; i < safes.length; i++) {
        	XY safe = safes[i];
 
        	spots[(int)safe.x][(int)safe.y].addStar();
        	spots[(int)safe.x][(int)safe.y].setType("safe");
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
        	XY coords = finals[i];
        	
        	double x = coords.x * gapX;
            double y = coords.y * gapY;
        	
        	spots[(int)coords.x][(int)coords.y] = new Spot(new XY(x,y), "final", gc);
        }
    }
}
