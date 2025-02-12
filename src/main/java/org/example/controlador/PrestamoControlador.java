package org.example.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class PrestamoControlador {

    @FXML
    private Label estadoPrestamo;

    @FXML
    private Button registrarPrestamoButton;

    @FXML
    private void registrarPrestamo() {
        estadoPrestamo.setText("Préstamo registrado!");
    }
}
