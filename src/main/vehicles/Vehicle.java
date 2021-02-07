package main.vehicles;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Point;

import java.io.IOException;

public abstract class Vehicle implements Runnable{
    private final Point currentPosition;
    private final int id;
    private double speed = 10.0;
    private Circle circle = new Circle();
    private Point target;

    public Vehicle(Point currentPosition, int id) throws IOException {
        this.currentPosition = currentPosition;
        this.id = id;
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.BLACK);
        circle.setCenterX(currentPosition.getX());
        circle.setCenterY(currentPosition.getY());
        circle.setRadius(currentPosition.getRadius());
        openVehiclesView();
    }

    protected abstract VBox vehicleViewPanelContent();

    @Override
    public void run() {}

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void updatePosition(double newX, double newY) {
        Pair<Double, Double> xy = this.checkOvershoot(newX, newY);

        this.currentPosition.setX(xy.getKey());
        this.currentPosition.setY(xy.getValue());
    }

    private Pair<Double, Double> checkOvershoot(double newX, double newY){
        double x = currentPosition.getX();
        double y = currentPosition.getY();

        double targetX = this.getTarget().getX();
        double targetY = this.getTarget().getY();

        double middleX = (x+newX) / 2;
        double middleY = (y+newY) / 2;

        boolean isXOvershoot = Math.abs(targetX - middleX) <= (Math.abs(x - middleX));
        boolean isYOvershoot = Math.abs(targetY - middleY) <= (Math.abs(y - middleY));

        if(isXOvershoot){
            newX = targetX;
        }
        if(isYOvershoot){
            newY = targetY;
        }

        return new Pair<>(newX, newY);
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setObjectColor(Color color){
        this.circle.setFill(color);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private void openVehiclesView() throws IOException {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vehicleViewPanel/vehicleView.fxml"));
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                Group group = new Group();
                ObservableList list = group.getChildren();
                list.add(vehicleViewPanelContent());

                Scene scene = new Scene(group);
                stage.setScene(scene);
                stage.setTitle("Vehicle view");

                stage.show();
                stage.setMinWidth(200);
            }
        };
        getCircle().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void draw() {
        circle.setCenterX(currentPosition.getX());
        circle.setCenterY(currentPosition.getY());
    }

}
