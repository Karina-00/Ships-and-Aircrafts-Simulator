package main.vehicles;

import main.AirRoute;
import main.Point;

import java.util.Objects;

public class MilitaryAircraft extends Plane {
    private final String armament;

    public MilitaryAircraft(Point currentPosition, int id, String armament, int personnelCount, AirRoute route) {
        super(currentPosition, id, personnelCount, route);
        this.armament = Objects.requireNonNullElse(armament, "Versatile armament");
    }

    public String getArmament() {
        return armament;
    }

    public void draw(){
        System.out.println("Drawing plane");
    }
}
