package main.vehicles;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Point;
import main.ShipStop;
import main.map.Map;

import java.io.IOException;


public class Ship extends Vehicle {
    private ShipStop destination;
    private SimpleObjectProperty<ShipStop> destinationObservable;
    private ShipStop previousShipStop;

    public Ship(int id, double speed, Point currentPosition, ShipStop startingShipStop, ShipStop destination) throws IOException {
        super(currentPosition, id);
        this.setSpeed(speed / 10);
        this.destination = destination;
        destinationObservable =  new SimpleObjectProperty<ShipStop>(destination);
        this.setTarget(destination.getCenter());
        this.previousShipStop = startingShipStop;
        getCircle().setStroke(Color.BLUE);
    }

    @Override
    protected VBox vehicleViewPanelContent() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Ship");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Label x = new Label();
        Label y = new Label();
        Label destinationLabel = new Label();
        if(this.getCurrentPosition().aProperty() != null){
            x.textProperty().bind(this.getCurrentPosition().aProperty().asString());
            y.textProperty().bind(this.getCurrentPosition().bProperty().asString());
            destinationLabel.textProperty().bind(this.destinationObservable.asString());
        }else {
            x.textProperty().unbind();
            y.textProperty().unbind();
        }
//
        Label parameters[] = new Label[]{
                new Label("Ship ID: " + this.getId()),
                new Label("Position X: " ),
                x,
                new Label("Position Y: " ),
                y,
                new Label("Destination: "),
                destinationLabel,
        };

        for (int i=0; i<7; i++) {
            VBox.setMargin(parameters[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(parameters[i]);
        }
        return vbox;
    }


    @Override
    public void run() {

        while (!Map.getInstance().getCruiseShips().getElements().isEmpty() ||
                !Map.getInstance().getAircraftCarriers().getElements().isEmpty()) {
            try {
                System.out.println("running" + getId());
                move();
                draw();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public ShipStop getDestination() {
        return destination;
    }

    public void setDestination(ShipStop destination) {
        this.destination = destination;
    }

    public ShipStop getPreviousShipStop() {
        return previousShipStop;
    }

    public void setPreviousShipStop(ShipStop previousShipStop) {
        this.previousShipStop = previousShipStop;
    }

    private synchronized void chooseNewDestination() {
        ShipStop currentDestination = this.destination;
        this.destination = currentDestination.getRandomElementExcept(previousShipStop.getId());
        Platform.runLater(()->{
            this.destinationObservable.set(destination);
        });
        this.setTarget(destination.getCenter());
        this.setPreviousShipStop(currentDestination);
    }

    public synchronized void move() {
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
}
