package controller;

import model.*;

/**
 * De AbstractController klasse doet weinig op dit moment van het project. Hij zegt alleen dat 
 * het een protected sub is van Model en er wordt een AbstractController aangemaakt met het 
 * object model.
 * @author Michiel
 *
 */
public abstract class AbstractController{
	protected Model model;
	
	public AbstractController(Model model) {
		this.model = model;
	}
}
