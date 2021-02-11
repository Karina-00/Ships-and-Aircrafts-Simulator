package app.baseClasses;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Represents a Point on a 2D map.
 */
public class Point {
    private double x;
    private double y;
    SimpleDoubleProperty xObservable;
    SimpleDoubleProperty yObservable;
    private double radius = 7.0;

    public Point(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.xObservable = new SimpleDoubleProperty(x);
        this.yObservable = new SimpleDoubleProperty(y);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.xObservable = new SimpleDoubleProperty(x);
        this.yObservable = new SimpleDoubleProperty(y);
    }

    public double calculateDistance(Point p){
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    public String toString(){
        return String.format("(%f, %f)", this.x, this.y);
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
        Platform.runLater(()->{
            String str = String.format("%1.2f", y);
            this.yObservable.set(Double.parseDouble(str));
        });
    }

    /**
     * @return Returns X coordinate value binding to a displaying text in the vehicle information panel.
     */
    public SimpleDoubleProperty getXObservable() {
        return xObservable;
    }

    /**
     * @return Returns Y coordinate value binding to a displaying text in the vehicle information panel.
     */
    public SimpleDoubleProperty getYObservable() {
        return yObservable;
    }

    public void setX(double x) {
        this.x = x;
        Platform.runLater(()->{
            String str = String.format("%1.2f", x);
            this.xObservable.set(Double.parseDouble(str));
        });
    }

    public double getDeltaX(Point target){
        return target.getX() - this.x;
    }

    public double getDeltaY(Point target){
        return target.getY() - this.y;
    }

    public double getRadius() {
        return radius;
    }
}
