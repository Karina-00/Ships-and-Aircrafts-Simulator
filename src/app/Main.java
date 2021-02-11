package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.map.Map;
import app.map.MapController;

/**
 *  Main class.
 */
public class Main extends Application{

    /**
     *  Opens the map window.
     */
    public static void openMapStage(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map/map.fxml"));
        Parent mapScene = fxmlLoader.load();
        primaryStage.setTitle("Map");
        Scene scene = new Scene(mapScene);
        primaryStage.setScene(scene);
        primaryStage.setX(400);
        primaryStage.setY(100);
        MapController mapController = fxmlLoader.getController();
        Map.setMapController(mapController);

        primaryStage.show();
        primaryStage.setOnCloseRequest(we -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        openMapStage(args);
    }
}
