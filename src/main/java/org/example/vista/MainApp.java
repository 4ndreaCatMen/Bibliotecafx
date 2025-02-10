package org.example.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear el diseño principal
        VBox root = new VBox(10);
        root.getChildren().add(new Label("¡Bienvenido a la Biblioteca!"));

        // Crear botones para cada sección
        Button btnLibros = new Button("Gestión de Libros");
        Button btnAutores = new Button("Gestión de Autores");
        Button btnSocios = new Button("Gestión de Socios");
        Button btnPrestamos = new Button("Gestión de Préstamos");

        // Agregar botones al diseño
        root.getChildren().addAll(btnLibros, btnAutores, btnSocios, btnPrestamos);

        // Crear la escena
        Scene scene = new Scene(root, 600, 400);

        // Configurar la ventana principal
        primaryStage.setTitle("Biblioteca BookScape");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
