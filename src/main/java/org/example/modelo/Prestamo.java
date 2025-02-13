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
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @Column
    private LocalDate fechaDevolucion;

    // Constructor vacío obligatorio para Hibernate
    public Prestamo(LocalDate of, LocalDate localDate, Libro la_ciudad_y_los_perros, Socio andrea_catalán) {}

    // Constructor con parámetros
    public Prestamo(Socio socio, Libro libro, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.socio = socio;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y Setters
    public int getId() {

        return id;
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
        return "Prestamo{" +
                "id=" + id +
                ", socio=" + socio.getNombre() +
                ", libro=" + libro.getTitulo() +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}
