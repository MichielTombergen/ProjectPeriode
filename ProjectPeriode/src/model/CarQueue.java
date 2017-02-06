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

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue(){
    	return queue.size();
    }
    
    public void queueSize(){
    	System.out.println(queue.size());
    }
}
