package main.vehicles;

import main.Point;

public class Vehicle {
    private Point currentPosition;
    private final int id;

    public Vehicle(Point currentPosition, int id) {
        this.currentPosition = currentPosition;
        this.id = id;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getId() {
        return id;
    }
}
