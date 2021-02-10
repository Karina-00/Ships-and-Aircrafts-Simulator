package main.vehicles.ships;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import main.baseClasses.Point;
import main.routes.ShipStop;

import java.io.IOException;

public class AircraftCarrier extends Ship {
    private final String armament;

    public AircraftCarrier(int id, double speed, Point currentPosition, ShipStop startingShipStop,
                           ShipStop destination, String armament) throws IOException {
        super(id, speed, currentPosition, startingShipStop, destination);
        this.armament = armament;
        this.setObjectColor(Color.GREEN);
    }

    public String getArmament() {
        return armament;
    }

    @Override
    protected Label[] getLabels(Label x, Label y, Label destination){
        return new Label[]{
                new Label("Ship ID: " + this.getId()),
                new Label("Coordinate X:"),
                x,
                new Label("Coordinate Y:" ),
                y,
                new Label("Destination point:"),
                destination,
                new Label("Armament:"),
                new Label(armament),
        };
    }
}