package main.vehicles.planes;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import main.routes.AirRoute;
import main.baseClasses.Point;

import java.io.IOException;
import java.util.Objects;

/**
 *  Represents a Military Aircraft.
 */
public class MilitaryAircraft extends Plane {
    private final String armament;

    public MilitaryAircraft(Point currentPosition, int id, String armament, int personnelCount, AirRoute route) throws IOException {
        super(currentPosition, id, personnelCount, route);
        this.armament = Objects.requireNonNullElse(armament, "Versatile armament");
        this.setObjectColor(Color.BROWN);
    }

    /**
     *  @return Returns a label representing its armament.
     */
    protected Label armamentLabel(){
        return new Label("Military Aircraft\nArmament: " + armament);
    }
}
