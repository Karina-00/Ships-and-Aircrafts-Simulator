package main.vehicles;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

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

    public Plane(Point currentPosition, int id, int personnelCount, AirRoute route) throws IOException {
        super(currentPosition, id);
        this.personnelCount = personnelCount;
        this.route = route;
        this.destination = route.getAirportsList().get(currentAirportIndex);
        this.destinationObservable = new SimpleObjectProperty<Airport>(destination);
        this.fuelObservable = new SimpleDoubleProperty(fuelLevel);
        this.setTarget(destination.getCenter());

    }

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

    private void setDestinationObservable(Airport destination){
        Platform.runLater(()->{
            this.destinationObservable.set(destination);
        });
    }

    private void setFuelObservable(double value){
        Platform.runLater(()->{
            String formatted = String.format("%1.2f", value);
            this.fuelObservable.set(Double.parseDouble(formatted));
        });
    }

    private void fuelPlane(){
        fuelLevel = 100.0;
        setFuelObservable(fuelLevel);
    }

    private void updateFuel() {
        fuelLevel = Math.max(fuelLevel - this.getSpeed()/this.route.getLength()*100, 0);
        setFuelObservable(fuelLevel);
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

    public double getFuelLevel() {
        return fuelLevel;
    }

    public AirRoute getRoute() {
        return route;
    }

    public void setRoute(AirRoute route) {
        this.route = route;
    }

    public SimpleDoubleProperty getFuelObservable() {
        return fuelObservable;
    }

    public void crashLanding(){
        this.destination = Map.getInstance().getClosestAirport(this.getCurrentPosition(), null);
        setDestinationObservable(destination);
        isCrashLanding = true;
    }

    protected Label armamentLabel(){
        return new Label("Passenger Plane");
    }

    protected Label[] getLabels(Label x, Label y, Label destination){
        Label fuelLabel = new Label();
        Label crashLanding = new Label("CRASH LANDING");
        crashLanding.addEventFilter(MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                crashLanding();
            }
        });

        if(this.getCurrentPosition().aProperty() != null){
            fuelLabel.textProperty().bind(fuelObservable.asString());
        }else {
            fuelLabel.textProperty().unbind();
        }
        
        Label[] labels = new Label[]{
                new Label("Plane ID: " + this.getId()),
                armamentLabel(),
                new Label("Coordinate X:"),
                x,
                new Label("Coordinate Y:" ),
                y,
                new Label("Destination point:"),
                destination,
                new Label("PersonnelCount:"),
                new Label(String.valueOf(personnelCount)),
                new Label("Fuel level:"),
                fuelLabel,
                crashLanding,
        };
        return labels;
    }
}
