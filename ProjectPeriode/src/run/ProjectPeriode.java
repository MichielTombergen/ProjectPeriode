package run;

import model.Model;

public class ProjectPeriode{

	public static Model model;
	private static Thread threadsim = null;
	
	/**
	 * Maakt een new Model aan. Zonder deze methode kan de Simulator niet runnen.
	 */
	public static void main(String[] args) {
		model = new Model();
	}
	
	/**
	 * Laat de simulator runnen.
	 */
	public static void go() {
		threadsim = new Thread();
		model.run();
		threadsim.start();
	}
	
	/**
	 * Laat de simulator 1 stap doen.
	 */
	public static void step() {
		model.tick();
	}
	
	/**
	 * Laat de simulator 100 stappen doen.
	 */
	public static void hstep(){
		for(int t = 0; t < 100; t++){
			model.tick();
		}
	}

}

