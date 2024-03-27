package ludoGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spot {
	private String type;
	private GraphicsContext gc;
	private static int id = 0;
    private double x;
    private double y;
    private final double size = 60; // Fixed size, do not change
	
    // TODO: Document this function
	public Spot(double x, double y, String type, GraphicsContext gc) {
		id++;
		this.x = x;
        this.y = y;
        this.type = type;
        
        boolean square = true;
        Color fillColor = Color.WHITE;
        Color outlineColor = Color.BLACK;
        
        switch (type) {
        	case "neutral":
        		break;
        	case "home":
                double radius = size / 2.0;
                double centerX = x + radius;
                double centerY = y + radius;

                // Draw a circular spot
                square = false;
                gc.setFill(fillColor);
                gc.setStroke(outlineColor);
                gc.fillOval(centerX - radius, centerY - radius, size, size);
                gc.strokeOval(centerX - radius, centerY - radius, size, size);
                break;
        	// TODO: Write the rest of the switch case statements
        }
        
        if (square) {
        	gc.setFill(fillColor);
            gc.setStroke(outlineColor);
            gc.fillRect(x, y, size, size);
            gc.strokeRect(x, y, size, size);
        }
	}
	
	// TODO: Document this function
	public Spot(double x, double y, GraphicsContext gc) {
		id++;
		this.x = x;
        this.y = y;
        this.gc = gc;
        this.type = "neutral";
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.fillRect(x, y, size, size);
        gc.strokeRect(x, y, size, size);
	}
	
	// TODO: Document this function
	public int getID() {
		return id;
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
	
	// TODO: Document this function
	public void setType(String type) {
		// TODO: Set type based on keywords, throwing error for invalid type.
		// Tip: Use gc.clearRect to replace a shape, use switch cases from constructor.
	}
	
}
