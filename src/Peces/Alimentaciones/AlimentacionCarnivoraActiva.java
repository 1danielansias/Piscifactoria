package Peces.Alimentaciones;

import java.util.Random;
import Commons.Piscifactoria;
import Commons.Tanque;
import Peces.Pez;
import propiedades.PecesDatos;

/** Clase que controla la lógica de la alimentación carnívora activa.
 * 
 * @author Daniel Ansias
 */
public abstract class AlimentacionCarnivoraActiva extends Pez {

    /**
     * Constructor parametrizado.
     * 
     * @param datos Datos del pez.
     * @param sexo Sexo del pez.
     * @param p Piscifactoría del pez.
     */
    public AlimentacionCarnivoraActiva(PecesDatos datos, boolean sexo, Piscifactoria p) {
        super(datos, sexo, p);
    }

    /** Método alimentar sobreescrito para la alimentación carnívora activa. */
    @Override
    public void alimentar(Tanque<? extends Pez> tanque) {
        for (int i = 0; i < tanque.getPeces().size(); i++) {
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
        if (!alimentado && hayComidaDisponible()) {
            this.alimentado = true;
            // 50% de probabilidades de comer 2 de alimento ese día
            Random rand2 = new Random();
            int y = rand2.nextInt(2);
            if (y == 0) {
                piscifactoria.setComidaDisponible(piscifactoria.getComidaDisponible() - 1);
            } else {
                piscifactoria.setComidaDisponible(piscifactoria.getComidaDisponible() - 2);
            }
        }
    }   
}