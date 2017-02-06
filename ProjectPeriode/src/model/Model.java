package model;


import java.util.Random;
import view.SimulatorView;

/**
 * In model word alles over de queues etc. in de simulator weergeven.
 * @author Marc Elzinga
 *
 */

public class Model implements Runnable {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String ABO = "3";
	
	public boolean run=false;
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue entranceAboQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    int weekDayAboArrivals= 75; // average number of arriving cars per hour
    int weekendAboArrivals = 10; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    /**
     * Dit is de constructor van Model.
     */
    public Model() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceAboQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);
    }
    

/**
 * voor 10000 ticks gaat de applicatie runnen.
 */
    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
        }
    }

    /**
     * Deze methode laat de tijd tikken. de Cars die moeten vertrekken vertrekken en die arriveren worden toegevoegt.
     * Deze krijgen een positie in de parkeergarage. Dit word geupdate in de carParkView.
     */
    public void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    /**
     * De tijd word bepaalt.
     */
    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    /**
     * De ingangen worden toegevoegt en Cars arriveren.
     */
    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    	carsEntering(entranceAboQueue);
    }
    
    /**
     * Cars die klaar zijn om te vertrekken word bepaalt of ze moeten betalen. Zo ja dan betalen ze en verlaten
     * zij de simulatorview. Als ze betaalt hebben worden ze toegevoegt aan de exitCarQueue.
     */
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    /**
     * De tickmethode in SimulatorView word aangeroepen om de positie van alle Cars te veranderen. Dit word
     * in de carParkView geupdatet.
     */
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
    }
    
    /**
     * Laat Cars arriveren. Voor elk type Car zijn er vershillende aantallen Cars die arriveren.
     */
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, ABO);
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			simulatorView.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = simulatorView.getFirstFreeLocation();
            Location freeAboLocation = simulatorView.getFirstAboFreeLocation();
            if(car instanceof AboCar){
            simulatorView.setCarAt(freeAboLocation, car);
            } else{
            simulatorView.setCarAt(freeLocation, car);
            }
            i++;
        }
    }
    
    /**
     * Neem de eerste Car die de simulator gaat verlaten en laat deze eventueel betalen door hem in de 
     * paymentCarQueue te zetten. Als die al heeft betaalt dan verlaat deze zijn plaats.
     */
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    /**
     * Laat de Cars in de paymentCarQueue betalen.
     */
    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    /**
     * Laat Cars uit de exitCarQueue vertrekken.
     */
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    /**
     * Genereerd een willekeurig aantal auto's die arriveren. Hierbij word rekening gehouden of het een weekday
     * of in het weekend is.
     * @param weekDay
     * @param weekend
     * @return numberOfCarsPerHour
     */
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    /**
     * Haalt auto's uit hun entranceCarQueue's en voegt ze toe aan de parkeerSimulator.
     * @param numberOfCars
     * @param type
     */
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;
    	case ABO:
            for (int i = 0; i < numberOfCars; i++) {
            	entranceAboQueue.addCar(new AboCar());
            }
            break;
    	}
    }
    
    /**
     * Haalt een Car uit de simulatorView en voegt deze toe aan de exitCarQueue
     * @param car
     */
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

}
