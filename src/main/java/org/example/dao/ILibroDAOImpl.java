package org.example.dao;
import org.example.modelo.Libro;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
public class ILibroDAOImpl implements ILibroDAO {
    @Override
    public void guardar(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(libro);
        tx.commit();
        session.close();
    }

    @Override
    public void actualizar(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(libro);
        tx.commit();
        session.close();
    }

    @Override
    public void eliminar(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(libro);
        tx.commit();
        session.close();
    }

    @Override
    public Libro buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Libro libro = session.get(Libro.class, id);
        session.close();
        return libro;
    }

    @Override
    public List<Libro> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery("FROM Libro", Libro.class).list();
        session.close();
        return libros;
    }
}
