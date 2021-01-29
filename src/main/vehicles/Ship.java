package main.vehicles;

import main.Point;
import main.ShipStop;

public class Ship {
    private int id;
    private final double maxVelocity;
    private Point currentPosition;
    private ShipStop destination;

    public Ship(int id, double maxVelocity, Point currentPosition, ShipStop destination) {
        this.id = id;
        this.maxVelocity = maxVelocity;
        this.currentPosition = currentPosition;
        this.destination = destination;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ShipStop getDestination() {
        return destination;
    }

    public void setDestination(ShipStop destination) {
        this.destination = destination;
    }


}
