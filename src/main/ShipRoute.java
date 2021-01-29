package main;

import java.util.ArrayList;

public class ShipRoute extends Route{
    private ArrayList<Point> stops;

    public ShipRoute(ArrayList<Point> stops) {
        this.stops = stops;
    }

    public ArrayList<Point> getStops() {
        return stops;
    }

    public void addStop(Point stop) {
        this.stops.add(stop);
    }
}
