package newton;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import newton.GameFrame;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	OptionFrame optionf;
	
	public MainFrame() {
		super("Start");
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Language");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.getAccessibleContext().setAccessibleDescription("Choose your language");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem1 = new JMenuItem("Polish",KeyEvent.VK_T);
		menuItem1.getAccessibleContext().setAccessibleDescription("Set language to Polish");
		menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		
		menu.add(menuItem1);
		//-----------------------
		menuItem2 = new JMenuItem("English", KeyEvent.VK_T);
		menuItem2.getAccessibleContext().setAccessibleDescription("Set language to English");
		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		
		menu.add(menuItem2);
		//------------------------
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		this.setSize(640,480);
		setTitle("Newton");
		
		java.net.URL im = getClass().getResource("background3.jpg");//path to image
		ImageIcon imageIcon = new ImageIcon(im); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(this.getWidth(), this.getHeight(),java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		setContentPane(new JLabel(imageIcon));
		
		
		game=new JButton("Nowa gra");
		option = new JButton ("Ustawienia");
		ranking = new JButton("Ranking");
		exit = new JButton ("Wyjscie");
		
		
		
		
		menuItem1.addActionListener(ItemListener);
		menuItem2.addActionListener(ItemListener);
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		game.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new GameFrame(MainFrame.this);
			
				
			}
		});
		option.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				optionf=new OptionFrame(MainFrame.this);
				optionf.setVisible(true);
				
				
			}
		});
		ranking.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new RankingFrame(MainFrame.this);
				
				
			}
		});
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		menuPanel=new JPanel();
		menuPanel.setSize(100,100);
		menuPanel.setLayout(new GridLayout(4,1));
		menuPanel.add(game);
		menuPanel.add(option);
		menuPanel.add(ranking);
		menuPanel.add(exit);
		
		c.ipady = 40;      //make this component tall
		c.ipadx = 60;
		c.gridwidth = 0;
		c.gridx = 1;
		c.gridy = 1;
		this.add(menuPanel, c);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
	}
	
	
	public static void main(String[] args) {
		MainFrame main =new MainFrame();
		main.setVisible(true);

	}
	

	ActionListener ItemListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==menuItem1) {
				game.setText("Nowa gra");
				option.setText("Ustawienia");
				ranking.setText("Ranking");
				exit.setText("Wyjście");
				n="pl";
			
			}
			if(e.getSource()==menuItem2) {
				game.setText("New Game");
				option.setText("Options");
				ranking.setText("Ranking");
				exit.setText("Exit");
				n="en";
			}
			
		}
	};
	
	//---------------------------------
		JMenuBar menuBar;
		public String n="pl";
		JMenu menu, submenu;
		JMenuItem menuItem1, menuItem2;
		//---------------------------------
		JPanel center;
		JPanel menuPanel;
		//------------------
		JButton game;
		JButton option;
		JButton ranking;
		JButton exit;

		
}

