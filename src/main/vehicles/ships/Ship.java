package main.vehicles.ships;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import main.baseClasses.Point;
import main.routes.ShipStop;
import main.vehicles.Vehicle;

import java.io.IOException;


public class Ship extends Vehicle {
    private ShipStop destination;
    private SimpleObjectProperty<ShipStop> destinationObservable;
    private ShipStop previousShipStop;

    public Ship(int id, double speed, Point currentPosition, ShipStop startingShipStop, ShipStop destination) throws IOException {
        super(currentPosition, id);
        this.setSpeed(speed / 10);
        this.destination = destination;
        this.destinationObservable =  new SimpleObjectProperty<ShipStop>(destination);
        this.setTarget(destination.getCenter());
        this.previousShipStop = startingShipStop;
    }

    protected SimpleObjectProperty<ShipStop> getDestinationObservable(){
        return destinationObservable;
    }

    public void setPreviousShipStop(ShipStop previousShipStop) {
        this.previousShipStop = previousShipStop;
    }

    private void chooseNewDestination() {
        ShipStop previousDestination = this.destination;
        this.destination = previousDestination.getRandomElementExcept(previousShipStop.getId());
        Platform.runLater(()->{
            this.destinationObservable.set(destination);
        });
        this.setTarget(destination.getCenter());
        this.setPreviousShipStop(previousDestination);
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

    protected Label[] getLabels(Label x, Label y, Label destination){
        return new Label[]{
                new Label("Ship ID: " + this.getId()),
                new Label("Coordinate X:"),
                x,
                new Label("Coordinate Y:" ),
                y,
                new Label("Destination point:"),
                destination,
        };
    }
}