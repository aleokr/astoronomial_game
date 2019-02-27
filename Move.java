package newton;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Move  {

	Draw draw;
	Simulation symA;//symulacja dla obiektów
	Simulation symR;//symulacja rakiety
	boolean logTrace=false;
	boolean logRocketStart=false;
	boolean language=true;
	GameFrame gameFrame;
	
	void rozpocznijRuch() {
		

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		//instrukcja gry
		scheduler.schedule(new Runnable() {
            @Override
			public void run() { 
            	draw.setInstruction(false);
			}
        },5,TimeUnit.SECONDS );
		scheduler.schedule(new Runnable() {
            @Override
			public void run() { 
            	logRocketStart=true;
			}
        },5,TimeUnit.SECONDS );
		//gra
		scheduler.scheduleAtFixedRate((new Runnable() {
			public void run() {
				new CollectPoints(draw).start();
				symA=new Simulation(draw.objects,draw.skala); 
				symA.run(); 
				symR=new Simulation(draw.rocket, draw.objects,logTrace, draw.skala, draw.position_s_x, draw.position_s_y); 
				draw.position_s_x=symR.getPositionX();
				draw.position_s_y=symR.getPositionY();
				if(logRocketStart==true)symR.run(); 
				draw.repaint(); 
				gameFrame.score_points.setText(""+CollectPoints.score);
			}}),3,2,MILLISECONDS);


	}
	public Move(GameFrame gameFrame,boolean language) {
		this.language=language;
		draw=new Draw(language);
		this.gameFrame = gameFrame;
	}
	

}