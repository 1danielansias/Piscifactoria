package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IMar;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivora;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Trucha Arcoiris.
 * 
 * @author Daniel Ansias
 */
public class TruchaArcoiris extends AlimentacionCarnivora implements IMar {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public TruchaArcoiris(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.TRUCHA_ARCOIRIS, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new TruchaArcoiris(sexo, piscifactoria);
    }
}
