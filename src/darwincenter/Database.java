package darwincenter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jahir
 */
public class Database {

    public static void crearDatabase() {
        Connection connection = null;

        try {
            // Verificar si la base de datos ya existe
            File databaseFile = new File("database.sqlite");
            if (databaseFile.exists()) {
                return;
            }

            // Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");

            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            // Crear una declaración
            Statement statement = connection.createStatement();

            // Crear tabla EstiloAprendizaje
            String crearTablaEstiloAprendizaje = "CREATE TABLE IF NOT EXISTS EstiloAprendizaje "
                    + "(id INTEGER PRIMARY KEY, Valor TEXT)";
            statement.execute(crearTablaEstiloAprendizaje);

            // Insertar datos en la tabla EstiloAprendizaje
            statement.execute("INSERT INTO EstiloAprendizaje (Valor) VALUES ('Activo')");
            statement.execute("INSERT INTO EstiloAprendizaje (Valor) VALUES ('Teórico')");
            statement.execute("INSERT INTO EstiloAprendizaje (Valor) VALUES ('Pragmático')");
            statement.execute("INSERT INTO EstiloAprendizaje (Valor) VALUES ('Reflexivo')");

            // Crear tabla IntMultiples
            String crearTablaIntMultiples = "CREATE TABLE IF NOT EXISTS IntMultiples "
                    + "(id INTEGER PRIMARY KEY, Valor TEXT)";
            statement.execute(crearTablaIntMultiples);

            // Insertar datos en la tabla IntMultiples
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Linguistico')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Logico-Matemático')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Musical')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Visual-Espacial')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Cinéstica-Corporal')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Interpersonal')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Intrapersonal')");
            statement.execute("INSERT INTO IntMultiples (Valor) VALUES ('Naturalista')");

            // Crear tabla Usuario con claves foráneas
            String crearTablaUsuario = "CREATE TABLE IF NOT EXISTS Usuario "
                    + "(id INTEGER PRIMARY KEY, Username TEXT, Password TEXT, "
                    + "EstiloAprendizajeId INTEGER, "
                    + "IntMultiplesId INTEGER, "
                    + "CocienteIntelectual INTEGER, "
                    + "FOREIGN KEY (EstiloAprendizajeId) REFERENCES EstiloAprendizaje(id), "
                    + "FOREIGN KEY (IntMultiplesId) REFERENCES IntMultiples(id))";
            statement.execute(crearTablaUsuario);

            // Crear tabla Docs con claves foráneas
            String crearTablaDocs = "CREATE TABLE IF NOT EXISTS Docs "
                    + "(id INTEGER PRIMARY KEY, Archivo BLOB, "
                    + "EstiloAprendizajeId INTEGER, "
                    + "IntMultiplesId INTEGER, "
                    + "CocienteIntelectual INTEGER, "
                    + "FOREIGN KEY (EstiloAprendizajeId) REFERENCES EstiloAprendizaje(id), "
                    + "FOREIGN KEY (IntMultiplesId) REFERENCES IntMultiples(id))";
            statement.execute(crearTablaDocs);

            System.out.println("Base de datos creada exitosamente.");

        } catch (ClassNotFoundException | SQLException e) {
        } finally {
            try {
                // Cerrar la conexión
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }
}
