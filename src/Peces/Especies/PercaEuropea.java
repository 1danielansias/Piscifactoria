package Peces.Especies;

import Commons.Piscifactoria;
import Peces.IRio;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivoraActiva;
import propiedades.AlmacenPropiedades;

/**
 * Clase que representa una Perca Europea.
 * 
 * @author Daniel Ansias
 */
public class PercaEuropea extends AlimentacionCarnivoraActiva implements IRio{
    
    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public PercaEuropea(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.PERCA_EUROPEA, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new PercaEuropea(sexo, piscifactoria);
    }
}
