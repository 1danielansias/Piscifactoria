package Peces.Especies;

import propiedades.AlmacenPropiedades;
import Commons.Piscifactoria;
import Peces.IRio;
import Peces.Pez;
import Peces.Alimentaciones.AlimentacionFiltrador;

/**
 * Clase que representa una Carpa Plateada.
 * 
 * @author Daniel Ansias
 */
public class CarpaPlateada extends AlimentacionFiltrador implements IRio {

    /**
     * Constructor parametrizado.
     * 
     * @param sexo Sexo del pez.
     * @param p    Piscifactoría a la que pertenece el pez.
     */
    public CarpaPlateada(boolean sexo, Piscifactoria p) {
        super(AlmacenPropiedades.CARPA_PLATEADA, sexo, p);
    }

    /** Método getInstace sobreescrito. */
    @Override
    public Pez getInstance() {
        return new CarpaPlateada(sexo, piscifactoria);
    }
}