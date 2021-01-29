package main.vehicles;

import main.*;

public class AircraftCarrier extends Ship {
    private final String armament;

    public AircraftCarrier(int id, double maxVelocity, Point currentPosition, ShipStop destination, String armament) {
        super(id, maxVelocity, currentPosition, destination);
        this.armament = armament;
    }

    public String getArmament() {
        return armament;
    }

    public MilitaryAircraft newAircraft(Point currentPosition, int id, int personnelCount, AirRoute route){
        return new MilitaryAircraft(currentPosition, id, this.armament, personnelCount, route);
    }

    public void draw(){
        System.out.println("Drawing Ship");
    }

}
