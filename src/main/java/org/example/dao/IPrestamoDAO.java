package org.example.dao;

import org.example.modelo.Prestamo;

import java.util.List;

public interface IPrestamoDAO {
    void guardar(Prestamo prestamo);

    void actualizar(Prestamo prestamo);

    void eliminar(Prestamo prestamo);

    Prestamo buscarPorId(int id);

    List<Prestamo> listarTodos();
}
