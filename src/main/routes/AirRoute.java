package main.routes;

import main.baseClasses.Storage;
import main.map.Map;

import java.util.ArrayList;
import java.util.LinkedList;

public class AirRoute {
    private double length;
    private final ArrayList<Airport> airportOrderList = new ArrayList<>();


    public AirRoute(Airport startingAirport){
        generateAirRoute(startingAirport);
        calculateLength();
    }

    private void generateAirRoute(Airport source){
        Map map = Map.getInstance();
        Airport target;
        if(source.isMilitary()){
            target = getTargetAirport(map.getMilitaryAirports(), source);
        } else{
            target = getTargetAirport(map.getCivilianAirports(), source);
        }
        Dijkstra dijkstra = new Dijkstra(map.getAirportConnections().getElements());

        dijkstra.execute(source);
        LinkedList<Airport> path = dijkstra.getPath(target);

        this.airportOrderList.addAll(path);
    }

    private Airport getTargetAirport(Storage<Airport> list, Airport source){
        if(list.getElements().size() < 2){
            return null;
        }
        Airport target;
        do {
            target = list.getRandomElement();
        } while(target.getId() == source.getId());
        return target;
    }

    private void calculateLength(){
        double len = 0;
        for(int i = 0; i < airportOrderList.size()-1; i++){
            Airport current = airportOrderList.get(i);
            Airport next = airportOrderList.get(i+1);
            AirportConnection connection = current.getAirportConnection(next.getId());
            len += connection.getLength();
        }
        this.length = len;
    }

    public double getLength() {
        return length;
    }

    public ArrayList<Airport> getAirportsList() {
        return airportOrderList;
    }

    @Override
    public String toString() {
        return "length=" + Math.round(length) +
                "\n" + airportOrderList.toString();
    }
}

