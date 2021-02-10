package main.map;

import main.baseClasses.Point;
import main.baseClasses.Storage;
import main.routes.Airport;
import main.routes.AirportConnection;
import main.routes.ShipStop;
import main.vehicles.*;
import main.vehicles.planes.MilitaryAircraft;
import main.vehicles.planes.PassengerPlane;
import main.vehicles.ships.AircraftCarrier;
import main.vehicles.ships.CruiseShip;

import java.util.ArrayList;

public final class Map{

    private static final Map instance = new Map();

    private Storage<Airport> militaryAirports = new Storage<Airport>();
    private Storage<Airport> civilianAirports = new Storage<Airport>();

    private Storage<ShipStop> shipStops = new Storage<ShipStop>();

    private Storage<AirportConnection> airportConnections = new Storage<AirportConnection>();

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

    public Storage<AirportConnection> getAirportConnections() {
        return airportConnections;
    }


    public Storage<ShipStop> getShipStops() {
        return shipStops;
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

    public Airport getClosestAirport(Point p, Boolean military){
        double minDistance = Double.MAX_VALUE;
        Airport closestAirport = null;
        ArrayList<Airport> airportsList = new ArrayList<>();

        if (military == null){
            airportsList.addAll(this.militaryAirports.getElements());
            airportsList.addAll(this.civilianAirports.getElements());
        } else if (military){
            airportsList.addAll(this.militaryAirports.getElements());
        } else{
            airportsList.addAll(this.civilianAirports.getElements());
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
        resetStorage();
        this.cruiseShips = new Storage<>();
        this.passengerPlanes = new Storage<>();
        this.aircraftCarriers = new Storage<>();
        this.militaryAircrafts = new Storage<>();
    }

    private void resetStorage(){
        for (Vehicle vehicle: cruiseShips.getElements()) {
            vehicle.stopRunning();
        }
        for (Vehicle vehicle: aircraftCarriers.getElements()) {
            vehicle.stopRunning();
        }
        for (Vehicle vehicle: passengerPlanes.getElements()) {
            vehicle.stopRunning();
        }
        for (Vehicle vehicle: militaryAircrafts.getElements()) {
            vehicle.stopRunning();
        }
    }
}
