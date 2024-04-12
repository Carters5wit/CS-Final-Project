package ludoGame;

/**
 * Responsible for representing a player within the Ludo game.
 * 
 * @author Reggie Andrade
 */
public class Player {
	Pawn[] pawns;
	int id;
	
	/**
	 * Basic constructor to create a player with a specific ID.
	 * 
	 * @param id ID of the player
	 * @param pawns Pawns belonging to the player
	 */
	public Player(int id, Pawn[] pawns) {
		this.id = id;
		this.pawns = pawns;
	}
	
	/**
	 * Gets the pawns of the player
	 * 
	 * @return Array of pawns
	 */
	public Pawn[] getPawns() {
		return pawns;
	}
	
	/**
	 * Gets the ID of the player
	 * 
	 * @return Player ID
	 */
	public int getID() {
		return id;
	}
}
