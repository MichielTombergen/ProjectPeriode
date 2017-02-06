package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import model.Car;
import model.Location;

/**
 * In CarParkView worden de geparkeerde auto's in simulatorview weergegeven.
 * @author Marc Elzinga
 *
 */
public class CarParkView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension size;
    private Image carParkImage;   
    private SimulatorView simulatorView;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorView simulatorView) {
        size = new Dimension(0, 0);
        this.simulatorView = simulatorView;
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Deze methode geeft voor elke keer dat het is aangeroepen elke locatie bij langs en geeft het vakje de kleur 
     * die het volgens het programma moet hebben.
     */
    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < simulatorView.getNumberOfFloors(); floor++) {
            for(int row = 0; row < simulatorView.getNumberOfRows(); row++) {
                for(int place = 0; place < simulatorView.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = simulatorView.getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
}

