package ludoGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class Pawn {
	private GraphicsContext gc;
	private Spot spot; // Current spot of the pawn
	private int player = 0;
	private boolean occ = false;
	private Label forClicks = new Label("");
	private ImageView imageView; // ImageView to display the pawn image

	public Pawn(Spot spot, int player, Pane pawnPane) {
        this.spot = spot;
        this.player = player;
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

        // Add the ImageView to the pawnPane
        pawnPane.getChildren().add(imageView);
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
	
	public void updateSpot(Spot newer) {
		this.spot = newer;
		System.out.print(spot);
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
	
	
}
