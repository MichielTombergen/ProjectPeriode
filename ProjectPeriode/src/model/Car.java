package model;


import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    /**
     * Return de locatie waar de auto zich bevind.
     * @return de locatie.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Stel de locatie van de auto vast.
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return het aantal minuten dat de auto nog in 
     * de parkeergarage blijft staan.
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Stel vast hoeveel minuten de auto in de
     * parkeergarage blijft staan.
     * @param minutesLeft
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    /**
     * Is de auto aan het betalen?
     * @return true of false
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Stel vast of de auto aan het betalen is.
     * @param isPaying, true of false
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Vraag of de auto moet betalen.
     * @return true of false
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Stel vast dat een auto moet betalen of niet.
     * @param hasToPay, true of false.
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }
    
    /**
     * Er word een minuut afgetrokken van de tijd die over is voor de auto
     * om in de parkeergarage 
     */
    public void tick() {
        minutesLeft--;
    }
    
    /*
     * Deze abstracte getColor() is ervoor dat de getColor via Car kan worden aangeroepen. Wanneer die word aangeroepen word deze niet gebruikt maar juist
     * de getColor() methode in de subklasses aangeroepen.
     */
    public abstract Color getColor();
}