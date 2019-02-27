package newton;

public class CollectPoints extends Thread {

	Draw draw;
	static int score = 0;

	public CollectPoints(Draw draw) {
		this.draw = draw;

	}

	public void run() {

		try {
			for (int i = 0; i < draw.position_s_x.size(); i++) {
				if (draw.rocket.getX() < draw.position_s_x.get(i) + draw.rocket.get_r()
						&& draw.rocket.getX() > draw.position_s_x.get(i) - draw.rocket.get_r()
						&& draw.rocket.getY() < draw.position_s_y.get(i) + draw.rocket.get_r()
						&& draw.rocket.getY() > draw.position_s_y.get(i) - draw.rocket.get_r()) {

					draw.position_s_x.set(i, (double)5960);
					draw.position_s_y.set(i, (double)5960);
					score++;

				}

				sleep(100);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

}
