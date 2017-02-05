package controller;

import model.*;

public abstract class AbstractController{
	protected Model model;
	
	public AbstractController(Model model) {
		this.model = model;
	}
}
