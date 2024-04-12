package ludoGame;


import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Responsible for drawing rectangular and circular spots onto the board
 * to support pawn movement.
 * 
 * @author Saleem Carter
 * @author Reggie Andrade
 */
public class Spot {
	private String type;
	private Pane p;
	private XY coords;
	private ArrayList<Pawn> occupyingPawns = new ArrayList<Pawn>();
	private ImageView gradient; // ImageView to display the spot gradient
	private Rectangle r;
	private final double size = 50; // Fixed size, do not change
  
	
   /**
    * Default constructor to construct a spot onto the GraphicsContext (non-clickable)
    * 
    * @param coords The XY object containing the coordinates of the spot
    * @param type The type of spot ("home" or "final")
    * @param gc The GraphicsContext to draw to
    */
	public Spot(XY coords, String type, GraphicsContext gc) {
		this.coords = coords;
		this.type = type;
       
		boolean normal = true;
		Color fillColor = Color.WHITE;
      
		switch (type) {
       		case "home":
               double radius = size / 2.0;
               double centerX = coords.x + radius;
               double centerY = coords.y + radius;
               // Draw a circular spot
               normal = false;
               gc.setFill(fillColor);
               gc.setStroke(Color.BLACK);
               gc.fillOval(centerX - radius, centerY - radius, size, size);
               gc.strokeOval(centerX - radius, centerY - radius, size, size);
               break;
       		case "final":
       			normal = false;
       			break;
       		default:
       			type = "neutral";
       			break;
       	
       }
      
		if (normal) {
			gc.setFill(fillColor);
			gc.setStroke(Color.BLACK);
			gc.fillRect(coords.x, coords.y, size, size);
			gc.strokeRect(coords.x, coords.y, size, size);
       }
	}
	
	/**
	 * Overloaded Spot constructor to create a blank white square spot. Used as a base
	 * for safe spots, start spots, and home stretch spots.
	 * 
	 * Draws the spot onto a Pane to allow clicking interaction.
	 * 
	 * @param coords The XY object containing the coordinates of the spot
	 * @param p The pane to draw the spot onto.
	 */
	public Spot(XY coords, Pane p) {
		this.coords = coords;
		this.p = p;
		this.type = "neutral";
       
		r = new Rectangle(coords.x, coords.y, size, size);
		r.setFill(Color.WHITE);
		r.setStroke(Color.BLACK);
		p.getChildren().add(r);
       
		gradient = new ImageView("file:src/images/spotGradient.png");
		gradient.setTranslateX(coords.x);
		gradient.setTranslateY(coords.y);
		gradient.setFitWidth(size);
		gradient.setFitHeight(size);
		gradient.setMouseTransparent(true);
       	p.getChildren().add(gradient);
	}
	
	/**
	 * Method to add an arrow to the spot. Used for start spots.
	 * 
	 * @param angle The angle to draw the arrow at
	 */
	public void addArrow(int angle) {
	    ImageView arrow = new ImageView("file:src/images/plain-arrow.png");

	    arrow.setTranslateX((coords.x - (size-10)/2) + size/2);
	    arrow.setTranslateY((coords.y - (size-10)/2) + size/2);
	    arrow.setRotate(angle);
	    arrow.setFitWidth(size - 10);
	    arrow.setFitHeight(size - 10);
	    arrow.setMouseTransparent(true);
	    p.getChildren().add(arrow);
	}
	
	/**
	 * Method to add a star to the spot. Used for safe spots.
	 */
	public void addStar() {
	    ImageView star = new ImageView("file:src/images/polar-star.png");
	    star.setTranslateX((coords.x - (size-10)/2) + size/2);
	    star.setTranslateY((coords.y - (size-10)/2) + size/2);
	    star.setFitWidth(size - 10);
	    star.setFitHeight(size - 10);
	    star.setMouseTransparent(true);
	    p.getChildren().add(star);
	}
	
	/**
	 * Method to remove the gradient from the spot.
	 */
	public void removeGradient() {
		p.getChildren().remove(gradient);
	}
	
	/**
	 * Adds an occupying pawn
	 * 
	 * @param p Pawn to occupy the spot
	 */
	public void occupy(Pawn p) {
		occupyingPawns.add(p);
	}
	
	/**
	 * Removes an occupying pawn
	 * 
	 * @param p Pawn to unoccupy the spot
	 */
	public void unoccupy(Pawn p) {
		occupyingPawns.remove(p);
	}
	
	public void setHomeStrechColor(Color clr) {
		r.setFill(clr);
	}
	
	/**
	 * Gets the X coordinate of the spot
	 * 
	 * @return X-coordinate
	 */
	public double getX() {
		return coords.x;
	}
	
	/**
	 * Gets the Y coordinate of the spot
	 * 
	 * @return Y-coordinate
	 */
	public double getY() {
		return coords.y;
	}
	
	/**
	 * Gets the type of the spot (ex. "safe", "neutral")
	 * 
	 * @return X-coordinate
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the size of each spot
	 * 
	 * @return Size of the spot
	 */
	public double getSize() {
		return size;
	}
	
	/**
	 * Sets type of the spot
	 * 
	 * @param type The type to change the spot to
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the rectangle object of the spot. Will return null if the spot is circular.
	 * 
	 * @return Rectangle object
	 */
	public Rectangle getRectangle() {
        return r;
    }
	
	/**
	 * Gets the pawns which reside on this spot
	 * 
	 * @return Occupying pawns
	 */
	public ArrayList<Pawn> getOccupying() {
		return occupyingPawns;
	}
	
	/**
	 * Prints a formatted version of the Spot class (Spot (x, y))
	 */
	public String toString() {
		return "Spot (" + coords.x + ", " + coords.y + ")";
	}
}
