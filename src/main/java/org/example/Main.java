package org.example;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.dao.HibernateUtil;
import org.example.modelo.Autor;
import org.example.modelo.Libro;
import org.example.modelo.Socio;
import org.example.modelo.Prestamo;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("🔄 Iniciando inserción de datos en la base de datos...");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // INSERTAR AUTORES
            Autor autor1 = new Autor("Jorge Luis Borges", "Argentina");
            Autor autor2 = new Autor("Isabel Allende", "Chile");
            Autor autor3 = new Autor("Mario Vargas Llosa", "Perú");
            Autor autor4 = new Autor("Julio Cortázar", "Argentina");
            Autor autor5 = new Autor("Miguel de Cervantes", "España");
            Autor autor6 = new Autor("Jane Austen", "Reino Unido");
            Autor autor7 = new Autor("Franz Kafka", "República Checa");
            Autor autor8 = new Autor("Haruki Murakami", "Japón");

            session.persist(autor1);
            session.persist(autor2);
            session.persist(autor3);
            session.persist(autor4);
            session.persist(autor5);
            session.persist(autor6);
            session.persist(autor7);
            session.persist(autor8);
            System.out.println("Autores insertados.");

            // INSERTAR LIBROS
            Libro libro1 = new Libro("Ficciones", "978-84-206-2923-6", "Emece Editores", 1944, autor1);
            Libro libro2 = new Libro("La casa de los espíritus", "978-84-663-1724-4", "Plaza & Janés", 1982, autor2);
            Libro libro3 = new Libro("La ciudad y los perros", "978-84-663-0538-7", "Alfaguara", 1963, autor3);
            Libro libro4 = new Libro("Rayuela", "978-84-663-2713-4", "Alfaguara", 1963, autor4);
            Libro libro5 = new Libro("Don Quijote de la Mancha", "978-84-670-1625-8", "Ediciones Cátedra", 1605, autor5);
            Libro libro6 = new Libro("Orgullo y Prejuicio", "978-84-376-0496-1", "Penguin Clásicos", 1813, autor6);
            Libro libro7 = new Libro("La metamorfosis", "978-84-376-0497-8", "Alianza Editorial", 1915, autor7);
            Libro libro8 = new Libro("Tokio Blues", "978-84-9838-376-3", "Tusquets Editores", 1987, autor8);

            session.persist(libro1);
            session.persist(libro2);
            session.persist(libro3);
            session.persist(libro4);
            session.persist(libro5);
            session.persist(libro6);
            session.persist(libro7);
            session.persist(libro8);
            System.out.println("Libros insertados.");

            // INSERTAR SOCIOS
            Socio socio1 = new Socio("Patricia Catalán", "Calle A, 123", "654321987");
            Socio socio2 = new Socio("Gema García", "Calle B, 456", "678912345");
            Socio socio3 = new Socio("Andrea Catalán", "Calle C, 789", "612345678");
            Socio socio4 = new Socio("Gema Menacho", "Avenida Central 10", "612345679"); // Cambié el número
            Socio socio5 = new Socio("Ginés Sanz", "Calle del Sol 5", "634567890");
            Socio socio6 = new Socio("Áurea Cabrera", "Paseo de la Paz 22", "689123456");
            Socio socio7 = new Socio("José Miguel Teruel", "Plaza Mayor 3", "677890123");
            Socio socio8 = new Socio("Luis Catalán", "Calle del Bosque 45", "645678901");


            session.persist(socio1);
            session.persist(socio2);
            session.persist(socio3);
            session.persist(socio4);
            session.persist(socio5);
            session.persist(socio6);
            session.persist(socio7);
            session.persist(socio8);
            System.out.println("Socios insertados.");

            // REGISTRAR PRÉSTAMO (Libro 1 "Ficciones" para Socio 1)
            Prestamo prestamo2 = new Prestamo();
            prestamo2.setLibro(libro2);
            prestamo2.setSocio(socio2);
            prestamo2.setFechaPrestamo(LocalDate.now());
            prestamo2.setFechaDevolucion(LocalDate.now().plusDays(10));

            Prestamo prestamo3 = new Prestamo();
            prestamo3.setLibro(libro3);
            prestamo3.setSocio(socio3);
            prestamo3.setFechaPrestamo(LocalDate.now());
            prestamo3.setFechaDevolucion(LocalDate.now().plusDays(7));

            session.persist(prestamo2);
            session.persist(prestamo3);

            System.out.println(" Préstamo registrado.");

            // CONFIRMAR TRANSACCIÓN
            transaction.commit();
            System.out.println("Todos los datos fueron insertados correctamente.");
        } catch (Exception e) {
            System.err.println("Error durante la inserción: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
