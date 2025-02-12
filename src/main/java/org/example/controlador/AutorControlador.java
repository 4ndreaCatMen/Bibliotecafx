package org.example.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AutorControlador {

    @FXML
    private TextField nombreAutor;

    @FXML
    private void guardarAutor() {
        System.out.println("Autor guardado: " + nombreAutor.getText());
    }
}
