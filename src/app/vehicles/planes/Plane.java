package app.vehicles.planes;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import app.routes.AirRoute;
import app.routes.Airport;
import app.baseClasses.Point;
import app.map.Map;
import app.vehicles.Vehicle;

import java.io.IOException;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

/**
 *  Represents a plane.
 */
public class Plane extends Vehicle {
    private int personnelCount;
    private double fuelLevel = 100.0;
    private SimpleDoubleProperty fuelObservable;
    private AirRoute route;
    private SimpleObjectProperty<Airport> destinationObservable;
    private Airport destination;
    private int currentAirportIndex = 1;
    private boolean gettingBack = false;
    private boolean isCrashLanding = false;

    public Plane(Point currentPosition, int id, int personnelCount, AirRoute route) {
        super(currentPosition, id);
        this.personnelCount = personnelCount;
        this.route = route;
        this.destination = route.getAirportsList().get(currentAirportIndex);
        this.destinationObservable = new SimpleObjectProperty<Airport>(destination);
        this.fuelObservable = new SimpleDoubleProperty(fuelLevel);
        this.setTarget(destination.getCenter());
    }

    /**
     *  Sets new plane destination according to the route (called when the plane reach temporary destination).
     */
    private void chooseNewDestination() {
        if(isCrashLanding){
            this.stopRunning();
            return;
        }
        if(!gettingBack){
            currentAirportIndex++;
        } else{
            currentAirportIndex--;
        }

        if(currentAirportIndex >= route.getAirportsList().size()){
            gettingBack = true;
            currentAirportIndex -= 2;
            fuelPlane();
        } else if(currentAirportIndex < 0){
            gettingBack = false;
            currentAirportIndex += 2;
            fuelPlane();
        }

        this.destination = route.getAirportsList().get(currentAirportIndex);
        setDestinationObservable(destination);
        this.setTarget(destination.getCenter());

    }

    public void move() {
        Point position = this.getCurrentPosition();
        Point target = destination.getCenter();

        double deltaX = position.getDeltaX(target);
        double deltaY = position.getDeltaY(target);
        double direction = Math.atan2(deltaY, deltaX);

        double newX = position.getX() + (this.getSpeed() * Math.cos(direction));
        double newY = position.getY() + (this.getSpeed() * Math.sin(direction));

        this.updatePosition(newX, newY);

        if (this.getCurrentPosition().calculateDistance(target) < 1) {
            chooseNewDestination();
        }
        updateFuel();
    }

    /**
     *  Updates the value binding to the destination.
     */
    private void setDestinationObservable(Airport destination){
        Platform.runLater(()->{
            this.destinationObservable.set(destination);
        });
    }

    /**
     *  Updates the value binding to the fuel level.
     */
    private void setFuelObservable(double value){
        Platform.runLater(()->{
            String formatted = String.format("%1.2f", value);
            this.fuelObservable.set(Double.parseDouble(formatted));
        });
    }

    /**
     *  Fuels the plane.
     */
    private void fuelPlane(){
        fuelLevel = 100.0;
        setFuelObservable(fuelLevel);
    }

    /**
     *  Updates the fuel level.
     */
    private void updateFuel() {
        fuelLevel = Math.max(fuelLevel - this.getSpeed()/this.route.getLength()*100, 0);
        setFuelObservable(fuelLevel);
    }

    /**
     *  @return Returns value binding to the destination.
     */
    protected SimpleObjectProperty<Airport> getDestinationObservable(){
        return destinationObservable;
    }

    /**
     *  Performs crash landing.
     */
    public void crashLanding(){
        this.destination = Map.getInstance().getClosestAirport(this.getCurrentPosition(), null);
        setDestinationObservable(destination);
        isCrashLanding = true;
    }

    protected Label armamentLabel(){
        return new Label("Passenger Plane");
    }

    /**
     *  @return Returns labels needed for the vehicle information panel.
     */
    protected Label[] getLabels(Label x, Label y, Label destination){
        Label fuelLabel = new Label();
        Label crashLanding = new Label("CRASH LANDING");
        crashLanding.setStyle("-fx-border-color: black; -fx-padding: 5");
        crashLanding.addEventFilter(MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                crashLanding();
            }
        });

        if(this.fuelObservable != null){
            fuelLabel.textProperty().bind(fuelObservable.asString());
        }else {
            fuelLabel.textProperty().unbind();
        }

        return new Label[]{
                new Label("Plane ID: " + this.getId()),
                armamentLabel(),
                new Label("Coordinate X:"),
                x,
                new Label("Coordinate Y:" ),
                y,
                new Label("Current destination point:"),
                destination,
                new Label("Route:\n" + route),
                new Label("PersonnelCount:"),
                new Label(String.valueOf(personnelCount)),
                new Label("Fuel level:"),
                fuelLabel,
                crashLanding,
        };
    }
}
