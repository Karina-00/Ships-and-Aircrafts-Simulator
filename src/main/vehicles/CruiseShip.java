package main.vehicles;

import main.Point;
import main.ShipStop;
import main.vehicles.Ship;

public class CruiseShip extends Ship {
    private int passengersCount;
    private int maxCapacity;
    private String brand;

    public CruiseShip(int id, double maxVelocity, Point currentPosition, ShipStop destination, String brand, int passengersCount, int maxCapacity) {
        super(id, maxVelocity, currentPosition, destination);
        this.brand = brand;
        this.passengersCount = passengersCount;
        this.maxCapacity = maxCapacity;
    }

    public int getPassengersCount() {
        return passengersCount;
    }

    public void setPassengersCount(int passengersCount) {
        this.passengersCount = passengersCount;
    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void draw(){
        System.out.println("Drawing Ship");
    }

}
