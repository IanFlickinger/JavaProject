package guiBased;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StartMenu extends JPanel implements ActionListener {
	private JTextArea output;	//To output the instructions of how to play the game
	private JTextArea setPlyrs;
	
	private JPanel plyrPnl;						//Will hold btnPnl and choosePlyrs
	private JPanel btnPnl;						//Will hold the buttons in a grid layout
	
	private JRadioButton twoPlyrBtn;			//These buttons will
	private JRadioButton threePlyrBtn;			//be used to select the
	private JRadioButton fourPlyrBtn;			//number of players
	
	private ButtonGroup btns;					//To ensure only one amount of players is chosen
	
	private int numOfPlayers;
	/**
	 * Create the panel.
	 */
	public StartMenu() {
		this.setBounds(100, 100, 500, 300);
		this.setLayout(new BorderLayout());
		
		output = new JTextArea();				//Create output panel
		output.setBounds(5, 30, 490, 260);		//Will take up whole StartMenu
		this.formatOutput(output);				//Format using method
		output.setEditable(false);				//Will be used solely for output
		output.setVisible(true);				//Will not initially be used
		
		setPlyrs = new JTextArea();				//Create choosePlyrs panel
		this.formatOutput(setPlyrs);			//Format using method
		setPlyrs.setEditable(false);			//Will be used to take input of names eventually, but not yet
		
		btnPnl = new JPanel(new GridLayout(3, 1, 10, 10));	//Holds three buttons
		this.buildBtnPnl();
		
		plyrPnl = new JPanel(new GridLayout(1, 2, 10, 10));	//Holds two panels
		plyrPnl.add(btnPnl);
		plyrPnl.add(setPlyrs);
		
		this.add(plyrPnl);
	}
	
	/**
	 * Format the outputs. 
	 * TODO: Format the output to look nice
	 */
	private void formatOutput(JTextArea output) {
		output.setBackground(new Color(240, 240, 240));
		output.setBorder(new LineBorder(Color.BLACK));
	}
	
	
	/**
	 * Build the btnPnl
	 * TODO: Format to look nice
	 */
	private void buildBtnPnl() {
		twoPlyrBtn = new JRadioButton("Two Players");		//Build radio buttons and add listeners
		twoPlyrBtn.addActionListener(this);
		threePlyrBtn = new JRadioButton("Three Players");
		threePlyrBtn.addActionListener(this);
		fourPlyrBtn = new JRadioButton("Four Players");
		fourPlyrBtn.addActionListener(this);				
		
		btns = new ButtonGroup();							//Add buttons to group to ensure only one button is selected at a time
		btns.add(twoPlyrBtn);
		btns.add(threePlyrBtn);
		btns.add(fourPlyrBtn);
		
		btnPnl.add(twoPlyrBtn);								//Add buttons to panel
		btnPnl.add(threePlyrBtn);
		btnPnl.add(fourPlyrBtn);
	}
	
	/**
	 * Change display to instructional view
	 * TODO: Write instructions in "HowToPlay.txt"
	 */
	public void howToPlay() {
		this.removeAll();								//Clear current view
		output.setText(null);							//Clear output
		this.add(output);								//Add output area to screen
		try {
			BufferedReader reader = new BufferedReader(new FileReader("HowToPlay.txt"));	//Open instructions with a reader
			String nextLine = reader.readLine();		//Create a string that holds the first line
			while (nextLine != null) {					//Loop the following code until the string no longer holds information
				output.append(nextLine + "\n");				//Add the line of instructions to the output area
				nextLine = reader.readLine();				//Read the next line into the string
			}
			reader.close();								//Close the reader to avoid memory leaks
		} catch (FileNotFoundException ex) {			//Runs if the file is not found
			output.append("File Not Found: \n");			
			output.append(ex.getLocalizedMessage());
		} catch (IOException ex) {						//Runs if there is an issue reading the file
			output.append("Issue Reading: \n");				
			output.append(ex.getLocalizedMessage());
		}
	}
	
	/**
	 * Set Players
	 */
	public String[] setPlyrs() {
		String[] names = new String[this.numOfPlayers];
		setPlyrs.setText(null);
		setPlyrs.setEditable(false);
		for (int i = 0; i < this.numOfPlayers; i++) {
			setPlyrs.append("Player " + i + ", what is your name? \n");
			/**TODO: Take input from setPlyrs**/	
			setPlyrs.append("\n");
		}
		
		return names;
	}
	
	/**
	 * Change display back to original screen
	 */
	public void plyrScreen () {
		this.removeAll();						//Clear current display
		this.add(plyrPnl);						//Add the plyrPnl back to the display
	}
	
	/**
	 * Add label to plyrScreen to inform the user to choose a number of players
	 */
	public void noPlyrs() {
		setPlyrs.setEditable(false);
		setPlyrs.setText(null);
		setPlyrs.append("Please Choose a Number of Players\n");
	}
	
	public void actionPerformed(ActionEvent e) {
		String btn = e.getActionCommand();
		if (btn == "Two Players") {
			numOfPlayers = 2;
		} else if (btn == "Three Players") {
			numOfPlayers = 3;
		} else if (btn == "Four Players") {
			numOfPlayers = 4;
		}
	}
	
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public void setNumOfPlayers(int num) {
		numOfPlayers = num;
	}
}