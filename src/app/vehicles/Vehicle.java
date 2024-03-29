package app.vehicles;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Pair;
import app.baseClasses.Point;
import app.map.Map;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

/**
 *  Represents an abstract class of vehicle.
 */
public abstract class Vehicle implements Runnable{
    private final Point currentPosition;
    private final int id;
    private double speed = 5.0;
    private Circle circle = new Circle();
    private Point target;
    boolean running = true;

    public Vehicle(Point currentPosition, int id) {
        this.currentPosition = currentPosition;
        this.id = id;
        circle.setStroke(Color.BLACK);
        circle.setCenterX(currentPosition.getX());
        circle.setCenterY(currentPosition.getY());
        circle.setRadius(currentPosition.getRadius());
        openVehiclesView();
    }


    @Override
    public void run() {
        while(running){
            try {
                System.out.println("running " + getId());
                move();
                draw();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Map.getMapController().deleteObject(circle);
    }

    /**
     *  Stops the vehicle, and as a result deletes from the map.
     */
    public void stopRunning() {
        this.running = false;
    }

    /**
     *  Abstract method, performs a move of the vehicle.
     */
    public abstract void move();

    /**
     *  Draws the vehicle's symbol.
     */
    public void draw() {
        Platform.runLater(()->{
            circle.setCenterX(currentPosition.getX());
            circle.setCenterY(currentPosition.getY());
        });
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void updatePosition(double newX, double newY) {
        Pair<Double, Double> xy = this.checkOvershoot(newX, newY);
        this.currentPosition.setX(xy.getKey());
        this.currentPosition.setY(xy.getValue());
    }

    /**
     *  @return Checks for overshoots and returns valid values.
     */
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
        this.target = new Point(target.getX(), target.getY());
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

    protected abstract Label[] getLabels(Label x, Label y, Label destination);

    protected abstract SimpleObjectProperty<?> getDestinationObservable();

    /**
     *  @return Returns the content for vehicle's information panel.
     */
    protected VBox vehicleViewPanelContent() {
        VBox vbox = new VBox();
        Label x = new Label();
        Label y = new Label();
        Label destinationLabel = new Label();
        if(this.getCurrentPosition().getXObservable() != null){
            x.textProperty().bind(this.getCurrentPosition().getXObservable().asString());
            y.textProperty().bind(this.getCurrentPosition().getYObservable().asString());
            destinationLabel.textProperty().bind(this.getDestinationObservable().asString());
        }else {
            x.textProperty().unbind();
            y.textProperty().unbind();
            destinationLabel.textProperty().unbind();
        }
        Label[] labels = getLabels(x, y, destinationLabel);

        for (Label label: labels) {
            VBox.setMargin(label, new Insets(5, 5, 10, 10));
            vbox.getChildren().add(label);
        }
        return vbox;
    }

    /**
     *  @return Creates "delete vehicle" button.
     */
    private Button deleteVehicleButton(){
        Button button = new Button("Delete Vehicle");
        button.addEventFilter(MOUSE_CLICKED, mouseEvent -> stopRunning());
        return button;
    }

    /**
     *  Opens Vehicle's information panel.
     */
    private void openVehiclesView() {
        EventHandler<MouseEvent> eventHandler = new EventHandler<>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vehicleViewPanel/vehicleView.fxml"));
                Stage stage = new Stage();
                Group group = new Group();
                Scene scene = new Scene(group);
                group.getChildren().add(vehicleViewPanelContent());
                group.getChildren().add(deleteVehicleButton());
                stage.setScene(scene);
                stage.setTitle("Vehicle info");
                stage.show();
                stage.setMinWidth(200);
            }
        };
        getCircle().addEventFilter(MOUSE_CLICKED, eventHandler);
    }
}
