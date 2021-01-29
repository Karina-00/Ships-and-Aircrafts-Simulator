package main;

import main.vehicles.Vehicle;

import java.util.ArrayList;

public class Route {
    private ArrayList<Vehicle> vehicles;

    public Route() {
        this.vehicles = new ArrayList<Vehicle>();
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public int getVehiclesCount() {
        return vehicles.size();
    }
}
