package view;


import javax.swing.*;
import model.*;
import java.awt.*;
import controller.*;

public class SimulatorView extends JFrame {
 
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    
    
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView(this);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));

            	button1 = new JButton("+1 Step");
                button1.setPreferredSize(new Dimension(100, 100));
                button1.addActionListener(e -> controller.step());
                buttons.add(button1);
                button2 = new JButton("Start");
                button2.setPreferredSize(new Dimension(100, 100));
                button2.addActionListener(e -> controller.start());
                buttons.add(button2);
                button3 = new JButton("Pause");
                button3.setPreferredSize(new Dimension(100, 100));
                button3.addActionListener(e -> controller.pause());
                buttons.add(button3);
                button4 = new JButton("Stop");
                button4.setPreferredSize(new Dimension(100, 100));
                button4.addActionListener(e -> controller.stop());
                buttons.add(button4);
                button5 = new JButton("+100 Steps");
                button5.setPreferredSize(new Dimension(100, 100));
                button5.addActionListener(e -> controller.hstep());
                buttons.add(button5);

                JPanel Pane1 = new JPanel();
                Pane1 .setLayout(new GridLayout(1,0));
                setVisible(true);
        
        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(buttons, BorderLayout.SOUTH);
        pack();
        setVisible(true);

        updateView();
    }

    public void start() {
        carParkView.updateView();
    }
    
    public void updateView() {
        carParkView.updateView();
    }
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }
/*
 * De eerste vrije locatie word opgezocht. Maar door de if statement kunnen ze niet op floor 0 in rijen 1-3 parkeren en zullen zij die overslaan.
 */
    public Location getFirstFreeLocation() {
        
    	for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    if(floor == 0 && row <=3){
                    	
                    }
                    else{
                	Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                    }
                }
            }
        }
        return null;
    }
    
    /*
     * De eerste vrije locatie word opgeroepen. Het begint bij de eerste plaats op floor 0. Wanneer er niet genoeg plekken voor abbonomentenhouders
     * gereserveerd zijn. Dan zullen abonomentenhouders zich voegen tussen de gewone bezoekers.
     */
    public Location getFirstAboFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            	for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }


    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    
    
}
