package guiBased;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class GameWindow extends JFrame implements ActionListener {

	private JPanel contentPane;			//Main display of Frame
	private JPanel startingPane;		//Displays startMenu
	private JPanel gamePane;			//Displays game
	
	private JMenuBar menuBar;			//Holds optional actions throughout
	
	private JMenuItem mntmStart;		//Used to start game
	private JMenuItem mntmHowToPlay;	//Used to display instructions
	private JMenuItem mntmQuit;			//Used to quit
	private JMenuItem mntmPlayer;		//Will be used to display the current player
	
	private StartMenu startMenu;		//Will be the gui used to prep the game
	private Game game;					//Will be the game's gui 
	
	private LineBorder mntmBorder;		//Will be the border for each menu item
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public GameWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		startingPane = new JPanel();							//Create starting panel
		startingPane.setBorder(new EmptyBorder(5, 5, 5, 5));		//Set an empty border with gaps of value 5
		startingPane.setLayout(new BorderLayout(10, 10));			//Set layout as a Border Layout with gaps of value 10
		
		buildMenu();											//Build Menu
		
		startMenu = new StartMenu();							//Create startMenu
		
		startingPane.add(menuBar, BorderLayout.NORTH);			//Add menuBar to startingPane
		startingPane.add(startMenu);							//Add startMenu to startingPane
		
		contentPane = new JPanel(new BorderLayout());			//Create contentPane
		contentPane.add(startingPane, BorderLayout.CENTER);		//Add startingPane to contentPane
		
		this.setContentPane(contentPane);						//Set contentPane as the frame's content pane
		
		gamePane = new JPanel();								//Create the gamePane
		gamePane.setBorder(new EmptyBorder(5, 5, 5, 5));			//Set an empty border with gaps of value 5
		gamePane.setLayout(new BorderLayout(0, 0));					//Set layout as a Border Layout with no gaps
		
		game = new Game();										//Create the game itself
	}
	
	/**
	 * Build the Menu Bar
	 */
	private void buildMenu() {
		menuBar = new JMenuBar();									//Create the menuBar
		menuBar.setToolTipText("");									//Set empty text
		menuBar.setBounds(5,5,470,20);								//Set bounds to cover up top of display
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(64, 64, 64), new Color(192, 192, 192)));	//Set a beveled border
		menuBar.setBackground(Color.BLACK);							//Set a black Background (which will not actually be seen, but matches the border just in case)
		
		mntmBorder = new LineBorder(Color.BLACK);					//Create the border for menu items
		
		mntmStart = new JMenuItem("Start Game");					//Create and format the start menu item
		formatMntm(mntmStart);
		
		mntmHowToPlay = new JMenuItem("How To Play");				//Create and format How-To menu item
		formatMntm(mntmHowToPlay);
		
		mntmQuit = new JMenuItem("Quit");							//Create and format Quit menu item
		formatMntm(mntmQuit);
		
		menuBar.add(mntmStart);										//Add menu items to the menu bar
		menuBar.add(mntmHowToPlay);
		menuBar.add(mntmQuit);
	}
	
	/**
	 * Format menu items
	 */
	private void formatMntm(JMenuItem mntm) {
		mntm.setBackground(Color.WHITE);						//Set menu item background
		mntm.setFont(new Font("Segoe UI", Font.PLAIN, 10));		//Set font
		mntm.setBorder(mntmBorder);								//Add the prescribed border
		mntm.addActionListener(this);							//Add the action listener
	}
	
	/**
	 * Update the menu when the game begins
	 */
	private void updateMenu() {
		menuBar.remove(mntmStart);						//Game is already started. Start is no longer needed
		menuBar.remove(mntmHowToPlay);					/**Will probably need to put back in**/
												
		mntmPlayer = new JMenuItem("Player ");			//Add player item to define which player's turn it is
		formatMntm(mntmPlayer);							//Format player item
		menuBar.add(mntmPlayer, 0);						//Set player item as the first item on the menu bar
	}
	
	private void setPlyrs() {
		String[] names = startMenu.setPlyrs();
		Player[] players = new Player[names.length];
		for (int i = 0; i < names.length; i++) {
			players[i] = new Player(names[i]);
		}
	}
	
	/**
	 * Define actions
	 */
	public void actionPerformed(ActionEvent e) {
		/**
		 * Define "Start Game" action
		 */
		if (e.getActionCommand() == "Start Game") {		
			game.setNumOfPlayers(startMenu.getNumOfPlayers());	//Set number of players to the current start menu selection
			
			if (game.getNumOfPlayers() >= 2) {					/**If there is a current selection of players**/
				setPlyrs();
				
				contentPane.remove(startingPane);					//Remove the starting pane from the frame
			
				updateMenu();										//Update the menu bar
				
				gamePane.add(menuBar);								//Add the new menu bar to the game panel
				gamePane.add(game);									//Add the game to the game panel
			
				contentPane.add(gamePane, BorderLayout.CENTER);		//Add the game panel to the frame
				contentPane.revalidate();							//Refresh the content of the frame
				
			} else if (game.getNumOfPlayers() == 0){			/**If there is no selection of players**/ 
				startMenu.noPlyrs();								//Update the startMenu to add a notice 
				contentPane.revalidate();							//Refresh the frame content
				
			} else if (game.getNumOfPlayers() == 1) {			/**If the frame is not currently on the player selection display**/
				startMenu.setNumOfPlayers(0);						//Set player number to have no current selection
				
				startMenu.plyrScreen();								//Change frame to player selection display
				contentPane.repaint();								//Refresh frame's content
				contentPane.revalidate();							//...
			}
		} 
		/**
		 * Define "How-To" action
		 */
		else if (e.getActionCommand() == "How To Play") {
			startMenu.setNumOfPlayers(1);							//Set number of players to one to signify the display is not on the player page
			startMenu.howToPlay();									//Change start menu display to instructional
			contentPane.revalidate();								//Refresh frame's content
		} 
		/**
		 * Define "Quit" Action
		 */
		else if (e.getActionCommand() == "Quit") {
			this.dispose();											//Close frame
		}
	}
}