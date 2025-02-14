package org.example.dao;

import org.example.modelo.Libro;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class ILibroDAOImpl implements ILibroDAO {
    @Override
    public void guardar(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(libro); // Inserta el objeto
            transaction.commit();
            System.out.println("Libro guardado: " + libro.getTitulo());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al guardar libro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction(); // <-- Añade transacción
            List<Libro> libros = session.createQuery("FROM Libro", Libro.class).list();
            session.getTransaction().commit();
            return libros;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery("FROM Libro WHERE titulo LIKE :titulo", Libro.class)
                .setParameter("titulo", "%" + titulo + "%").list();
        session.close();
        return libros;
    }

    @Override
    public List<Libro> buscarPorAutor(String nombreAutor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery("SELECT l FROM Libro l JOIN l.autor a WHERE a.nombre LIKE :nombreAutor", Libro.class)
                .setParameter("nombreAutor", "%" + nombreAutor + "%").list();
        session.close();
        return libros;
    }

    @Override
    public List<Libro> buscarPorISBN(String isbn) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery("FROM Libro WHERE isbn = :isbn", Libro.class)
                .setParameter("isbn", isbn).list();
        session.close();
        return libros;
    }

    @Override
    public List<Libro> listarDisponibles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = session.createQuery(
                "SELECT l FROM Libro l WHERE l.id NOT IN (SELECT p.libro.id FROM Prestamo p WHERE p.fechaDevolucion IS NULL)",
                Libro.class
        ).list();
        session.close();
        return libros;
    }
}

