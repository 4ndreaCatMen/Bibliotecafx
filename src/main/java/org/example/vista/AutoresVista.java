package org.example.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.IAutorDAO;
import org.example.dao.IAutorDAOImpl;
import org.example.modelo.Autor;
import java.util.List;

public class AutoresVista {
    private final IAutorDAO autorDAO = new IAutorDAOImpl();
    private ObservableList<Autor> listaAutores = FXCollections.observableArrayList();
    private TableView<Autor> tablaAutores;
    private TextField campoNombre, campoNacionalidad, campoBusqueda;

    public void mostrar() {
        Stage ventana = new Stage();
        ventana.setTitle("Gestión de Autores");

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Administración de Autores"));

        inicializarCampos(layout);
        inicializarTabla();
        inicializarBotones(layout);

        layout.getChildren().add(tablaAutores);

        Scene escena = new Scene(layout, 600, 500);
        ventana.setScene(escena);
        ventana.show();

        cargarAutores();
    }

    private void inicializarCampos(VBox layout) {
        campoNombre = new TextField();
        campoNombre.setPromptText("Nombre del Autor");

        campoNacionalidad = new TextField();
        campoNacionalidad.setPromptText("Nacionalidad");

        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por nombre");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> buscarAutor());

        layout.getChildren().addAll(new Label("Nombre:"), campoNombre, new Label("Nacionalidad:"), campoNacionalidad, new Label("Buscar:"), campoBusqueda, btnBuscar);
    }

    private void inicializarTabla() {
        tablaAutores = new TableView<>(listaAutores);
        tablaAutores.getColumns().addAll(
                crearColumna("Nombre", "nombre"),
                crearColumna("Nacionalidad", "nacionalidad")
        );
    }

    private TableColumn<Autor, String> crearColumna(String titulo, String propiedad) {
        TableColumn<Autor, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(propiedad));
        return columna;
    }

    private void inicializarBotones(VBox layout) {
        Button btnAgregar = new Button("Añadir Autor");
        btnAgregar.setOnAction(e -> agregarAutor());

        Button btnModificar = new Button("Modificar Autor");
        btnModificar.setOnAction(e -> modificarAutor());

        Button btnEliminar = new Button("Eliminar Autor");
        btnEliminar.setOnAction(e -> eliminarAutor());

        layout.getChildren().addAll(btnAgregar, btnModificar, btnEliminar);
    }

    private void agregarAutor() {
        String nombre = campoNombre.getText().trim();
        String nacionalidad = campoNacionalidad.getText().trim();

        if (!nombre.isEmpty() && !nacionalidad.isEmpty()) {
            Autor nuevoAutor = new Autor(nombre, nacionalidad);
            autorDAO.guardar(nuevoAutor);
            cargarAutores();
        } else {
            mostrarAlerta("Complete todos los campos.");
        }
    }

    private void modificarAutor() {
        Autor seleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(campoNombre.getText().trim());
            seleccionado.setNacionalidad(campoNacionalidad.getText().trim());
            autorDAO.actualizar(seleccionado);
            cargarAutores();
        } else {
            mostrarAlerta("Seleccione un autor para modificar.");
        }
    }

    private void eliminarAutor() {
        Autor seleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            autorDAO.eliminar(seleccionado);
            cargarAutores();
        } else {
            mostrarAlerta("Seleccione un autor para eliminar.");
        }
    }

    private void buscarAutor() {
        String criterio = campoBusqueda.getText().trim();
        if (!criterio.isEmpty()) {
            listaAutores.setAll(autorDAO.buscarPorNombre(criterio));
        } else {
            cargarAutores();
        }
    }

    private void cargarAutores() {
        listaAutores.setAll(autorDAO.listarTodos());
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
