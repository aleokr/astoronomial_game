package newton;

import java.awt.image.BufferedImage;

public class AstronomicalObject  {
	protected double posX;
	protected double posY;
	protected double velocityX;
	protected double velocityY;
	protected Integer radius;
	protected double forceX;
	protected double forceY;
	protected double energy;
	protected double energy0;
	protected boolean firstLoop=true;

	//-----------------------------------
	protected double e;//mimośród
	protected double a;//wielka półoś
	protected double fi=0;
	//-----------------------------------
	protected double mass;
	private String name;//nazwa obrazka musi być taka sama jak nazwa obiektu
	BufferedImage image;
	GameFrame game;

	public void setX(double posX) {
		this.posX = posX;
	}

	public double getX() {
		return posX;
	}

	public void setY(double posY) {
		this.posY = posY;
	}

	public double getY() {
		return posY;
	}

	public void set_vx(double velocityX) {
		this.velocityX = velocityX;
	}

	public double get_vx() {
		return velocityX;
	}

	public void set_vy(double velocityY) {
		this.velocityY = velocityY;
	}

	
	public double get_vy() {
		return velocityY;
	}
	public void set_r(int radius) {
		this.radius = radius;
	}

	public int get_r() {
		return radius;
	}

	public void set_fx(double forceX) {
		this.forceX = forceX;
	}

	public double get_fx() {
		return forceX;
	}

	public void set_fy(double forceY) {
		this.forceY = forceY;
	}

	public double get_fy() {
		return forceY;
	}

	public void set_m(double mass) {
		this.mass=mass;
	}
	
	public double get_m() {
		return mass;
	}
	
	public void set_fi(double fi) {
		this.fi=fi;
	}
	
	public double get_fi() {
		return fi;
	}
	
	public void set_e(double e) {
		this.e=e;
	}
	
	public double get_e() {
		return e;
	}
	
	public void set_a(double a) {
		this.a=a;
	}
	
	public double get_a() {
		return a;
	}

	public double get_energy() {
		return energy;
	}
	
	public void set_energy(double energy) {
		this.energy=energy;
	}
	
	public double get_energy0() {
		return energy0;
	}
	
	public void set_energy0(double energy0) {
		this.energy0=energy0;
	}
	public boolean get_loop() {
		return firstLoop;
	}
	
	public void set_loop(boolean firstLoop) {
		this.firstLoop=firstLoop;
	}
	//-----------------------------------
	public void set_name(String name) {
		this.name=name;
	}
	
	public String get_name() {
		return name;
	}
}