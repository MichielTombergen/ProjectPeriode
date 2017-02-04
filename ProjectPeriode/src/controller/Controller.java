package controller;

import model.*;
import java.awt.event.*;

/**
 * The controller reacts on input received from the view. 
 * Here implementation is provided for the actionPerformed method
 * that is implemented in the AbstractController.
 * @author MichielTombergen
 * @version 02-02-2017
 */
public class Controller extends AbstractController implements ActionListener{
	
	public Controller(Model model) {
		super(model);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Start")) {
			start();
		}
		
		if (e.getActionCommand().equals("Stop")) {
			stop();
		}
		
		if (e.getActionCommand().equals("+1 Step")) {
			step();
		}
		
		if (e.getActionCommand().equals("+100 Steps")) {
			hstep();
		}
		
	}
	public void start() {
		model.run();
	}
	
	public void stop() {
		model.run=false;
	}
	
	public void pause() {
		model.run=false;
	}
	
	public void step() {
		model.tick();
	}
	
	public void hstep() {
		model.tick();
	}

}