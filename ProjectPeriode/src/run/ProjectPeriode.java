
package run;

import model.Model;

public class ProjectPeriode {

	public static Model model;
	
	public static void main(String[] args) {
		model = new Model();
		model.run();
	}
	
	public static void go() {
		model.run();
	}
}

