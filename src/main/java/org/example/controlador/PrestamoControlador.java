package org.example.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
import org.example.dao.IPrestamoDAO;
import org.example.dao.IPrestamoDAOImpl;
import org.example.dao.ISocioDAO;
import org.example.dao.ISocioDAOImpl;
import org.example.modelo.Libro;
import org.example.modelo.Prestamo;
import org.example.modelo.Socio;
import java.time.LocalDate;
import java.util.List;

public class PrestamoControlador {

    @FXML
    private ComboBox<Socio> socioComboBox;
    @FXML
    private ComboBox<Libro> libroComboBox;
    @FXML
    private DatePicker fechaPrestamoPicker;
    @FXML
    private TableView<Prestamo> prestamosTable;
    @FXML
    private TableColumn<Prestamo, String> socioColumn;
    @FXML
    private TableColumn<Prestamo, String> libroColumn;
    @FXML
    private TableColumn<Prestamo, String> fechaPrestamoColumn;
    @FXML
    private TableColumn<Prestamo, String> fechaDevolucionColumn;
    @FXML
    private TableView<Prestamo> historialTable;
    @FXML
    private TableColumn<Prestamo, String> historialSocioColumn;
    @FXML
    private TableColumn<Prestamo, String> historialLibroColumn;
    @FXML
    private TableColumn<Prestamo, String> historialFechaPrestamoColumn;
    @FXML
    private TableColumn<Prestamo, String> historialFechaDevolucionColumn;

    private final IPrestamoDAO prestamoDAO = new IPrestamoDAOImpl();
    private final ILibroDAO libroDAO = new ILibroDAOImpl();
    private final ISocioDAO socioDAO = new ISocioDAOImpl(); // ✅ Nuevo DAO para socios
    private final ObservableList<Prestamo> prestamosList = FXCollections.observableArrayList();
    private final ObservableList<Prestamo> historialList = FXCollections.observableArrayList();

    public void initialize() {
        configurarColumnasTablas();
        cargarDatosIniciales();
        configurarFechaPredeterminada();
    }

    private void configurarColumnasTablas() {
        // Configurar columnas para préstamos activos
        socioColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        libroColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
        fechaPrestamoColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));
        fechaDevolucionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaDevolucion() != null ?
                        cellData.getValue().getFechaDevolucion().toString() : "Pendiente"));
        prestamosTable.setItems(prestamosList);

        // Configurar columnas para historial
        historialSocioColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        historialLibroColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
        historialFechaPrestamoColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));
        historialFechaDevolucionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaDevolucion() != null ?
                        cellData.getValue().getFechaDevolucion().toString() : "Pendiente"));
        historialTable.setItems(historialList);
    }

    private void cargarDatosIniciales() {
        cargarSocios();
        cargarLibrosDisponibles();
        cargarPrestamos();
    }

    private void configurarFechaPredeterminada() {
        fechaPrestamoPicker.setValue(LocalDate.now()); // Fecha actual por defecto
    }

    @FXML
    private void registrarPrestamo() {
        Socio socio = socioComboBox.getValue();
        Libro libro = libroComboBox.getValue();
        LocalDate fechaPrestamo = fechaPrestamoPicker.getValue();

        if (socio == null || libro == null || fechaPrestamo == null) {
            mostrarAlerta("Error", "Socio, libro y fecha de préstamo son obligatorios");
            return;
        }

        try {
            Prestamo prestamo = new Prestamo(socio, libro, fechaPrestamo); // ✅ Constructor correcto
            prestamoDAO.guardar(prestamo);
            cargarPrestamos(); // Actualizar lista desde la base de datos
            cargarLibrosDisponibles(); // Actualizar libros disponibles
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void cargarPrestamos() {
        List<Prestamo> prestamos = prestamoDAO.listarPrestamosActivos();
        prestamosList.setAll(prestamos);
    }

    @FXML
    private void listarHistorialPrestamos() {
        Socio socio = socioComboBox.getValue();
        if (socio != null) {
            List<Prestamo> historial = prestamoDAO.listarHistorialPorSocio(socio.getId());
            historialList.setAll(historial);
        } else {
            mostrarAlerta("Error", "Seleccione un socio para ver su historial");
        }
    }

    private void cargarSocios() {
        List<Socio> socios = socioDAO.listarTodos();
        socioComboBox.setItems(FXCollections.observableArrayList(socios));
    }

    private void cargarLibrosDisponibles() {
        List<Libro> librosDisponibles = libroDAO.listarDisponibles();
        libroComboBox.setItems(FXCollections.observableArrayList(librosDisponibles));
    }

    private void limpiarCampos() {
        socioComboBox.setValue(null);
        libroComboBox.setValue(null);
        fechaPrestamoPicker.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}