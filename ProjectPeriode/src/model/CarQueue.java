package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * In de klasse CarQueue word een linkedlist gemaakt en hier worden 
 * Car's aan toegevoegd. En eventueel weer uit gehaald.
 * @author Michiel
 *
 */
public class CarQueue {
    public Queue<Car> queue = new LinkedList<>();

    /**
     * Voegt een Car toe aan de Queue
     * @param car
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Haalt een Car uit de Queue
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Geeft de lengte van de Queue terug.
     * @return queue.size()
     */
    public int carsInQueue(){
    	return queue.size();
    }
}
