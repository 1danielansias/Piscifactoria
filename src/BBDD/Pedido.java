package BBDD;

import java.text.DecimalFormat;

/**
 * Clase que representa un pedido en la base de datos.
 * 
 * @author Daniel Ansias
 */
public class Pedido {

    /** Identificador único del pedido. */
    private int id_pedido;

    /** Identificador del cliente asociado al pedido. */
    private int id_cliente;

    /** Nombre del cliente asociado al pedido. */
    private String nombre_cliente;

    /** Identificador del pez asociado al pedido. */
    private int id_pez;

    /** Nombre del pez asociado al pedido. */
    private String nombre_pez;

    /** Cantidad solicitada del pez en el pedido. */
    private int cantidad_solicitada;

    /** Cantidad enviada del pez en el pedido. */
    private int cantidad_enviada;

    public Pedido(int id_pedido, int id_cliente, String nombre_cliente, int id_pez, String nombre_pez,
            int cantidad_solicitada, int cantidad_enviada) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.id_pez = id_pez;
        this.nombre_pez = nombre_pez;
        this.cantidad_solicitada = cantidad_solicitada;
        this.cantidad_enviada = cantidad_enviada;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public int getId_pez() {
        return id_pez;
    }

    public String getNombre_pez() {
        return nombre_pez;
    }

    public int getCantidad_solicitada() {
        return cantidad_solicitada;
    }

    public int getCantidad_enviada() {
        return cantidad_enviada;
    }

    /**
     * Verifica si el pedido está completo comparando la cantidad enviada con la
     * cantidad solicitada.
     *
     * @return true si el pedido está completo, false en caso contrario.
     */
    public boolean estaCompleto() {
        return cantidad_enviada >= cantidad_solicitada;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        double porcentaje = ((double) cantidad_enviada / (double) cantidad_solicitada * 100);
        return "[" + id_pedido + "] " + nombre_cliente + ": " + nombre_pez + " " + cantidad_enviada + "/"
                + cantidad_solicitada + " (" + df.format(porcentaje) + "%)";
    }
}
