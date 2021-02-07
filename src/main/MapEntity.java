package main;

import javafx.scene.image.ImageView;

public class MapEntity {
    private final Point position;
    private ImageView mapObject;
    private double radius = 5.0;

    public MapEntity(double x, double y, ImageView mapObject){
        this.position = new Point(x, y, radius);
        this.mapObject = mapObject;
    }

    public MapEntity(double x, double y){
        this.position = new Point(x, y, radius);
    }

    public MapEntity(Point point){
        this.position = point;
    }

    public Point getCenter(){
        double x = this.position.getX();
        double y = this.position.getY();
        double width, height;

        if(this.mapObject != null){
            width = this.mapObject.getFitWidth();
            height = this.mapObject.getFitHeight();
        } else{
            width = this.radius * 2;
            height = this.radius * 2;
        }
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
