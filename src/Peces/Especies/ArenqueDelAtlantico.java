package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IMar;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionFiltrador;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa un Arenque del Atlántico.
 * 
 * @author Daniel Ansias
 */
public class ArenqueDelAtlantico extends AlimentacionFiltrador implements IMar {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public ArenqueDelAtlantico(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.ARENQUE_ATLANTICO, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new ArenqueDelAtlantico(sexo, piscifactoria);
    }
}