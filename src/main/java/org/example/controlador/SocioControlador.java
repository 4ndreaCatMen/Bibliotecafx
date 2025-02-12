package org.example.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class SocioControlador {

    @FXML
    private TextField nombreSocio;

    @FXML
    private TextField telefonoSocio;

    @FXML
    private Label estadoSocio;

    @FXML
    private Button registrarSocioButton;

    @FXML
    private void registrarSocio() {
        String nombre = nombreSocio.getText();
        String telefono = telefonoSocio.getText();

        if (!nombre.isEmpty() && !telefono.isEmpty()) {
            estadoSocio.setText("Socio registrado: " + nombre);
        } else {
            estadoSocio.setText("Por favor, completa todos los campos.");
        }
    }
}
