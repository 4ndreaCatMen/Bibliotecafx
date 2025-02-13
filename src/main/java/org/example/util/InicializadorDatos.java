package org.example.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.modelo.Autor;
import org.example.modelo.Libro;
import org.example.modelo.Socio;
import org.example.modelo.Prestamo;
import org.example.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InicializadorDatos {
    public static void insertarDatosIniciales() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Insertar Autores
            Long countAutores = (Long) session.createQuery("SELECT COUNT(a) FROM Autor a").uniqueResult();
            if (countAutores == 0) {
                Autor[] autores = {
                        new Autor("Jorge Luis Borges", "Argentina"),
                        new Autor("Isabel Allende", "Chile"),
                        new Autor("Mario Vargas Llosa", "Perú"),
                        new Autor("Julio Cortázar", "Argentina"),
                        new Autor("Miguel de Cervantes", "España"),
                        new Autor("Jane Austen", "Reino Unido"),
                        new Autor("Franz Kafka", "República Checa"),
                        new Autor("Haruki Murakami", "Japón")
                };
                for (Autor autor : autores) {
                    session.persist(autor);
                }
                System.out.println("✅ Autores insertados.");
            }

            // Obtener autores de la BD para asociarlos a los libros
            List<Autor> listaAutores = session.createQuery("FROM Autor", Autor.class).getResultList();
            Map<String, Autor> mapaAutores = listaAutores.stream().collect(Collectors.toMap(Autor::getNombre, a -> a));

            // Insertar Libros
            Long countLibros = (Long) session.createQuery("SELECT COUNT(l) FROM Libro l").uniqueResult();
            if (countLibros == 0) {
                Libro[] libros = {
                        new Libro("Ficciones", "978-84-206-2923-6", "Emecé Editores", 1944, mapaAutores.get("Jorge Luis Borges")),
                        new Libro("La casa de los espíritus", "978-84-663-1724-4", "Plaza & Janés", 1982, mapaAutores.get("Isabel Allende")),
                        new Libro("La ciudad y los perros", "978-84-663-0538-7", "Alfaguara", 1963, mapaAutores.get("Mario Vargas Llosa")),
                        new Libro("Rayuela", "978-84-663-2713-4", "Alfaguara", 1963, mapaAutores.get("Julio Cortázar")),
                        new Libro("Don Quijote de la Mancha", "978-84-670-1625-8", "Ediciones Cátedra", 1605, mapaAutores.get("Miguel de Cervantes")),
                        new Libro("Orgullo y Prejuicio", "978-84-376-0496-1", "Penguin Clásicos", 1813, mapaAutores.get("Jane Austen")),
                        new Libro("La metamorfosis", "978-84-376-0497-8", "Alianza Editorial", 1915, mapaAutores.get("Franz Kafka")),
                        new Libro("Tokio Blues", "978-84-9838-376-3", "Tusquets Editores", 1987, mapaAutores.get("Haruki Murakami"))
                };


                for (Libro libro : libros) {
                    session.persist(libro);
                }
                System.out.println("✅ Libros insertados.");
            }

            // Insertar Socios
            Long countSocios = (Long) session.createQuery("SELECT COUNT(s) FROM Socio s").uniqueResult();
            if (countSocios == 0) {
                Socio[] socios = {
                        new Socio("Patricia Catalán", "Calle A, 123", "654321987"),
                        new Socio("Gema García", "Calle B, 456", "678912345"),
                        new Socio("Andrea Catalán", "Calle C, 789", "612345678"),
                        new Socio("Gema Menacho", "Avenida Central 10", "612345679"),
                        new Socio("Ginés Sanz", "Calle del Sol 5", "634567890"),
                        new Socio("Áurea Cabrera", "Paseo de la Paz 22", "689123456"),
                        new Socio("José Miguel Teruel", "Plaza Mayor 3", "677890123"),
                        new Socio("Luis Catalán", "Calle del Bosque 45", "645678901")
                };

                for (Socio socio : socios) {
                    session.persist(socio);
                }
                System.out.println("✅ Socios insertados.");
            }

            // Obtener libros y socios de la BD para asociarlos a los préstamos
            List<Libro> listaLibros = session.createQuery("FROM Libro", Libro.class).getResultList();
            List<Socio> listaSocios = session.createQuery("FROM Socio", Socio.class).getResultList();

            // Crear mapa de búsqueda rápida
            Map<String, Libro> mapaLibros = listaLibros.stream().collect(Collectors.toMap(Libro::getTitulo, l -> l));
            Map<String, Socio> mapaSocios = listaSocios.stream().collect(Collectors.toMap(Socio::getNombre, s -> s));

            // Insertar Préstamos
            Long countPrestamos = (Long) session.createQuery("SELECT COUNT(p) FROM Prestamo p").uniqueResult();
            if (countPrestamos == 0) {
                Prestamo[] prestamos = {
                        new Prestamo(LocalDate.of(2025, 2, 7), LocalDate.of(2025, 2, 17), mapaLibros.get("La casa de los espíritus"), mapaSocios.get("Gema García")),
                        new Prestamo(LocalDate.of(2025, 2, 7), LocalDate.of(2025, 2, 14), mapaLibros.get("La ciudad y los perros"), mapaSocios.get("Andrea Catalán"))
                };
                for (Prestamo prestamo : prestamos) {
                    session.persist(prestamo);
                }
                System.out.println("✅ Préstamos insertados.");
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

