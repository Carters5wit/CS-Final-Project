package ludoGame;

/**
 * Responsible for representing a 2D XY point in space.
 * 
 * @author Reggie Andrade
 */
public class XY {
	public double x;
	public double y;
	
	/**
	 * Default constructor to create a simple XY object
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 */
	public XY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Factory method to quickly create an XY object
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @return XY objects
	 */
	public static XY form(double x, double y) {
		return new XY(x,y);
	}
}
