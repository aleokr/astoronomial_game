package newton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Draw extends JPanel {
	private static final long serialVersionUID = 1L;
	boolean instruction=true;
	BufferedImage image;
	Rocket rocket;
	double skala = 1;
	double xsun = 450;
	double ysun = 300;
	boolean language=true;
	int p = 0;//kierunek zwrotu obrazka rakiety
	int l=6;//do określenia numeru obrazka rakiety (wybór w optionFrame)
	ArrayList<AstronomicalObject> objects = new ArrayList<AstronomicalObject>();
	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	String[] names = { "sun", "mercury", "venus", "earth", "mars","moon","jupiter","neptune", "saturn", "uranus", "rocket1_1","rocket1_2","rocket1_3","rocket1_4"};
	String points = "star";
	String instrString = "instruction";
	// String[] names = { "sun", "mercury", "venus", "earth", "mars", "jupiter",
	// "neptune", "saturn", "uranus",option.getS() };
	// String[] names = { "sun", "mercury", "venus", "earth", "mars", "jupiter",
	// "neptune", "saturn", "uranus","rocket1_1", "rocket1_2", "rocket1_3",
	// "rocket1_4" };

	int count_star = 100;
	Random r_x = new Random();
	Random r_y = new Random();
	ArrayList<Double> position_s_x = new ArrayList<Double>();
	ArrayList<Double> position_s_y = new ArrayList<Double>();

	public Draw(boolean language) {
		super();
		this.language=language;
		this.setVisible(true);
		set_points();

		addImages();
		// nazwa,x,y,vx,vy,r,m,a,e
		addAstroObject("Slonce", xsun, ysun, 0, 0, 50,20000000, 1, 1);
		addAstroObject("Merkury", xsun - 100, ysun, 0, 1.1, (int) (10*skala), 3.3, 1, 0.2056);
		addAstroObject("Wenus", xsun - 160, ysun, 0, 1, (int) (10 * skala * 2), 47, 2, 0.006773);
		addAstroObject("Ziemie", xsun - 310, ysun, 0, 0.5, (int) (10 * skala * 2), 59, 2.5, 0.01671);
		addAstroObject("Mars", xsun - 410, ysun, 0, 0.5, (int) (5 * skala * 2), 6.4, 3, 0.934);
		addAstroObject("Ksiezyc", xsun - 310, ysun + 6, 0.05, 0.5, (int) (10 * skala), 0.01, 2.5, 0.0484);
		addAstroObject("Jowisz", xsun - 700, ysun, 0, 0.5, (int) (110 * skala), 18990, 1000*skala, 0.0484);
		addAstroObject("Neptun",xsun-1800,ysun, 0,0.3, (int)(40*skala),1024, 3500*skala,0.0086); 
		addAstroObject("Saturn",xsun-1100,ysun,0,0.5, (int)(90*skala), 5685, 1900*skala,0.0542);
		addAstroObject("Uran",xsun-1400,ysun, 0,0.4, (int)(40*skala), 868, 2500*skala,0.0471); 
		//nazwa,x,y,vx,vy,r,m,f,fc,fv,cx,cy
		addRocket("Rakieta", xsun + 100, ysun, 0, 1.5, 10, 0.1, 1, 0.00001, 100, 0, 10);
	}

	public void set_points() {
		int p_x=0;
		int p_y=0;
		int density=0;//PARAMETR UTWORZONY, ABY WIECEJ GWIAZDEK GENEROWAŁO SIĘ W CENTRUM
		double pi=3.14159265;
		int fi,r;//UKŁAD BIEGUNOWY
		for (int i = 0; i < count_star; i++) {
			density=r_x.nextInt(10);
			if(density>3) {
				fi=r_x.nextInt(360);
				r=r_y.nextInt(900);
				p_x = (int)(r*Math.sin(fi*2*pi/360)+xsun);// ranodomowe położenie x punktu
				p_y = (int)(r*Math.cos(fi*2*pi/360)+ysun);// ranodomowe położenie y punktu
			}
			else {
				fi=r_x.nextInt(360);
				r=r_y.nextInt(4000);
				p_x = (int)(r*Math.sin(fi*2*pi/360)+xsun);// ranodomowe położenie x punktu
				r=r_y.nextInt(3000);
				p_y = (int)(r*Math.cos(fi*2*pi/360)+ysun);// ranodomowe położenie y punktu
			}
			position_s_x.add((double)p_x);
			position_s_y.add((double)p_y);
		}
	}

	public void addImages() {
		for (int i = 0; i < names.length; i++) {
			String name_file = names[i] + ".png";
			URL sciezka = getClass().getResource(name_file);
			try {
				image = ImageIO.read(sciezka);
				images.add(image);
			} catch (IOException e) {
				System.err.println("Blad odczytu grafiki dla obiektu" + names[i]);
				e.printStackTrace();
			}
		}
		//DODAWANIE GRAFIKI GWIAZKI
		String name_file = points + ".png";
		URL sciezka = getClass().getResource(name_file);
		try {

			image = ImageIO.read(sciezka);
			images.add(image);
		} catch (IOException e) {
			System.err.println("Blad odczytu grafiki dla obiektu" + points);
			e.printStackTrace();
		}
		//DODAWANIE GRAFIKI STARTOWEJ
		if(language==true)name_file = instrString + "PL.png";
		else name_file = instrString + ".png";
		sciezka = getClass().getResource(name_file);
		try {

			image = ImageIO.read(sciezka);
			images.add(image);
		} catch (IOException e) {
			System.err.println("Blad odczytu grafiki dla obiektu" + points);
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		if(instruction==true)
		{
			g.drawImage(images.get(images.size() - 1), 0, 0, null);
		}
		else {
			double x = 0;
			double y = 0;
			g.setColor(Color.black);
			g.fillRect(0, 0, 900, 700);
			for (int i = 0; i < count_star; i++) {
				x = xsun+(Math.sqrt(Math.pow((xsun-position_s_x.get(i)), 2)+Math.pow((ysun-position_s_y.get(i)),2)))*skala*(xsun-position_s_x.get(i))/Math.sqrt(Math.pow(xsun-position_s_x.get(i), 2)+Math.pow(ysun-position_s_y.get(i), 2));
				y = ysun+(Math.sqrt(Math.pow((xsun-position_s_x.get(i)), 2)+Math.pow((ysun-position_s_y.get(i)),2)))*skala*(ysun-position_s_y.get(i))/Math.sqrt(Math.pow(xsun-position_s_x.get(i), 2)+Math.pow(ysun-position_s_y.get(i), 2));
				g.drawImage(images.get(images.size() - 2), (int)(x), (int)(y),(int) (10*skala),(int)(10*skala), null);
			}
			
			for (int i = 0; i < images.size() - 6; i++) {
				double r = objects.get(i).get_r();
				x = xsun+(Math.sqrt(Math.pow((xsun-objects.get(i).getX()), 2)+Math.pow((ysun-objects.get(i).getY()),2)))*skala*(xsun-objects.get(i).getX())/Math.sqrt(Math.pow(xsun-objects.get(i).getX(), 2)+Math.pow(ysun-objects.get(i).getY(), 2));
				y = ysun+(Math.sqrt(Math.pow((xsun-objects.get(i).getX()), 2)+Math.pow((ysun-objects.get(i).getY()),2)))*skala*(ysun-objects.get(i).getY())/Math.sqrt(Math.pow(xsun-objects.get(i).getX(), 2)+Math.pow(ysun-objects.get(i).getY(), 2));
				g.drawImage(images.get(i), (int)x-(int)objects.get(i).get_r(), (int)y-(int)objects.get(i).get_r(), (int)(r*2), (int)(r*2), null);
			}
			x = xsun+(Math.sqrt(Math.pow((xsun-rocket.getX()), 2)+Math.pow((ysun-rocket.getY()),2)))*skala*(xsun-rocket.getX())/Math.sqrt(Math.pow(xsun-rocket.getX(), 2)+Math.pow(ysun-rocket.getY(), 2));
			y = ysun+(Math.sqrt(Math.pow((xsun-rocket.getX()), 2)+Math.pow((ysun-rocket.getY()),2)))*skala*(ysun-rocket.getY())/Math.sqrt(Math.pow(xsun-rocket.getX(), 2)+Math.pow(ysun-rocket.getY(), 2));
			g.drawImage(images.get(images.size()-l+p), (int)x, (int)y, rocket.get_r(),rocket.get_r(), null);
			
		}
	}
	
	public void setScale(double scale) {
		this.skala = scale;
	}

	public double getScale() {
		return skala;
	}
	public boolean getInstruction() {
		return instruction;
	}
	public void setInstruction(boolean instruction) {
		this.instruction=instruction;
	}

	public void addAstroObject(String name, double x, double y, double Vx, double Vy, int r, double m, double a,
			double e) {
		AstronomicalObject o = new AstronomicalObject();
		o.set_name(name);
		o.set_vx(Vx);
		o.set_vy(Vy);
		o.setX(x);
		o.setY(y);
		o.set_r(r);
		o.set_m(m);
		o.set_a(a);
		o.set_e(e);
		objects.add(o);

	}

	public void addRocket(String name, double x, double y, double Vx, double Vy, int r, double m, double fuel,
			double fuel_consump, double fuel_velo, double course_x, double course_y) {
		rocket = new Rocket();
		rocket.set_name(name);
		rocket.set_vx(Vx);
		rocket.set_vy(Vy);
		rocket.setX(x);
		rocket.setY(y);
		rocket.set_r(r);
		rocket.set_m(m);
		rocket.set_fuel(fuel);
		rocket.set_consup(fuel_consump);
		rocket.set_fuelVelo(fuel_velo);
		rocket.set_course_x(course_x);
		rocket.set_course_y(course_y);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paint(g);

	}

}