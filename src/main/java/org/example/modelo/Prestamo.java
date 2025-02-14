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
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion_prevista", nullable = false)
    private LocalDate fechaDevolucionPrevista;

    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    // Constructor vacío requerido por Hibernate
    public Prestamo() {
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionPrevista = LocalDate.now().plusWeeks(2); // Ejemplo: 2 semanas por defecto
    }

    // Constructor con parámetros
    public Prestamo(Libro libro, Socio socio, LocalDate fechaDevolucionPrevista) {
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, Libro libro, Socio socio) {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) {
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }




    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", libro=" + (libro != null ? libro.getTitulo() : "null") +
                ", socio=" + (socio != null ? socio.getNombre() : "null") +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucionPrevista=" + fechaDevolucionPrevista +
                ", fechaDevolucionReal=" + fechaDevolucionReal +
                '}';
    }


}