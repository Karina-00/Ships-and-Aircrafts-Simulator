package main;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import main.vehicles.Plane;


public class Airport extends MapEntity{

    private final int id;
    private final int capacity;

    private final Storage<Plane> currentPlanes = new Storage<Plane>();
    private final Storage<Airport> connectedAirports = new Storage<Airport>();

    private final boolean isMilitary;
    private final int minAirportCapacity = 7;
    private final int maxAirportCapacity = 20;
    private final double airportRadius = 16.5;

    public Airport(int id, double x, double y, ImageView mapObject) {
        super(x, y, mapObject);
        this.id = id;
        this.setRadius(airportRadius);
        this.capacity = minAirportCapacity + (int)(Math.random() * ((maxAirportCapacity - minAirportCapacity) + 1));
        this.isMilitary = Math.random() < 0.3;
        if (isMilitary){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(0.6);
            mapObject.setEffect(colorAdjust);
        }
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Storage<Airport> getConnectedAirports() {
        return connectedAirports;
    }

    public Storage<Plane> getCurrentPlanes() {
        return currentPlanes;
    }

    public boolean isMilitary() {
        return isMilitary;
    }
}
