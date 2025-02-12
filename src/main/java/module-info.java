module org.example.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    // Abrir paquetes para Hibernate
    opens org.example.modelo to org.hibernate.orm.core, jakarta.persistence;

    // Abrir paquetes para JavaFX
    opens org.example.controlador to javafx.fxml;

    // Exportar paquetes necesarios
    exports org.example.modelo;
    exports org.example.controlador;

    // Permitir acceso a MainApp desde JavaFX
    opens org.example.Main to javafx.graphics;
}
