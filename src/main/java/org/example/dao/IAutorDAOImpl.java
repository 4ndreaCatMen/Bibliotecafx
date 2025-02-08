package org.example.dao;

import org.example.modelo.Autor;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

// Implementación del DAO para Autor
public class IAutorDAOImpl implements IAutorDAO {
    @Override
    public void guardar(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(autor);
        tx.commit();
        session.close();
    }

    @Override
    public void actualizar(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(autor);
        tx.commit();
        session.close();
    }

    @Override
    public void eliminar(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(autor);
        tx.commit();
        session.close();
    }

    @Override
    public Autor buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Autor autor = session.get(Autor.class, id);
        session.close();
        return autor;
    }

    @Override
    public List<Autor> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autores = session.createQuery("FROM Autor", Autor.class).list();
        session.close();
        return autores;
    }
    @Override
    public List<Autor> buscarPorNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autores = session.createQuery("FROM Autor WHERE nombre LIKE :nombre", Autor.class)
                .setParameter("nombre", "%" + nombre + "%").list();
        session.close();
        return autores;
        }
}
