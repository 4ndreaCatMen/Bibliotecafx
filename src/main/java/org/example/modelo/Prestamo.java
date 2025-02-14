package org.example.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "socio_id")
    private Socio socio;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @Column
    private LocalDate fechaDevolucion;

    // Constructor vacío obligatorio para Hibernate
    public Prestamo() {}

    // Constructor para nuevos préstamos (sin devolución)
    public Prestamo(Socio socio, Libro libro, LocalDate fechaPrestamo) {
        this.socio = socio;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
    }

    // Constructor completo (para préstamos con devolución)
    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, Libro libro, Socio socio) {
        this.socio = socio;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Métodos solicitados
    public void setDisponible(boolean disponible) {
        if (libro != null) {
            libro.setDisponible(disponible);
        }
    }

    public LocalDate getDevolucion() {
        return fechaDevolucion;
    }

    // Getters y Setters completos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        String nombreSocio = (socio != null) ? socio.getNombre() : "Socio desconocido";
        String tituloLibro = (libro != null) ? libro.getTitulo() : "Libro desconocido";

        return "Préstamo ID: " + id +
                " | Libro: " + tituloLibro +
                " | Socio: " + nombreSocio +
                " | Fecha préstamo: " + fechaPrestamo +
                " | Devolución: " + (fechaDevolucion != null ? fechaDevolucion : "Pendiente");
    }
}