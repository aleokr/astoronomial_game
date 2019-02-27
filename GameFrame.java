package newton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import newton.MainFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public GameFrame(MainFrame main) {

		mPanel = main;
		int score=0;
		key();
		//-----------------------PANEL PRAWY----------------------------------
		rightPanel = new JPanel();
		time = new JLabel("CZAS:");
		time_to_end = new JLabel("...");
		fuel = new JLabel("PALIWO");
		level_fuel = new JLabel("...");
		points = new JLabel("PUNKTY DO ZDOBYCIA:");
		//points.setPreferredSize(new Dimension(100, 20));
		score_points = new JLabel(""+score);
		exit = new JButton("Powrót");
		trace=new JButton ("Śledź");
		
		
		score_points.setForeground(Color.white);
		trace.setBackground(Color.WHITE);
		exit.setBackground(Color.WHITE);
		time.setForeground(Color.WHITE);
		fuel.setForeground(Color.WHITE);
		points.setForeground(Color.WHITE);
		//--------------ACTION LISTINER DO PRZYCISKU WYJŚCIE----------------
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		//-------------ACTION LISTINER DO PRZYCISKU SLEDZ-------------------
		trace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(traceLog==true) {
					if (mPanel.n == "pl") {
						trace.setText("Zatrzymaj");
					}
					if (mPanel.n == "en") {
						trace.setText("Stop");
					}
					traceLog=false;
					move.logTrace=true;
				}
				else {
					if (mPanel.n == "pl") {
						trace.setText("Śledź");
					}
					if (mPanel.n == "en") {
						trace.setText("Trace");
					}
					traceLog=true;
					move.logTrace=false;
				}
			}
		});
		//----------------------SUWAK-------------------------------------------------
		slider = new JSlider(JSlider.HORIZONTAL, SLIDER_INIT, SLIDER_MAX, SLIDER_MIN);
		slider.setBackground(Color.WHITE);
		scale = new JLabel("Skala");
		value_scale = new JLabel(String.format("%d", slider.getValue()));

		slider.addChangeListener(new SliderChangeListener());
		scale.setForeground(Color.WHITE);
		value_scale.setForeground(Color.WHITE);
		//------------------------------------------------------------------------------
		//-------------------------ZMIANA JĘZYKA----------------------------------------
		if (mPanel.n == "pl") {
			exit.setText("Powrót");
			time.setText("Czas");
			points.setText("Punkty");
			fuel.setText("Paliwo");
			trace.setText("Śledź");
			language=true;
		}
		if (mPanel.n == "en") {
			exit.setText("Back");
			time.setText("Time");
			points.setText("Points");
			fuel.setText("Fuel");
			scale.setText("Scale");
			trace.setText("Trace");
			language=false;
		}
		//-------------------------RIGHT PANEL DODANIE DO PANELU--------------------------
		rightPanel.setLayout(new FlowLayout());
		rightPanel.add(fuel);
		rightPanel.add(level_fuel);
		rightPanel.add(time);
		rightPanel.add(time_to_end);
		rightPanel.add(points);
		rightPanel.add(score_points);

		rightPanel.setPreferredSize(new Dimension(500, 40));
		rightPanel.setBackground(Color.BLACK);

		rightPanel.add(scale);
		rightPanel.add(slider);
		rightPanel.add(value_scale);
		rightPanel.add(trace);
		rightPanel.add(exit);
		//-------------PANEL CENTRALNY I START SYMULACJI------------------
		move = new Move(this,language);
		move.rozpocznijRuch();
		//---------------------------------------------------------------
		//----------------------PRZESUWANIE MAPY----------------------------
		xSun0=move.draw.objects.get(0).getX();
		ySun0=move.draw.objects.get(0).getY();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();
				double boundaryXmax = move.draw.xsun+(Math.sqrt(Math.pow((move.draw.xsun-4000), 2)+Math.pow((move.draw.ysun-2500),2)))*move.draw.skala*(move.draw.xsun-4000)/Math.sqrt(Math.pow(move.draw.xsun-4000, 2)+Math.pow(move.draw.ysun-2500, 2))-2000/Math.pow(slider.getValue(),2);
				double boundaryYmax = move.draw.ysun+(Math.sqrt(Math.pow((move.draw.xsun-4000), 2)+Math.pow((move.draw.ysun-2500),2)))*move.draw.skala*(move.draw.ysun-2500)/Math.sqrt(Math.pow(move.draw.xsun-4000, 2)+Math.pow(move.draw.ysun-2500, 2))-2000/Math.pow(slider.getValue(),2);
				double boundaryXmin = move.draw.xsun+(Math.sqrt(Math.pow((move.draw.xsun+3000), 2)+Math.pow((move.draw.ysun+2000),2)))*move.draw.skala*(move.draw.xsun+3000)/Math.sqrt(Math.pow(move.draw.xsun+3000, 2)+Math.pow(move.draw.ysun+2000, 2))+2000/Math.pow(slider.getValue(),2);
				double boundaryYmin = move.draw.ysun+(Math.sqrt(Math.pow((move.draw.xsun+3000), 2)+Math.pow((move.draw.ysun+2000),2)))*move.draw.skala*(move.draw.ysun+2000)/Math.sqrt(Math.pow(move.draw.xsun+3000, 2)+Math.pow(move.draw.ysun+2000, 2))+2000/Math.pow(slider.getValue(),2);
				if((oldX-currentX)<0&move.draw.objects.get(0).getX()>(boundaryXmax)||(oldX-currentX)>0&move.draw.objects.get(0).getX()<(boundaryXmin))
				{
					for (int i = 0; i < move.draw.objects.size(); i++) {
						move.draw.objects.get(i).setX(move.draw.objects.get(i).getX()+oldX-currentX);
					}
					for (int i = 0; i < move.draw.count_star; i++) {
						move.draw.position_s_x.set(i,(move.draw.position_s_x.get(i)+oldX-currentX));
					}
					move.draw.rocket.setX(move.draw.rocket.getX()+oldX-currentX);
				}
				if((oldY-currentY)<0&move.draw.objects.get(0).getY()>(boundaryYmax)||(oldY-currentY)>0&move.draw.objects.get(0).getY()<(boundaryYmin))
				{
					for (int i = 0; i < move.draw.objects.size(); i++) {
						move.draw.objects.get(i).setY(move.draw.objects.get(i).getY()+oldY-currentY);
					}
					for (int i = 0; i < move.draw.count_star; i++) {
						move.draw.position_s_y.set(i,(move.draw.position_s_y.get(i)+oldY-currentY));
					}
					move.draw.rocket.setY(move.draw.rocket.getY()+oldY-currentY);
				}
				
				oldX = currentX;
				oldY = currentY;
				//System.out.println("X:" +move.draw.objects.get(0).getX());
				//System.out.println("Y:" +move.draw.objects.get(0).getY());
			}
		});
		//---------------------------------SKLEJANIE WSZYSTKIEGO W CALOSC-RAZEM----------------------------------
		this.setSize(900, 700);
		this.setLayout(new BorderLayout());
		this.add(rightPanel, BorderLayout.NORTH);
		this.add(move.draw, BorderLayout.CENTER);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
//-----------------------------SLUCHACZ SUWAKA--------------------------
	public class SliderChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			String value = String.format("" + slider.getValue());
			value_scale.setText(value);
			double s = move.draw.getScale();// poprzednia skala
			move.draw.setScale(slider.getValue()/10.0);//Ustawianie nowej skali zakres 0,1-1,5
			for (int i = 0; i < move.draw.objects.size(); i++) {
				move.draw.objects.get(i).set_r((int)(move.draw.objects.get(i).get_r()/s * move.draw.getScale()));
				//System.out.println("OBIEKT 1:"+move.draw.objects.get(1).get_r());
				//System.out.println("OBIEKT 2:"+move.draw.objects.get(2).get_r());
			}
			move.draw.rocket.set_r((int)(move.draw.rocket.get_r()/ s * move.draw.getScale()));
		}

	}
//---------------------------SLUCHACZ PRZYCISOW Z KLAWIATURY------------------------
	public void key() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_LEFT) {
					if (i == 1)
						i = 4;
					else
						i--;
					switch (i) {
					case 1: {
						move.draw.rocket.course_x = 0;
						move.draw.rocket.course_y = 10;
						break;

					}
					case 2: {
						move.draw.rocket.course_x = -10;
						move.draw.rocket.course_y = 0;
						break;
					}
					case 3: {
						move.draw.rocket.course_x = 0;
						move.draw.rocket.course_y = -10;
						break;
					}
					case 4: {
						move.draw.rocket.course_x = 10;
						move.draw.rocket.course_y =0;
						break;
					}

					}

					move.draw.p = i - 1;

				}
				if (key == KeyEvent.VK_RIGHT) {
					if (i == 4)
						i = 1;
					else
						i++;
					switch (i) {
					case 1: {
						move.draw.rocket.course_x = 0;
						move.draw.rocket.course_y = 10;
						break;

					}
					case 2: {
						move.draw.rocket.course_x = -10;
						move.draw.rocket.course_y = 0;
						break;
					}
					case 3: {
						move.draw.rocket.course_x = 0;
						move.draw.rocket.course_y = -10;
						break;
					}
					case 4: {
						move.draw.rocket.course_x = 10;
						move.draw.rocket.course_y =0;
						break;
					}

					}

					move.draw.p = i - 1;

				}
				if (key == KeyEvent.VK_UP) {
					move.draw.rocket.set_consup(move.draw.rocket.get_consup() + 0.00001);
					//System.out.println(draw.rocket.get_consup());

				}
				if (key == KeyEvent.VK_DOWN) {
					if (move.draw.rocket.fuel_consumption > 0.00001)
						move.draw.rocket.set_consup(move.draw.rocket.get_consup() - 0.00001);
					else
						move.draw.rocket.set_consup(0.0);
					//System.out.println(draw.rocket.get_consup());
				}

			}
		});

	}
//----------------------------MILION ZMIENNYCH UŻYWANYCH-------------------------------
	static final int SLIDER_MIN = 10;
	static final int SLIDER_MAX = 15;
	static final int SLIDER_INIT = 1;
	int currentX, currentY, oldX, oldY;
	
	double xSun0, ySun0;
	int boundary;
	JLabel scale;
	JSlider slider;
	JLabel value_scale;
	MainFrame mPanel;
	JPanel rightPanel;
	JPanel center;
	Draw centerPanel;
	JLabel time;
	JLabel points;
	JLabel fuel;
	JLabel level_fuel;
	JLabel time_to_end;
	JLabel score_points;
	JButton exit;
	JButton trace;
	boolean traceLog=true;
	Rocket r;
	Move move;
	boolean language=true;
	int i = 1;// numer grafiki


}