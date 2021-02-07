package main;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Point {
    private double x;
    private double y;
    SimpleDoubleProperty a;
    SimpleDoubleProperty b;
    private double radius = 5.0;

    public Point(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.a = new SimpleDoubleProperty(x);
        this.b = new SimpleDoubleProperty(y);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
            this.b.set(Double.parseDouble(str));
        });

    }

    public double getA() {
        return a.get();
    }

    public SimpleDoubleProperty aProperty() {
        return a;
    }

    public double getB() {
        return b.get();
    }

    public SimpleDoubleProperty bProperty() {
        return b;
    }

    public void setX(double x) {
        this.x = x;
        Platform.runLater(()->{
            String str = String.format("%1.2f", x);
            this.a.set(Double.parseDouble(str));
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

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
