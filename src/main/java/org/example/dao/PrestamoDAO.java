package org.example.dao;

import org.example.modelo.Prestamo;
import org.example.modelo.Socio;
import org.hibernate.Session;
import java.util.List;

public class PrestamoDAO {

    public void guardar(Prestamo prestamo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(prestamo);
        session.getTransaction().commit();
        session.close();
    }

    public List<Prestamo> obtenerHistorialSocio(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Prestamo> prestamos = session.createQuery(
                        "FROM Prestamo WHERE socio.id = :socioId", Prestamo.class)
                .setParameter("socioId", socio.getId())
                .list();
        session.close();
        return prestamos;
    }
}
