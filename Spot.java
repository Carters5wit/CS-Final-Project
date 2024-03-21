
public class Spot {
	private String catagory = "Neutral";
	private static int id = 0;
	
	public Spot() {
		id++;
	}
	public static int getID() {
		return id;
	}
	
	public void setHome() {
		this.catagory = "Home";
	}
	
	public void setSafe() {
		this.catagory = "Safe";
	}
	
	public void setStart() {
		this.catagory = "Start";
	}
	
	public void setHomeStretch() {
		this.catagory = "HomeStretch";
	}

	public String getType() {
		return catagory;
	}
	

	
	
	
	
	
}
