package guiBased;

public class Player {
	private String name;
	private int spot;
	
	public Player (String name) {
		this.name = name;
		spot = 0;
	}
	
	public void moveForward(int roll) {
		this.spot = (spot + roll) % 12;
	}
	
	public String getName() {
		return name;
	}
}
