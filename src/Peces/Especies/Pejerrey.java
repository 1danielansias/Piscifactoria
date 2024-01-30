package Peces.Especies;

import propiedades.AlmacenPropiedades;
import Commons.Piscifactoria;
import Peces.IRio;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionCarnivora;

/**
 * Clase que representa una Pejerrey.
 * 
 * @author Daniel Ansias
 */
public class Pejerrey extends AlimentacionCarnivora implements IRio{
    
    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public Pejerrey(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.PEJERREY, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new Pejerrey(sexo, piscifactoria);
    }
}