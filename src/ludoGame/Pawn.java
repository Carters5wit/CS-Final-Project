package ludoGame;

public class Pawn {
	private int[] pos = new int[2];
	private int player =0;
	private int pawnnum = 4;
	private String color;
	
	public Pawn(int[] spot, int player) {
		this.pos= spot;
		this.player=player;
		switch (player) {
		case 1:
			this.color= "Blue";
			break;
		case 2:
			this.color="Orange";
			break;
		case 3:
			this.color="Yellow";
			break;
		case 4:
			this.color="Green";
			break;
		}
	}
		
	
	public int[] returnPosition() {
		return pos;
	}
	
	public String pawnColor() {
		return color;
	}
	

	
	
	
	
}
