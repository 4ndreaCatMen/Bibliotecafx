package org.example.dao;

import org.example.modelo.Autor;
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
            session.merge(autor); // Usamos merge en lugar de update
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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




