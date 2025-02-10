package org.example.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.IPrestamoDAO;
import org.example.dao.IPrestamoDAOImpl;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
import org.example.dao.ISocioDAO;
import org.example.dao.ISocioDAOImpl;
import org.example.modelo.Libro;
import org.example.modelo.Prestamo;
import org.example.modelo.Socio;

import java.time.LocalDate;
import java.util.List;

public class PrestamosVista {
    private final IPrestamoDAO prestamoDAO = new IPrestamoDAOImpl();
    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private final ISocioDAO socioDAO = new ISocioDAOImpl();

    private ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList();
    private TableView<Prestamo> tablaPrestamos;
    private ComboBox<Socio> comboSocios;
    private ComboBox<Libro> comboLibros;

    public void mostrar() {
        Stage ventana = new Stage();
        ventana.setTitle("Gestión de Préstamos");

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Registrar Préstamo"));

        inicializarComponentes(layout);
        inicializarTabla();
        inicializarBotones(layout);

        layout.getChildren().add(tablaPrestamos);

        Scene escena = new Scene(layout, 700, 500);
        ventana.setScene(escena);
        ventana.show();

        cargarPrestamos();
    }

    private void inicializarComponentes(VBox layout) {
        comboSocios = new ComboBox<>();
        comboSocios.setPromptText("Seleccione un socio");
        comboSocios.setItems(FXCollections.observableArrayList(socioDAO.listarTodos()));

        comboLibros = new ComboBox<>();
        comboLibros.setPromptText("Seleccione un libro");
        comboLibros.setItems(FXCollections.observableArrayList(libroDAO.listarDisponibles()));

        layout.getChildren().addAll(new Label("Socio:"), comboSocios, new Label("Libro:"), comboLibros);
    }

    private void inicializarTabla() {
        tablaPrestamos = new TableView<>(listaPrestamos);
        tablaPrestamos.getColumns().addAll(
                crearColumna("Socio", "socio.nombre"),
                crearColumna("Libro", "libro.titulo"),
                crearColumna("Fecha Préstamo", "fechaPrestamo"),
                crearColumna("Fecha Devolución", "fechaDevolucion")
        );
    }

    private TableColumn<Prestamo, String> crearColumna(String titulo, String propiedad) {
        TableColumn<Prestamo, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(propiedad));
        return columna;
    }

    private void inicializarBotones(VBox layout) {
        Button btnRegistrar = new Button("Registrar Préstamo");
        btnRegistrar.setOnAction(e -> registrarPrestamo());

        Button btnDevolver = new Button("Devolver Libro");
        btnDevolver.setOnAction(e -> devolverLibro());

        Button btnHistorial = new Button("Ver Historial de Socio");
        btnHistorial.setOnAction(e -> verHistorialSocio());

        layout.getChildren().addAll(btnRegistrar, btnDevolver, btnHistorial);
    }

    private void registrarPrestamo() {
        Socio socio = comboSocios.getValue();
        Libro libro = comboLibros.getValue();

        if (socio != null && libro != null) {
            Prestamo nuevoPrestamo = new Prestamo(socio, libro, LocalDate.now(), null);
            prestamoDAO.guardar(nuevoPrestamo);
            cargarPrestamos();
        } else {
            mostrarAlerta("Seleccione un socio y un libro.");
        }
    }

    private void devolverLibro() {
        Prestamo seleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setFechaDevolucion(LocalDate.now());
            prestamoDAO.actualizar(seleccionado);
            cargarPrestamos();
        } else {
            mostrarAlerta("Seleccione un préstamo para devolver.");
        }
    }

    private void verHistorialSocio() {
        Socio socio = comboSocios.getValue();
        if (socio != null) {
            listaPrestamos.setAll(prestamoDAO.listarHistorialPorSocio(socio.getId()));
        } else {
            mostrarAlerta("Seleccione un socio para ver su historial.");
        }
    }

    private void cargarPrestamos() {
        listaPrestamos.setAll(prestamoDAO.listarPrestamosActivos());
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
