package newton;

import java.util.ArrayList;

public class Simulation implements Runnable {
	
	ArrayList<AstronomicalObject> objects = new ArrayList<AstronomicalObject>();
	ArrayList<Double> position_s_x=new ArrayList<Double>();
	ArrayList<Double> position_s_y=new ArrayList<Double>();
	Rocket rocket = new Rocket();
	boolean logObject = true;
	boolean logTrace=false;
	static double totalEnergy0=0;
	static double xSun0=0.0;
	static double ySun0=0.0;
	double scale;

	public Simulation(ArrayList<AstronomicalObject> objects,double scale) {
		this.objects = objects;
		this.logObject = true;// jeśli wartość prawdziwa wykonuje dla obiektu astronomicznego
		this.scale=scale;
	}

	public Simulation(Rocket rocket, ArrayList<AstronomicalObject> objects,boolean logTrace,double scale,ArrayList<Double> position_s_x,ArrayList<Double> position_s_y) {
		this.rocket = rocket;
		this.objects = objects;
		this.logObject = false;// jeśli wartość prawdziwa wykonuje dla rakiety
		this.logTrace=logTrace;
		this.scale=scale;
		this.position_s_x=position_s_x;
		this.position_s_y=position_s_y;
	}
	
	public ArrayList<AstronomicalObject> getAstroObject(){
		return this.objects;
	}
	public ArrayList<Double> getPositionX(){
		return this.position_s_x;
	}
	public ArrayList<Double> getPositionY(){
		return this.position_s_y;
	}
	public void run() {
		
		double dt = 0.1;
		double fx = 0;
		double fy = 0;

		double G = 6.67*Math.pow(10, -6);// Trzeba zmienić potem na wartość rzeczywistą 
		double tmp1 = 0;
		double tmp2 = 0;
		double tmp3 = 0;

		double potentialEnergy=0;
		double kineticEnergy=0;
		double totalEnergy=0;
		double epsilon=0.0001;//dokładność ZZE
		//---------------------------
		double Lo=0;
		double r=0;
		if (logObject == true) {
			//NA DALSZE OBIEKTY
			for (int i = objects.size()-4; i < objects.size(); i++) {
				//System.out.println("OBIKET: "+i);
				r=objects.get(i).get_a()*(1-Math.pow(objects.get(i).get_e(), 2))/(1+objects.get(i).get_e()*Math.cos(objects.get(i).get_fi()));
				//System.out.println("X: "+objects.get(i).getX());
				//System.out.println("Y: "+objects.get(i).getY());
				Lo=objects.get(i).get_m()*r*objects.get(i).get_vy();
				//System.out.println("Lo: "+Lo);
				objects.get(i).setX(objects.get(0).getX()-r*Math.cos(objects.get(i).get_fi()));
				//System.out.println("X NEW: "+objects.get(i).getX());
				objects.get(i).setY(objects.get(0).getY()+r*Math.sin(objects.get(i).get_fi()));
				//System.out.println("Y NEW: "+objects.get(i).getY());
				objects.get(i).set_fi(objects.get(i).get_fi()+Lo/(Math.pow(r, 2)*objects.get(i).get_m())/5);
				//System.out.println("FI: "+objects.get(i).get_fi());
			}
			 // LICZENIE SIŁY METODĄ LEAP FROG
			 
			for (int i = 0; i < objects.size(); i++) {
				//----------------------------OBLICZENIA ENERGI KINETYCZNEJ-------------------
				kineticEnergy=(Math.pow(objects.get(i).get_vx(),2)+Math.pow(objects.get(i).get_vy(),2))*objects.get(i).get_m()/2;
				for (int j = 0; j < objects.size(); j++) {
					if (i != j) {
						//----------------------------OBLICZENIA POMOCNICZE-------------------
						tmp1 = Math.pow((objects.get(i).getX() - objects.get(j).getX()), 2);
						tmp2 = Math.pow((objects.get(i).getY()- objects.get(j).getY()), 2);
						tmp3 = Math.sqrt(tmp1 + tmp2);
						tmp1 = Math.pow(tmp3, 3);
						//----------------------------OBLICZENIA SIŁY-------------------
						fx += G * (objects.get(i).get_m()*objects.get(j).get_m()*(-objects.get(i).getX() + objects.get(j).getX()))/ tmp1;
						fy += G * (objects.get(i).get_m()* objects.get(j).get_m()* (-objects.get(i).getY() + objects.get(j).getY()))/ tmp1;
						//---------------------OBLICZENIA ENERGI POTENCJALNEJ-------------------
						potentialEnergy+=(-G*objects.get(i).get_m()*objects.get(j).get_m()/(tmp3));
					}
				}
				objects.get(i).set_fx(fx);
				objects.get(i).set_fy(fy);
				totalEnergy+=potentialEnergy+kineticEnergy;
				objects.get(i).set_energy(potentialEnergy+kineticEnergy);
				fx = 0;
				fy = 0;
				//----------------------------DODANIE E0,xSun0,ySun0----------------------------------
				if (objects.get(i).get_loop()==true) {
					objects.get(i).set_energy0(potentialEnergy+kineticEnergy);
					totalEnergy0+=potentialEnergy+kineticEnergy;
					xSun0=objects.get(0).getX();
					ySun0=objects.get(0).getY();
					objects.get(i).set_loop(false);
				}
				potentialEnergy=0;
				kineticEnergy=0;
			}

			// LICZENIE X, Y, Vx, Vy
			for (int i = 0; i < objects.size()-4; i++) {
				//----------------------------OKREŚLENIE VX I VY---------------------------
				tmp1=objects.get(i).get_vx() + objects.get(i).get_fx() * dt / (objects.get(i).get_m());
				objects.get(i).set_vx(tmp1);
				tmp1=objects.get(i).get_vy() + objects.get(i).get_fy() * dt / (objects.get(i).get_m());
				objects.get(i).set_vy(tmp1);
				//----------------------------OKREŚLENIE X I Y-----------------------------
				tmp1=(objects.get(i).getX() + objects.get(i).get_vx() * dt + objects.get(i).get_fx() * dt*dt / (objects.get(i).get_m()*2));
				objects.get(i).setX((tmp1));
				tmp1=(objects.get(i).getY() + objects.get(i).get_vy() * dt+ objects.get(i).get_fy() * dt*dt / (objects.get(i).get_m()*2));
				objects.get(i).setY((tmp1));
				//----------------------------POPRAWKA ENERGETYCZNA-----------------------------
				if(Math.abs(totalEnergy0-totalEnergy)>epsilon) {
					//System.out.println("ENERGIA 0: "+totalEnergy0+" ENERGIA POTEM: "+totalEnergy);
				}
			}
			totalEnergy=0;
		} 
		else {
		// -----------------------------LICZENIE SIŁY-----------------------------------------------
			for (int j = 0; j < objects.size()-1; j++) {
				tmp1 = Math.pow((rocket.getX() - objects.get(j).getX()), 2);
				tmp2 = Math.pow((rocket.getY() - objects.get(j).getY()), 2);
				tmp3 = Math.sqrt(tmp1 + tmp2);
				tmp1 = Math.pow(tmp3, 3);
				fx += G * ((rocket.get_m() + rocket.get_fuel()) * objects.get(j).get_m()* (-rocket.getX() + objects.get(j).getX()))/tmp1;
				fy += G * ((rocket.get_m() + rocket.get_fuel()) * objects.get(j).get_m() * (-rocket.getY() + objects.get(j).getY()))/tmp1;
			}
			//----------------------------xSun0,ySun0----------------------------------
			if (rocket.get_loop()==true) {
				xSun0=objects.get(0).getX();
				ySun0=objects.get(0).getY();
				rocket.set_loop(false);
			}
			// --------------------DOLICZENIE SIŁY CIOŁKOWSKIEGO------------------------------------
			if(rocket.get_fuel()>0)
			{
				//System.out.println("ILOŚĆ PALIWA TO:"+rocket.get_fuel());
				tmp1 = Math.pow((rocket.getX() - (rocket.getX()+rocket.get_course_x())), 2);
				tmp2 = Math.pow((rocket.getY() - (rocket.getY()+rocket.get_course_y())), 2);
				tmp3 = Math.sqrt(tmp1 + tmp2);
				fx += (((rocket.get_fuelVelo() * rocket.get_consup()) / (rocket.get_m() - rocket.get_consup() * dt)* (rocket.get_m() + rocket.get_fuel()))*(rocket.getX() - (rocket.getX()+rocket.get_course_x()))/tmp3);
				fy += (((rocket.get_fuelVelo() * rocket.get_consup()) / (rocket.get_m() - rocket.get_consup() * dt)* (rocket.get_m() + rocket.get_fuel()))*(rocket.getY() - (rocket.getY()+rocket.get_course_y()))/tmp3);
				rocket.set_fuel(rocket.get_fuel()-rocket.get_consup() * dt);
			}
			else
			{
				System.out.println("KONIEC PALIWA");
			}
			
			rocket.set_fx(fx);
			rocket.set_fy(fy);
			//System.out.println("FX RAKIETY: "+fx);
			//System.out.println("FY RAKIETY: "+fy);
			fx = 0;
			fy = 0;
			// ------------------------------LICZENIE Vx, Vy----------------------------------
			tmp1=rocket.get_vx() + rocket.get_fx() *dt / (rocket.get_m() + rocket.get_fuel());
			rocket.set_vx(tmp1);
			//System.out.println("VX RAKIETY: "+rocket.get_vx());
			tmp1=rocket.get_vy() + rocket.get_fy() *dt / (rocket.get_m() + rocket.get_fuel());
			rocket.set_vy(tmp1);
			//System.out.println("VY RAKIETY: "+rocket.get_vy());
			//----------------------------TRZEBA DOPRAWCOWAĆ GRANICE-------------------------
			if(logTrace==false)
			{
				tmp1=(rocket.getX() + rocket.get_vx()*dt + rocket.get_fx()*dt*dt / (2*(rocket.get_m() + rocket.get_fuel())));
				/*if(tmp1<-xSun0-400*scale)rocket.setX(tmp1+xSun0+400*scale);
				else if(tmp1>xSun0+400*scale)rocket.setX(tmp1-xSun0-400*scale);*/
				rocket.setX(tmp1);
				//System.out.println("X: "+rocket.getX());
				tmp1=(rocket.getY() + rocket.get_vy() *dt+ rocket.get_fy()*dt*dt / (2*(rocket.get_m() + rocket.get_fuel())));
				/*if(tmp1<-ySun0-300*scale)rocket.setY(tmp1+ySun0+300*scale);
				else if(tmp1>ySun0+300*scale)rocket.setY(tmp1-ySun0-300*scale);*/
				rocket.setY(tmp1);
				
				//System.out.println("X:"+rocket.getX());
				//System.out.println("Y:"+rocket.getY());
			}
			//---------------------------JEŻELI RUCH RAKIETY JEST ŚLEDZONY------------------
			if(logTrace==true)
			{
				//sprowadz rakiete do srodka planszy
				tmp1=rocket.getX()-xSun0;
				tmp2=rocket.getY()-ySun0;
				if(Math.abs(tmp1)>1||Math.abs(tmp2)>1) {
					rocket.setX(xSun0);
					rocket.setY(ySun0);
				//przesun wszystkie obiekty
				for (int i = 0; i < objects.size(); i++) {
					objects.get(i).setX((objects.get(i).getX()-tmp1));
					objects.get(i).setY((objects.get(i).getY()-tmp2));
				}
				//przesuwam gwiazdki
				for (int i = 0; i < position_s_x.size(); i++) {
					position_s_x.set(i,(position_s_x.get(i)-tmp1));
					position_s_y.set(i,(position_s_y.get(i)-tmp2));
				}
				}
				
				//przesuwaj planety a nie rakiete
				tmp1=(rocket.get_vx()*dt + rocket.get_fx()*dt*dt / (2*(rocket.get_m() + rocket.get_fuel())));
				tmp2=(rocket.get_vy()*dt+ rocket.get_fy()*dt*dt / (2*(rocket.get_m() + rocket.get_fuel())));
				for (int i = 0; i < objects.size(); i++) {
					objects.get(i).setX(objects.get(i).getX()-tmp1);
					objects.get(i).setY(objects.get(i).getY()-tmp2);
					//System.out.println("X:"+rocket.getX());
					//System.out.println("Y:"+rocket.getY());
				}
				//przesuwam gwiazdki
		
				for (int i = 0; i < position_s_x.size(); i++) {
					position_s_x.set(i,(position_s_x.get(i)-tmp1));
					position_s_y.set(i,(position_s_y.get(i)-tmp2));
				}
			}
		}	
	}
}