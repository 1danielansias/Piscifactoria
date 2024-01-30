package Commons;

/**
 * Clase que representa el Monedero donde se almacenará la cantidad de dinero de
 * la que dispone el usuario.
 * 
 * @author Daniel Ansias
 */
public class Monedero {
    
    /** Dinero disponible. */
    protected static int dinero;

    /**
     * Suma dinero al monedero.
     * 
     * @param cantidad Cantidad a sumar.
     */
    public static void sumar(int cantidad) {
        dinero += cantidad;
    }

    /**
     * Resta dinero del monedero.
     * 
     * @param cantidad Cantidad a restar.
     */
    public static void restar(int cantidad) {
        dinero -= cantidad;
    }
}
