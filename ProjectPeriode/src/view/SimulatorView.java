package view;


import javax.swing.*;
import model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import run.*;

/**
 * In de SimulatorView word de interface van de Simulatie gemaakt.
 * @author Marc Elzinga
 *
 */
public class SimulatorView extends JFrame implements ActionListener{
 
	private static final long serialVersionUID = 1L;
	
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
    private JLabel queuesize1;
    
    private CarQueue entrance;
    
    /**
     * De constructor voor Simulatorview. Hier worden de frames en buttons etc. gemaakt.
     * @param numberOfFloors
     * @param numberOfRows
     * @param numberOfPlaces
     */
    
    public SimulatorView(Model model, int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        carParkView = new CarParkView(this);
        
        
        JPanel queuesize = new JPanel();
        queuesize.setLayout(new GridLayout(1, 0));
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));
        
        entrance = model.getEntranceCarQueue();
        
        		queuesize1 = new JLabel("QueueSize :" + entrance.carsInQueue());
        		queuesize1.setFont(new Font("Serif", Font.BOLD, 20));

        		queuesize1.setHorizontalAlignment( SwingConstants.CENTER );
        		queuesize.add(queuesize1);
        
            	button1 = new JButton("+1 Step");
                button1.setPreferredSize(new Dimension(100, 50));
                button1.addActionListener(this);
                button1.setActionCommand("een");
                buttons.add(button1);
                button2 = new JButton("Start");
                button2.setPreferredSize(new Dimension(100, 50));
                button2.addActionListener(this);
                button2.setActionCommand("ga");
                buttons.add(button2);
                button3 = new JButton("Pause");
                button3.setPreferredSize(new Dimension(100, 50));
                button3.addActionListener(this);
                button3.setActionCommand("pauze");
                buttons.add(button3);
                button4 = new JButton("Stop");
                button4.setPreferredSize(new Dimension(100, 50));
                button4.addActionListener(this);
                button4.setActionCommand("klaar");
                buttons.add(button4);
                button5 = new JButton("+100 Steps");
                button5.setPreferredSize(new Dimension(100, 50));
                button5.addActionListener(this);
                button5.setActionCommand("honderd");
                buttons.add(button5);

                JPanel Pane1 = new JPanel();
                Pane1 .setLayout(new GridLayout(1,0));
                setVisible(true);
        
        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(buttons, BorderLayout.SOUTH);
        contentPane.add(queuesize, BorderLayout.NORTH);
        pack();
        setTitle("ParkingSimulatorBV 2017");
        setVisible(true);

        updateView();
    }

    /**
     * Dit start de carParkView en roept de updateView methode in CarParkView op.
     */
    public void start() {
        carParkView.updateView();
    }
    
    /**
     * Dit roept de updateView op in carParkView 
     */
    public void updateView() {
        carParkView.updateView();
    }
    
    /**
     * @return numberOfFloors
     */
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

	/**
	 * @return numberOfRows
	 */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @return NumberOfPlaces
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * @return getNumberOfOpenSpots
     */
    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    /**
     * Deze methode checkt eerst of de gegeven locatie geldig is. Zo ja returnt hij de Car op die locatie.
     * @param location
     * @return Car
     */
    
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

    /**
     * Deze methode checkt eerst of de gegeven locatie geldig is in de simulator en checkt. Als dit zo is kijkt hij of er een auto op de
     * locatie zit. Zo ja dan maakt hij die null. Daarna maakt hij ook de locatie leeg. Ook telt hij numberOfOpenSpots op. 
     * @param location
     * @return car
     */
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
/**
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
    
    /**
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

/**
 * Vind de eerstvolgende auto die gaat vertrekken. Zorg ervoor dat de locatie vrij word gemaakt als de minutesleft 0 of kleiner is en
 * dat de auto aan het betalen is.
 */
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

    /**
     * Deze methode gaat elke locatie bij langs. Als er een auto staat zorgt hij ervoor dat de tijd met een minuut tikt en dus de 
     * tick methode over de Car methode word aangeroepen en dus minutesleft word afgetrokken.
     */
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
/**
 * Deze methode kijkt of de gegeven locatie een geldige locatie is die groter is dan 0 maar kleiner dan de number of floors/rows/places is.
 */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
		
		if (action.equals("ga")) {
			ProjectPeriode.go();
		}
		
		if (action.equals("klaar")) {
			ProjectPeriode.weg();
		}
		
		if (action.equals("een")) {
			ProjectPeriode.step();
		}
		
		if (action.equals("honderd")) {
			ProjectPeriode.hstep();
		}
		
		if (action.equals("pauze")) {
			pause();
		}
		
	}
	
	
	/**
	 * 
	 */
	public void pause() {
		System.out.println("Button pressed!");
	}
    
}
