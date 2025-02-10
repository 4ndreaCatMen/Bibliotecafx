package org.example.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
import org.example.dao.IAutorDAO;
import org.example.dao.IAutorDAOImpl;
import org.example.modelo.Libro;
import org.example.modelo.Autor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class LibrosVista {
    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private final IAutorDAO autorDAO = new IAutorDAOImpl();
    private ObservableList<Libro> listaLibros;
    private TableView<Libro> tablaLibros;
    private TextField campoBusqueda;
    private ComboBox<String> tipoBusqueda;

    public void mostrar() {
        Stage ventana = new Stage();
        ventana.setTitle("Gestión de Libros");

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Administración de libros"));

        inicializarBusqueda(layout);
        inicializarTabla();
        inicializarBotones(layout);
        Button actualizarBtn = new Button("Actualizar Lista");
        actualizarBtn.setOnAction(e -> cargarLibros());

        layout.getChildren().addAll(tablaLibros, actualizarBtn);

        Scene escena = new Scene(layout, 700, 500);
        ventana.setScene(escena);
        ventana.show();

        cargarLibros();
    }

    private void cargarLibros() {
    }

    private void inicializarBusqueda(VBox layout) {
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Ingrese el término de búsqueda");

        tipoBusqueda = new ComboBox<>();
        tipoBusqueda.getItems().addAll("Título", "Autor", "ISBN");
        tipoBusqueda.setValue("Título");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> buscarLibros());

        layout.getChildren().addAll(new Label("Buscar por:"), tipoBusqueda, campoBusqueda, btnBuscar);
    }

    private void buscarLibros() {
    }

    private void inicializarTabla() {
        tablaLibros = new TableView<>();

        TableColumn<Libro, Integer> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<Libro, String> columnaTitulo = new TableColumn<>("Título");
        columnaTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));

        TableColumn<Libro, String> columnaISBN = new TableColumn<>("ISBN");
        columnaISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        TableColumn<Libro, String> columnaEditorial = new TableColumn<>("Editorial");
        columnaEditorial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));

        TableColumn<Libro, Integer> columnaAnio = new TableColumn<>("Año");
        columnaAnio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnioPublicacion()));

        tablaLibros.getColumns().addAll(columnaId, columnaTitulo, columnaISBN, columnaEditorial, columnaAnio);
    }

    private void inicializarBotones(VBox layout) {
        Button btnAgregar = new Button("Añadir Libro");
        btnAgregar.setOnAction(e -> agregarLibro());

        Button btnModificar = new Button("Modificar Libro");
        btnModificar.setOnAction(e -> modificarLibro());

        Button btnEliminar = new Button("Eliminar Libro");
        btnEliminar.setOnAction(e -> eliminarLibro());

        layout.getChildren().addAll(btnAgregar, btnModificar, btnEliminar);
    }

    private void agregarLibro() {
    }

    private void eliminarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            libroDAO.eliminar(seleccionado);
            cargarLibros();
        } else {
            mostrarAlerta("Seleccione un libro para eliminar.");
        }
    }

    private void mostrarAlerta(String s) {
    }

    private void modificarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un libro para modificar.");
            return;
        }

        Dialog<String> dialogo = new Dialog<>();
        dialogo.setTitle("Modificar Libro");
        dialogo.setHeaderText("Modifique los detalles del libro");

        VBox contenido = new VBox(10);
        TextField tituloField = new TextField(seleccionado.getTitulo());
        TextField isbnField = new TextField(seleccionado.getIsbn());
        TextField editorialField = new TextField(seleccionado.getEditorial());
        TextField anioField = new TextField(String.valueOf(seleccionado.getAnioPublicacion()));

        ComboBox<Autor> autorCombo = new ComboBox<>(FXCollections.observableArrayList(autorDAO.listarTodos()));
        autorCombo.setValue(seleccionado.getAutor());

        contenido.getChildren().addAll(new Label("Título:"), tituloField,
                new Label("ISBN:"), isbnField,
                new Label("Editorial:"), editorialField,
                new Label("Año:"), anioField,
                new Label("Autor:"), autorCombo);

        dialogo.getDialogPane().setContent(contenido);
        dialogo.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialogo.setResultConverter(boton -> {
            if (boton == ButtonType.OK) {
                try {
                    String titulo = tituloField.getText().trim();
                    String isbn = isbnField.getText().trim();
                    String editorial = editorialField.getText().trim();
                    int anio = Integer.parseInt(anioField.getText().trim());
                    Autor autorSeleccionado = autorCombo.getValue();

                    if (titulo.isEmpty() || isbn.isEmpty() || editorial.isEmpty() || autorSeleccionado == null) {
                        mostrarAlerta("Todos los campos son obligatorios.");
                        return null;
                    }

                    seleccionado.setTitulo(titulo);
                    seleccionado.setIsbn(isbn);
                    seleccionado.setEditorial(editorial);
                    seleccionado.setAnioPublicacion(anio);
                    seleccionado.setAutor(autorSeleccionado);
                    libroDAO.actualizar(seleccionado);
                    cargarLibros();
                } catch (NumberFormatException e) {
                    mostrarAlerta("El año debe ser un número válido.");
                }
            }
            return null;
        });

        dialogo.showAndWait();
    }
}
