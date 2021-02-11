package app.vehicles.ships;

import javafx.scene.paint.Color;
import app.baseClasses.Point;
import app.routes.ShipStop;


/**
 *  Represents a Cruise Ship.
 */
public class CruiseShip extends Ship {
    private int passengersCount;
    private int maxCapacity;
    private String brand;


    public CruiseShip(int id, double speed, Point currentPosition, ShipStop startingShipStop, ShipStop destination,
                      String brand, int passengersCount, int maxCapacity) {
        super(id, speed, currentPosition, startingShipStop, destination);
        this.brand = brand;
        this.passengersCount = passengersCount;
        this.maxCapacity = maxCapacity;
        this.setObjectColor(Color.ORANGE);
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
}
