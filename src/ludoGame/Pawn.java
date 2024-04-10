package ludoGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class Pawn {
	private Spot spot; // Current spot of the pawn
	private int player = 0;
	private Pane p;
	private ImageView imageView; // ImageView to display the pawn image
	private boolean clickable = false;
	Ellipse selectCircle = new Ellipse();

	public Pawn(Spot spot, int player, Pane pawnPane) {
        this.spot = spot;
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
    }

	
	public void moveTo(Spot newSpot) {
		spot = newSpot;
		imageView.setTranslateX(spot.getX() + spot.getSize() / 2 - imageView.getFitWidth() / 2);
        imageView.setTranslateY(spot.getY() + spot.getSize() / 2 - imageView.getFitHeight() / 2);
	}
	
	public void toggleClick(Scene sc) {
		if (!clickable) {
			clickable = true;
			
			// Set cursor to hand when mouse enters the ImageView
	        imageView.setOnMouseEntered(e -> {
	            sc.setCursor(Cursor.HAND);
	        });

	        // Set cursor to default when mouse exits the ImageView
	        imageView.setOnMouseExited(e -> {
	            sc.setCursor(Cursor.DEFAULT);
	        });
	        
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
	
	public ImageView getImageView() {
        return imageView;
    }
	
	public boolean isClickable() {
		return clickable;
	}
	
}
