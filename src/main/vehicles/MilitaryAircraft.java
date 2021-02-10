package main.vehicles;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import main.AirRoute;
import main.Point;

import java.io.IOException;
import java.util.Objects;

public class MilitaryAircraft extends Plane {
    private final String armament;

    public MilitaryAircraft(Point currentPosition, int id, String armament, int personnelCount, AirRoute route) throws IOException {
        super(currentPosition, id, personnelCount, route);
        this.armament = Objects.requireNonNullElse(armament, "Versatile armament");
        this.setObjectColor(Color.BROWN);
    }

    protected Label armamentLabel(){
        return new Label("Military Aircraft\nArmament: " + armament);
    }
}
