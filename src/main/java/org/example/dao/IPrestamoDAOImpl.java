package org.example.dao;

import org.example.modelo.Prestamo;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class IPrestamoDAOImpl implements IPrestamoDAO {
    @Override
    public void guardar(Prestamo prestamo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction(); // ✅ No es necesario hacer cast
        session.save(prestamo);
        tx.commit();
        session.close();
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(prestamo);
        tx.commit();
        session.close();
    }

    @Override
    public void eliminar(Prestamo prestamo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(prestamo);
        tx.commit();
        session.close();
    }

    @Override
    public Prestamo buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Prestamo prestamo = session.get(Prestamo.class, id);
        session.close();
        return prestamo;
    }

    @Override
    public List<Prestamo> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Prestamo> prestamos = session.createQuery("FROM Prestamo", Prestamo.class).list();
        session.close();
        return prestamos;
    }

    public List<Prestamo> listarPrestamosActivos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Prestamo> prestamos = session.createQuery("FROM Prestamo WHERE fechaDevolucion IS NULL", Prestamo.class).list();
        session.close();
        return prestamos;
    }

    @Override
    public List<Prestamo> listarHistorialPorSocio(int idSocio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Prestamo> prestamos = session.createQuery("FROM Prestamo WHERE socio.id = :idSocio", Prestamo.class)
                .setParameter("idSocio", idSocio).list();
        session.close();
        return prestamos;
    }
}