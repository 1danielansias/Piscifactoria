package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IMar;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivora;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Besugo.
 * 
 * @author Daniel Ansias
 */
public class Besugo extends AlimentacionCarnivora implements IMar {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public Besugo(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.BESUGO, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new Besugo(sexo, piscifactoria);
    }
}
