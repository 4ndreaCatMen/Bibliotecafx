package org.example.dao;

import org.example.modelo.Libro;
import org.hibernate.Session;
import java.util.List;

public class LibroDAO {

    public void guardar(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(libro);
        session.getTransaction().commit();
        session.close();
    }

    public List<Libro> obtenerTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery("FROM Libro", Libro.class).list();
        session.close();
        return libros;
    }

    public List<Libro> buscarPorAutor(String autorNombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery(
                        "FROM Libro WHERE autor.nombre = :nombre", Libro.class)
                .setParameter("nombre", autorNombre)
                .list();
        session.close();
        return libros;
    }
}
