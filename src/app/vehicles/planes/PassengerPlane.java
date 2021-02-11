package app.vehicles.planes;

import javafx.scene.paint.Color;
import app.routes.AirRoute;
import app.baseClasses.Point;


/**
 *  Represents a passenger plane.
 */
public class PassengerPlane extends Plane {
    private int maxCapacity;
    private int passengersCount;

    public PassengerPlane(Point currentPosition, int id, int maxCapacity, int personnelCount, int passengersCount, AirRoute route) {
        super(currentPosition, id, personnelCount, route);
        this.maxCapacity = maxCapacity;
        this.passengersCount = passengersCount;
        this.setObjectColor(Color.DODGERBLUE);
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
}
