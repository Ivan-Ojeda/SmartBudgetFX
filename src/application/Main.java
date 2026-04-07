package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargamos el FXML desde el mismo paquete
            Parent root = FXMLLoader.load(getClass().getResource("escena.fxml"));
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Smart Budget"); // Nombre oficial según el manual
            primaryStage.setScene(scene);
            
            // Tamaño mínimo recomendado para el dashboard
            primaryStage.setMinWidth(1100);
            primaryStage.setMinHeight(800);
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}