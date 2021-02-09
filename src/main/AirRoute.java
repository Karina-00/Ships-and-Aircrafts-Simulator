package main;

import javafx.util.Pair;
import main.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class AirRoute extends Route {
    private double length;
    private Pair<Airport, Airport> pair;
    private boolean isOneLane;
//    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    private final ArrayList<Airport> airportOrderList = new ArrayList<>();

    public AirRoute(Airport a1, Airport a2) {
        this.isOneLane = Math.random() < 0.1;
        this.length = 0;
        if(Math.min(a1.getId(), a2.getId()) == a1.getId()){
            this.pair = new Pair<Airport,Airport>(a1 , a2);
        } else{
            this.pair = new Pair<Airport,Airport>(a2 , a1);
        }
    }

    public AirRoute(Airport a1){
        Airport a2 = a1.getConnectedAirports().getRandomElement();
        this.airportOrderList.add(a1);
        this.airportOrderList.add(a2);
        this.pair = new Pair<Airport,Airport>(a1 , a2);
    }

    public ArrayList<Airport> getAirportsList() {
        return airportOrderList;
    }

    public Airport getElement(int index){
        if(index == 0){
            return pair.getKey();
        } else{
            return pair.getValue();
        }
    }
}

