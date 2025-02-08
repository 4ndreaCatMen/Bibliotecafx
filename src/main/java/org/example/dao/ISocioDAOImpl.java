package org.example.dao;

import org.example.modelo.Socio;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ISocioDAOImpl implements ISocioDAO {
    @Override
    public void guardar(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(socio);
        tx.commit();
        session.close();
    }

    @Override
    public void actualizar(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(socio);
        tx.commit();
        session.close();
    }

    @Override
    public void eliminar(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(socio);
        tx.commit();
        session.close();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Socio> socios = session.createQuery("FROM Socio", Socio.class).list();
        session.close();
        return socios;
    }

    @Override
    public List<Socio> buscarPorNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Socio> socios = session.createQuery("FROM Socio WHERE nombre LIKE :nombre", Socio.class)
                .setParameter("nombre", "%" + nombre + "%").list();
        session.close();
        return socios;
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


