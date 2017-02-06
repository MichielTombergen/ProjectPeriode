package model;


import java.util.Random;
import java.awt.*;

/**
 * Dit is een subklasse van Car.
 * @author Marc Elzinga
 *
 */
public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;
	
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    /**
     * Een get methode voor de kleur van het hok van de ParkingPassCar.
     */
    public Color getColor(){
    	return COLOR;
    }
    
}

