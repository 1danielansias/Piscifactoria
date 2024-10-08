package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import Registros.Registro;

/**
 * Clase utilizada para establecer y gestionar la conexion con la base de datos.
 * 
 * @author Daniel Ansias
 */
public class Conexion {

    /** Propiedades de la conexión. */
    private static Properties connectionProps;

    /** Nombre de usuario de la base de datos. */
    private static final String USERNAME = "admin_bd_pisci";

    /** Contraseña para autenticarse en la base de datos. */
    private static final String PASSWORD = "abc123.";

    /** Dirección del servidor de la base de datos. */
    private static final String SERVER = "localhost";

    /** Puerto utilizado para la conexión. */
    private static final String PORTNUMBER = "3306";

    /** Nombre de la base de datos */
    private static final String DATABASE = "bd_pisci";
    
    /** Objeto Connection que representa la conexion establecida con la BD. */
    private static Connection conexion;

    /**
     * Constructor privado para evitar instanciación externa.
     */
    private Conexion() {
        connectionProps = new Properties();
        connectionProps.put("user", USERNAME);
        connectionProps.put("password", PASSWORD);
        connectionProps.put("rewriteBatchedStatements", "true");
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://" +
                            SERVER + ":" +
                            PORTNUMBER + "/" +
                            DATABASE,
                    connectionProps);
        } catch (SQLException e) {
            Registro.registrarEnLog("Error al conectarse con la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve un instancia única de la conexión.
     *
     * @return Objecto Connection.
     */
    public static Connection getConnection() {
        if (conexion == null) {
            new Conexion();
        }
        return conexion;
    }

    /**
     * Cierra la conexion con la base de datos.
     */
    public static void closeConnection() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            Registro.registrarEnLog("Error al cerrar la conexión con la base de datos.");
            e.printStackTrace();
        }
    }
}