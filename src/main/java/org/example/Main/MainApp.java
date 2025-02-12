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
            System.out.println("Intentando cargar la ventana principal...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vista/libros-vista.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            primaryStage.setTitle("Biblioteca BookScape");
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Ventana principal cargada con éxito.");
        } catch (IOException e) {
            System.out.println("Error al cargar la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
