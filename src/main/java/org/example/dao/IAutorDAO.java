package org.example.dao;

import org.example.modelo.Autor;

import java.util.List;

public interface IAutorDAO {
    void guardar(Autor autor);

    void actualizar(Autor autor);

    void eliminar(Autor autor);

    Autor buscarPorId(int id);

    List<Autor> listarTodos();
    List<Autor> buscarPorNombre(String nombre);

}
