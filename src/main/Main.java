package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.map.Map;

import java.util.concurrent.TimeUnit;

public class Main extends Application{

    public static void openMapStage(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mapScene = FXMLLoader.load(getClass().getResource("map/map.fxml"));
        primaryStage.setTitle("Map");
        Scene scene = new Scene(mapScene);
        primaryStage.setScene(scene);
        primaryStage.setX(400);
        primaryStage.setY(100);
        primaryStage.show();

//        Map map = Map.getInstance();
//        int i = 100;
//        while(i > 0){
//            System.out.println("Military Planes: " + map.getMilitaryAircrafts().getElements().size());
//            System.out.println("Civilian Planes: " + map.getPassengerPlanes().getElements().size());
//            System.out.println("Cruise Ships: " + map.getCruiseShips().getElements().size());
//            System.out.println("Military Ships: " + map.getAircraftCarriers().getElements().size());
////            TimeUnit.SECONDS.sleep(10);
//            i--;
//        }
    }

    public static void main(String[] args) {
        openMapStage(args);
    }
}
