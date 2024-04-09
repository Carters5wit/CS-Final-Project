package ludoGame;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spot {
	private String type;
	private Pane p;
	private static int id = 0;
	private double x;
	private double y;
	private ImageView gradient; // ImageView to display the spot gradient
	private Rectangle r;
	private final double size = 50; // Fixed size, do not change
  
	
   // TODO: Document this function
	public Spot(double x, double y, String type, GraphicsContext gc) {
		id++;
		this.x = x;
		this.y = y;
		this.type = type;
       
		boolean normal = true;
		Color fillColor = Color.WHITE;
      
		switch (type) {
       		case "home":
               double radius = size / 2.0;
               double centerX = x + radius;
               double centerY = y + radius;
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
       	
       }
      
		if (normal) {
			gc.setFill(fillColor);
			gc.setStroke(Color.BLACK);
			gc.fillRect(x, y, size, size);
			gc.strokeRect(x, y, size, size);
       }
	}
	
	// TODO: Document this function
	public Spot(double x, double y, Pane p) {
		id++;
		this.x = x;
		this.y = y;
		this.p = p;
		this.type = "neutral";
       
		r = new Rectangle(x, y, size, size);
		r.setFill(Color.WHITE);
		r.setStroke(Color.BLACK);
		p.getChildren().add(r);
       
		gradient = new ImageView("file:src/images/spotGradient.png");
		gradient.setTranslateX(x);
		gradient.setTranslateY(y);
		gradient.setFitWidth(size);
		gradient.setFitHeight(size);
		gradient.setMouseTransparent(true);
       	p.getChildren().add(gradient);
	}
	public void addArrow(int angle) {
	    ImageView arrow = new ImageView("file:src/images/plain-arrow.png");

	    arrow.setTranslateX((x - (size-10)/2) + size/2);
	    arrow.setTranslateY((y - (size-10)/2) + size/2);
	    arrow.setRotate(angle);
	    arrow.setFitWidth(size - 10);
	    arrow.setFitHeight(size - 10);
	    arrow.setMouseTransparent(true);
	    p.getChildren().add(arrow);
	}
	
	public void addStar() {
	    ImageView star = new ImageView("file:src/images/polar-star.png");
	    star.setTranslateX((x - (size-10)/2) + size/2);
	    star.setTranslateY((y - (size-10)/2) + size/2);
	    star.setFitWidth(size - 10);
	    star.setFitHeight(size - 10);
	    star.setMouseTransparent(true);
	    p.getChildren().add(star);
	}
	
	// TODO: Document this function
	public int getID() {
		return id;
	}
	
	public void setHomeStrechColor(Color clr) {
		r.setFill(clr);
	}
	
	// TODO: Document this function
	public double getX() {
		return x;
	}
	
	// TODO: Document this function
	public double getY() {
		return y;
	}
	
	// TODO: Document this function
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the size of each spot
	 * 
	 * @return size the size of the spot
	 */
	public double getSize() {
		return size;
	}
	
	// TODO: Document this function
	public void setType(String type) {
		this.type = type;
	}
	
	public Rectangle getRectangle() {
        return r;
    }
	
	public String toString() {
		return "Spot (" + x + ", " + y + ")";
	}
}

