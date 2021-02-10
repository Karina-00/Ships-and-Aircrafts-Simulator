package main;

import main.vehicles.Plane;

import java.util.ArrayList;
import java.util.Random;

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

    public double getLength() {
        return length;
    }

    public Airport getSource() {
        return source;
    }

    public Airport getDestination() {
        return destination;
    }
    
    public boolean canEnterRoute() {
        return (isOneLane & planes.isEmpty()) || !isOneLane;
    }

    public void addPlane(Plane plane){
        this.planes.add(plane);
    }

    public void removePlane(Plane plane){
        this.planes.remove(plane);
    }
}
