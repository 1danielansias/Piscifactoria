package Commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import Peces.*;

/**
 * Clase que efectúa la lógica de la piscifactoría.
 * 
 * @author Daniel Ansias
 */
public class Piscifactoria {
    /** Objeto Scanner para las entradas del usuario. */
    protected Scanner sc = new Scanner(System.in);
    /** Lista de tanques en la piscifactoría. */
    protected ArrayList<Tanque<? extends Pez>> tanques;
    /** Nombre de la piscifactoría. */
    protected String nombre;
    /** Tipo de piscifactoría. */
    protected String tipo;
    /** Cantidad máxima de comida que puede guardar el almacén */
    protected int comidaPiscifactoriaMax = 0;
    /** Cantidad de comida disponible en el almacén */
    protected int comidaDisponible = 0;
    /** Número máximo de tanques que puede tener la piscifactoría */
    protected int tanquesMax = 10;

    /**
     * Constructor parametrizado.
     * 
     * @param tipo   Tipo de piscifactoría.
     * @param nombre Nombre de la piscifactoría.
     */
    public Piscifactoria(String tipo, String nombre) {
        this.tipo = tipo.toLowerCase();
        this.nombre = nombre.toLowerCase();
        // inicialización del arrayList de tanques
        tanques = new ArrayList<>();
        if (this.tipo.equals("rio")) {
            // establecer el máximo de comida
            comidaPiscifactoriaMax = 25;
            comidaDisponible = 25;
            // se inicializa con un tanque
            Tanque<Pez> tanque1 = new Tanque<Pez>(25, this);
            tanques.add(tanque1);
        } else if (this.tipo.equals("mar")) {
            comidaPiscifactoriaMax = 100;
            comidaDisponible = 100;
            Tanque<Pez> tanque = new Tanque<Pez>(100, this);
            tanques.add(tanque);
        }
    }

    /**
     * @return El ArrayList de tanques de la piscifactoría.
     */
    public ArrayList<Tanque<? extends Pez>> getTanques() {
        return tanques;
    }

    /**
     * @return El nombre de la piscifactoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return El tipo de la piscifactoría.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return La cantidad de comida máxima de la piscifactoría.
     */
    public int getComidaPiscifactoriaMax() {
        return comidaPiscifactoriaMax;
    }

    /**
     * @return La cantidad de comida disponible en la piscifactoría.
     */
    public int getComidaDisponible() {
        return comidaDisponible;
    }

    /**
     * @param comidaDisponible Comida disponible.
     */
    public void setComidaDisponible(int comidaDisponible) {
        this.comidaDisponible = comidaDisponible;
    }

    /**
     * 
     * Método delegado para acceder directamente a los tanques del array.
     * 
     * @param index Índice del tanque que se va a devolver.
     * @return El tanque seleccionado.
     */
    public Tanque<? extends Pez> getTanque(int index) {
        return tanques.get(index);
    }

    /** Crea un nuevo tanque y lo añade a la lista. */
    public void crearTanque() {
        if (this.tipo.equals("rio")) {
            Tanque<Pez> tanque = new Tanque<Pez>(25, this);
            tanques.add(tanque);
        } else if (this.tipo.equals("mar")) {
            Tanque<Pez> tanque = new Tanque<Pez>(100, this);
            tanques.add(tanque);
        }
    }

    /**
     * Obtiene el total de peces vivos de una piscifactoría
     * 
     * @return El total de peces vivos.
     */
    public int getTotalVivos() {
        int totalVivos = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalVivos += tanques.get(i).pecesVivos();
        }
        return totalVivos;
    }

    /**
     * Obtiene el total de peces alimentados de una piscifactoría
     * 
     * @return El total de peces alimentados.
     */
    public int getTotalAlimentados() {
        int totalAlimentados = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalAlimentados += tanques.get(i).pecesAlimentados();
        }
        return totalAlimentados;
    }

    /**
     * Obtiene el total de peces adultos de una piscifactoría
     * 
     * @return El total de peces adultos.
     */
    public int getTotalAdultos() {
        int totalAdultos = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalAdultos += tanques.get(i).pecesAdultos();
        }
        return totalAdultos;
    }

    /**
     * Obtiene el total de peces hembra de una piscifactoría
     * 
     * @return El total de peces hembra.
     */
    public int getTotalHembras() {
        int totalHembras = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalHembras += tanques.get(i).pecesHembra();
        }
        return totalHembras;
    }

    /**
     * Obtiene el total de peces macho de una piscifactoría
     * 
     * @return El total de peces macho.
     */
    public int getTotalMachos() {
        int totalMachos = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalMachos += tanques.get(i).pecesMacho();
        }
        return totalMachos;
    }

    /**
     * Obtiene el total de peces fertiles de una piscifactoría
     * 
     * @return El total de peces fertiles.
     */
    public int getTotalFertiles() {
        int totalFertiles = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalFertiles += tanques.get(i).pecesFertiles();
        }
        return totalFertiles;
    }

    /**
     * Obtiene la capacidad total de los tanques de la piscifactoría.
     * 
     * @return La capacidad total.
     */
    public int getCapacidadTotal() {
        int capacidad = 0;
        for (int i = 0; i < tanques.size(); i++) {
            capacidad += tanques.get(i).getCapacidad();
        }
        return capacidad;
    }

    /**
     * Obtiene el total de peces en la piscifactoría.
     * 
     * @return El total de peces en la piscifactoría.
     */
    public int getTotalPeces() {
        int totalPeces = 0;
        for (int i = 0; i < tanques.size(); i++) {
            totalPeces += tanques.get(i).getNumPeces();
        }
        return totalPeces;
    }

    /** Muestra toda la información de la piscifactoría */
    public void showStatus() {
        System.out.println("=========" + this.nombre + "=========");
        System.out.println("Tanques: " + tanques.size());
        double ocupacion = (getCapacidadTotal() != 0) ? ((double)getTotalPeces() / (double)getCapacidadTotal() * 100) : 0;
        System.out.println("Ocupación: " + getTotalPeces() + "/" + getCapacidadTotal() +  " (" + ocupacion + "%)");

        double pecesVivos = (getTotalPeces() != 0) ? ((double)getTotalVivos() / (double)getTotalPeces() * 100) : 0;
        System.out.println("Peces vivos: " + getTotalVivos() + "/" + getTotalPeces() + " (" + pecesVivos + "%)");

        double pecesAlimentados = (getTotalVivos() != 0) ? ((double)getTotalAlimentados() / (double)getTotalVivos() * 100) : 0;
        System.out.println("Peces alimentados: " + getTotalAlimentados() + "/" + getTotalVivos() + " (" + pecesAlimentados + "%)");

        double pecesAdultos = (getTotalVivos() != 0) ? ((double)getTotalAdultos() / (double)getTotalVivos() * 100) : 0;
        System.out.println("Peces adultos: " + getTotalAdultos() + "/" + getTotalVivos() + " (" + pecesAdultos + "%)");

        System.out.println("Hembras/Machos: " + getTotalHembras() + "/" + getTotalMachos());

        double pecesFertiles = (getTotalVivos() != 0) ? ((double)getTotalFertiles() / (double)getTotalVivos() * 100) : 0;
        System.out.println("Fértiles: " + getTotalFertiles() + "/" + getTotalVivos() + " (" + pecesFertiles + "%)");

        double porcentajeComida = (comidaPiscifactoriaMax != 0) ? ((double)comidaDisponible / (double)comidaPiscifactoriaMax * 100) : 0;
        System.out.println("Almacén de comida: " + comidaDisponible + "/" + comidaPiscifactoriaMax + " (" + porcentajeComida + "%)");
    }

    /**
     * Muestra la información de cada tanque.
     */
    public void showTankStatus() {
        for (int i = 0; i < tanques.size(); i++) {
            tanques.get(i).showStatus(i);
        }
    }

    /**
     * Muestra la información de todos los peces de un tanque determinado.
     * 
     * @param index Índice del tanque del que se va a mostrar la información.
     */
    public void showFishStatus(int index) {
        tanques.get(index).showFishStatus();
    }

    /**
     * Comprueba si un tanque es elegible para introducir un nuevo pez.
     * 
     * @param pezSelec Pez seleccionado para introducir por el usuario.
     * @return ArrayList de los tanques disponible para introducir un pez
     *         determinado.
     */
    public ArrayList<Tanque<? extends Pez>> comprobarPecesTanque(Pez pezSelec) {
        ArrayList<Tanque<? extends Pez>> tanquesDisponibles = new ArrayList<>();
        // recorrer tanques
        for (int i = 0; i < tanques.size(); i++) {
            // si hay un tanque vacío, estará disponible para insertar un pez nuevo
            if (tanques.get(i).estaVacio()) {
                tanquesDisponibles.add(tanques.get(i));
                System.out.println("- Tanque #" + (i + 1) + " de la piscifactoría "
                        + this.nombre + " disponible");
                // si no está vacío, se comprueba su ocupación y si admite el tipo de pez se
                // quiere meter
            } else if (tanques.get(i).hayEspacio()) {
                // Obtener el tipo de pez que admite el tanque
                String pezTanque = tanques.get(i).peces.get(0).getClass().getName();
                // Si coincide con el que el usuario quiere insertar
                if (pezTanque.equals(pezSelec.getClass().getName()) || tanques.get(i).estaVacio()) {
                    tanquesDisponibles.add(tanques.get(i));
                    System.out.println("- Tanque #" + (i + 1) + " de la piscifactoría "
                            + this.nombre + " disponible");
                }
            }
        }
        return tanquesDisponibles;
    }

    /** Elimina todos los peces muertos de los tanques de una piscifactoría */
    public void cleanTank() {
        // recorrer los tanques de la piscifactoria
        for (Tanque<? extends Pez> t : tanques) {
            // recorrer los peces dentro del tanque
            for (int i = 0; i < t.peces.size(); i++) {
                // si el pez está muerto se elimina del array
                if (!t.peces.get(i).isVivo()) {
                    t.peces.remove(i);
                }
            }
        }
        System.out.println("Peces muertos eliminados de los tanques de " + this.nombre);
    }

    /**
     * Muestra la capacidad total de un tanque de la pisicfactoría.
     * 
     * @param index Número del tanque.
     */
    public void showCapacity(int index) {
        tanques.get(index).showCapacity(index, this);
    }

    /** Hace la lógica de pasar de día de todos los peces de la piscifactoría */
    public void nextDay() {
        for (Tanque<? extends Pez> t : tanques) {
            t.nextDay();
        }
    }

    /**
     * Vende todos los peces de la piscifactoría que sean adultos y estén vivos
     * 
     * @return Array con la cantidad de peces vendidos y cantidad de dinero
     *         obtenido.
     */
    public int[] sellFish() {
        // array para guardar el número de peces vendidos y el total ganado
        int[] ventas = new int[2];
        // Recorrer tanques de la piscifactoría
        for (Tanque<? extends Pez> t : tanques) {
            // Recorrer los peces de cada tanque con iterator para poder modificar mientras
            // se recorre
            Iterator<Pez> iterator = (Iterator<Pez>) t.peces.iterator();
            while (iterator.hasNext()) {
                Pez pez = iterator.next();
                // comprobar si el pez está vivo y es adulto
                if (pez.isVivo() && pez.isMaduro()) {
                    // aumentar los peces vendidos
                    ventas[0]++;
                    // sumar el dinero ganado --> Modificación, ahora dan la mitad de dinero de lo normal
                    ventas[1] += (pez.getDatosPez().getMonedas()/2);
                    // quitar pez del array
                    iterator.remove();
                    // registrar la venta
                    Simulador.estadisticas.registrarVenta(pez.getDatosPez().getNombre(),
                            pez.getDatosPez().getMonedas());
                }
            }

        }
        /**System.out.println(
                "Piscifactoría " + this.nombre + ": " + ventas[0] + " vendidos por " + ventas[1] + " monedas");*/
        return ventas;
    }

    /** Mejora el almacén de comida. */
    public void upgradeFood() {
        if (tipo.equals("rio")) {
            System.out.println("Aumentar almacén en 25 unidades. 100 monedas. ");
            if (Monedero.dinero >= 100) {
                // Comprobar que no se haya alcanzado el máximo de capacidad
                if (comidaPiscifactoriaMax >= 250) {
                    System.out.println("Capacidad máxima alcanzada. No se puede mejorar.");
                } else {
                    comidaPiscifactoriaMax += 25;
                    Monedero.restar(100);
                    System.out.println("Almacén de comida de la piscifactoría " + this.nombre
                            + " mejorado. Su capacidad ha aumentado en 25.");
                }
            } else {
                System.out.println("No dispones del dinero suficiente.");
            }
        } else if (tipo.equals("mar")) {
            System.out.println("Aumentar almacén en 100 unidades. 200 monedas. ");
            // comprobar que tenga dinero para la mejora
            if (Monedero.dinero >= 200) {
                // comprobar que no se haya alcanzado el máximo de capacidad
                if (comidaPiscifactoriaMax >= 1000) {
                    System.out.println("Capacidad máxima alcanzada. No se puede mejorar.");
                } else {
                    comidaPiscifactoriaMax += 100;
                    Monedero.restar(200);
                    System.out.println("Almacén de comida de la piscifactoría " + this.nombre
                            + " mejorado. Su capacidad ha aumentado en 100.");
                }
            } else {
                System.out.println("No dispones del dinero suficiente.");
            }
        }
    }
}