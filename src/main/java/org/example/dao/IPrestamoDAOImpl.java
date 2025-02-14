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
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                // Verificar disponibilidad del libro
                Long count = session.createQuery(
                                "SELECT COUNT(p) FROM Prestamo p WHERE p.libro.id = :libroId " +
                                        "AND p.fechaDevolucionReal IS NULL", Long.class)
                        .setParameter("libroId", prestamo.getLibro().getId())
                        .getSingleResult();

                if (count > 0) {
                    throw new IllegalStateException("El libro ya está prestado");
                }

                // Establecer fechas por defecto
                if (prestamo.getFechaPrestamo() == null) {
                    prestamo.setFechaPrestamo(LocalDate.now());
                }

                // Actualizar estado del libro
                Libro libro = prestamo.getLibro();
                libro.setDisponible(false);
                session.persist(prestamo);
                session.update(libro);

                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw new RuntimeException("Error al guardar préstamo: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Prestamo prestamoActualizado = session.merge(prestamo);

                // Si se registra la devolución real
                if (prestamoActualizado.getFechaDevolucionReal() != null) {
                    Libro libro = prestamoActualizado.getLibro();
                    libro.setDisponible(true);
                    session.update(libro);
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw new RuntimeException("Error al actualizar préstamo: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void eliminar(Prestamo prestamo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                // Recargar el préstamo para asegurar el estado actual
                Prestamo prestamoManaged = session.get(Prestamo.class, prestamo.getId());

                // Restaurar disponibilidad del libro si estaba activo
                if (prestamoManaged.getFechaDevolucionReal() == null) {
                    Libro libro = prestamoManaged.getLibro();
                    libro.setDisponible(true);
                    session.update(libro);
                }

                session.delete(prestamoManaged);
                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw new RuntimeException("Error al eliminar préstamo: " + e.getMessage(), e);
            }
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
                    "FROM Prestamo WHERE fechaDevolucionReal IS NULL",
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
                    ).setParameter("idSocio", idSocio)
                    .getResultList();
        }
    }
}