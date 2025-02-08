package org.example.dao;

import org.example.modelo.Socio;

import java.util.List;

public interface ISocioDAO {
    void guardar(Socio socio);

    void actualizar(Socio socio);

    void eliminar(Socio socio);

    Socio buscarPorId(int id);

    List<Socio> listarTodos();
    List<Socio> buscarPorNombre(String nombre);
    List<Socio> buscarPorTelefono(String telefono);

}
