package main.vehicles;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.AirRoute;
import main.Airport;
import main.Point;
import main.ShipStop;
import main.map.Map;

import java.io.IOException;

public class Plane extends Vehicle {
    private int personnelCount;
    private float fuelLevel;
    private AirRoute route;
    private SimpleObjectProperty<Airport> destinationObservable;
    private Airport destination;
    private int currentAirportIndex = 1;
    private boolean gettingBack = false;

    public Plane(Point currentPosition, int id, int personnelCount, AirRoute route) throws IOException {
        super(currentPosition, id);
        this.personnelCount = personnelCount;
        this.route = route;
        this.destination = route.getAirportsList().get(currentAirportIndex);
        this.destinationObservable = new SimpleObjectProperty<Airport>(destination);
        this.setTarget(destination.getCenter());

    }

    private void chooseNewDestination() {
        if(gettingBack){
            currentAirportIndex++;
        } else{
            currentAirportIndex--;
        }

        if(currentAirportIndex >= route.getAirportsList().size()){
            gettingBack = true;
            currentAirportIndex -= 2;
        } else if(currentAirportIndex < 0){
            gettingBack = false;
            currentAirportIndex += 2;
        }

        this.destination = route.getAirportsList().get(currentAirportIndex);
        Platform.runLater(()->{
            this.destinationObservable.set(destination);
        });
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
    }

    protected SimpleObjectProperty<Airport> getDestinationObservable(){
        return destinationObservable;
    }

    public int getPersonnelCount() {
        return personnelCount;
    }

    public void setPersonnelCount(int personnelCount) {
        this.personnelCount = personnelCount;
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public AirRoute getRoute() {
        return route;
    }

    public void setRoute(AirRoute route) {
        this.route = route;
    }

    protected Label[] getLabels(Label x, Label y, Label destination){
        return new Label[]{
                new Label("Plane ID: " + this.getId()),
                new Label("Coordinate X:"),
                x,
                new Label("Coordinate Y:" ),
                y,
                new Label("Destination point:"),
                destination,
                new Label("PersonnelCount:"),
                new Label(String.valueOf(personnelCount)),
        };
    }
}
