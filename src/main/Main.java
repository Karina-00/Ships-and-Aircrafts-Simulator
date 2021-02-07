package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.map.Map;
import main.map.MapController;

import java.util.concurrent.TimeUnit;

public class Main extends Application{

    public static void openMapStage(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map/map.fxml"));
        Parent mapScene = (Parent) fxmlLoader.load();
        primaryStage.setTitle("Map");
        Scene scene = new Scene(mapScene);
        primaryStage.setScene(scene);
        primaryStage.setX(400);
        primaryStage.setY(100);
        MapController mapController = (MapController) fxmlLoader.getController();
        Map.setMapController(mapController);

        primaryStage.show();
    }

    public static void main(String[] args) {
        openMapStage(args);
    }
}
