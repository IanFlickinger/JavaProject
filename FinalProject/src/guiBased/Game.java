package guiBased;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Game extends JPanel {
	//Define constants
	private final double MIN_TO_WIN = 10000.00;
	
	//Declare player variables
	private int numOfPlayers;		
	private Player[] players;	
	private int currentPlayer;
	private int displayedPlayer;	

	//Declare news data variables
	private String[] actions;
	private String effect;
	
	//Declare JPanels
	private JPanel playerDisplay;
	private JPanel news;
	private JPanel popup;
	
	//Declare dice
	private Die die1;
	private Die die2;
	
	//Declare fonts
	private Font plainFont;
	private Font boldFont;

	/**
	 * Create the panel.
	 * @param players	Sets the players in the game
	 */
	public Game() {
		//Set absolute layout
		setLayout(null);
		
		//Initialize fonts
		boldFont = new Font("Times New Roman", Font.BOLD, 12);
		plainFont = new Font("Times New Roman", Font.PLAIN, 12);
		
		//Initialize Dice
		die1 = new Die(System.currentTimeMillis());
		die1.setBounds(65, 90, 40, 40);
		die2 = new Die(System.nanoTime());
		die2.setBounds(115, 90, 40, 40);
		add(die1);
		add(die2);
		
		//Initialize action strings
		actions = new String[] {
				"Went Home",					//Where you start and end
				"Got a Promotion", 				//Get A Raise
				"Paid Taxes",					//Pay taxes (According to real brackets)
				"Got a Pay Day",				//Receive a quarter of your annual salary
				"Went to the Bank",				//Can deposit money for safe keeping and compounded interest
				"Had Life Happen", 				//A random event occurs (Life Happens)
				"Got a Holiday Bonus",			//Get 10% Annual Salary
				"Lost Their Job",				//No More Payments Come In
				"Was Placed on Sabbatical",		//Free Pay Day for this turn and the next, but no moving	
				"Got a Pay Day",				//Receive a quarter of your annual salary
				"Experienced Cut Backs",		//Annual Salary is cut by 5%. Hourly Wage is reduced by 5%.
				"Got Offered a New Job"			//You're offered a new job. Do you take it?
		};
		/*Optional Life Events Include
		 * 		Influenza				Lose A Turn
		 * 		New Child				Pay child support if not married
		 * 		Get Married				Receive Wedding Gifts
		 * 		Death in the Family		Pay for the Funeral, and lose a turn
		 * 		Audit					Pay Double taxes if you haven't paid since you were last home
		 * 		Fantasy Champion		Receive Winnings from a Fantasy Football game proportional to your income
		 * 		Stock Market Crash		Your money in the bank loses 10% of its value
		 */
		
		//Initialize player detail display panel
		playerDisplay = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paintBorder(g);
				Player player = players[displayedPlayer];
				g.setColor(Color.BLACK);
				player.getIcon().paintIcon(this, g, 2, 2);
				g.setFont(boldFont);
				g.drawString("Name", 30, 12);
				g.drawString("Cash", 150, 12);
				g.drawString("Wage $", 2, 36);
				g.drawString("Salary $", 100, 36);
				g.setFont(plainFont);
				g.drawString(player.getName(), 30, 23);
				g.drawString("$" + player.getCash(), 150, 23);
				g.drawString(Double.toString(player.getWage()), 42, 36);
				g.drawString(Double.toString(player.getSalary()), 146, 36);
			}
		};
		playerDisplay.setBounds(250, 10, 200, 40);
		playerDisplay.setBorder(new LineBorder(Color.BLACK));
		playerDisplay.setVisible(true);
		this.add(playerDisplay);
		
		//Initialize news display panel
		news = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paintBorder(g);
				paintComponents(g);
			}
		};
		news.setBounds(250, 50, 200, 160);
		news.setLayout(new GridLayout(5, 1));
		news.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Facebook Feed"));
		news.setVisible(true);
		this.add(news);
		
		
//		//TODO: Initialize popup panel
//		popup = new JPanel();
//		popup.setVisible(false);
//		this.add(popup);
	}
	
									/**GETTERS AND SETTERS**/
	public JPanel getNews() {
		return news;
	}
	public String getAction(int num) {
		return actions[num];
	}
	public Player getPlayer(int num) {
		return players[num];
	}
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public void setNumOfPlayers(int num) {
		numOfPlayers = num;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
		players[0].setShift(2, 2);
		players[0].setIcon(new ImageIcon("MarioIcon.png"));
		players[1].setShift(27, 2);
		players[1].setIcon(new ImageIcon("MushroomIcon.jpg"));
		if (numOfPlayers > 2) {
			players[2].setShift(2, 27);
			players[2].setIcon(new ImageIcon("TurtleCloudIcon.png"));
		}
		if (numOfPlayers > 3) {
			players[3].setShift(27, 27);
			players[3].setIcon(new ImageIcon("Mario2Icon.png"));
		}
	}
	public void setDisplayedPlayer(int num) {
		displayedPlayer = num;
		repaint();
	}
	public String getName(int i) {
		return players[i].getName();
	}
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void roll(boolean backwards) {
		int roll = 0;
		roll += die1.roll();
		roll += die2.roll();
		players[currentPlayer].move(roll, backwards);
		repaint();
	}
	
	public String getEffect() {
		Player player = players[currentPlayer];
		String effect = "";
		switch (player.getSpot()) {
			case 0:
				if (player.getCash() > MIN_TO_WIN) {
					winner(getGraphics());
					effect = " Won!";
				} else
					player.home();
				break;
			case 1:
				player.changeWage(25);
				effect = " Got a 25% Wage Raise!";
				break;
			case 2: 
				player.payTaxes();
				effect = " Paid Taxes...";
				break;
			case 3: 
			case 9:
				player.payDay();
				effect = " Got Paid!";
				break;
			case 4:
				effect = player.bank();
				break;
			case 5:
				effect = player.life();
				break;
			case 6:
				player.bonus();
				effect = " Got A 10% Salary Bonus!";
				break;
			case 7:
				player.setIsNotWorking();
				effect = " Is No Longer Making Money...";
				break;
			case 8:
				player.setSabbatical();
				effect = " Will Be On Paid Leave";
				break;
			case 10:
				player.changeWage(-10);
				effect = " Had Their Wages Cut by 10%";
				break;
			case 11:
				effect = player.newJob();
				break;
		}
		return effect;
	}

	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % numOfPlayers;
		displayedPlayer = currentPlayer;
		if (!players[currentPlayer].isRolling())
			nextPlayer();
		repaint();
	}
	
	private void winner(Graphics g) {
		
	}
 	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
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

			g.drawRect(x, y, 50, 50);
		}
		
		die1.paintComponent(g);
		die2.paintComponent(g);
		
		for (int i = 0; i < numOfPlayers; i++)
			players[i].paintIcon(this, g);
		paintComponents(g);
	}
	
//	@Override
//	public void mouseClicked(MouseEvent e) {}
//	
//	@Override
//	public void mousePressed(MouseEvent e) {}
//	
//	@Override
//	public void mouseReleased(MouseEvent e) {}
//	
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		System.out.println("Entered");
//		item = (NewsItem) e.getComponent();
//		popup = new JPanel() {
//			@Override 
//			public void paint(Graphics g) {
//				System.out.println("Painting");
//				g.setColor(Color.BLACK);
//				int[] xPoints = {
//						item.getX() + news.getX(), 
//						item.getX() + news.getX() + item.getWidth()/2 - item.getHeight()/2, 
//						item.getX() + news.getX() + item.getWidth()/2,
//						item.getX() + news.getX() + item.getWidth()/2 + item.getHeight()/2,
//						item.getX() + news.getX() + item.getWidth(),
//						item.getX() + news.getX() + item.getWidth(),
//						item.getX() + news.getX()
//				};
//				int[] yPoints = {
//						item.getY() + news.getY() + item.getHeight(), 
//						item.getY() + news.getY() + item.getHeight(),
//						item.getY() + news.getY() + item.getHeight()/2,
//						item.getY() + news.getY() + item.getHeight(),
//						item.getY() + news.getY() + item.getHeight(),
//						item.getY() + news.getY() + 100,
//						item.getY() + news.getY() + 100
//				};
//				
//				g.fillPolygon(xPoints, yPoints, 7);
//			}
//		};
//		popup.setBounds(item.getX() + news.getX(), item.getY() + news.getY() + 10, item.getWidth(), item.getHeight());
//		popup.setVisible(true);
//		popup.paint(getGraphics());
//		this.revalidate();
//		this.add(popup);
//	}
//	
//	@Override 
//	public void mouseExited(MouseEvent e) {
//		System.out.println("Exited");
//		this.remove(popup);
//		this.paintComponents(getGraphics());
//		this.revalidate();
//	}
}
