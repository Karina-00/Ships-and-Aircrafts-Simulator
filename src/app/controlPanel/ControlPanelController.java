package app.controlPanel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import app.routes.AirRoute;
import app.routes.Airport;
import app.baseClasses.Point;
import app.routes.ShipStop;
import app.map.Map;
import app.vehicles.*;
import app.vehicles.planes.MilitaryAircraft;
import app.vehicles.planes.PassengerPlane;
import app.vehicles.ships.AircraftCarrier;
import app.vehicles.ships.CruiseShip;


/**
 *  Controller for the Control Panel.
 */
public class ControlPanelController {
    @FXML
    private Slider passengerPlanePassengersCount;
    @FXML
    private Slider passengerPlaneCapacity;
    @FXML
    private Slider passengerPlanePersonnelCount;
    @FXML
    private Slider militaryPlanePersonnelCount;
    @FXML
    private Slider cruiseShipPassengersCount;
    @FXML
    private Slider cruiseShipCapacity;
    @FXML
    private TextField cruiseShipBrand;
    @FXML
    private Slider cruiseShipVelocity;
    @FXML
    private Button createCruiseShipBtn;
    @FXML
    private Slider aircraftCarrierVelocity;
    @FXML
    private TextField aircraftCarrierArmament;
    @FXML
    private Button createAircraftCarrierBtn;

    private final Map map = Map.getInstance();

    @FXML
    void initialize() {
        formValidation();
    }

    /**
     *  Validates the form, blocks "Create Vehicle" buttons until all the needed parameters are filled.
     */
    private void formValidation() {
        BooleanBinding validateAircraftCarrierCreator = Bindings.createBooleanBinding(() ->
                !aircraftCarrierArmament.getText().isEmpty(), aircraftCarrierArmament.textProperty());

        BooleanBinding validateCruiseShipCreator = Bindings.createBooleanBinding(() ->
                !cruiseShipBrand.getText().isEmpty(), cruiseShipBrand.textProperty());

        this.createAircraftCarrierBtn.disableProperty().bind(validateAircraftCarrierCreator.not());
        this.createCruiseShipBtn.disableProperty().bind(validateCruiseShipCreator.not());
    }

    /**
     *  Creates Passenger Plane.
     */
    public void createPassengerPlane() {
        int id = map.getPlanesIdGenerator();
        Airport startingAirport =  map.getCivilianAirports().getRandomElement();
        Point airportPosition = startingAirport.getCenter();
        Point startingPosition = new Point(airportPosition.getX(), airportPosition.getY());
        int maxCapacity = (int) passengerPlaneCapacity.getValue();
        int personnelsCount = (int) passengerPlanePersonnelCount.getValue();
        int passengersCount = (int) passengerPlanePassengersCount.getValue();

        AirRoute airRoute = new AirRoute(startingAirport);

        PassengerPlane plane = new PassengerPlane(startingPosition, id, maxCapacity, personnelsCount, passengersCount, airRoute);
        map.getPassengerPlanes().addElement(plane);

        addObjectToMap(plane);
    }

    /**
     *  Creates Military Aircraft.
     */
    public void createMilitaryAircraft() {
        int id = map.getPlanesIdGenerator();
        int personnelCount = (int) militaryPlanePersonnelCount.getValue();

        AirRoute airRoute;
        Point startingPosition;
        String armament = null;
        boolean noAircraftCarriersOnMap = map.getAircraftCarriers().getElements().isEmpty();

        if(noAircraftCarriersOnMap){
            Airport startingAirport = map.getMilitaryAirports().getRandomElement();
            Point airportPosition = startingAirport.getCenter();
            startingPosition = new Point(airportPosition.getX(), airportPosition.getY());
            airRoute = new AirRoute(startingAirport);
        } else{
            AircraftCarrier aircraftCarrier = map.getAircraftCarriers().getRandomElement();
            Point shipPosition = aircraftCarrier.getCurrentPosition();
            startingPosition = new Point(shipPosition.getX(), shipPosition.getY());
            armament = aircraftCarrier.getArmament();
            airRoute = new AirRoute(map.getClosestAirport(startingPosition, true));
        }

        MilitaryAircraft plane = new MilitaryAircraft(startingPosition, id, armament, personnelCount, airRoute);
        map.getMilitaryAircraft().addElement(plane);

        addObjectToMap(plane);
    }

    /**
     *  Creates Cruise Ship.
     */
    public void createCruiseShip() {
        int id = map.getShipsIdGenerator();
        double speed = cruiseShipVelocity.getValue();
        ShipStop startingShipStop = map.getShipStops().getRandomElement();
        Point shipStopPosition = startingShipStop.getCenter();
        Point currentPosition = new Point(shipStopPosition.getX(), shipStopPosition.getY());
        ShipStop destination = startingShipStop.getNeighbours().getRandomElement();

        String brand = cruiseShipBrand.getText();
        int passengersCount = (int) cruiseShipPassengersCount.getValue();
        int maxCapacity = (int) cruiseShipCapacity.getValue();

        CruiseShip ship = new CruiseShip(id, speed, currentPosition, startingShipStop, destination, brand, passengersCount, maxCapacity);
        map.getCruiseShips().addElement(ship);

        addObjectToMap(ship);
    }

    /**
     *  Creates Aircraft Carrier.
     */
    public void createAircraftCarrier() {
        int id = map.getShipsIdGenerator();
        double speed = aircraftCarrierVelocity.getValue();

        ShipStop startingShipStop = map.getShipStops().getRandomElement();
        Point shipStopPosition = startingShipStop.getCenter();
        Point currentPosition = new Point(shipStopPosition.getX(), shipStopPosition.getY());
        ShipStop destination = startingShipStop.getNeighbours().getRandomElement();
        String armament = aircraftCarrierArmament.getText();

        AircraftCarrier ship = new AircraftCarrier(id, speed, currentPosition, startingShipStop, destination, armament);
        map.getAircraftCarriers().addElement(ship);

        addObjectToMap(ship);
    }


    /**
     *  Adds the newly-created object to the map and the object starts running.
     */
    private void addObjectToMap(Vehicle vehicle){
        Map.getMapController().getVehiclesHolder().getChildren().add(vehicle.getCircle());
        try{
            Thread t = new Thread(vehicle);
            t.start();
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }
}
