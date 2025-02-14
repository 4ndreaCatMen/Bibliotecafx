package org.example.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.IPrestamoDAO;
import org.example.dao.IPrestamoDAOImpl;
import org.example.dao.ILibroDAO;
import org.example.dao.ILibroDAOImpl;
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
    private DatePicker fechaDevolucionPicker;
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
    private final ObservableList<Prestamo> prestamosList = FXCollections.observableArrayList();
    private final ObservableList<Prestamo> historialList = FXCollections.observableArrayList();

    public void initialize() {
        socioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        libroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
        fechaPrestamoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));
        fechaDevolucionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFechaDevolucion() != null ? cellData.getValue().getFechaDevolucion().toString() : "Pendiente"));

        prestamosTable.setItems(prestamosList);

        historialSocioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        historialLibroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
        historialFechaPrestamoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));
        historialFechaDevolucionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFechaDevolucion() != null ? cellData.getValue().getFechaDevolucion().toString() : "Pendiente"));

        historialTable.setItems(historialList);

        cargarPrestamos();
        cargarLibrosDisponibles();
    }

    @FXML
    private void registrarPrestamo() {
        Socio socio = socioComboBox.getValue();
        Libro libro = libroComboBox.getValue();
        LocalDate fechaPrestamo = fechaPrestamoPicker.getValue();
        LocalDate fechaDevolucion = fechaDevolucionPicker.getValue();

        if (socio == null || libro == null || fechaPrestamo == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

        Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, libro, socio);
        prestamoDAO.guardar(prestamo);
        prestamosList.add(prestamo);
        limpiarCampos();
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

    private void cargarLibrosDisponibles() {
        List<Libro> librosDisponibles = libroDAO.listarDisponibles();
        libroComboBox.setItems(FXCollections.observableArrayList(librosDisponibles));
    }

    private void limpiarCampos() {
        socioComboBox.setValue(null);
        libroComboBox.setValue(null);
        fechaPrestamoPicker.setValue(null);
        fechaDevolucionPicker.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
