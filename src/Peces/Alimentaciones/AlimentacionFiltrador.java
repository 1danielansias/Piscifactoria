package Peces.Alimentaciones;

import java.util.Random;
import Commons.Piscifactoria;
import Commons.Tanque;
import Peces.Pez;
import propiedades.PecesDatos;

/**
 * Clase que controla la lógica de la alimentación filtradora.
 * 
 * @author Daniel Ansias
 */
public abstract class AlimentacionFiltrador extends Pez {

    /**
     * Constructor parametrizado.
     * 
     * @param datos Datos del pez.
     * @param sexo  Sexo del pez.
     * @param p     Piscifactoría del pez.
     */
    public AlimentacionFiltrador(PecesDatos datos, boolean sexo, Piscifactoria p) {
        super(datos, sexo, p);
    }

    /** Método alimentar sobreescrito para la alimentación filtradora. */
    @Override
    public void alimentar(Tanque<? extends Pez> tanque) {
        // 50% de probabilidad de que no consuma comida
        Random random = new Random();
        int x = random.nextInt(2);
        if (x == 0) {
            // se alimenta sin consumir comida
            this.alimentado = true;
        } else {
            if (hayComidaDisponible()) {
                this.alimentado = true;
                // consume comida siempre que haya disponible
                piscifactoria.setComidaDisponible(piscifactoria.getComidaDisponible() - 1);
            }
        }
    }
}

// Filtrador, 50% de posbilidades de no consumir comida
// HACER ANTES