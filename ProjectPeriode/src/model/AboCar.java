package model;

import java.util.Random;
import java.awt.*;

public class AboCar extends Car {
	private static final Color COLOR=Color.pink;
	
    public AboCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
