package ludoGame;

import javafx.scene.image.Image;

public class Pawn {
	private Spot spot; // Current spot of the pawn
	private int player = 0;
	private final int PAWN_NUM = 4; // Number of pawns on each team
	
	// Pawn images
	private Image blue = new Image("file:src/images/blue.png");
	private Image orange = new Image("file:src/images/orange.png");
	private Image yellow = new Image("file:src/images/yellow.png");
	private Image green = new Image("file:src/images/green.png");
	
	public Pawn(Spot spot, int player) {
		this.spot = spot;
		this.player = player;
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
