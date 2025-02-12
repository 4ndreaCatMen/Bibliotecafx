package org.example.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Cargando menú principal...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vista/main-vista.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            primaryStage.setTitle("Menú Principal - Biblioteca");
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Menú principal cargado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al cargar el menú principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
