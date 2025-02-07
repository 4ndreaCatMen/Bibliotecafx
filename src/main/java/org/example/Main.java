package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.dao.HibernateUtil;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar la ventana principal de la biblioteca
            Parent root = FXMLLoader.load(getClass().getResource("/vista/Biblioteca.fxml"));
            primaryStage.setTitle("Biblioteca BookScape");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Verificar conexión con MySQL antes de iniciar JavaFX
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("✅ Conexión con MySQL establecida correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con MySQL: " + e.getMessage());
        }

        // Iniciar la interfaz gráfica de JavaFX
        launch(args);
    }
}
