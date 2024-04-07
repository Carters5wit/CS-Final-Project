package ludoGame;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class Pawn {
	private GraphicsContext gc;
	private Spot spot; // Current spot of the pawn
	private int player = 0;
	private final int PAWN_NUM = 4; // Number of pawns on each team
	private boolean occ =false;
	private Label forClicks = new Label("");
	// Pawn images
	private Image blue = new Image("file:src/images/blue.png");
	private Image orange = new Image("file:src/images/orange.png");
	private Image yellow = new Image("file:src/images/yellow.png");
	private Image green = new Image("file:src/images/green.png");
	
	public Pawn(Spot spot, int player, GraphicsContext gc) {
		this.gc = gc;
		this.spot = spot;
		this.player = player;
		Image img = null;
		
		switch (player) {
			case 1:
				img = blue;
				break;
			case 2:
				img = orange;
				break;
			case 3:
				img = yellow;
				break;
			case 4:
				img = green;
				break;
		}
		
		gc.save(); // Save the current state of the graphics context
	    gc.translate(spot.getX() + spot.getSize()/2, spot.getY() + spot.getSize()/2);
	    
	    double scale = 0.1;
	    
	    double scaledWidth = img.getWidth() * scale;
	    double scaledHeight = img.getHeight() * scale;

	    gc.drawImage(img, -scaledWidth / 2, -scaledHeight / 2, scaledWidth, scaledHeight); // Draw the scaled image

	    gc.restore(); // Restore the saved state (removes the translation and rotation)
	}
	//it do
	public boolean occupied(Spot spot) {
		if(this.spot == spot){
			occ =true;
		}
		return occ;
	}
	
	public void eaten(Spot spot) {
		
	}
	
	public void spotClicked() {
		Label clickable = spot.returnLabel();
		
		clickable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Spot moveTo= new Spot(spot.getX(), spot.getY(), gc);
                updateSpot(moveTo);
            }
            
        });
		
	}
	
	public void updateSpot(Spot newer) {
		this.spot = newer;
	}
	public void pawnClicked() {
		forClicks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               // .spotclicked();
            }
            
        });
	}
		
	/**
	 * Get the spot the pawn resides on
	 * 
	 * @return spot the pawn is currently on
	 */
	public Spot returnSpot() {
		return spot;
	}
	
	/**
	 * Get the color (player) of the pawn
	 * 
	 * @return player 
	 */
	public int pawnColor() {
		return player;
	}
	

	
	
	
	
}
