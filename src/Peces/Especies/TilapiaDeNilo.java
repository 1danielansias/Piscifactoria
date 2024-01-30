package Peces.Especies;

import propiedades.AlmacenPropiedades;
import Commons.Piscifactoria;
import Peces.IRio;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionFiltrador;

/**
 * Clase que representa una Tilapia Del Nilo.
 * 
 * @author Daniel Ansias
 */
public class TilapiaDeNilo extends AlimentacionFiltrador implements IRio{
    
    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public TilapiaDeNilo(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.TILAPIA_NILO, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new TilapiaDeNilo(true, null);
    }
}
