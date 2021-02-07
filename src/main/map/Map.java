package main.map;

import main.*;
import main.vehicles.*;

import java.util.ArrayList;

public final class Map{

    private static final Map instance = new Map();

    private Storage<Airport> militaryAirports = new Storage<Airport>();
    private Storage<Airport> civilianAirports = new Storage<Airport>();

    private Storage<ShipStop> shipStops = new Storage<ShipStop>();

    private Storage<AirRoute> flightRoutes = new Storage<AirRoute>();

    private Storage<PassengerPlane> passengerPlanes = new Storage<PassengerPlane>();
    private Storage<MilitaryAircraft> militaryAircrafts = new Storage<MilitaryAircraft>();

    private Storage<AircraftCarrier> aircraftCarriers = new Storage<AircraftCarrier>();
    private Storage<CruiseShip> cruiseShips = new Storage<CruiseShip>();

    private int planesIdGenerator = 1;
    private int shipsIdGenerator = 1;

    private static MapController mapController;

    private Map() {
        System.out.println("Map created");
    }

    public static Map getInstance() {
        return instance;
    }


    public int getPlanesIdGenerator() {
        return planesIdGenerator++;
    }

    public int getShipsIdGenerator() {
        return shipsIdGenerator++;
    }


    public Storage<Airport> getMilitaryAirports() {
        return militaryAirports;
    }

    public Storage<Airport> getCivilianAirports() {
        return civilianAirports;
    }

    public Storage<ShipStop> getShipStops() {
        return shipStops;
    }

    public Storage<AirRoute> getFlightRoutes() {
        return flightRoutes;
    }

    public Storage<PassengerPlane> getPassengerPlanes() {
        return passengerPlanes;
    }

    public Storage<MilitaryAircraft> getMilitaryAircrafts() {
        return militaryAircrafts;
    }

    public Storage<AircraftCarrier> getAircraftCarriers() {
        return aircraftCarriers;
    }

    public Storage<CruiseShip> getCruiseShips() {
        return cruiseShips;
    }

    public static MapController getMapController() {
        return mapController;
    }

    public static void setMapController(MapController mC){
        mapController = mC;
    }

    public Airport getClosestAirport(Point p, boolean military){
        double minDistance = Double.MAX_VALUE;
        Airport closestAirport = null;
        ArrayList<Airport> airportsList;

        if (military){
            airportsList = this.militaryAirports.getElements();
        } else{
            airportsList = this.civilianAirports.getElements();
        }

        for (Airport airport: airportsList) {
            double distance = airport.getPosition().calculateDistance(p);
            if (distance < minDistance){
                minDistance = distance;
                closestAirport = airport;
            }
        }
        return closestAirport;
    }

    public void reset(){
        this.cruiseShips = new Storage<>();
        this.passengerPlanes = new Storage<>();
        this.aircraftCarriers = new Storage<>();
        this.militaryAircrafts = new Storage<>();
    }
}
