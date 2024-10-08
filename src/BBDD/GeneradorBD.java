package BBDD;

import Registros.Registro;
import propiedades.PecesDatos;

import java.sql.*;

/**
 * Clase encargada de generar datos de prueba para la base de datos.
 * 
 * @author Daniel Ansias
 */
public class GeneradorBD {

    /** Objeto Connection que representa la conexión con la base de datos. */
    private static Connection conexion;

    /** Sentencia SQL para insertar un cliente en la base de datos. */
    private static String insertarCliente = "INSERT INTO Cliente (nombre, NIF, telefono) " +
            "SELECT ?, ?, ? " +
            "WHERE NOT EXISTS (SELECT * FROM Cliente WHERE NIF = ?)";

    /** Sentencia SQL para insertar un pez en la base de datos. */
    private static String insertarPez = "INSERT INTO Pez (nombre, nombre_cientifico) " +
            "SELECT ?, ? " +
            "WHERE NOT EXISTS (SELECT * FROM Pez WHERE nombre = ?)";

    /** Lista de nombres de clientes. */
    private static String[] nombres = {
            "Lucía", "Hugo", "Martina", "Daniel", "Sofía", "Pablo", "Paula", "Alejandro", "Laura", "David"
    };

    /** Lista de NIFs de clientes. */
    private static String[] NIFs = {
            "03438343G", "79017467R", "37927001R", "64133605Z", "93176341Y", "88917714J", "70041935N", "25100528F",
            "95402707H", "62858780X"
    };

    /** Lista de telefonos de clientes. */
    private static String[] telefonos = {
            "612345678", "655432198", "699876543", "644567890", "677890123", "688901234", "622345678", "633456789",
            "677654321", "655789012"
    };

    /**
     * Genera datos de prueba en la base de datos.
     * 
     * @param pecesDatos Array de objetos de tipo PecesDatos que contiene
     *                   información sobre los peces.
     */
    public static void generarDatos(PecesDatos[] pecesDatos) {
        conexion = Conexion.getConnection();
        crearTablas();
        insertarClientes();
        insertarPeces(pecesDatos);
    }

    /**
     * Crea las tablas necesarias en la base de datos si no existen.
     */
    private static void crearTablas() {
        try {
            String tablaCliente = "CREATE TABLE IF NOT EXISTS Cliente (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nombre VARCHAR(255) NOT NULL," +
                    "NIF VARCHAR(255) UNIQUE NOT NULL," +
                    "telefono VARCHAR(255) NOT NULL" +
                    ")";
            ejecutar(tablaCliente);

            String tablaPez = "CREATE TABLE IF NOT EXISTS Pez (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nombre VARCHAR(255) NOT NULL," +
                    "nombre_cientifico VARCHAR(255) NOT NULL" +
                    ")";
            ejecutar(tablaPez);

            String tablaPedido = "CREATE TABLE IF NOT EXISTS Pedido (" +
                    "ref INT AUTO_INCREMENT PRIMARY KEY," +
                    "id_cliente INT," +
                    "id_pez INT," +
                    "cantidad_solicitada INT," +
                    "cantidad_enviada INT," +
                    "FOREIGN KEY (id_cliente) REFERENCES Cliente(id)," +
                    "FOREIGN KEY (id_pez) REFERENCES Pez(id)" +
                    ")";
            ejecutar(tablaPedido);
        } catch (SQLException ex) {
            Registro.registrarEnLog("Error durante la creación de las tablas de la base de datos.");
            ex.printStackTrace();
        }
    }

    /**
     * Inserta registros de clientes en la base de datos.
     */
    private static void insertarClientes() {
        try (PreparedStatement pstm = conexion.prepareStatement(insertarCliente)) {
            for (int i = 0; i < 10; i++) {
                String nombre = nombres[i];
                String NIF = NIFs[i];
                String telefono = telefonos[i];

                pstm.setString(1, nombre);
                pstm.setString(2, NIF);
                pstm.setString(3, telefono);
                pstm.setString(4, NIF);

                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            Registro.registrarEnLog("Error durante la inserción de datos en la base de datos.");
            ex.printStackTrace();
        }
    }

    /**
     * Inserta registros de peces en la base de datos.
     * 
     * @param pecesDatos Array de objetos de tipo PecesDatos que contiene
     *                   información sobre los peces.
     */
    private static void insertarPeces(PecesDatos[] pecesDatos) {
        try (PreparedStatement pstm = conexion.prepareStatement(insertarPez)) {
            for (PecesDatos pezDatos : pecesDatos) {
                String nombre = pezDatos.getNombre();
                String nombre_cientifico = pezDatos.getCientifico();

                pstm.setString(1, nombre);
                pstm.setString(2, nombre_cientifico);
                pstm.setString(3, nombre);

                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            Registro.registrarEnLog("Error durante la inserción de datos en la base de datos.");
            ex.printStackTrace();
        }
    }

    /**
     * Ejecuta una consulta SQL que no devuelve resultados.
     * 
     * @param query Consulta SQL a ejecutar.
     * @throws SQLException Si ocurre un error durante la ejecución de la consulta.
     */
    private static void ejecutar(String query) throws SQLException {
        // Statement para creacion de tablas
        try (Statement stm = conexion.createStatement()) {
            stm.executeUpdate(query);
        }
    }
}
