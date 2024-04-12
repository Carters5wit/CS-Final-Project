package ludoGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

/**
 * Responsible for creating pawn objects for each player, which can be moved around 
 * the board onto different spots.
 * 
 * @author Saleem Carter
 * @author Reggie Andrade
 */
public class Pawn {
	private Spot spot = null; // Current spot of the pawn
	private Spot home;
	private int player = 0;
	private int displace = 0;
	private int moves = 0;
	private int offset = 0;
	private boolean onBoard;
	private Pane p;
	private ImageView imageView; // ImageView to display the pawn image
	private boolean clickable = false;
	private boolean movable = true;
	private boolean won = false;
	Ellipse selectCircle = new Ellipse();

	/**
	 * Default constructor to create a pawn object. Places the pawn on a given spot.
	 * 
	 * @param spot The spot to place the pawn on
	 * @param player The player the pawn belongs to (1 = blue, 2 = orange, 3 = yellow, 4 = green)
	 * @param pawnPane The pane object to place the pawn onto
	 */
	public Pawn(Spot spot, int player, Pane pawnPane) {
        this.spot = spot;
        home = spot;
        this.player = player;
        this.p = pawnPane;
        Image img = null;

        switch (player) {
            case 1:
                img = new Image("file:src/images/blue.png");
                break;
            case 2:
                img = new Image("file:src/images/orange.png");
                break;
            case 3:
                img = new Image("file:src/images/yellow.png");
                break;
            case 4:
                img = new Image("file:src/images/green.png");
                break;
        }

        imageView = new ImageView(img);
        imageView.setFitWidth(spot.getSize()); // Set the size of the ImageView
        imageView.setFitHeight(spot.getSize());

        // Translate the ImageView to the correct position
        imageView.setTranslateX(spot.getX() + spot.getSize() / 2 - imageView.getFitWidth() / 2);
        imageView.setTranslateY(spot.getY() + spot.getSize() / 2 - imageView.getFitHeight() / 2);
        imageView.setMouseTransparent(true);

        // Add the ImageView to the pawnPane
        p.getChildren().add(imageView);
        
        spot.occupy(this);
    }

	/**
	 * Method to move the pawn to a spot.
	 * 
	 * @param newSpot The spot to move the pawn to
	 * @param amountOfMoves The amount of spots the pawn moved
	 * @return Pawn object which has been captured, null otherwise.
	 */
	public ArrayList<Pawn> moveTo(Spot newSpot, int amountOfMoves) {
		if (spot != null && !(spot.equals(newSpot))) {
			spot.unoccupy(this);
			offset = 0;
		}
		
		spot = newSpot;
		ArrayList<Pawn> cap = new ArrayList<Pawn>();
		
		imageView.setTranslateX(spot.getX() + spot.getSize() / 2 - imageView.getFitWidth() / 2);
        imageView.setTranslateY(spot.getY() + spot.getSize() / 2 - imageView.getFitHeight() / 2);
        
        if (!(spot.getOccupying().isEmpty())) {
        	ArrayList<Pawn> pawns = spot.getOccupying();
        	int least = 0;
        	for (int i = 0; i < pawns.size(); i++) {
        		Pawn p = pawns.get(i);
        		if (spot.getType().equals("safe") || pawns.get(i).pawnColor() == player) {
        			
    				if (p.getOffset() < least) {
    					least = p.getOffset();
    				}
        		} else {
        			cap.add(pawns.get(i));
        		}
        	}
        	
        	if (cap.isEmpty()) {
        		this.applyOffset(least - 10);
        	}
		}
		
		spot.occupy(this);
        
        displace += amountOfMoves;
        moves++;
        
        return cap;
	}
	
	/**
	 * Overloaded method to move the pawn to a spot, and declare if it is on the board.
	 * 
	 * @param amountOfMoves The amount of spots the pawn moved
	 * @param on Whether the pawn is moving onto the board or not
	 * @return Pawn objects which have been captured, null otherwise.
	 */
	public ArrayList<Pawn> moveTo(Spot newSpot, int amountOfMoves, boolean on) {
		if (spot != null && !(spot.equals(newSpot))) {
			spot.unoccupy(this);
			offset = 0;
		}
		
		spot = newSpot;
		ArrayList<Pawn> cap = new ArrayList<Pawn>();
		
		imageView.setTranslateX(spot.getX() + spot.getSize() / 2 - imageView.getFitWidth() / 2);
        imageView.setTranslateY(spot.getY() + spot.getSize() / 2 - imageView.getFitHeight() / 2);
        
        if (!(spot.getOccupying().isEmpty())) {
        	ArrayList<Pawn> pawns = spot.getOccupying();
        	int least = 0;
        	for (int i = 0; i < pawns.size(); i++) {
        		Pawn p = pawns.get(i);
        		if (spot.getType().equals("safe") || pawns.get(i).pawnColor() == player) {
        			
    				if (p.getOffset() < least) {
    					least = p.getOffset();
    				}
        		} else {
        			cap.add(pawns.get(i));
        		}
        	}
        	
        	if (cap.isEmpty()) {
        		this.applyOffset(least - 10);
        	}
		}
		
		spot.occupy(this);
        
        displace += amountOfMoves;
        moves++;
        onBoard = on;
        
        return cap;
	}
	
	/**
	 * Translate the pawn upwards or downwards by a certain offset. Used for stacked pawns
	 * 
	 * @param amount Amount of offset to apply (in pixels)
	 */
	public void applyOffset(int amount) {
		offset = amount;
		imageView.setTranslateY(spot.getY() + offset + spot.getSize() / 2 - imageView.getFitHeight() / 2);
	}
	
	/**
	 * Method to enable and disable clicking of the pawn. Allows the pawn to be clicked, and draws a red circle
	 * around the pawn.
	 * 
	 * @param sc The scene the pawn resides on (used for changing mouse cursor when hovering pawn)
	 */
	public void setClick(boolean state, Scene sc) {
		if (state) {
			clickable = true;
			
	        imageView.setMouseTransparent(false);
	        
	        double xVal = spot.getX() + spot.getSize() - imageView.getFitWidth() / 2;
	        double yVal = spot.getY() + spot.getSize() - imageView.getFitHeight() / 2;
	        
	        selectCircle.setLayoutX(xVal);
	        selectCircle.setLayoutY(yVal);
	        selectCircle.setRadiusX(spot.getSize()/2);
	        selectCircle.setRadiusY(spot.getSize()/2);
	        selectCircle.setStroke(Color.RED);
	        selectCircle.setStrokeWidth(2);
	        selectCircle.setFill(Color.TRANSPARENT);
	        selectCircle.setMouseTransparent(true);
	        
			// Set cursor to hand when mouse enters the ImageView
	        imageView.setOnMouseEntered(e -> {
	            sc.setCursor(Cursor.HAND);
	            selectCircle.setStroke(Color.SALMON);
	        });

	        // Set cursor to default when mouse exits the ImageView
	        imageView.setOnMouseExited(e -> {
	            sc.setCursor(Cursor.DEFAULT);
	            selectCircle.setStroke(Color.RED);
	        });
	        
	        
	        p.getChildren().add(selectCircle);
		} else {
			clickable = false;
			
			// Reset cursor to defualt on entered
	        imageView.setOnMouseEntered(e -> {
	            sc.setCursor(Cursor.DEFAULT);
	        });
			
	        imageView.setMouseTransparent(true);
	        
	        p.getChildren().remove(selectCircle);
		}
	}
	
	public void reset() {
		spot.unoccupy(this);
		offset = 0;
		moves = 0;
		displace = 0;
		onBoard = false;
	}
	
	/**
	 * Get the spot the pawn resides on
	 * 
	 * @return Spot the pawn is currently on
	 */
	public Spot returnSpot() {
		return spot;
	}
	
	/**
	 * Get the color (player) of the pawn
	 * 
	 * @return Player ID (1 = blue, 2 = orange, 3 = yellow, 4 = green)
	 */
	public int pawnColor() {
		return player;
	}
	
	/**
	 * Get the ImageView object of the pawn
	 * 
	 * @return ImageView object
	 */
	public ImageView getImageView() {
        return imageView;
    }
	
	/**
	 * Return if the pawn is clickable or not as a boolean
	 * 
	 * @return clickable boolean (true/false)
	 */
	public boolean isClickable() {
		return clickable;
	}
	
	/**
	 * Return the amount of times the pawn has moved
	 * 
	 * @return Amount of moves
	 */
	public int getMoves() {
		return moves;
	}
	
	/**
	 * Return the original home position of the pawn
	 * 
	 * @return
	 */
	public Spot getHome() {
		return home;
	}
	
	/**
	 * Return the amount of displacement the pawn has
	 * 
	 * @return Movements on the board (displacement)
	 */
	public int getDisplace() {
		return displace;
	}
	
	/**
	 * Returns the current Y offset of the pawn
	 * 
	 * @return Y offset
	 */
	public int getOffset() {
		return offset;
	}
	
	public boolean getMovable() {
		return movable;
	}
	
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	
	public boolean getWon() {
		return won;
	}
	
	public void setWon(boolean won) {
		this.won = won;
	}
	
	/**
	 * Return if the pawn is on the board (outside of home)
	 * 
	 * @return If the pawn is on the board (true/false)
	 */
	public boolean isOnBoard() {
		return onBoard;
	}
	
	public String toString() {
		return "Pawn - Team: " + player + " | Moves: " + moves + " | Clickable: " + clickable;
	}
}
