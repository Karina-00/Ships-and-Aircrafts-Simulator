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


    public void createPassengerPlane() {
        int id = map.getPlanesIdGenerator();
        Airport startingAirport =  map.getCivilianAirports().getRandomElement();
        Point startingPosition = startingAirport.getCenter();
        int maxCapacity = (int) passengerPlaneCapacity.getValue();
        int personnelsCount = (int) passengerPlanePersonnelCount.getValue();
        int passengersCount = (int) passengerPlanePassengersCount.getValue();

        AirRoute airRoute = new AirRoute(startingAirport); // Do poprawienia!

        PassengerPlane newPlane = new PassengerPlane(startingPosition, id, maxCapacity, personnelsCount, passengersCount, airRoute);
        map.getPassengerPlanes().addElement(newPlane);
    }

    public void createMilitaryAircraft() {
        int id = map.getPlanesIdGenerator();
        int personnelCount = (int) militaryPlanePersonnelCount.getValue();

        AirRoute airRoute;
        Point startingPosition;
        String armament = null;
        boolean noAircraftCarriersOnMap = map.getAircraftCarriers().getElements().isEmpty();

        if(noAircraftCarriersOnMap){
            Airport startingAirport = map.getMilitaryAirports().getRandomElement();
            startingPosition = startingAirport.getCenter();
            airRoute = new AirRoute(startingAirport); // Do poprawienia!!!! NIC NIE GENERUJE!
        } else{
            AircraftCarrier aircraftCarrier = map.getAircraftCarriers().getRandomElement();
            startingPosition = aircraftCarrier.getCurrentPosition();
            armament = aircraftCarrier.getArmament();
            airRoute = new AirRoute(map.getClosestAirport(startingPosition, true)); // Do poprawienia! j.w.
        }

        MilitaryAircraft newPlane = new MilitaryAircraft(startingPosition, id, armament, personnelCount, airRoute);
        map.getMilitaryAircrafts().addElement(newPlane);
    }

    public void createCruiseShip() {
        int id = map.getShipsIdGenerator();
        double maxVelocity = cruiseShipVelocity.getValue();
        ShipStop startingShipStop = map.getShipStops().getRandomElement();
        Point currentPosition = startingShipStop.getPosition();
        ShipStop destination = startingShipStop.getNeighbours().getRandomElement();

        String brand = cruiseShipBrand.getText();
        int passengersCount = (int) cruiseShipPassengersCount.getValue();
        int maxCapacity = (int) cruiseShipCapacity.getValue();

        CruiseShip ship = new CruiseShip(id, maxVelocity, currentPosition, destination, brand, passengersCount, maxCapacity);
        map.getCruiseShips().addElement(ship);
    }

    public void createAircraftCarrier() {
        int id = map.getShipsIdGenerator();
        double maxVelocity = aircraftCarrierVelocity.getValue();

        ShipStop startingShipStop = map.getShipStops().getRandomElement();
        Point currentPosition = startingShipStop.getPosition();
        ShipStop destination = startingShipStop.getNeighbours().getRandomElement();
        String armament = aircraftCarrierArmament.getText();

        AircraftCarrier ship = new AircraftCarrier(id, maxVelocity, currentPosition, destination, armament);
        map.getAircraftCarriers().addElement(ship);
    }
}
