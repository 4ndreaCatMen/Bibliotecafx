package org.example.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.ISocioDAO;
import org.example.dao.ISocioDAOImpl;
import org.example.modelo.Socio;
import java.util.List;

public class SociosVista {
    private final ISocioDAO socioDAO = new ISocioDAOImpl();
    private ObservableList<Socio> listaSocios = FXCollections.observableArrayList();
    private TableView<Socio> tablaSocios;
    private TextField campoNombre, campoDireccion, campoTelefono, campoBusqueda;

    public void mostrar() {
        Stage ventana = new Stage();
        ventana.setTitle("Gestión de Socios");

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Administración de Socios"));

        inicializarCampos(layout);
        inicializarTabla();
        inicializarBotones(layout);

        layout.getChildren().add(tablaSocios);

        Scene escena = new Scene(layout, 600, 500);
        ventana.setScene(escena);
        ventana.show();

        cargarSocios();
    }

    private void inicializarCampos(VBox layout) {
        campoNombre = new TextField();
        campoNombre.setPromptText("Nombre del Socio");

        campoDireccion = new TextField();
        campoDireccion.setPromptText("Dirección");

        campoTelefono = new TextField();
        campoTelefono.setPromptText("Teléfono");

        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por nombre");
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> buscarSocio());

        layout.getChildren().addAll(new Label("Nombre:"), campoNombre, new Label("Dirección:"), campoDireccion, new Label("Teléfono:"), campoTelefono, new Label("Buscar:"), campoBusqueda, btnBuscar);
    }

    private void inicializarTabla() {
        tablaSocios = new TableView<>(listaSocios);
        tablaSocios.getColumns().addAll(
                crearColumna("Nombre", "nombre"),
                crearColumna("Dirección", "direccion"),
                crearColumna("Teléfono", "telefono")
        );
    }

    private TableColumn<Socio, String> crearColumna(String titulo, String propiedad) {
        TableColumn<Socio, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(propiedad));
        return columna;
    }

    private void inicializarBotones(VBox layout) {
        Button btnAgregar = new Button("Añadir Socio");
        btnAgregar.setOnAction(e -> agregarSocio());

        Button btnModificar = new Button("Modificar Socio");
        btnModificar.setOnAction(e -> modificarSocio());

        Button btnEliminar = new Button("Eliminar Socio");
        btnEliminar.setOnAction(e -> eliminarSocio());

        layout.getChildren().addAll(btnAgregar, btnModificar, btnEliminar);
    }

    private void agregarSocio() {
        String nombre = campoNombre.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String telefono = campoTelefono.getText().trim();

        if (!nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty()) {
            Socio nuevoSocio = new Socio(nombre, direccion, telefono);
            socioDAO.guardar(nuevoSocio);
            cargarSocios();
        } else {
            mostrarAlerta("Complete todos los campos.");
        }
    }

    private void modificarSocio() {
        Socio seleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(campoNombre.getText().trim());
            seleccionado.setDireccion(campoDireccion.getText().trim());
            seleccionado.setTelefono(campoTelefono.getText().trim());
            socioDAO.actualizar(seleccionado);
            cargarSocios();
        } else {
            mostrarAlerta("Seleccione un socio para modificar.");
        }
    }

    private void eliminarSocio() {
        Socio seleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            socioDAO.eliminar(seleccionado);
            cargarSocios();
        } else {
            mostrarAlerta("Seleccione un socio para eliminar.");
        }
    }

    private void buscarSocio() {
        String criterio = campoBusqueda.getText().trim();
        if (!criterio.isEmpty()) {
            listaSocios.setAll(socioDAO.buscarPorNombre(criterio));
        } else {
            cargarSocios();
        }
    }

    private void cargarSocios() {
        listaSocios.setAll(socioDAO.listarTodos());
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
