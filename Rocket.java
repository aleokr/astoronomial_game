package newton;


public class Rocket extends AstronomicalObject{
	protected double  fuel;//paliwo
	protected double  fuel_consumption;//zurzycie paliwa kg/s
	protected double  fuel_velocity;//prędkość gazów wylotowych względem rakiety

	protected double course_x=1;
	protected double course_y=1;
	int grafika;
	//kierunki zwrotu rakiety
	
	public void set_fuel(double fuel) {
		this.fuel = fuel;
	}

	public double get_fuel() {
		return fuel;
	}
	
	public void set_consup(double fuel_consumption) {
		this.fuel_consumption = fuel_consumption;
	}

	public double get_consup() {
		return fuel_consumption;
	}
	
	public void set_fuelVelo(double fuel_velocity) {
		this.fuel_velocity = fuel_velocity;
	}

	public double get_fuelVelo() {
		return fuel_velocity;
	}
	public void set_course_x(double course_x2) {
		this.course_x = course_x2;
	}

	public double get_course_x() {
		return course_x;
	}
	public void set_course_y(double course_y) {
		this.course_y = course_y;
	}

	public double get_course_y() {
		return course_y;
	}
	
	
}