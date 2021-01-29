package main.vehicles;

import main.AirRoute;
import main.Point;

public class PassengerPlane extends Plane {
    private int maxCapacity;
    private int passengersCount;

    public PassengerPlane(Point currentPosition, int id, int maxCapacity, int personnelCount, int passengersCount, AirRoute route) { //Dodać aktualną liczbę pasażerów!
        super(currentPosition, id, personnelCount, route);
        this.maxCapacity = maxCapacity;
        this.passengersCount = passengersCount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getPassengersCount() {
        return passengersCount;
    }

    public void setPassengersCount(int passengersCount) {
        this.passengersCount = passengersCount;
    }

    public void draw(){
        System.out.println("Drawing plane");
    }
}
