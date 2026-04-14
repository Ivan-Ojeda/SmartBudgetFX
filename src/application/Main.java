package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("escena.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            
            // Cargar icono desde la carpeta externa "img" en la raíz del proyecto
            try {
                primaryStage.getIcons().add(new Image("file:img/logo.jpg"));
            } catch (Exception e) {
                System.out.println("Nota: Icono logo.jpg no encontrado en la carpeta externa img/");
            }
            
            primaryStage.setTitle("SmartBudget - Gestión Familiar");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true); 
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
