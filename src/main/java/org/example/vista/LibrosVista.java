package org.example.vista;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
import org.example.modelo.Libro;

import java.util.List;
import java.util.stream.Collectors;

public class LibrosVista {
    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private ObservableList<Libro> listaLibros;
    private TableView<Libro> tablaLibros;
    private TextField campoBusqueda;

    public void mostrar() {
        Stage ventana = new Stage();
        ventana.setTitle("Gestión de Libros");

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Administración de libros"));

        inicializarBusqueda(layout);
        inicializarTabla(layout);
        inicializarBotones(layout);

        Scene escena = new Scene(layout, 600, 400);
        ventana.setScene(escena);
        ventana.show();
    }

    private void inicializarBusqueda(VBox layout) {
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por título, autor o ISBN");
        campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> filtrarLibros(newValue));
        layout.getChildren().add(campoBusqueda);
    }

    private void inicializarTabla(VBox layout) {
        tablaLibros = new TableView<>();
        TableColumn<Libro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        TableColumn<Libro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAutor().getNombre()));
        TableColumn<Libro, String> colISBN = new TableColumn<>("ISBN");
        colISBN.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsbn()));

        tablaLibros.getColumns().addAll(colTitulo, colAutor, colISBN);
        cargarLibros();
        layout.getChildren().add(tablaLibros);
    }

    private void inicializarBotones(VBox layout) {
        Button btnAgregar = new Button("Agregar Libro");
        Button btnEditar = new Button("Editar Libro");
        Button btnEliminar = new Button("Eliminar Libro");
        Button btnCerrar = new Button("Cerrar");

        btnAgregar.setOnAction(e -> agregarLibro());
        btnEditar.setOnAction(e -> editarLibro());
        btnEliminar.setOnAction(e -> eliminarLibro());
        btnCerrar.setOnAction(e -> ((Stage) btnCerrar.getScene().getWindow()).close());

        layout.getChildren().addAll(btnAgregar, btnEditar, btnEliminar, btnCerrar);
    }

    private void cargarLibros() {
        List<Libro> libros = libroDAO.listarDisponibles();
        listaLibros = FXCollections.observableArrayList(libros);
        tablaLibros.setItems(listaLibros);
    }

    private void filtrarLibros(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tablaLibros.setItems(listaLibros);
        } else {
            List<Libro> librosFiltrados = listaLibros.stream()
                    .filter(libro -> libro.getTitulo().toLowerCase().contains(filtro.toLowerCase()) ||
                            libro.getAutor().getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                            libro.getIsbn().toLowerCase().contains(filtro.toLowerCase()))
                    .collect(Collectors.toList());
            tablaLibros.setItems(FXCollections.observableArrayList(librosFiltrados));
        }
    }

    private void agregarLibro() {
        // Implementar lógica para agregar libro
    }

    private void editarLibro() {
        // Implementar lógica para editar libro
    }

    private void eliminarLibro() {
        // Implementar lógica para eliminar libro
    }
}
