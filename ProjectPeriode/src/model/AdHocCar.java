package model;


import java.util.Random;
import java.awt.*;

/**
 * Dit is een subklasse van car. 
 * @author Marc Elzinga
 *
 */
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }
    
    /**
     * Een get methode voor de kleur van het hok van de AdHocCar.
     */
    public Color getColor(){
    	return COLOR;
    }
}
