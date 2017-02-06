package run;

import model.Model;

public class ProjectPeriode{

	public String name;
	public static Model model;
	
	/**
	 * Maakt een new Model aan. Zonder deze methode kan de Simulator niet runnen.
	 */
	public static void main(String[] args) {
		model = new Model();
	}
	
	static Thread t = new Thread(new Runnable() {
        public void run()
        {
             model.run();
        }
});
	
	static Thread hs = new Thread(new Runnable() {
        public void run()
        {
    		for(int t = 0; t < 100; t++){
    			model.tick();
    		}
        }
});
	
	/**
	 * Laat de simulator runnen.
	 */
	public static void go() {
		t.start();
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
		hs.start();
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static void weg() {
		t.stop();
	}
	
}

