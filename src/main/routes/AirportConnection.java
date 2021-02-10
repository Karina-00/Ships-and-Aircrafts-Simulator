package main.routes;

import main.vehicles.planes.Plane;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Represents a connection between two Airports (an edge in a graph).
 */
public class AirportConnection {
    private Airport source;
    private Airport destination;
    private final boolean isOneLane;
    private double length;
    private final ArrayList<Plane> planes = new ArrayList<Plane>();

    public AirportConnection(Airport a1, Airport a2) {
        this.source = a1;
        this.destination = a2;
        Random random = new Random();
        this.isOneLane = random.nextFloat() < 0.1;
        this.length = a1.getCenter().calculateDistance(a2.getCenter());
    }

    /**
     * @return Returns the length of the edge.
     */
    public double getLength() {
        return length;
    }

    /**
     * @return Returns the source of the edge.
     */
    public Airport getSource() {
        return source;
    }

    /**
     * @return Returns the destination of the edge.
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     *  Determines whether a new vehicle is allowed to run over the edge.
     */
    public boolean canEnterRoute() {
        return (isOneLane & planes.isEmpty()) || !isOneLane;
    }

    /**
     *  Adds a plane to the edge.
     */
    public void addPlane(Plane plane){
        this.planes.add(plane);
    }

    /**
     *  Removes a plane from the edge.
     */
    public void removePlane(Plane plane){
        this.planes.remove(plane);
    }
}
