package guiBased;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import java.util.Random;

import javax.swing.ImageIcon;

public class Player {
	//Initialize Constants
	private static final int INIT_SALARY = 20000;
	private static final double INIT_CASH = 5000;
	private static final double INIT_WAGE = 10.0;
	
	//Declare instance variables
	private String name;
	private int spot;
	private int salary;
	private double cash;
	private double wage;
	private int x, y;
	private int xShift, yShift;
	
	//Declare status flags
	private boolean married, parent;
	private boolean rolling, sabbatical;
	private boolean paidTaxes;
	
	//Declare character icon
	private ImageIcon icon;
	
	/*CONSTRUCTOR - name:String
	 * Implemented at the termination of the StartMenu
	 */
	public Player (String name) {
		//Initialize instance variables
		this.name = name;
		spot = 0;
		cash = INIT_CASH;
		wage = INIT_WAGE;
		salary = INIT_SALARY;
		
		//Initialize location variables
		updateCoords();
	}
	
	/*MOVE - roll:int, backwards:boolean
	 * Moves player to their new spot
	 * Pays player for every spot traversed
	 * Executed whenever the player moves
	 */
	public void move(int roll, boolean backwards) {
		//Move player to appropriate spot
		if (!backwards)
			this.spot = (spot + roll) % 12;
		else
			this.spot = (spot - roll);
		
		//Check for underflow
		if (spot < 0)
			spot = 12 + spot;
		
		//Pay player for every spot traversed
		cash += roll * wage;
		
		//Update the player's coordinates
		updateCoords();
	}
	
	/*PAINT ICON - c:Component, g:Graphics
	 * Displays icon at the player's current position on the board
	 * Executed whenever the player is painted
	 */
	public void paintIcon(Component c, Graphics g) {
		icon.paintIcon(c, g, x + xShift, y + yShift);
	}
	
	/*IS ROLLING
	 * Returns false if the player is not supposed to roll this round
	 * Updates the player's rolling status
	 * Executed every round
	 */
	public boolean isRolling() {
		//Initialize a variable with the current rolling status
		boolean bool = rolling;
		
		//
		if (sabbatical)
			payDay();
		sabbatical = false;
		rolling = true;
		return bool;
	}
	
	/*GETTERS AND SETTERS*/
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public double getWage() {
		return wage;
	}
	public double getSalary() {
		return salary;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public String getName() {
		return name;
	}
	public int getX() {
		//Ensure the coordinates are accurate
		updateCoords();
		return x;
	}
	public int getY() {
		//Ensure the coordinates are accurate
		updateCoords();
		return y;
	}
	public double getCash() {
		return cash;
	}
	public int getSpot() {
		return spot;
	}
	public void setShift(int xShift, int yShift) {
		this.xShift = xShift;
		this.yShift = yShift;
	}
	public void setIsNotWorking() {
		salary = 0;
		wage = 0;
	}
	public void setSabbatical() {
		rolling = false;
		payDay();
	}
	
	/*CHANGE WAGE - percentage:int
	 * Increases the player's wage by a given percentage
	 */
	public void changeWage(int percentage) {
		wage += percentage * wage / 100;
	}
	
	/*PAY DAY
	 * Add a quarter salary's worth to cash value
	 */
	public void payDay() {
		cash += 0.25 * salary;
	}
	
	/*BONUS
	 * Add ten-percent salary's worth to cash value
	 */
	public void bonus() {
		cash += 0.10 * salary;
	}
	
	
									/**SPOT METHODS**/
	/*PAY TAXES
	 * Player pays a sum of cash relevant to their tax bracket and marital status
	 * Executed when the player lands on tax spot
	 */
	public String payTaxes() {
		//Define percentage player must pay
		int per;
		if (married)
			per = 12;
		else
			per = 22;
		
		//Remove proper amount of cash, and return message
		cash -= per * salary / 100;
		return " Paid " + per + "% Income Tax";
	}
	
	/*NEW JOB
	 * TODO: Method Description and Code
	 * Executes when the player lands on new job
	 */
	public String newJob() {
		return "";
	}
	
	/*BANK
	 * TODO: Method Description and Code
	 * Executes when the player lands on bank
	 */
	public String bank() {
		return "";
	}
	
	/*HOME
	 * Resets most recent tax date
	 * Executes when the player lands on Home
	 */
	public void home() {
		paidTaxes = false;
	}
	
	/*LIFE
	 * TODO: Method Description and Commenting
	 * Executes when the player lands on life
	 */
	public String life() {
		String str;
		Random rand = new Random(System.currentTimeMillis());
		int event = rand.nextInt(6);
		if (event == 0) {
			if (married) {
				married = false;
				str = " Got A Divorce...";
			}
			else {
				married = true;
				str = "Got Married";
			}
		} else if (event == 1) {
			if (!paidTaxes) {
				payTaxes();
				payTaxes();
				str = " Got Audited...";
			} else {
				str = " Passed an Audit!";
			}
		} else if (event == 2) {
			if (parent && !married) {
				cash -= 10000;
				str = " Paid Child Support...";
			} else if (parent && married) {
				cash -= 5000;
				str = " Paid For Their Child's Broken Arm...";
			} else {
				parent = true;
				str = " Had a Child!";
			}
		} else if (event == 3) {
			rolling = false;
			str = " Got the Flu...";
		} else if (event == 4) {
			cash += 0.10 * salary;
			str = " Won a Tournament of Fantasy Football";
		} else if (event == 5) {
			cash += 100000;
			str = " Won The Lottery!";
		} else {
			str = " Did Nothing";
		}
		return str;
	}
	
	/*UPDATE COORDS
	 * Set the coordinates for the player's given spot
	 * Executed every turn + every call to the player's coordinates
	 */
	private void updateCoords() {
		//Top Row
		if(spot < 4)
			y = 10;
		//Right Column
		if (spot > 2 && spot < 8)
			x = 160;
		//Bottom Row
		if (spot > 5 && spot < 10)
			y = 160;
		//Left Column
		if (spot > 8 || spot == 0)
			x = 10;
		
		//Second Column
		if (spot == 1 || spot == 8)
			x = 60;
		//Third Column
		if (spot == 2 || spot == 7)
			x = 110;
		//Second Row
		if (spot == 4 || spot == 11)
			y = 60;
		//Third Row
		if (spot == 5 || spot == 10)
			y = 110;
	}
}
