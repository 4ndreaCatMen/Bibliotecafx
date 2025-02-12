package org.example.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.IAutorDAO;
import org.example.dao.IAutorDAOImpl;
import org.example.modelo.Autor;

import java.util.List;

public class AutorControlador {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField nacionalidadField;
    @FXML
    private TextField buscarField;
    @FXML
    private Button buscarButton;
    @FXML
    private Button actualizarButton;
    @FXML
    private TableView<Autor> autoresTable;
    @FXML
    private TableColumn<Autor, String> nombreColumn;
    @FXML
    private TableColumn<Autor, String> nacionalidadColumn;

    private final IAutorDAO autorDAO = new IAutorDAOImpl();
    private final ObservableList<Autor> autoresList = FXCollections.observableArrayList();

    public void initialize() {
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        nacionalidadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNacionalidad()));
        autoresTable.setItems(autoresList);
        cargarAutores();

        buscarButton.setOnAction(e -> buscarAutor());
        actualizarButton.setOnAction(e -> actualizarAutor());
    }

    @FXML
    private void agregarAutor() {
        String nombre = nombreField.getText();
        String nacionalidad = nacionalidadField.getText();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        Autor autor = new Autor(nombre, nacionalidad);
        autorDAO.guardar(autor);
        autoresList.add(autor);
        limpiarCampos();
    }

    @FXML
    private void eliminarAutor() {
        Autor autorSeleccionado = autoresTable.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            autorDAO.eliminar(autorSeleccionado);
            autoresList.remove(autorSeleccionado);
        } else {
            mostrarAlerta("Error", "Seleccione un autor para eliminar");
        }
    }

    @FXML
    private void buscarAutor() {
        String nombre = buscarField.getText();
        if (!nombre.isEmpty()) {
            List<Autor> resultados = autorDAO.buscarPorNombre(nombre);
            autoresList.setAll(resultados);
        } else {
            cargarAutores();
        }
    }

    @FXML
    private void actualizarAutor() {
        Autor autorSeleccionado = autoresTable.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            autorSeleccionado.setNombre(nombreField.getText());
            autorSeleccionado.setNacionalidad(nacionalidadField.getText());
            autorDAO.actualizar(autorSeleccionado);
            cargarAutores();
        } else {
            mostrarAlerta("Error", "Seleccione un autor para actualizar");
        }
    }

    private void cargarAutores() {
        List<Autor> autores = autorDAO.listarTodos();
        autoresList.setAll(autores);
    }

    private void limpiarCampos() {
        nombreField.clear();
        nacionalidadField.clear();
        buscarField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
