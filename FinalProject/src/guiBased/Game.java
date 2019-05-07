package guiBased;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

public class Game extends JPanel {
	private int numOfPlayers;		//Used to track number of players
	private Player[] players;		//Used to track player information
	Rectangle[] boardSpots;			//Used to refer to board information
	
	private JPanel board;
	
	/**
	 * Create the panel.
	 * @param players	Sets the players in the game
	 */
	public Game() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		setLayout(groupLayout);
		
		boardSpots = new Rectangle[12];
		
		numOfPlayers = 0;
	}
	
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public void setNumOfPlayers(int num) {
		numOfPlayers = num;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public String getName(int i) {
		return players[i].getName();
	}
	
	@Override
	public void paint(Graphics g) {
//		Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
		g.setColor(Color.BLACK);
		for (int i= 0, x, y; i < 12; i++) {
			x = y = 0;
			
			if(i < 4)
				y = 10;
			if (i > 2 && i < 8)
				x = 160;
			if (i > 5 && i < 10)
				y = 160;
			if (i > 8 || i == 0)
				x = 10;
			
			if (i == 1 || i == 8)
				x = 60;
			if (i == 2 || i == 7)
				x = 110;
			if (i == 4 || i == 11)
				y = 60;
			if (i == 5 ||i == 10)
				y = 110;
			
//			g.setColor(colors[i % colors.length]);
			g.drawRect(x, y, 50, 50);
		}
	}
}
