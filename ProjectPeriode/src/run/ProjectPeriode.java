
package run;

import model.Model;

public class ProjectPeriode {

	public static Model model;
	
	public static void main(String[] args) {
		model = new Model();
	}
	
	public static void go() {
		model.run();
	}
	
	public static void step() {
		model.tick();
	}
	
	public static void hstep(){
		for(int t = 0; t < 100; t++){
			model.tick();
		}
	}
}

