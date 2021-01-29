package main;

import javafx.util.Pair;
import main.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class AirRoute extends Route {
    private double length;
    private Pair<Airport, Airport> pair;
    private boolean isOneLane;
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    public AirRoute(Airport a1, Airport a2) {
        this.isOneLane = Math.random() < 0.1;
        this.length = 0;
        if(Math.min(a1.getId(), a2.getId()) == a1.getId()){
            this.pair = new Pair<Airport,Airport>(a1 , a2);
        } else{
            this.pair = new Pair<Airport,Airport>(a2 , a1);
        }

    }

    public AirRoute(Airport airport){ // DO POPRAwy
        // zamiast tego w mapie albo lotnisku generować losowy ciąg lotnisk!
    }
}

