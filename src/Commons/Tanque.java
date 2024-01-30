package Commons;

import java.util.ArrayList;
import java.util.Iterator;
import Peces.Pez;

/**
 * Clase que representa el lugar en el que se almacenan los peces.
 * 
 * @author Daniel Ansias
 */
public class Tanque<T extends Pez> {

    /** Lista de peces dentro del tanque. */
    protected ArrayList<T> peces;
    /** Capacidad del tanque. */
    protected int capacidad;
    /** Referencia a la piscifactoria a la que pertenece el tanque. */
    Piscifactoria piscifactoria;

    /**
     * Constructor parametrizado
     * 
     * @param capacidad Capacidad del tanque.
     * @param p Piscifactoría a la que pertenece el tanque.
     */
    public Tanque(int capacidad, Piscifactoria p) {
        this.capacidad = capacidad;
        this.peces = new ArrayList<>();
        this.piscifactoria = p;
    }

    /**
     * 
     * @return Capacidad del tanque.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * 
     * @return ArrayList de los peces dentro del tanque.
     */
    public ArrayList<T> getPeces() {
        return peces;
    }

    /**
     * Comprueba el número de peces de un tanque.
     * 
     * @return El número de peces dentro del tanque.
     */
    public int getNumPeces() {
        return peces.size();
    }

    /**
     * Comprueba el número de peces vivos dentro de un tanque.
     * 
     * @return El número de peces vivos.
     */
    public int pecesVivos() {
        int contador = 0;
        for (Pez p : peces) {
            if (p.isVivo()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba el número de peces alimentados dentro de un tanque.
     * 
     * @return El número de peces alimentados.
     */
    public int pecesAlimentados() {
        int contador = 0;
        for (Pez p : peces) {
            if (p.isAlimentado()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba el número de peces adultos dentro de un tanque.
     * 
     * @return El número de peces adultos.
     */
    public int pecesAdultos() {
        int contador = 0;
        for (Pez p : peces) {
            if (p.isMaduro()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba el número de peces macho dentro de un tanque.
     * 
     * @return El número de peces macho.
     */
    public int pecesMacho() {
        int contador = 0;
        for (Pez p : peces) {
            if (p.sexo()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba el número de peces hembra dentro de un tanque.
     * 
     * @return El número de peces hembra.
     */
    public int pecesHembra() {
        int contador = 0;
        for (Pez p : peces) {
            if (!p.sexo()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Compruba el número de peces fértiles dentro de un tanque.
     * 
     * @return El número de peces fértiles.
     */
    public int pecesFertiles() {
        int contador = 0;
        for (Pez p : peces) {
            if (p.isFertil()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Muestra información del tanque
     * 
     * @param index Tanque del que se mostrará la información.
     */
    public void showStatus(int index) {
        System.out.println("==========Tanque #" + (index + 1) + "==========");
        System.out.println("Ocupación: " + peces.size() + "/" + this.capacidad + "(" +
                ((this.capacidad != 0) ? ((peces.size() * 100) / this.capacidad) : 0) + "%)");
        System.out.println("Peces vivos: " + pecesVivos() + "/" + peces.size() + "(" +
                ((peces.size() != 0) ? ((pecesVivos() * 100) / peces.size()) : 0) + "%)");
        System.out.println("Peces alimentados: " + pecesAlimentados() + "/" + pecesVivos() + "(" +
                ((pecesVivos() != 0) ? ((pecesAlimentados() * 100) / pecesVivos()) : 0) + "%)");
        System.out.println("Peces adultos: " + pecesAdultos() + "/" + pecesVivos() + "(" +
                ((pecesVivos() != 0) ? ((pecesAdultos() * 100) / pecesVivos()) : 0) + "%)");
        System.out.println("Hembras/Machos: " + pecesMacho() + "/" + pecesHembra());
        System.out.println("Fértiles: " + pecesFertiles() + "/" + pecesVivos());
    }

    /** Muestra la información de todos los peces del tanque */
    public void showFishStatus() {
        for (Pez p : peces) {
            p.showStatus();
        }
    }

    /**
     * Muestra la ocupación del tanque.
     * 
     * @param index Índice del tanque.
     * @param p     Piscifactoría a la que pertenece.
     */
    public void showCapacity(int index, Piscifactoria p) {
        System.out.println("Tanque #" + index + " de la piscifactoría " + p.nombre + " al "
                + (peces.size() / this.capacidad) + "% de capacidad [" + peces.size() + "/" + this.capacidad + "]");
    }

    /**
     * Comprueba si un tanque está vacío.
     * 
     * @return True si está vacío, false si no lo está.
     */
    public boolean estaVacio() {
        if (peces.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si hay espacio disponible dentro de un tanque determinado.
     * 
     * @return True si hay espacio, false si no lo hay.
     */
    public boolean hayEspacio() {
        if (peces.size() < capacidad) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Añade un pez dentro del tanque.
     * 
     * @param pezSelec Pez seleccionado por el usuario.
     */
    public void addFish(Pez pezSelec) {
        this.peces.add((T) pezSelec);
        System.out.println(pezSelec.getDatosPez().getNombre() + " ha sido añadid@ al tanque");
        // Establecer la referencia de la piscifactoria a la que pertenece el pez
        pezSelec.setPiscifactoria(piscifactoria);

        // controlar la lógica de sexo 50/50
        if (pecesMacho() > pecesHembra()) {
            pezSelec.setSexo(false);
        } else if (pecesMacho() < pecesHembra()) {
            pezSelec.setSexo(true);
        }

        // registrar nacimiento/compra
        Simulador.estadisticas.registrarNacimiento(pezSelec.getDatosPez().getNombre());
    }

    /**
     * Método que recorre un tanque y comprueba si se puede dar la reproducción.
     * 
     * @return True si se puede dar la reproducción dentro del tanque, false si no
     *         se puede.
     */
    public boolean reproduccionPosible() {
        boolean hayMachoFertil = false;
        boolean hayHembraFertil = false;
        // recorrer el tanque
        for (Pez pez : peces) {
            // comprobar si el pez es fértil y macho
            if (pez.isFertil() && pez.sexo()) {
                hayMachoFertil = true;
            }
            // comprobar si el pez es fértil y hembra
            if (pez.isFertil() && !pez.sexo()) {
                hayHembraFertil = true;
            }
            if (hayMachoFertil && hayHembraFertil) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hace crecer todos los peces del tanque, luego realiza el proceso de
     * reproducción y, por último, vende aquellos que hayan llegado a la edad óptima
     */
    public void nextDay() {
        // 1.CRECIMIENTO de los peces
        for (Pez pez : peces) {
            if (pez != null) {
                pez.grow(this);
            }
        }
        // eliminar los peces que murieron (null) durante el proceso de grow
        Iterator<T> iterator = peces.iterator();
        while(iterator.hasNext()) {
            Pez pez = iterator.next();
            if (pez == null) {
                iterator.remove();
                System.out.println("Pez eliminado del tanque (comido por otro pez)");
            }
        }

        // 2.REPRODUCCIÓN, se comprueba si se puede dar la reproducción dentro del
        // tanque, es decir, si hay un macho y hembra fértil
        if (reproduccionPosible()) {
            for (int i = 0; i < peces.size(); i++) {
                Pez pez = peces.get(i);
                // solo se reproducen las hembras
                if (!pez.sexo()) {
                    pez.reproducir(this);
                }
            }
        }

        // 3. VENTA automática de peces en edad óptima
        Iterator<T> iterator2 = peces.iterator();
        while(iterator2.hasNext()) {
            Pez pez = iterator2.next();
            if (pez.isVivo()) {
                if (pez.getEdad() >= pez.getDatosPez().getOptimo()) {
                    // eliminar pez del array
                    iterator2.remove();
                    // Sumar las monedas ganadas
                    Monedero.sumar(pez.getDatosPez().getMonedas());
                    // Registrar la venta y dinero ganado
                    Simulador.estadisticas.registrarVenta(pez.getDatosPez().getNombre(), pez.getDatosPez().getMonedas());
                    System.out.println(pez.getDatosPez().getNombre() + " vendid@ automáticamente por " + pez.getDatosPez().getMonedas() + " monedas.");
                }
            }
        }
    }
}