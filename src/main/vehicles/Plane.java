package main.vehicles;

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
import main.map.Map;

import java.io.IOException;

public class Plane extends Vehicle {
    private int personnelCount;
    private float fuelLevel;
    private AirRoute route;
    private Airport nextAirport;

    public Plane(Point currentPosition, int id, int personnelCount, AirRoute route) throws IOException {
        super(currentPosition, id);
        this.personnelCount = personnelCount;
        this.route = route;
        this.nextAirport = route.getElement(1);
        this.setTarget(nextAirport.getCenter());
    }

    @Override
    public void run() {

        while (!Map.getInstance().getPassengerPlanes().getElements().isEmpty() ||
                !Map.getInstance().getMilitaryAircrafts().getElements().isEmpty()) {
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

    private void move() {
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

    public Airport getNextAirport() {
        return nextAirport;
    }

    public void setNextAirport(Airport nextAirport) {
        this.nextAirport = nextAirport;
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
//            destinationLabel.textProperty().bind(this.destinationObservable.asString());
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
}
