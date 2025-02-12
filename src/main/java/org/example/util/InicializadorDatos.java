package org.example.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.modelo.Autor;  // Ajusta el paquete si es necesario
import org.example.util.HibernateUtil;

public class InicializadorDatos {

    public static void insertarDatosIniciales() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Verifica si la tabla tiene datos
            Long count = (Long) session.createQuery("SELECT COUNT(a) FROM Autor a").uniqueResult();
            if (count == 0) {  // Si no hay datos, los insertamos

                // Autores de prueba
                Autor autor1 = new Autor("Argentina", "Jorge Luis Borges");
                Autor autor2 = new Autor("Chile", "Isabel Allende");
                Autor autor3 = new Autor("Perú", "Mario Vargas Llosa");
                Autor autor4 = new Autor("Argentina", "Julio Cortázar");
                Autor autor5 = new Autor("España", "Miguel de Cervantes");
                Autor autor6 = new Autor("Reino Unido", "Jane Austen");
                Autor autor7 = new Autor("República Checa", "Franz Kafka");
                Autor autor8 = new Autor("Japón", "Haruki Murakami");

                session.persist(autor1);
                session.persist(autor2);
                session.persist(autor3);
                session.persist(autor4);
                session.persist(autor5);
                session.persist(autor6);
                session.persist(autor7);
                session.persist(autor8);

                System.out.println("✅ Datos iniciales insertados en la base de datos.");
            } else {
                System.out.println("🔹 La base de datos ya tiene datos, no es necesario insertarlos.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
