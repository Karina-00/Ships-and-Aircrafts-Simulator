package main;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import main.map.Map;
import main.vehicles.Ship;

public class Simulation {
    private Map map = Map.getInstance();
    private double timeStep = 0.1;
    private AnchorPane pane;

    private static final Simulation instance = new Simulation();

    public Simulation(){}

    public Simulation(AnchorPane mapPane){
        this.pane = mapPane;
    }

    public static Simulation getInstance() {
        return instance;
    }


    public void move(){
        for (Ship ship: map.getCruiseShips().getElements()) {
            ship.move();
        }
    }

    public void draw(){
        for (Ship ship: map.getCruiseShips().getElements()) {
            ship.draw();
            System.out.println((ship.getCircle()).toString());
        }
    }

    public void resetSimulation(){
        this.pane.getChildren().clear();
    }
}
