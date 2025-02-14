package org.example.modelo;

import jakarta.persistence.*;
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column
    private String editorial;

    @Column
    private int anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    // Constructor vacío obligatorio para Hibernate
    public Libro() {}

    // Constructor con parámetros
    public Libro(String titulo, String isbn, String editorial, int anioPublicacion, Autor autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.autor = autor;
    }


    // Getters y Setters
    public int getId() {

        return id;
    }

    public String getTitulo() {

        return titulo;
    }

    public void setTitulo(String titulo) {

        this.titulo = titulo;
    }

    public String getIsbn() {

        return isbn;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public String getEditorial() {

        return editorial;
    }

    public void setEditorial(String editorial) {

        this.editorial = editorial;
    }

    public int getAnioPublicacion() {

        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {

        this.anioPublicacion = anioPublicacion;
    }

    public Autor getAutor() {

        return autor;
    }

    public void setAutor(Autor autor) {

        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editorial='" + editorial + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", autor=" + autor.getNombre() +
                '}';
    }

    public void setDisponible(boolean disponible) {
    }
}
