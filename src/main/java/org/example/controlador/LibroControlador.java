package org.example.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.IAutorDAO;
import org.example.dao.IAutorDAOImpl;
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
    private TextField buscarField;
    @FXML
    private TableView<Libro> librosTable;
    @FXML
    private TableColumn<Libro, String> tituloColumn;
    @FXML
    private TableColumn<Libro, String> isbnColumn;
    @FXML
    private TableColumn<Libro, String> autorColumn;
    @FXML
    private TableColumn<Libro, String> editorialColumn;
    @FXML
    private TableColumn<Libro, String> anioPublicacionColumn;

    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private final IAutorDAO autorDAO = new IAutorDAOImpl(); // Así debe verse


    private final ObservableList<Libro> librosList = FXCollections.observableArrayList();


    public LibroControlador() {

    }

    public void initialize() {
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        autorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNombre()));
        editorialColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        anioPublicacionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAnioPublicacion())));
        librosTable.setItems(librosList);
        cargarAutores();
        cargarLibros();
    }

    @FXML
    private void cargarLibros() {
        List<Libro> libros = libroDAO.listarTodos();
        librosList.setAll(libros);
        librosTable.setItems(librosList);
    }

    @FXML
    private void agregarLibro() {
        String titulo = tituloField.getText();
        String isbn = isbnField.getText();
        String editorial = editorialField.getText();
        String anioTexto = anioField.getText();
        Autor autor = autorComboBox.getValue();
        if (titulo.isEmpty() || isbn.isEmpty() || autor == null || anioTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        try {
            int anio = Integer.parseInt(anioTexto);
            Libro libro = new Libro(titulo, isbn, editorial, anio, autor);
            libroDAO.guardar(libro);
            cargarLibros();
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El año debe ser un número válido");
        }
    }

    @FXML
    private void eliminarLibro() {
        Libro libroSeleccionado = librosTable.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            libroDAO.eliminar(libroSeleccionado);
            cargarLibros();
        } else {
            mostrarAlerta("Error", "Seleccione un libro para eliminar");
        }
    }

    @FXML
    private void editarLibro() {
        Libro libroSeleccionado = librosTable.getSelectionModel().getSelectedItem();
        if (libroSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un libro para editar");
            return;
        }

        String titulo = tituloField.getText();
        String isbn = isbnField.getText();
        String editorial = editorialField.getText();
        String anioTexto = anioField.getText();
        Autor autor = autorComboBox.getValue();

        if (titulo.isEmpty() || isbn.isEmpty() || autor == null || anioTexto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        try {
            int anio = Integer.parseInt(anioTexto);
            libroSeleccionado.setTitulo(titulo);
            libroSeleccionado.setIsbn(isbn);
            libroSeleccionado.setEditorial(editorial);
            libroSeleccionado.setAnioPublicacion(anio);
            libroSeleccionado.setAutor(autor);
            libroDAO.actualizar(libroSeleccionado);
            cargarLibros();
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El año debe ser un número válido");
        }
    }

    @FXML
    private void buscarLibro() {
        String busqueda = buscarField.getText();
        List<Libro> libros = libroDAO.buscarPorTitulo(busqueda);
        librosList.setAll(libros);
    }


    private void limpiarCampos() {
        tituloField.clear();
        isbnField.clear();
        editorialField.clear();
        anioField.clear();
        autorComboBox.setValue(null);
    }

    private void cargarAutores() {
        List<Autor> autores = autorDAO.listarTodos(); // Usa la instancia, NO la clase
        autorComboBox.getItems().setAll(autores);
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
