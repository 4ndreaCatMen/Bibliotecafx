package org.example.dao;

import org.example.modelo.Autor;
import org.example.modelo.Libro;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(autor); // Usa merge para actualizar
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar autor", e);
        }
    }



    @Override
    public void eliminar(Autor autor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Obtener todos los libros del autor
            List<Libro> libros = session.createQuery(
                    "FROM Libro WHERE autor.id = :autorId", Libro.class
            ).setParameter("autorId", autor.getId()).list();

            // Desvincular autor de los libros
            for (Libro libro : libros) {
                libro.setAutor(null);
                session.merge(libro);
            }

            // Eliminar autor
            session.remove(autor);
            tx.commit();
        }
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Autor> autores = session.createQuery("FROM Autor", Autor.class).list();
            session.getTransaction().commit();
            return autores; // La sesión se cierra automáticamente gracias al try-with-resources
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public List<Autor> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Ejecutando búsqueda con nombre: " + nombre);

            List<Autor> autores = session.createQuery(
                            "FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)", Autor.class)
                    .setParameter("nombre", "%" + nombre.toLowerCase() + "%")
                    .getResultList();

            System.out.println("Resultados encontrados en la BD: " + autores);
            return autores;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }





}




