package newton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RankingFrame extends JFrame {
	
	
	
	private static final long serialVersionUID = 1L;
	RankingFrame(MainFrame main){
		
		super("Ranking");
		mainFrame=main;
		this.setSize(400,400);
		JPanel panel= new JPanel();
		
		exit=new JButton("Powrót");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		if(mainFrame.n=="pl")
		{
			exit.setText("Powrót");
			
		}
		if(mainFrame.n=="en")
		{
			exit.setText("Back");
			
		}
		java.net.URL im = getClass().getResource("background.jpg");//path to image
		ImageIcon imageIcon = new ImageIcon(im); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(this.getWidth(), this.getHeight(),java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		setContentPane(new JLabel(imageIcon));
		this.setLayout( new BorderLayout());
		this.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout());
		panel.add(exit);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
JButton exit;
JPanel panel;
MainFrame mainFrame;
}