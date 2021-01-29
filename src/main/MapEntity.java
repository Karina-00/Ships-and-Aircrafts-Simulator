package main;

import javafx.scene.image.ImageView;

public class MapEntity {
    private final Point position;
    private ImageView mapObject;
    private double radius;

    public MapEntity(double x, double y, ImageView mapObject){
        this.position = new Point(x, y, radius);
        this.mapObject = mapObject;
    }

    public MapEntity(double x, double y){
        this.position = new Point(x, y, radius);
    }

    public Point getCenter(){
        double x = this.position.getX();
        double y = this.position.getY();
        double width = this.mapObject.getFitWidth();
        double height = this.mapObject.getFitHeight();
        return new Point(x + width/2, y + height/2, radius);
    }

    public Point getPosition() {
        return position;
    }

    public ImageView getMapObject(){
        return mapObject;
    }

    public void setMapObject(ImageView mapObject) {
        this.mapObject = mapObject;
    }

    public double getRadius(){
        return radius;
    }

    public void setRadius(double newRadius){
        this.radius = newRadius;
    }
}
