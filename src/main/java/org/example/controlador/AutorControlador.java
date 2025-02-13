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
        System.out.println("AutorControlador inicializado");

        if (buscarButton == null) {
            System.out.println("Error: buscarButton es null. Verifica el fx:id en el archivo FXML.");
        } else {
            buscarButton.setOnAction(e -> buscarAutor());
        }

        if (actualizarButton == null) {
            System.out.println("Error: actualizarButton es null. Verifica el fx:id en el archivo FXML.");
        } else {
            actualizarButton.setOnAction(e -> actualizarAutor());
        }

        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        nacionalidadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNacionalidad()));
        autoresTable.setItems(autoresList);
        cargarAutores();
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
        System.out.println("Buscando autor con nombre: " + nombre);

        if (!nombre.isEmpty()) {
            List<Autor> resultados = autorDAO.buscarPorNombre(nombre);
            System.out.println("Resultados encontrados: " + resultados);
            autoresList.setAll(resultados);
        } else {
            cargarAutores();
        }
    }


    @FXML
    private void actualizarAutor() {
        Autor autorSeleccionado = autoresTable.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            System.out.println("Antes de actualizar: " + autorSeleccionado);

            autorSeleccionado.setNombre(nombreField.getText());
            autorSeleccionado.setNacionalidad(nacionalidadField.getText());
            autorDAO.actualizar(autorSeleccionado);

            System.out.println("Después de actualizar: " + autorSeleccionado);
            cargarAutores();
        } else {
            mostrarAlerta("Error", "Seleccione un autor para actualizar");
        }
    }


    private void cargarAutores() {
        List<Autor> autores = autorDAO.listarTodos();
        System.out.println("Autores cargados después de actualizar: " + autores);
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
