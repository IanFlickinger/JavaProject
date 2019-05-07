package guiBased;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetPlyrs extends JFrame {
	private int num;					//Will hold the number of players being created
	
	private JButton start;				//Will create the players and begin the game
	
	private JLabel enterPlyr;			//Header for the content
	private JLabel plyrOne;				//These labels will simply be used to display which player name goes where
	private JLabel plyrTwo;			
	private JLabel plyrThree;
	private JLabel plyrFour;
	
	private JPanel plyrNames;			//Panel to hold the player labels and name inputs
	
	private JTextField plyrOneName;		//These fields take the player names as inputs
	private JTextField plyrTwoName;
	private JTextField plyrThreeName;
	private JTextField plyrFourName;
	
	private String[] names;
	
	/**
	 * Constructor is used to build the frame and inputs
	 * @param num		The number of players being created. Used to set this.num
	 * @param listener	Will be the GameWindow. Used as an action listener for the start button
	 */
	public SetPlyrs(int num, ActionListener listener) {
		this.num = num;
		this.setLayout(new GridLayout(3, 1));
		this.setBounds(250, 150, 300, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);		//This frame is a pop-up. Closing this without proper course would disrupt the program
		this.setResizable(false);								//Just doesn't look good
		
		plyrNames = new JPanel();								//Initialize the plyrNames panel
		plyrNames.setLayout(new GridLayout(num, 2));			//Will hold one label and one field for each player
		
		start = new JButton("Start");							//Must not be start game, because GameWindow is the Listener
		start.addActionListener(listener);						//GameWindow will facilitate communication between classes
		enterPlyr = new JLabel("<html><h1>Enter Player Names</h1></html>");
																//Use html for header code
		plyrOne = new JLabel("Player One");						//Set all labels to the proper text
		plyrTwo = new JLabel("Player Two");
		plyrThree = new JLabel("Player Three");
		plyrFour = new JLabel("Player Four");
		
		plyrOneName = new JTextField();							//Initialize all text fields with blank text
		plyrTwoName = new JTextField();
		plyrThreeName = new JTextField();
		plyrFourName = new JTextField();

		plyrNames.add(plyrOne);
		plyrNames.add(plyrOneName);
		plyrNames.add(plyrTwo);
		plyrNames.add(plyrTwoName);
		
		if (num > 3) {
			plyrNames.add(plyrThree);
			plyrNames.add(plyrThreeName);
		}
		if (num > 2) {
			plyrNames.add(plyrFour);
			plyrNames.add(plyrFourName);
		}
		
		this.add(enterPlyr);									//Add header to frame
		this.add(plyrNames);									//Add labels and fields to frame
		this.add(start);										//Add start button to frame
	}
	
	/**
	 * Used when the game is being started, and the player names must be retrieved 
	 * @return	sends back the names inputed into the frame
	 */
	public String[] getNames() {
		names = new String[num];				//Creates a string array with one element for each player
		switch (num) {									//Assigns the inputed names to the proper number of elements in the array
			case 4:
				names[3] = plyrFourName.getText();
			case 3:
				names[2] = plyrThreeName.getText();
			case 2:
				names[1] = plyrTwoName.getText();
				names[0] = plyrOneName.getText();
		}
		for (int i = 0; i < num; i++) {
			if (names[i].isBlank())
				names[i] = "Player " + (i+1);
		}
		
		return names;									//Sends the names array back to the GameWindow
	}
}
