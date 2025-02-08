package org.example.dao;

import org.example.modelo.Libro;

import java.util.List;

public interface ILibroDAO {
    void guardar(Libro libro);

    void actualizar(Libro libro);

    void eliminar(Libro libro);

    Libro buscarPorId(int id);

    List<Libro> listarTodos();
}
