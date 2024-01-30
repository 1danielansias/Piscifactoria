package Peces.Alimentaciones;

import java.util.Random;
import Commons.Piscifactoria;
import Commons.Tanque;
import Peces.Pez;
import propiedades.PecesDatos;

/** Clase que controla la lógica de la alimentación carnívora.
 * 
 * @author Daniel Ansias
 */
public abstract class AlimentacionCarnivora extends Pez {

    /**
     * Constructor parametrizado.
     * 
     * @param datos Datos del pez.
     * @param sexo Sexo del pez.
     * @param p Piscifactoría del pez.
     */
    public AlimentacionCarnivora(PecesDatos datos, boolean sexo, Piscifactoria p) {
        super(datos, sexo, p);
    }

    /** Método alimentar sobreescrito para la alimentación carnívora. */
    @Override
    public void alimentar(Tanque<? extends Pez> tanque) {
        // recorrer tanque para comprobar si hay peces muertos
        for (int i = 0; i < tanque.getPeces().size(); i++) {
            /** evitar problemas con peces null (que hayan sido comidos antes por otro pez pero 
             * que aun no han sido eliminados del array) */
            if (tanque.getPeces().get(i) != null) {
                if (!tanque.getPeces().get(i).isVivo()) {
                    this.alimentado = true;
                    Random rand = new Random();
                    int x = rand.nextInt(2);
                    if (x == 0) {
                        tanque.getPeces().set(i, null);
                    }
                    break;
                }
            }
        }
        // si no se come ningun pez, se alimenta de comida si la hay
        if (!alimentado && hayComidaDisponible()) {
            this.alimentado = true;
            piscifactoria.setComidaDisponible(piscifactoria.getComidaDisponible() - 1);
        }
    }
}


