package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IMar;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivora;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Lubina Rayada.
 * 
 * @author Daniel Ansias
 */
public class LubinaRayada extends AlimentacionCarnivora implements IMar {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public LubinaRayada(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.LUBINA_RAYADA, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new LubinaRayada(sexo, piscifactoria);
    }
}
