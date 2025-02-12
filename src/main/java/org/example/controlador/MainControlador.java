package org.example.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainControlador {

    @FXML
    private void abrirLibros() throws IOException {
        cargarVentana("/org/example/vista/libros-vista.fxml", "Gestión de Libros");
    }

    @FXML
    private void abrirAutores() throws IOException {
        cargarVentana("/org/example/vista/autores-vista.fxml", "Gestión de Autores");
    }

    @FXML
    private void abrirPrestamos() throws IOException {
        cargarVentana("/org/example/vista/prestamos-vista.fxml", "Gestión de Préstamos");
    }

    @FXML
    private void abrirSocios() throws IOException {
        cargarVentana("/org/example/vista/socios-vista.fxml", "Gestión de Socios");
    }

    private void cargarVentana(String rutaFXML, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
