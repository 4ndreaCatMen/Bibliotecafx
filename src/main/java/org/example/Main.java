package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.dao.HibernateUtil;
import org.example.modelo.Autor;
import org.example.modelo.Libro;

public class Main {
    public static void main(String[] args) {
        System.out.println("🔄 Iniciando prueba de Hibernate...");

        // Abrir sesión con Hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Crear un autor de prueba
            Autor autor = new Autor("Gabriel García Márquez", "Colombiana");
            session.persist(autor);  // Guardar en la base de datos

            // Crear un libro de prueba asociado a ese autor
            Libro libro = new Libro("Cien años de soledad", "978-84-376-0494-7", "Sudamericana", 1967, autor);
            session.persist(libro);  // Guardar en la base de datos

            // Confirmar la transacción
            transaction.commit();

            System.out.println("✅ Datos insertados correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error en la prueba: " + e.getMessage());
        }
    }
}
