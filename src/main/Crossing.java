package main;

import javafx.scene.shape.Rectangle;

public class Crossing {
    private final int id;
    private final Point position;
    private final Rectangle mapObject;
    private final double radius = 20.0;

    public Crossing(int id, double x, double y, Rectangle mapObject){
        this.id = id;
        this.position = new Point(x, y, radius);
        this.mapObject = mapObject;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public Rectangle getMapObject() {
        return mapObject;
    }

    public double getRadius() {
        return radius;
    }
}
