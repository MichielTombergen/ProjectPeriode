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
public class Controller implements ActionListener {
	
	public Controller() {

	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Start")) {
			model.start();
		}
		
		if (e.getActionCommand().equals("Stop")) {
			model.stop();
		}
		
	}
}