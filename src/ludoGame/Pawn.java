package ludoGame;

public class Pawn {
	private int[] pos = new int[2];
	private int player =0;
	private int pawnnum = 4;
	
	public Pawn(int[] spot, int player) {
		this.pos= spot;
		this.player=player;
	}
		
	
	public int[] returnPosition() {
		return pos;
	}
	
	
	
	
}
