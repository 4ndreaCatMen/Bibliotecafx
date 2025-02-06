package org.example.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Cargar configuración de hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("✅ Conexión exitosa con Hibernate y MySQL.");
        } catch (Throwable ex) {
            System.err.println("❌ Error al inicializar Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
