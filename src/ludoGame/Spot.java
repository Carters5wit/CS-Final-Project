package ludoGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
public class Spot {
	private String type;
	private GraphicsContext gc;
	private static int id = 0;
   private double x;
   private double y;
  
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
       	case "neutral":
       		break;
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
       	// TODO: Write the rest of the switch case statements
       	case "start":
       		
       		gc.setFill(fillColor);
               gc.setStroke(Color.BLACK);
               gc.fillRect(x, y, size, size);
               gc.strokeRect(x, y, size, size);
            
       	
       }
      
       if (normal) {
       	gc.setFill(fillColor);
           gc.setStroke(Color.BLACK);
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
	public void addArrow(int angle) {
		Image img = new Image("src/images/plain-arrow.png");
       ImageView arrow = new ImageView(img);
       arrow.setX(x);
       arrow.setY(y);
      
      
       Rotate r = new Rotate();
       r.setAngle(angle);
       r.setPivotX(img.getWidth()/2);
       r.setPivotY(img.getHeight()/2);
       arrow.getTransforms().add(r);
      
      
      
	}
	
	// TODO: Document this function
	public int getID() {
		return id;
	}
	
	public void setHomeStrechColor(Color clr) {
		gc.setFill(clr);
		gc.setStroke(Color.BLACK);
		gc.clearRect(x, y, size, size);
		gc.fillRect(x,y,size,size);
		gc.strokeRect(x, y, size, size);
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

