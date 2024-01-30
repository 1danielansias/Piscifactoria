package Peces.Especies;

import propiedades.AlmacenPropiedades;
import Commons.Piscifactoria;
import Peces.IRio;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivoraActiva;

/**
 * Clase que representa un Lucio Del Norte.
 * 
 * @author Daniel Ansias
 */
public class LucioDelNorte extends AlimentacionCarnivoraActiva implements IRio {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public LucioDelNorte(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.LUCIO_NORTE, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new LucioDelNorte(sexo, piscifactoria);
    }
}
