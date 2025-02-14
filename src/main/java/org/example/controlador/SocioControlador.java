package org.example.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.ISocioDAO;
import org.example.dao.ISocioDAOImpl;
import org.example.modelo.Socio;
import java.util.List;

public class SocioControlador {

    @FXML
    private TextField nombreSocioField;
    @FXML
    private TextField telefonoSocioField;
    @FXML
    private TextField direccionSocioField;
    @FXML
    private TextField buscarField;
    @FXML
    private TableView<Socio> sociosTable;
    @FXML
    private TableColumn<Socio, String> nombreColumn;
    @FXML
    private TableColumn<Socio, String> telefonoColumn;
    @FXML
    private TableColumn<Socio, String> direccionColumn;

    private final ISocioDAO socioDAO = new ISocioDAOImpl();
    private final ObservableList<Socio> sociosList = FXCollections.observableArrayList();

    public void initialize() {
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        telefonoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        direccionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        sociosTable.setItems(sociosList);
        cargarSocios();
    }

    @FXML
    private void agregarSocio() {
        String nombre = nombreSocioField.getText();
        String telefono = telefonoSocioField.getText();
        String direccion = direccionSocioField.getText();

        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        Socio socio = new Socio(nombre, telefono, direccion);
        socioDAO.guardar(socio);
        cargarSocios();
        limpiarCampos();
    }

    @FXML
    private void eliminarSocio() {
        Socio socioSeleccionado = sociosTable.getSelectionModel().getSelectedItem();
        if (socioSeleccionado != null) {
            socioDAO.eliminar(socioSeleccionado);
            cargarSocios();
        } else {
            mostrarAlerta("Error", "Seleccione un socio para eliminar");
        }
    }

    @FXML
    private void actualizarSocio() {
        Socio socioSeleccionado = sociosTable.getSelectionModel().getSelectedItem();
        if (socioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un socio para editar");
            return;
        }

        String nombre = nombreSocioField.getText();
        String telefono = telefonoSocioField.getText();
        String direccion = direccionSocioField.getText();

        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        socioSeleccionado.setNombre(nombre);
        socioSeleccionado.setTelefono(telefono);
        socioSeleccionado.setDireccion(direccion);
        socioDAO.actualizar(socioSeleccionado);
        cargarSocios();
        limpiarCampos();
    }

    @FXML
    private void buscarSocio() {
        String busqueda = buscarField.getText();
        List<Socio> socios = socioDAO.buscarPorNombre(busqueda);
        sociosList.setAll(socios);
    }

    @FXML
    private void cargarSocios() {
        List<Socio> socios = socioDAO.listarTodos();
        sociosList.setAll(socios);
    }

    private void limpiarCampos() {
        nombreSocioField.clear();
        telefonoSocioField.clear();
        direccionSocioField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
