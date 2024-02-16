package modelo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

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
                    + "(id INTEGER PRIMARY KEY, Username TEXT UNIQUE, Password TEXT, "
                    + "EstiloAprendizajeId INTEGER, "
                    + "IntMultiplesId INTEGER, "
                    + "CocienteIntelectual INTEGER, "
                    + "FOREIGN KEY (EstiloAprendizajeId) REFERENCES EstiloAprendizaje(id), "
                    + "FOREIGN KEY (IntMultiplesId) REFERENCES IntMultiples(id))";
            statement.execute(crearTablaUsuario);

            // Crear tabla Docs con claves foráneas
            String crearTablaDocs = "CREATE TABLE IF NOT EXISTS Docs "
                    + "(id INTEGER PRIMARY KEY, Titulo TEXT UNIQUE, Archivo TEXT, "
                    + "EstiloAprendizajeId INTEGER, "
                    + "IntMultiplesId INTEGER, "
                    + "CocienteIntelectual INTEGER, "
                    + "FOREIGN KEY (EstiloAprendizajeId) REFERENCES EstiloAprendizaje(id), "
                    + "FOREIGN KEY (IntMultiplesId) REFERENCES IntMultiples(id))";
            statement.execute(crearTablaDocs);

            // Crear tabla Interaccion con claves foráneas
            String crearTablaInteraccion = "CREATE TABLE IF NOT EXISTS Interaccion "
                    + "(id INTEGER PRIMARY KEY, "
                    + "UsuarioId INTEGER, "
                    + "DocId INTEGER, "
                    + "FOREIGN KEY (UsuarioId) REFERENCES Usuario(id), "
                    + "FOREIGN KEY (DocId) REFERENCES Doc(id))";
            statement.execute(crearTablaInteraccion);

            System.out.println("Base de datos creada exitosamente.");
            insertarDatosAleatorios();

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

    public static void insertarDatosAleatorios() {
        Connection connection = null;
        Random random = new Random();

        try {
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            // Insertar datos aleatorios en la tabla Usuario
            String insertarDatosUsuario = "INSERT INTO Usuario (Username, Password, EstiloAprendizajeId, IntMultiplesId, CocienteIntelectual) VALUES (?, ?, ?, ?, ?)";
            try ( var pstmtUsuario = connection.prepareStatement(insertarDatosUsuario)) {
                //admin
                pstmtUsuario.setString(1, "admin");
                pstmtUsuario.setString(2, "1234");
                pstmtUsuario.setInt(3, random.nextInt(4) + 1);  // EstiloAprendizajeId entre 1 y 4
                pstmtUsuario.setInt(4, random.nextInt(8) + 1);  // IntMultiplesId entre 1 y 8
                pstmtUsuario.setInt(5, random.nextInt(61) + 80); // CocienteIntelectual entre 80 y 140
                pstmtUsuario.executeUpdate();
                for (int i = 1; i <= 200; i++) {
                    pstmtUsuario.setString(1, "user" + i);
                    pstmtUsuario.setString(2, "pass" + i);
                    pstmtUsuario.setInt(3, random.nextInt(4) + 1);  // EstiloAprendizajeId entre 1 y 4
                    pstmtUsuario.setInt(4, random.nextInt(8) + 1);  // IntMultiplesId entre 1 y 8
                    pstmtUsuario.setInt(5, random.nextInt(61) + 80); // CocienteIntelectual entre 80 y 140
                    pstmtUsuario.executeUpdate();
                }
            }

            // Insertar datos aleatorios en la tabla Docs
            String insertarDatosDocs = "INSERT INTO Docs (Titulo, Archivo, EstiloAprendizajeId, IntMultiplesId, CocienteIntelectual) VALUES (?, ?, ?, ?, ?)";
            try ( var pstmtDocs = connection.prepareStatement(insertarDatosDocs)) {
                for (int i = 1; i <= 150; i++) {
                    pstmtDocs.setString(1, "Articulo" + i);
                    pstmtDocs.setString(2, "media/doc" + (random.nextInt(8) + 1) + ".pdf"); // Archivo entre 1 y 8
                    pstmtDocs.setInt(3, random.nextInt(4) + 1); // EstiloAprendizajeId entre 1 y 4
                    pstmtDocs.setInt(4, random.nextInt(8) + 1); // IntMultiplesId entre 1 y 8
                    pstmtDocs.setInt(5, random.nextInt(61) + 80); // CocienteIntelectual entre 80 y 140
                    pstmtDocs.executeUpdate();
                }
            }

            // Insertar datos aleatorios en la tabla Interaccion
            String insertarDatosInteraccion = "INSERT INTO Interaccion (UsuarioId, DocId) VALUES (?, ?)";
            try ( var pstmtInter = connection.prepareStatement(insertarDatosInteraccion)) {
                for (int i = 1; i <= 500; i++) {
                    pstmtInter.setInt(1, random.nextInt(31) + 1); // UsuarioId entre 1 y 31
                    pstmtInter.setInt(2, random.nextInt(150) + 1); // DocId entre 1 y 150
                    pstmtInter.executeUpdate();
                }
            }

            System.out.println("Datos aleatorios insertados exitosamente.");

        } catch (SQLException e) {
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
