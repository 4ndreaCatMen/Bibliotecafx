module org.example.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    // Abrir paquetes para Hibernate
    opens org.example.modelo to org.hibernate.orm.core, jakarta.persistence;

    // Abrir paquetes para JavaFX (para cargar vistas y controladores correctamente)
    opens org.example.vista to javafx.fxml;
    opens org.example.controlador to javafx.fxml;

    // Exportar paquetes necesarios
    exports org.example.modelo;
    exports org.example.controlador;
}
