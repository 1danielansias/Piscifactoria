package Commons;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Peces.*;
import Registros.Registro;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Clase que efectúa la lógica de la piscifactoría.
 *
 * @author Daniel Ansias
 */
public class Piscifactoria {
    /**
     * Objeto Scanner para las entradas del usuario.
     */
    protected Scanner sc = new Scanner(System.in);
    /**
     * Lista de tanques en la piscifactoría.
     */
    protected ArrayList<Tanque<? extends Pez>> tanques;
    /**
     * Nombre de la piscifactoría.
     */
    protected String nombre;
    /**
     * Tipo de piscifactoría.
     */
    protected String tipo;
    /**
     * Cantidad máxima de comida que puede guardar el almacén
     */
    protected int comidaPiscifactoriaMax = 0;
    /**
     * Cantidad de comida disponible en el almacén
     */
    protected int comidaDisponible = 0;
    /**
     * Número máximo de tanques que puede tener la piscifactoría
     */
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

    public ArrayList<Tanque<? extends Pez>> getTanques() {
        return tanques;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getComidaPiscifactoriaMax() {
        return comidaPiscifactoriaMax;
    }

    public int getComidaDisponible() {
        return comidaDisponible;
    }

    public void setComidaDisponible(int comidaDisponible) {
        this.comidaDisponible = comidaDisponible;
    }

    public void setTanques(ArrayList<Tanque<? extends Pez>> tanques) {
        this.tanques = tanques;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setComidaPiscifactoriaMax(int comidaPiscifactoriaMax) {
        this.comidaPiscifactoriaMax = comidaPiscifactoriaMax;
    }

    public void setTanquesMax(int tanquesMax) {
        this.tanquesMax = tanquesMax;
    }

    /**
     * Método delegado para acceder directamente a los tanques del array.
     *
     * @param index Índice del tanque que se va a devolver.
     * @return El tanque seleccionado.
     */
    public Tanque<? extends Pez> getTanque(int index) {
        return tanques.get(index);
    }

    /**
     * Crea un nuevo tanque y lo añade a la lista.
     */
    public Tanque<Pez> crearTanque() {
        Tanque<Pez> nuevoTanque = null;
        if (this.tipo.equals("rio")) {
            nuevoTanque = new Tanque<Pez>(25, this);
            tanques.add(nuevoTanque);
        } else if (this.tipo.equals("mar")) {
            nuevoTanque = new Tanque<Pez>(100, this);
            tanques.add(nuevoTanque);
        }
        return nuevoTanque;
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

    /**
     * Muestra toda la información de la piscifactoría
     */
    public void showStatus() {
        DecimalFormat df = new DecimalFormat("#.##"); // Definir el formato

        System.out.println("=========" + this.nombre + "=========");
        System.out.println("Tanques: " + tanques.size());

        double ocupacion = (getCapacidadTotal() != 0) ? ((double) getTotalPeces() / (double) getCapacidadTotal() * 100) : 0;
        System.out.println("Ocupación: " + getTotalPeces() + "/" + getCapacidadTotal() + " (" + df.format(ocupacion) + "%)");

        double pecesVivos = (getTotalPeces() != 0) ? ((double) getTotalVivos() / (double) getTotalPeces() * 100) : 0;
        System.out.println("Peces vivos: " + getTotalVivos() + "/" + getTotalPeces() + " (" + df.format(pecesVivos) + "%)");

        double pecesAlimentados = (getTotalVivos() != 0) ? ((double) getTotalAlimentados() / (double) getTotalVivos() * 100) : 0;
        System.out.println("Peces alimentados: " + getTotalAlimentados() + "/" + getTotalVivos() + " (" + df.format(pecesAlimentados) + "%)");

        double pecesAdultos = (getTotalVivos() != 0) ? ((double) getTotalAdultos() / (double) getTotalVivos() * 100) : 0;
        System.out.println("Peces adultos: " + getTotalAdultos() + "/" + getTotalVivos() + " (" + df.format(pecesAdultos) + "%)");

        System.out.println("Hembras/Machos: " + getTotalHembras() + "/" + getTotalMachos());

        double pecesFertiles = (getTotalVivos() != 0) ? ((double) getTotalFertiles() / (double) getTotalVivos() * 100) : 0;
        System.out.println("Fértiles: " + getTotalFertiles() + "/" + getTotalVivos() + " (" + df.format(pecesFertiles) + "%)");

        double porcentajeComida = (comidaPiscifactoriaMax != 0) ? ((double) comidaDisponible / (double) comidaPiscifactoriaMax * 100) : 0;
        System.out.println("Almacén de comida: " + comidaDisponible + "/" + comidaPiscifactoriaMax + " (" + df.format(porcentajeComida) + "%)");
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
     * determinado.
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

    /**
     * Elimina todos los peces muertos de los tanques de una piscifactoría
     */
    public void cleanTank() {
        // recorrer los tanques de la piscifactoria
        for (Tanque<? extends Pez> t : tanques) {
            // recorrer los peces dentro del tanque
            for (int i = 0; i < t.peces.size(); i++) {
                // si el pez está muerto se elimina del array
                if (!t.peces.get(i).isVivo()) {
                    t.peces.remove(i);
                }
                // Registrar
                Registro.registrar("Limpiado el tanque " + (i + 1) + " de la piscifactoria " + this.nombre);
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

    /**
     * Hace la lógica de pasar de día de todos los peces de la piscifactoría
     */
    public void nextDay() {
        for (Tanque<? extends Pez> t : tanques) {
            t.nextDay();
        }
    }

    /**
     * Vende todos los peces de la piscifactoría que sean adultos y estén vivos
     *
     * @return Array con la cantidad de peces vendidos y cantidad de dinero
     * obtenido.
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
                    ventas[1] += (pez.getDatosPez().getMonedas() / 2);
                    // quitar pez del array
                    iterator.remove();
                    // registrar la venta
                    Simulador.estadisticas.registrarVenta(pez.getDatosPez().getNombre(),
                            pez.getDatosPez().getMonedas());
                }
            }

        }
        return ventas;
    }

    /**
     * Método que vende todos los peces adultos de un tanque especificado.
     *
     * @param tankIndex Indice del tanque.
     * @return Número de peces vendidos.
     */
    public int sellFishTank(int tankIndex) {
        int pecesVendidos = 0;
        Iterator<Pez> iterator = (Iterator<Pez>) tanques.get(tankIndex).peces.iterator();
        while (iterator.hasNext()) {
            Pez pez = iterator.next();
            // comprobar si el pez está vivo y es adulto
            if (pez.isVivo() && pez.isMaduro()) {
                // aumentar los peces vendidos
                pecesVendidos++;
                // quitar pez del array
                iterator.remove();
                // registrar la venta
                Simulador.estadisticas.registrarVenta(pez.getDatosPez().getNombre(),
                        pez.getDatosPez().getMonedas());
                // sumar el dindero
                Monedero.sumar(pez.getDatosPez().getMonedas());
            }
        }
        
        return pecesVendidos;
    }

    /**
     * Mejora el almacén de comida.
     */
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
                    // Registrar operacion
                    Registro.registrarMejoraComida(this.nombre, comidaPiscifactoriaMax, 100);
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
                    // Registrar operacion
                    Registro.registrarMejoraComida(this.nombre, comidaPiscifactoriaMax, 200);
                }
            } else {
                System.out.println("No dispones del dinero suficiente.");
            }
        }
    }

    /**
     * Convierte una piscifactoría en un objeto de tipo JSON.
     *
     * @return Objeto JSON de la piscifactoría.
     */
    public JsonObject convertirAJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nombre", this.nombre);
        jsonObject.addProperty("tipo", this.tipo.equals("mar") ? 1 : 0);
        jsonObject.addProperty("capacidad", this.comidaPiscifactoriaMax);

        JsonObject jsonObjectComida = new JsonObject();
        jsonObjectComida.addProperty("general", this.comidaDisponible);
        jsonObject.add("comida", jsonObjectComida);

        JsonArray jsonArrayTanques = new JsonArray();
        for (Tanque<? extends Pez> t : tanques) {
            jsonArrayTanques.add(t.convertirAJson());
        }
        jsonObject.add("tanques", jsonArrayTanques);

        return jsonObject;
    }
}