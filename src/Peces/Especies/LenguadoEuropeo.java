package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IMar;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivora;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Lenguado Europeo.
 * 
 * @author Daniel Ansias
 */
public class LenguadoEuropeo extends AlimentacionCarnivora implements IMar {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public LenguadoEuropeo(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.LENGUADO_EUROPEO, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new LenguadoEuropeo(sexo, piscifactoria);
    }
}