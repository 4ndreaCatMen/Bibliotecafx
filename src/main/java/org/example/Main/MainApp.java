package org.example.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.util.InicializadorDatos;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        InicializadorDatos.insertarDatosIniciales(); // Llama al método de inserción
        System.out.println("Aplicación iniciada.");
        Parent root = FXMLLoader.load(getClass().getResource("/vista/main-vista.fxml")); // Asegúrate de que el nombre coincide
        primaryStage.setTitle("Biblioteca BookScape");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
