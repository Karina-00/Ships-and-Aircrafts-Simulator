package main.map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.baseClasses.MapEntity;
import main.baseClasses.Point;
import main.routes.Airport;
import main.routes.Crossing;
import main.routes.ShipStop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 *  Controller for the map.
 */
public class MapController {

    @FXML
    private AnchorPane vehiclesHolder;
    @FXML
    private AnchorPane mapObjectsHolder;
    private boolean controlPanelOpened = false;
    private final Map map = Map.getInstance();

    @FXML
    void initialize() {
        collectAirports();
        collectCrossings();
        collectShipPoints();
    }

    /**
     *  @return Returns the panel, to which all the vehicles are being added.
     */
    public AnchorPane getVehiclesHolder() {
        return vehiclesHolder;
    }

    /**
     *  Collects all the airports from the map, creates their instances, and adds them to the simulation (map).
     */
    private void collectAirports() {
        ArrayList<Airport> airportsList = new ArrayList<Airport>();
        Set<Node> airportObjects = mapObjectsHolder.lookupAll(".airport");
        int airportId = 0;

        for (Node node : airportObjects) {
            int finalAirportId = airportId;
            airportsList.add(new Airport(airportId++, node.getLayoutX(), node.getLayoutY(), (ImageView) node));
            node.setOnMouseClicked(event -> System.out.println(finalAirportId));
        }
        connectAllAirports(airportsList);

        for (Airport airport : airportsList) {
            if (airport.isMilitary()) {
                Map.getInstance().getMilitaryAirports().addElement(airport);
            } else {
                Map.getInstance().getCivilianAirports().addElement(airport);
            }
        }
    }

    /**
     *  Connects all airports.
     */
    private void connectAllAirports(ArrayList<Airport> airportsList) {
        Airport airport0 = airportsList.get(0);
        Airport airport1 = airportsList.get(1);
        Airport airport2 = airportsList.get(2);
        Airport airport3 = airportsList.get(3);
        Airport airport4 = airportsList.get(4);
        Airport airport5 = airportsList.get(5);
        Airport airport6 = airportsList.get(6);
        Airport airport7 = airportsList.get(7);
        Airport airport8 = airportsList.get(8);
        Airport airport9 = airportsList.get(9);
        Airport airport10 = airportsList.get(10);
        Airport airport11 = airportsList.get(11);

        connectTwoAirports(airport0, airport2);
        connectTwoAirports(airport0, airport4);
        connectTwoAirports(airport1, airport2);
        connectTwoAirports(airport1, airport4);
        connectTwoAirports(airport1, airport8);
        connectTwoAirports(airport3, airport5);
        connectTwoAirports(airport3, airport6);
        connectTwoAirports(airport4, airport5);
        connectTwoAirports(airport5, airport8);
        connectTwoAirports(airport6, airport5);
        connectTwoAirports(airport6, airport7);
        connectTwoAirports(airport7, airport9);
        connectTwoAirports(airport7, airport11);
        connectTwoAirports(airport8, airport10);
        connectTwoAirports(airport9, airport2);
        connectTwoAirports(airport10, airport11);
        connectTwoAirports(airport11, airport3);
    }

    private void connectTwoAirports(Airport a1, Airport a2) {
        a1.addConnectedAirport(a2);
        a2.addConnectedAirport(a1);
        drawLine(a1, a2);
    }

    /**
     *  Draws a line between two map entities.
     */
    private void drawLine(MapEntity a1, MapEntity a2) {
        Point center1 = a1.getCenter();
        Point center2 = a2.getCenter();

        Line line = new Line(center1.getX(), center1.getY(), center2.getX(), center2.getY());
        line.getStyleClass().add("air-route");

        mapObjectsHolder.getChildren().add(line);
    }

    /**
     *  Collects crossings from the map.
     */
    private void collectCrossings() {
        ArrayList<Crossing> crossingList = new ArrayList<Crossing>();
        Set<Node> crossingObjects = mapObjectsHolder.lookupAll(".crossing");
        int crossingId = 0;

        for (Node node : crossingObjects) {
            crossingList.add(new Crossing(crossingId++, node.getLayoutX(), node.getLayoutY(), (Rectangle) node));
        }

        for (Crossing crossing : crossingList) {
            System.out.println("Crossing nr: " + crossing.getId() + " Coordinates: " + crossing.getPosition().toString());
        }
    }

    /**
     *  Collects ship stops from the map.
     */
    public void collectShipPoints() {
        Map map = Map.getInstance();
        ArrayList<ShipStop> shipStops = new ArrayList<ShipStop>();
        Set<Node> pointObjects = mapObjectsHolder.lookupAll(".ship-stop");
        int shipStopId = 0;

        for (Node node : pointObjects) {
            int finalAirportId = shipStopId;
            shipStops.add(new ShipStop(shipStopId++, node.getLayoutX(), node.getLayoutY()));
            node.setOnMouseClicked(event -> System.out.println(finalAirportId));
        }
        connectShipPoints(shipStops);
        map.getShipStops().setElements(shipStops);
    }

    /**
     *  Connects ship stops.
     */
    private void connectShipPoints(ArrayList<ShipStop> shipStops) {
        shipStops.get(0).getNeighbours().setElements(getNeighboursByIndices(shipStops, 2, 5, 6));
        shipStops.get(1).getNeighbours().setElements(getNeighboursByIndices(shipStops, 3, 4));
        shipStops.get(2).getNeighbours().setElements(getNeighboursByIndices(shipStops, 0, 3, 4));
        shipStops.get(3).getNeighbours().setElements(getNeighboursByIndices(shipStops, 1, 2, 8));
        shipStops.get(4).getNeighbours().setElements(getNeighboursByIndices(shipStops, 1, 2));
        shipStops.get(5).getNeighbours().setElements(getNeighboursByIndices(shipStops, 0, 6, 10));
        shipStops.get(6).getNeighbours().setElements(getNeighboursByIndices(shipStops, 0, 5));
        shipStops.get(7).getNeighbours().setElements(getNeighboursByIndices(shipStops, 8, 9, 10));
        shipStops.get(8).getNeighbours().setElements(getNeighboursByIndices(shipStops, 3, 7, 9, 10));
        shipStops.get(9).getNeighbours().setElements(getNeighboursByIndices(shipStops, 7, 8, 10));
        shipStops.get(10).getNeighbours().setElements(getNeighboursByIndices(shipStops, 5, 7, 8, 9));
    }

    private ArrayList<ShipStop> getNeighboursByIndices(ArrayList<ShipStop> shipStops, int... args) {
        if (args.length == 2) {
            return new ArrayList<>(Arrays.asList(shipStops.get(args[0]), shipStops.get(args[1])));
        } else if (args.length == 3) {
            return new ArrayList<>(Arrays.asList(shipStops.get(args[0]),
                    shipStops.get(args[1]), shipStops.get(args[2])));
        } else if (args.length == 4) {
            return new ArrayList<>(Arrays.asList(shipStops.get(args[0]),
                    shipStops.get(args[1]), shipStops.get(args[2]), shipStops.get(args[3])));
        } else {
            return null;
        }
    }

    /**
     *  Opens control panel.
     */
    public void openControlPanel(ActionEvent event) throws IOException {
        if (!controlPanelOpened) {
            controlPanelOpened = true;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../controlPanel/controlPanel.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Control Panel");
            stage.show();
            stage.setX(50);
            stage.setY(100);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    controlPanelOpened = false;
                }
            });
        }
    }

    /**
     *  Calls the map's function to reset the simulation.
     */
    public void resetSimulation() {
        map.reset();
        this.vehiclesHolder.getChildren().clear();
    }

    /**
     *  Deletes specific object (vehicle) from the map.
     */
    public void deleteObject(Circle c){
        Platform.runLater(() -> {
            this.vehiclesHolder.getChildren().remove(c);
        });
    }
}
