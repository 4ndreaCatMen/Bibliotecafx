package org.example.dao;

import org.example.modelo.Libro;
import org.example.modelo.Prestamo;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

public class IPrestamoDAOImpl implements IPrestamoDAO {

    @Override
    public void guardar(Prestamo prestamo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Verificar disponibilidad del libro
            Long count = session.createQuery(
                            "SELECT COUNT(p) FROM Prestamo p WHERE p.libro.id = :libroId AND p.fechaDevolucion IS NULL",
                            Long.class
                    )
                    .setParameter("libroId", prestamo.getLibro().getId())
                    .getSingleResult();

            if (count > 0) {
                throw new IllegalStateException("El libro ya está prestado");
            }

            // Registrar préstamo
            prestamo.getLibro().setDisponible(false);
            session.persist(prestamo);
            tx.commit();
        }
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Prestamo prestamoActualizado = session.merge(prestamo);

            // Si se registra la devolución
            if (prestamoActualizado.getFechaDevolucion() != null) {
                prestamoActualizado.getLibro().setDisponible(true);
                session.merge(prestamoActualizado.getLibro());
            }

            tx.commit();
        }
    }

    @Override
    public void eliminar(Prestamo prestamo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Prestamo prestamoManaged = session.get(Prestamo.class, prestamo.getId());

            // Restaurar disponibilidad del libro si no se devolvió
            if (prestamoManaged.getFechaDevolucion() == null) { // ✅ Campo corregido
                Libro libro = prestamoManaged.getLibro();
                libro.setDisponible(true);
                session.merge(libro);
            }

            session.delete(prestamoManaged);
            tx.commit();
        }
    }

    @Override
    public Prestamo buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Prestamo.class, id);
        }
    }

    @Override
    public List<Prestamo> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Prestamo", Prestamo.class).getResultList();
        }
    }

    @Override
    public List<Prestamo> listarPrestamosActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Prestamo WHERE fechaDevolucion IS NULL",
                    Prestamo.class
            ).getResultList();
        }
    }

    @Override
    public List<Prestamo> listarHistorialPorSocio(int idSocio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Prestamo WHERE socio.id = :idSocio ORDER BY fechaPrestamo DESC",
                            Prestamo.class
                    )
                    .setParameter("idSocio", idSocio)
                    .getResultList();
        }
    }
}