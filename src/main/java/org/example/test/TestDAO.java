package org.example.test;

import org.example.dao.*;
import org.example.modelo.*;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        IAutorDAO autorDAO = new IAutorDAOImpl();
        ILibroDAO libroDAO = new ILibroDAOImpl();
        ISocioDAO socioDAO = new ISocioDAOImpl();
        IPrestamoDAO prestamoDAO = new IPrestamoDAOImpl();

        // 🔹 Prueba 1: Listar todos los libros
        System.out.println("📚 Lista de libros:");
        List<Libro> libros = libroDAO.listarTodos();
        for (Libro libro : libros) {
            System.out.println(libro.getId() + " - " + libro.getTitulo());
        }

        // 🔹 Prueba 2: Buscar libros con 'Java' en el título (Debe devolver 0 resultados)
        System.out.println("\n🔍 Buscar libros con 'Java':");
        List<Libro> librosJava = libroDAO.buscarPorTitulo("Java");
        if (librosJava.isEmpty()) {
            System.out.println("No se encontraron libros con 'Java'.");
        }

        // 🔹 Prueba 3: Listar libros disponibles (Debe excluir los libros con ID 2 y 3)
        System.out.println("\n📖 Libros disponibles:");
        List<Libro> librosDisponibles = libroDAO.listarDisponibles();
        for (Libro libro : librosDisponibles) {
            System.out.println(libro.getId() + " - " + libro.getTitulo());
        }

        // 🔹 Prueba 4: Listar préstamos activos (Debe mostrar 2 préstamos)
        System.out.println("\n📑 Préstamos activos:");
        List<Prestamo> prestamosActivos = prestamoDAO.listarPrestamosActivos();
        for (Prestamo prestamo : prestamosActivos) {
            System.out.println("Libro ID: " + prestamo.getLibro().getId() +
                    ", Socio ID: " + prestamo.getSocio().getId() +
                    ", Fecha préstamo: " + prestamo.getFechaPrestamo());
        }

        // 🔹 Prueba 5: Historial de préstamos del socio ID 2 (Debe mostrar solo el libro ID 2)
        int idSocio2 = 2;
        System.out.println("\n📜 Historial de préstamos del socio ID " + idSocio2 + ":");
        List<Prestamo> historial2 = prestamoDAO.listarHistorialPorSocio(idSocio2);
        for (Prestamo prestamo : historial2) {
            System.out.println("Libro ID: " + prestamo.getLibro().getId() +
                    ", Fecha préstamo: " + prestamo.getFechaPrestamo() +
                    ", Fecha devolución: " + prestamo.getFechaDevolucion());
        }

        // 🔹 Prueba 6: Historial de préstamos del socio ID 3 (Debe mostrar solo el libro ID 3)
        int idSocio3 = 3;
        System.out.println("\n📜 Historial de préstamos del socio ID " + idSocio3 + ":");
        List<Prestamo> historial3 = prestamoDAO.listarHistorialPorSocio(idSocio3);
        for (Prestamo prestamo : historial3) {
            System.out.println("Libro ID: " + prestamo.getLibro().getId() +
                    ", Fecha préstamo: " + prestamo.getFechaPrestamo() +
                    ", Fecha devolución: " + prestamo.getFechaDevolucion());
        }
    }
}
