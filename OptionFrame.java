package newton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OptionFrame extends JFrame {
	Draw draw;
	String s = "rocket1_1";
	BufferedImage[] rocket = new BufferedImage[3];
	BufferedImage image;

	private static final long serialVersionUID = 1L;

	public OptionFrame(MainFrame main) {

		mainFrame = main;

		rankingPanel = new JPanel();
		upPanel = new JPanel();
		centerPanel = new JPanel();

		rankingPanel.setLayout(new FlowLayout());
		upPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new GridLayout(3, 1));

		label_roc_1 = new JLabel("rakieta 1");
		label_roc_2 = new JLabel("rakieta 2");
		label_roc_3 = new JLabel("rakieta 3");
		label_roc_1.setBounds(400, 100, 100, 50);
		label_roc_2.setBounds(400, 300, 100, 50);
		label_roc_3.setBounds(400, 500, 100, 50);

		rocket1 = new JCheckBox("rakieta 1");
		rocket2 = new JCheckBox("rakieta 2");
		rocket3 = new JCheckBox("rakieta 3");

		group = new ButtonGroup();
		group.add(rocket1);
		group.add(rocket2);
		group.add(rocket3);

		rocket1.addActionListener(l);
		rocket2.addActionListener(l);
		rocket3.addActionListener(l);

		exit = new JButton("Powrót");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		if (mainFrame.n == "pl") {
			exit.setText("Powrót");

		}
		if (mainFrame.n == "en") {
			exit.setText("Back");

		}
		this.setSize(300, 300);

		java.net.URL im = getClass().getResource("background.jpg"); // path to image
		ImageIcon imageIcon = new ImageIcon(im); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale
																												// it
																												// the
																												// smooth
																												// way
		imageIcon = new ImageIcon(newimg); // transform it back
		setContentPane(new JLabel(imageIcon));

		upPanel.add(exit);

		rankingPanel.add(rocket1);
		rankingPanel.add(rocket2);
		rankingPanel.add(rocket3);

		centerPanel.add(label_roc_1);
		centerPanel.add(label_roc_2);
		centerPanel.add(label_roc_3);

		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(rankingPanel, BorderLayout.SOUTH);
		this.add(upPanel, BorderLayout.NORTH);

		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	ActionListener l = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object source = e.getSource();
			if (source == rocket1) {
				// draw.p=0;
			}
			if (source == rocket2) {
				// draw.p=4;

			}
			if (source == rocket3) {
				// draw.p=5;

			}

		}
	};

	/*public void addImages() {
		for (int i = 0; i < 3; i++) {
			String name_file = "rocket" + "i" + "_1" + ".png";
			URL sciezka = getClass().getResource(name_file);
			try {

				image = ImageIO.read(sciezka);
				rocket[i] = image;
			} catch (IOException e) {
				System.err.println("Blad odczytu grafiki dla rakiety" + i);
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {

		for (int i = 1; i < 3; i++) {

			g.drawImage(rocket[i], 300, 100 + i * 100, 50, 50, null);
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		paint(g);

	}*/

	JPanel centerPanel;
	JPanel upPanel;
	JPanel rankingPanel;
	JLabel label_roc_1;
	JLabel label_roc_2;
	JLabel label_roc_3;
	JCheckBox rocket1;
	JCheckBox rocket2;
	JCheckBox rocket3;
	JButton exit;
	ButtonGroup group;
	MainFrame mainFrame;

}
