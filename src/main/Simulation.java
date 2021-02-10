package main;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import main.map.Map;

public class Simulation {
    private Map map = Map.getInstance();
    private double timeStep = 0.1;
    private AnchorPane pane;

    private static final Simulation instance = new Simulation();

    public Simulation() {
    }

    public Simulation(AnchorPane mapPane) {
        this.pane = mapPane;
    }

    public static Simulation getInstance() {
        return instance;
    }

    public void resetSimulation() {
        this.pane.getChildren().clear();
    }

    public void deleteObject(Circle c) {
        Platform.runLater(() -> {
            this.pane.getChildren().remove(c);
        });
    }
}
