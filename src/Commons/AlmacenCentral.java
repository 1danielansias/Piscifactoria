package Commons;

import java.util.ArrayList;

/**
 * Clase que representa el almacén central de comida.
 * 
 * @author Daniel Ansias
 */
public class AlmacenCentral {
    /** True si el usuario dispone de Almacen Central, false si no */
    public static boolean almacenActivado = false;
    /** Capacidad del almacen. */
    public static int capacidad = 200;
    /** Cantidad de comida disponible en el almacen */
    public static int comidaDisponible = 0;

    /** Controla la lógica de distribución de comida entre las piscifactorias */
    public static void distribuirComida(ArrayList<Piscifactoria> piscifactorias) {
        // Verificar que haya comida disponible en el almacén y al menos una piscifactoría
        if (comidaDisponible > 0 && piscifactorias.size() > 0) {
            int comidaPisc = comidaDisponible / piscifactorias.size();
            // Distribuir la comida entre las piscifactorías
            for (Piscifactoria p : piscifactorias) {
                // Controlar que la piscifactoría no haya alcanzado su capacidad máxima
                if (p.comidaDisponible < p.comidaPiscifactoriaMax) {
                    int cantidadDistribuir = Math.min(comidaPisc, p.comidaPiscifactoriaMax - p.comidaDisponible);
                    // Distribuir la comida
                    p.comidaDisponible += cantidadDistribuir;
                    comidaDisponible -= cantidadDistribuir;

                    System.out.println("Distribuida automaticamente " + cantidadDistribuir + " de comida a la piscifactoría " + p.nombre);
                }
            }
        }
    }
}