package main.controlPanel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import main.AirRoute;
import main.Airport;
import main.Point;
import main.ShipStop;
import main.map.Map;
import main.vehicles.*;

import java.io.IOException;


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

    private void formValidation() {
        BooleanBinding validateAircraftCarrierCreator = Bindings.createBooleanBinding(() ->
                !aircraftCarrierArmament.getText().isEmpty(), aircraftCarrierArmament.textProperty());

        BooleanBinding validateCruiseShipCreator = Bindings.createBooleanBinding(() ->
                !cruiseShipBrand.getText().isEmpty(), cruiseShipBrand.textProperty());

        this.createAircraftCarrierBtn.disableProperty().bind(validateAircraftCarrierCreator.not());
        this.createCruiseShipBtn.disableProperty().bind(validateCruiseShipCreator.not());
    }


    public void createPassengerPlane() throws IOException {
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

    public void createMilitaryAircraft() throws IOException {
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
        map.getMilitaryAircrafts().addElement(plane);

        addObjectToMap(plane);
    }

    public void createCruiseShip() throws IOException {
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

    public void createAircraftCarrier() throws IOException {
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
