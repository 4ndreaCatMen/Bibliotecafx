package org.example.dao;

import org.example.modelo.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SocioDAO {

    public void guardar(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(socio);
        transaction.commit();
        session.close();
    }

    public List<Socio> obtenerTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Socio> socios = session.createQuery("FROM Socio", Socio.class).list();
        session.close();
        return socios;
    }

    public void eliminar(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(socio);
        transaction.commit();
        session.close();
    }
}
