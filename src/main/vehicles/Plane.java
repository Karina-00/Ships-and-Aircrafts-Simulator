package main.vehicles;

import main.AirRoute;
import main.Airport;
import main.Point;

public class Plane extends Vehicle {
    private int personnelCount;
    private float fuelLevel;
    private AirRoute route;
    private Airport nextAirport;

    public Plane(Point currentPosition, int id, int personnelCount, AirRoute route) {
        super(currentPosition, id);
        this.personnelCount = personnelCount;
        this.route = route;
    }

    public int getPersonnelCount() {
        return personnelCount;
    }

    public void setPersonnelCount(int personnelCount) {
        this.personnelCount = personnelCount;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public AirRoute getRoute() {
        return route;
    }

    public void setRoute(AirRoute route) {
        this.route = route;
    }

    public Airport getNextAirport() {
        return nextAirport;
    }

    public void setNextAirport(Airport nextAirport) {
        this.nextAirport = nextAirport;
    }

    public void move(){
        System.out.println("Flying Plane");
    }
}
