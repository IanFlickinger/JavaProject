package guiBased;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	private JMenuItem mntmBackToGame;
	
	private JTextArea howToPlay;
	
	private JMenu mnPlayerOne;			//Will be used to display the current players and their options
	private JMenu mnPlayerTwo;
	private JMenu mnPlayerThree;
	private JMenu mnPlayerFour;	
	private JMenu mnGameOptions;
	
	private StartMenu startMenu;		//Will be the gui used to prep the game
	private SetPlyrs plyrFrame;			//Will be used to take the player info
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		startingPane = new JPanel();							//Create starting panel
		startingPane.setBorder(new EmptyBorder(5, 5, 5, 5));		//Set an empty border with gaps of value 5
		startingPane.setLayout(new BorderLayout(10, 10));			//Set layout as a Border Layout with gaps of value 10
		
		buildMenu();											//Build Menu
		
		startMenu = new StartMenu();							//Create startMenu
		
		startingPane.add(menuBar, BorderLayout.NORTH);			//Add menuBar to starting panel
		startingPane.add(startMenu);							//Add startMenu to startingPane
		
		contentPane = new JPanel(new BorderLayout());			//Create contentPane
		contentPane.add(startingPane, BorderLayout.CENTER);		//Add startingPane to contentPane
		
		this.setContentPane(contentPane);						//Set contentPane as the frame's content pane
		
		gamePane = new JPanel();								//Create the gamePane
		gamePane.setBorder(new EmptyBorder(5, 5, 5, 5));		//Set an empty border with gaps of value 5
		gamePane.setLayout(new BorderLayout(0, 5));				//Set layout as a Border Layout a standard vertical gap
		
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
	 * Change the menu when the game begins
	 */
	private JMenuBar gameMenu() {
		JMenuBar gameMenu = new JMenuBar();
		gameMenu.setToolTipText("");
		gameMenu.setBounds(5, 5, 470, 20);
		gameMenu.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(64, 64, 64), new Color(192, 192, 192)));	//Set a beveled border
		gameMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		gameMenu.setBackground(Color.WHITE);
		
		Dimension menuSize = new Dimension(470/(game.getNumOfPlayers() + 1), 15);
		
		Font menuFont = new Font("Segoe UI", Font.BOLD, 10);
		mnPlayerOne = new JMenu(game.getName(0));				//Add player menus to define which player's turn it is, and what they can do
		formatMntm(mnPlayerOne);
		mnPlayerOne.setFont(menuFont);
		mnPlayerOne.setPreferredSize(menuSize);
		gameMenu.add(mnPlayerOne);					//Set player menus as the first items on the menu bar
		
		mnPlayerTwo = new JMenu(game.getName(1));
		formatMntm(mnPlayerTwo);
		mnPlayerTwo.setFont(menuFont);
		mnPlayerTwo.setPreferredSize(menuSize);
		gameMenu.add(mnPlayerTwo);
		
		if (startMenu.getNumOfPlayers() > 2) {
			mnPlayerThree = new JMenu(game.getName(2));
			formatMntm(mnPlayerThree);
			mnPlayerThree.setFont(menuFont);
			mnPlayerThree.setPreferredSize(menuSize);
			gameMenu.add(mnPlayerThree);
		};
		
		if (startMenu.getNumOfPlayers() > 3) {
			mnPlayerFour = new JMenu(game.getName(3));
			formatMntm(mnPlayerFour);
			mnPlayerFour.setFont(menuFont);
			mnPlayerFour.setPreferredSize(menuSize);
			gameMenu.add(mnPlayerFour);
		};
		
		mnGameOptions = new JMenu("Game Options");
		formatMntm(mnGameOptions);
		mnGameOptions.setFont(menuFont);
		mnGameOptions.setPreferredSize(menuSize);
		
		mntmBackToGame = new JMenuItem("Back To Game");
		formatMntm(mntmBackToGame);
		mntmBackToGame.setPreferredSize(menuSize);
		
		mntmQuit.setPreferredSize(menuSize);
		mntmHowToPlay.setPreferredSize(menuSize);
		mnGameOptions.add(mntmHowToPlay);
		mnGameOptions.add(mntmQuit);
		
		gameMenu.add(mnGameOptions);
		return gameMenu;
	}
	
	/**
	 * Build Player Array
	 */
	private void setPlyrs(String[] names) {
		Player[] players = new Player[names.length];	//Create player array length of names array			
		for (int i = 0; i < names.length; i++) {		//Set each index of array to a new player of the corresponding name
			players[i] = new Player(names[i]);
		}
		game.setPlayers(players);						//Set player array in game
	}
	
	private void quit() {
		JFrame quit = new JFrame("Are You Sure?");
		
		int quitWidth = 225;
		int quitHeight = 100;
		quit.setBounds(this.getX() + this.getWidth() - quitWidth, this.getY(), quitWidth, quitHeight);
		JLabel sure = new JLabel("Are You Sure You Want To Quit?");
		
		quit.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel buttons = new JPanel();
		
		ActionListener closer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Yes") {
					quit.dispose();
					dispose();
				} else if (e.getActionCommand() == "No") {
					quit.dispose();
				}
			}
		};
		
		JButton yes = new JButton("Yes");
		yes.addActionListener(closer);
		JButton no = new JButton("No");
		no.addActionListener(closer);
		buttons.add(yes);
		buttons.add(no);
		
		quit.add(sure);
		quit.add(buttons);
		quit.setVisible(true);
		quit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Define actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * Define "Start Game" action
		 */
		if (e.getActionCommand() == "Start Game") {		
			game.setNumOfPlayers(startMenu.getNumOfPlayers());	//Set number of players to the current start menu selection
			
			if (game.getNumOfPlayers() >= 2) {					/**If there is a current selection of players**/
				plyrFrame = new SetPlyrs(startMenu.getNumOfPlayers(), this);
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
		 * Define action of entering player names
		 */
		else if (e.getActionCommand() == "Start") {
			setPlyrs(plyrFrame.getNames());						//Set players using names from input frame
			plyrFrame.dispose(); 								//Close plyrFrame
			
			contentPane.remove(startingPane);					//Remove the starting pane from the frame
			
			menuBar = gameMenu();									//Update the menu bar
			menuBar.revalidate();
			
//			game.start();
			
			gamePane.add(menuBar, BorderLayout.NORTH);								//Add the new menu bar to the game panel
			gamePane.add(game);									//Add the game to the game panel
		
			contentPane.add(gamePane, BorderLayout.CENTER);		//Add the game panel to the frame
			contentPane.revalidate();							//Refresh the content of the frame
			this.revalidate();
		}
		/**
		 * Define "How-To" action
		 */
		else if (e.getActionCommand() == "How To Play") {
			if (game.getNumOfPlayers() < 2) {
				startMenu.setNumOfPlayers(1);						//Set number of players to one to signify the display is not on the player page
				startMenu.howToPlay();								//Change start menu display to instructional
			} else {
				startMenu.howToPlay();
				gamePane.remove(game);
				
				mnGameOptions.remove(mntmHowToPlay);
				mnGameOptions.add(mntmBackToGame, 0);
				
				howToPlay = startMenu.getHowToMenu();
				gamePane.add(howToPlay, BorderLayout.CENTER);
				gamePane.revalidate();
			}
			contentPane.revalidate();								//Refresh frame's content
		} 
		/**
		 * 
		 */
		else if (e.getActionCommand() == "Back To Game") {
			mnGameOptions.remove(mntmBackToGame);
			mnGameOptions.add(mntmHowToPlay, 0);
			
			gamePane.remove(howToPlay);
			gamePane.add(game, BorderLayout.CENTER);
			gamePane.repaint();
			contentPane.revalidate();
		}
		/**
		 * Define "Quit" Action
		 */
		else if (e.getActionCommand() == "Quit") {
			quit();
		}
	}
}