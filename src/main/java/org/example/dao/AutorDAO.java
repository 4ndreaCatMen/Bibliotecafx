package org.example.dao;

import org.example.modelo.Autor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AutorDAO {

    public void guardar(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(autor);
        transaction.commit();
        session.close();
    }

    public List<Autor> obtenerTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autores = session.createQuery("FROM Autor", Autor.class).list();
        session.close();
        return autores;
    }

    public List<Autor> buscarPorNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autores = session.createQuery(
                        "FROM Autor WHERE nombre LIKE :nombre", Autor.class)
                .setParameter("nombre", "%" + nombre + "%")
                .list();
        session.close();
        return autores;
    }

    public void eliminar(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(autor);
        transaction.commit();
        session.close();
    }
}
