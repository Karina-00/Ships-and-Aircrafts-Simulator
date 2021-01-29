package main;

import java.util.ArrayList;

public class AirportConnection {
    private ArrayList<Airport> stops;
    private final boolean isOneLane;

    public AirportConnection(ArrayList<Airport> stops, boolean isOneLane) {
        this.stops = stops;
        this.isOneLane = isOneLane;
    }

    public ArrayList<Airport> getStops() {
        return stops;
    }

    public void addStop(Airport stop) {
        this.stops.add(stop);
    }

    public boolean canEnterRoute() {
        return isOneLane & stops.isEmpty() || !isOneLane;
    }
}
