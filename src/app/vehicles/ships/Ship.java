package app.vehicles.ships;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import app.baseClasses.Point;
import app.routes.ShipStop;
import app.vehicles.Vehicle;


/**
 *  Represents a ship.
 */
public class Ship extends Vehicle {
    private ShipStop destination;
    private SimpleObjectProperty<ShipStop> destinationObservable;
    private ShipStop previousShipStop;

    public Ship(int id, double speed, Point currentPosition, ShipStop startingShipStop, ShipStop destination) {
        super(currentPosition, id);
        this.setSpeed(speed / 10);
        this.destination = destination;
        this.destinationObservable =  new SimpleObjectProperty<>(destination);
        this.setTarget(destination.getCenter());
        this.previousShipStop = startingShipStop;
    }

    /**
     *  @return Returns value binding to the ship's destination.
     */
    protected SimpleObjectProperty<ShipStop> getDestinationObservable(){
        return destinationObservable;
    }

    public void setPreviousShipStop(ShipStop previousShipStop) {
        this.previousShipStop = previousShipStop;
    }

    /**
     *  Sets new ship destination (called when the plane reach temporary destination).
     */
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

    /**
     *  @return Returns labels needed for the vehicle information panel.
     */
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
