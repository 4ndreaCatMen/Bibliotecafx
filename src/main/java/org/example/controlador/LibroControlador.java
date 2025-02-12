package org.example.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LibroControlador {

    @FXML
    private Label tituloLabel;

    @FXML
    private Button agregarLibroButton;

    @FXML
    private Button eliminarLibroButton;

    // Método que se ejecuta al hacer clic en "Agregar Libro"
    @FXML
    private void agregarLibro() {
        tituloLabel.setText("Libro agregado con éxito!");
    }

    // Método que se ejecuta al hacer clic en "Eliminar Libro"
    @FXML
    private void eliminarLibro() {
        tituloLabel.setText("Libro eliminado.");
    }
}
