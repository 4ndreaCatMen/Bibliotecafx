package org.example.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.IAutorDAO;
import org.example.dao.IAutorDAOImpl;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
import org.example.modelo.Libro;
import java.util.List;

public class LibrosVista {
    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private final IAutorDAO autorDAO = new IAutorDAOImpl();
    private ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
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
        List<Libro> librosDisponibles = libroDAO.listarDisponibles();
        listaLibros.setAll(librosDisponibles);
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
        String criterio = campoBusqueda.getText().trim();
        String tipo = tipoBusqueda.getValue();

        List<Libro> resultado;
        if ("Título".equals(tipo)) {
            resultado = libroDAO.buscarPorTitulo(criterio);
        } else if ("Autor".equals(tipo)) {
            resultado = libroDAO.buscarPorAutor(criterio);
        } else {
            resultado = libroDAO.buscarPorISBN(criterio);
        }

        listaLibros.setAll(resultado);
    }

    private void inicializarTabla() {
        tablaLibros = new TableView<>(listaLibros);
        tablaLibros.getColumns().addAll(
                crearColumna("Título", "titulo"),
                crearColumna("ISBN", "isbn"),
                crearColumna("Editorial", "editorial"),
                crearColumna("Año", "anioPublicacion")
        );
    }

    private TableColumn<Libro, String> crearColumna(String titulo, String propiedad) {
        TableColumn<Libro, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(propiedad));
        return columna;
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
        Libro nuevoLibro = new Libro("Nuevo Libro", "0000000000", "Editorial X", 2023, null);
        libroDAO.guardar(nuevoLibro);
        cargarLibros();
    }

    private void modificarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setTitulo(seleccionado.getTitulo() + " (Modificado)");
            libroDAO.actualizar(seleccionado);
            cargarLibros();
        } else {
            mostrarAlerta("Seleccione un libro para modificar.");
        }
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

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
