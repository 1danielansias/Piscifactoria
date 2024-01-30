package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IMar;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivora;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Salmón Atlántico.
 * 
 * @author Daniel Ansias
 */
public class SalmonAtlantico extends AlimentacionCarnivora implements IMar {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public SalmonAtlantico(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.SALMON_ATLANTICO, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new SalmonAtlantico(sexo, piscifactoria);
    }
}
