package run;

import model.Model;

public class ProjectPeriode{

	public static Model model;
	public static boolean Paused = true;
	
	/**
	 * Maakt een new Model aan. Zonder deze methode kan de Simulator niet runnen.
	 */
	public static void main(String[] args) {
		model = new Model();
	}
	
	public static Thread t = new Thread(new Runnable() {
        public void run() {
            while(Paused != false){
                try{
                    model.run();
                    Thread.sleep(100);
                } catch (InterruptedException iex) {
                    System.out.println("Exception in thread: "+iex.getMessage());
                }
            }
            
            System.out.println("End of Main Thread...");
            
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
	
	/**
	 * 
	 */
	public static void pause() {
	
		System.out.println("Wow de simulatie staat niet op pauze");
		
	}
    
}

