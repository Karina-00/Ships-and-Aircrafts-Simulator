package main.routes;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import main.baseClasses.MapEntity;
import main.baseClasses.Storage;
import main.map.Map;
import main.vehicles.planes.Plane;

import java.util.HashMap;

/**
 * Represents an airport on the map.
 */
public class Airport extends MapEntity {

    private final int id;
    private final int capacity;

    private final Storage<Plane> currentPlanes = new Storage<>();
    private final Storage<Airport> connectedAirports = new Storage<>();
    private final HashMap<Integer, AirportConnection> airportConnections = new HashMap<>();

    private final boolean isMilitary;
    private final int minAirportCapacity = 7;
    private final int maxAirportCapacity = 20;
    private final double airportRadius = 16.5;

    public Airport(int id, double x, double y, ImageView mapObject) {
        super(x, y, mapObject);
        this.id = id;
        this.setRadius(airportRadius);
        this.capacity = minAirportCapacity + (int)(Math.random() * ((maxAirportCapacity - minAirportCapacity) + 1));
        this.isMilitary = Math.random() < 0.4;
        if (isMilitary){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(0.6);
            mapObject.setEffect(colorAdjust);
        }
    }

    public int getId() {
        return id;
    }

    /**
     *  Creates a connection with another airport.
     */
    public void addConnectedAirport(Airport a){
        connectedAirports.getElements().add(a);
        AirportConnection newConnection = new AirportConnection(this, a);
        airportConnections.put(a.getId(), newConnection);
        Map.getInstance().getAirportConnections().addElement(newConnection);
    }

    /**
     *  @return Returns the airport connection with a specific connected airport.
     */
    public AirportConnection getAirportConnection(int id) {
        return airportConnections.get(id);
    }

    public boolean isMilitary() {
        return isMilitary;
    }

    @Override
    public String toString() {
        return "Airport id = " + id;
    }
}
