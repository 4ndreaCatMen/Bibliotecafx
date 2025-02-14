package org.example.dao;

import org.example.modelo.Socio;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ISocioDAOImpl implements ISocioDAO {
    @Override
    public void guardar(Socio socio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(socio);
            tx.commit();
        }
    }

    @Override
    public void actualizar(Socio socio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                // Usamos merge en lugar de update para evitar NonUniqueObjectException
                Socio socioActualizado = (Socio) session.merge(socio);

                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback(); // Rollback en caso de error
                throw new RuntimeException("Error actualizando socio: " + e.getMessage(), e);
            }
        }
    }


    @Override
    public List<Socio> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Socio WHERE LOWER(nombre) LIKE LOWER(:nombre)", Socio.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .list();
        }
    }
    @Override
    public void eliminar(Socio socio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(socio);
            tx.commit();
        }
    }

    @Override
    public Socio buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Socio socio = session.get(Socio.class, id);
        session.close();
        return socio;
    }

    @Override
    public List<Socio> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socio", Socio.class).getResultList();
        }
    }

    @Override
    public List<Socio> buscarPorTelefono(String telefono) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Socio> socios = session.createQuery("FROM Socio WHERE telefono = :telefono", Socio.class)
                .setParameter("telefono", telefono).list();
        session.close();
        return socios;
    }
}


