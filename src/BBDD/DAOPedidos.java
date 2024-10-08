package BBDD;

import Registros.Registro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase encargada de gestionar las operaciones relacionadas con la tabla Pedido
 * en la base de datos.
 * 
 * @author Daniel Ansias
 */
public class DAOPedidos {

    /** Instancia única de la clase DAOPedidos. */
    private static DAOPedidos instancia;

    /** Conexión a la base de datos. */
    private static Connection conexion;

    /** PreparedStatement para obtener los clientes de la base de datos. */
    private PreparedStatement ps_getClientes;

    /** PreparedStatement para obtener los peces de la base de datos. */
    private PreparedStatement ps_getPeces;

    /** PreparedStatement para obtener los pedidos de la base de datos. */
    private PreparedStatement ps_getPedidos;

    /** PreparedStatement para generar un nuevo pedido en la base de datos. */
    private PreparedStatement ps_generarPedido;

    /**
     * PreparedStatement para eliminar todos los registros de pedidos de la base de
     * datos.
     */
    private PreparedStatement ps_eliminarPedidos;

    /**
     * PreparedStatement para obtener la cantidad enviada de un pedido específico.
     */
    private PreparedStatement ps_getCantidadEnviada;

    /**
     * PreparedStatement para actualizar la cantidad enviada de un pedido
     * específico.
     */
    private PreparedStatement ps_actualizarPedido;

    /**
     * PreparedStatement para obtener la cantidad solicitada y enviada de un pedido
     * específico.
     */
    private PreparedStatement ps_getCantidadPedido;

    /**
     * Constructor privado para evitar instanciación externa. Se preparan los
     * PreparedStatement.
     */
    private DAOPedidos() {
        conexion = Conexion.getConnection();
        try {
            String sql_getClientes = "SELECT * FROM Cliente";
            ps_getClientes = conexion.prepareStatement(sql_getClientes);

            String sql_getPeces = "SELECT * FROM Pez";
            ps_getPeces = conexion.prepareStatement(sql_getPeces);

            String sql_getPedidos = "SELECT p.ref, p.id_cliente, c.nombre AS nombre_cliente, p.id_pez, pe.nombre AS nombre_pez, p.cantidad_solicitada, p.cantidad_enviada "
                    + "FROM Pedido p "
                    + "INNER JOIN Cliente c ON p.id_cliente = c.id "
                    + "INNER JOIN Pez pe ON p.id_pez = pe.id";
            ps_getPedidos = conexion.prepareStatement(sql_getPedidos);

            String sql_generarPedido = "INSERT INTO Pedido (id_cliente, id_pez ,cantidad_solicitada, cantidad_enviada) "
                    + "VALUES (?, ?, ?, ?)";
            ps_generarPedido = conexion.prepareStatement(sql_generarPedido, Statement.RETURN_GENERATED_KEYS);

            String sql_eliminarPedidos = "DELETE FROM Pedido";
            ps_eliminarPedidos = conexion.prepareStatement(sql_eliminarPedidos);

            String sql_getCantidadEnviada = "SELECT cantidad_enviada FROM Pedido WHERE ref = ?";
            ps_getCantidadEnviada = conexion.prepareStatement(sql_getCantidadEnviada);

            String sql_actualizarPedido = "UPDATE Pedido SET cantidad_enviada = ? WHERE ref = ?";
            ps_actualizarPedido = conexion.prepareStatement(sql_actualizarPedido);

            String sql_getCantidad = "SELECT cantidad_solicitada, cantidad_enviada FROM Pedido WHERE ref = ?";
            ps_getCantidadPedido = conexion.prepareStatement(sql_getCantidad);

        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la preparación de los statements de consulta.");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una instancia única de la clase DAOPedidos.
     * 
     * @return Instancia única de la clase DAOPedidos.
     */
    public static DAOPedidos getInstance() {
        if (instancia == null) {
            instancia = new DAOPedidos();
        }
        return instancia;
    }

    /**
     * Obtiene los registros de clientes de la base de datos.
     * 
     * @return ResultSet que contiene los registros de clientes.
     */
    public ResultSet getClientes() {
        ResultSet resultado = null;
        try {
            resultado = ps_getClientes.executeQuery();
        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la ejecución de la consulta.");
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Obtiene los registros de peces de la base de datos.
     * 
     * @return ResultSet que contiene los registros de peces.
     */
    public ResultSet getPeces() {
        ResultSet resultado = null;
        try {
            resultado = ps_getPeces.executeQuery();
        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la ejecución de la consulta.");
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Obtiene los registros de pedidos de la base de datos y devuelve en un
     * ArrayList de objetos Pedido todos los pedidos.
     * 
     * @return Lista de Pedidos.
     */
    public ArrayList<Pedido> getAllPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ResultSet resultado = null;
        try {
            resultado = ps_getPedidos.executeQuery();
            while (resultado.next()) {
                Pedido pedido = buildPedido(resultado);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la ejecución de la consulta.");
            e.printStackTrace();
        }
        return pedidos;
    }

    /**
     * Obtiene los registros de pedidos de la base de datos y devuelve en un
     * ArrayList de objetos Pedido solo aquellos que no hayan sido compleados.
     * 
     * @return Lista de Pedidos.
     */
    public ArrayList<Pedido> getPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ResultSet resultado = null;
        try {
            resultado = ps_getPedidos.executeQuery();
            while (resultado.next()) {
                Pedido pedido = buildPedido(resultado);
                if (!pedido.estaCompleto()) {
                    pedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la ejecución de la consulta.");
            e.printStackTrace();
        }
        return pedidos;
    }

    /**
     * Construye un objeto Pedido a partir del ResultSet de la consulta.
     * 
     * @param rs ResultSet de la consulta.
     * @return Objeto Pedido que representa un registro de la base de datos.
     * @throws SQLException Error de la base de datos.
     */
    private Pedido buildPedido(ResultSet rs) throws SQLException {
        int id = rs.getInt("ref");
        int id_cliente = rs.getInt("id_cliente");
        String nombre_cliente = rs.getString("nombre_cliente");
        int id_pez = rs.getInt("id_pez");
        String nombre_pez = rs.getString("nombre_pez");
        int cantidad_solicitada = rs.getInt("cantidad_solicitada");
        int cantidad_enviada = rs.getInt("cantidad_enviada");

        return new Pedido(id, id_cliente, nombre_cliente, id_pez, nombre_pez, cantidad_solicitada, cantidad_enviada);
    }

    /**
     * Genera un nuevo pedido con datos aleatorios en la base de datos.
     */
    public void generarPedido() {
        Random random = new Random();
        ArrayList<Integer> idClientes = new ArrayList<>();
        ArrayList<Integer> idPeces = new ArrayList<>();

        try {
            ResultSet rsClientes = getClientes();
            while (rsClientes.next()) {
                idClientes.add(rsClientes.getInt("id"));
            }

            ResultSet rsPeces = getPeces();
            while (rsPeces.next()) {
                idPeces.add(rsPeces.getInt("id"));
            }

            int idCliente = idClientes.get(random.nextInt(idClientes.size()));
            int idPez = idPeces.get(random.nextInt(idPeces.size()));
            int cantidadSolicitada = random.nextInt(41) + 10;

            try {
                ps_generarPedido.setInt(1, idCliente);
                ps_generarPedido.setInt(2, idPez);
                ps_generarPedido.setInt(3, cantidadSolicitada);
                ps_generarPedido.setInt(4, 0);

                ps_generarPedido.executeUpdate();

                ResultSet generatedKeys = ps_generarPedido.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idPedidoGenerado = generatedKeys.getInt(1);
                    Registro.registrar("Generado el pedido con referencia " + idPedidoGenerado);
                }

            } catch (SQLException e) {
                Registro.registrarEnLog("Error durante la generación del pedido.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la generación del pedido.");
            e.printStackTrace();
        }
    }

    /**
     * Elimina todos los registros de pedidos de la base de datos.
     */
    public void eliminarPedidos() {
        try {
            ps_eliminarPedidos.executeUpdate();
        } catch (SQLException e) {
            Registro.registrarEnLog("Error al eliminar los registros de pedidos.");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la cnatidad enviada de un pedido específico.
     * 
     * @param ref Referencia (Id) del pedido.
     * @return Cantidad enviada del pedido.
     */
    public int getCantidadEnviada(int ref) {
        int cantidadEnviada = 0;
        ResultSet resultado = null;
        try {
            ps_getCantidadEnviada.setInt(1, ref);
            resultado = ps_getCantidadEnviada.executeQuery();
            if (resultado.next()) {
                cantidadEnviada = resultado.getInt("cantidad_enviada");
            }
        } catch (SQLException e) {
            Registro.registrarEnLog("Error durante la consulta de la cantidad enviada.");
            e.printStackTrace();
        }
        return cantidadEnviada;
    }

    /**
     * Actualiza la cantidad enviada de un pedido especifico en la base de datos.
     * 
     * @param ref             Referencia (Id) del pedido.
     * @param cantidadEnviada Nueva cantidad enviada.
     */
    public void actualizarPedido(int ref, int cantidadEnviada) {
        try {
            int cantidadActual = getCantidadEnviada(ref);
            int cantidadNueva = cantidadActual + cantidadEnviada;
            ps_actualizarPedido.setInt(1, cantidadNueva);
            ps_actualizarPedido.setInt(2, ref);

            ps_actualizarPedido.executeUpdate();
        } catch (SQLException e) {
            Registro.registrarEnLog("Error al actualizar el pedido.");
            e.printStackTrace();
        }
    }

    /**
     * Verifica si un pedido está completo comparando la cantidad enviada con la
     * cantidad solicitada.
     * 
     * @param idPedido Referencia (Id) del pedido.
     * @return True si el pedido está completo, false en caso contrario.
     */
    public boolean isPedidoCompleto(int idPedido) {
        ResultSet resultado = null;
        try {
            ps_getCantidadPedido.setInt(1, idPedido);
            resultado = ps_getCantidadPedido.executeQuery();

            while (resultado.next()) {
                int cantidadSolicitada = resultado.getInt("cantidad_solicitada");
                int cantidadEnviada = resultado.getInt("cantidad_enviada");
                return cantidadEnviada >= cantidadSolicitada;
            }
        } catch (SQLException e) {
            Registro.registrarEnLog("Error al verificar si el pedido está completo.");
            e.printStackTrace(); 
        }
        return false;
    }

    /**
     * Cierra los PreparedStatements utilizadas para las consultas.
     */
    public void cerrarPS() {
        try {
            if (ps_getClientes != null) {
                ps_getClientes.close();
            }
            if (ps_getPeces != null) {
                ps_getPeces.close();
            }
            if (ps_getPedidos != null) {
                ps_getPedidos.close();
            }
            if (ps_getCantidadPedido != null) {
                ps_getCantidadPedido.close();
            }
            if (ps_generarPedido != null) {
                ps_generarPedido.close();
            }
            if (ps_actualizarPedido != null) {
                ps_actualizarPedido.close();
            }
            if (ps_eliminarPedidos != null) {
                ps_eliminarPedidos.close();
            }
            if (ps_getCantidadEnviada != null) {
                ps_getCantidadEnviada.close();
            }
        } catch (SQLException e) {
            Registro.registrarEnLog("Error al cerrar los PreparedStatements utilizados para las consultas.");
            e.printStackTrace();
        }
    }
}
