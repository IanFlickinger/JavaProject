package guiBased;

import java.util.Random;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Die extends JComponent {
	private Random rand;
	private int val;
	private final int diameter = 10;
	
	public Die(long seed) {
		rand = new Random();
		rand.setSeed(seed);
		this.setVisible(true);
		
		val = 1;
	}
	
	public int roll() {
		val = rand.nextInt(6) + 1;
		return val;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRoundRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, 5);
		g.setColor(Color.WHITE);
		if (val == 1)
			drawOne(g);
		if (val == 2) 
			drawTwo(g);
		if (val == 3)
			drawThree(g);
		if (val == 4)
			drawFour(g);
		if (val == 5)
			drawFive(g);
		if (val == 6)
			drawSix(g);
	}
	
	private void drawOne(Graphics g) {
		g.fillOval(this.getX() + this.getWidth()/2 - diameter/2, this.getY() + this.getHeight()/2 - diameter/2, diameter, diameter);
	}
	
	private void drawTwo(Graphics g) {
		g.fillOval(this.getX() + 5, this.getY() + 5, diameter, diameter);
		g.fillOval(this.getX() + this.getWidth() - 5 - diameter, this.getY() + this.getHeight() - 5 - diameter, diameter, diameter);
	}
	
	private void drawThree(Graphics g) {
		drawOne(g);
		drawTwo(g);
	}
	
	private void drawFour(Graphics g) {
		drawTwo(g);
		g.fillOval(this.getX() + 5, this.getY() + this.getHeight() - 5 - diameter, diameter, diameter);
		g.fillOval(this.getX() + this.getWidth() - 5 - diameter, this.getY() + 5, diameter, diameter);
	}
	
	private void drawFive(Graphics g) {
		drawOne(g);
		drawFour(g);
	}
	
	private void drawSix(Graphics g) {
		drawFour(g);
		g.fillOval(this.getX() + 5, this.getY() + this.getHeight()/2 - diameter/2, diameter, diameter);
		g.fillOval(this.getX() + this.getWidth() - 5 - diameter, this.getY() + this.getHeight()/2 - diameter/2, diameter, diameter);
	}
}
