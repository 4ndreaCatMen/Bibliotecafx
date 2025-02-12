package org.example.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
import org.example.modelo.Autor;
import org.example.modelo.Libro;

import java.util.List;

public class LibroControlador {

    @FXML
    private TextField tituloField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField editorialField;
    @FXML
    private TextField anioField;
    @FXML
    private ComboBox<Autor> autorComboBox;
    @FXML
    private TableView<Libro> librosTable;
    @FXML
    private TableColumn<Libro, String> tituloColumn;
    @FXML
    private TableColumn<Libro, String> isbnColumn;

    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private final ObservableList<Libro> librosList = FXCollections.observableArrayList();

    public void initialize() {
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        librosTable.setItems(librosList);
        cargarLibros();
    }

    @FXML
    private void agregarLibro() {
        String titulo = tituloField.getText();
        String isbn = isbnField.getText();
        String editorial = editorialField.getText();
        int anio = Integer.parseInt(anioField.getText());
        Autor autor = autorComboBox.getValue();

        if (titulo.isEmpty() || isbn.isEmpty() || autor == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        Libro libro = new Libro(titulo, isbn, editorial, anio, autor);
        libroDAO.guardar(libro);
        librosList.add(libro);
        limpiarCampos();
    }

    @FXML
    private void eliminarLibro() {
        Libro libroSeleccionado = librosTable.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            libroDAO.eliminar(libroSeleccionado);
            librosList.remove(libroSeleccionado);
        } else {
            mostrarAlerta("Error", "Seleccione un libro para eliminar");
        }
    }

    private void cargarLibros() {
        List<Libro> libros = libroDAO.listarTodos();
        librosList.setAll(libros);
    }

    private void limpiarCampos() {
        tituloField.clear();
        isbnField.clear();
        editorialField.clear();
        anioField.clear();
        autorComboBox.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
