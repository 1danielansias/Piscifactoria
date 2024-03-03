package Commons;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import Guardado.Guardar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Peces.*;
import Peces.Especies.ArenqueDelAtlantico;
import Peces.Especies.Besugo;
import Peces.Especies.Caballa;
import Peces.Especies.CarpaPlateada;
import Peces.Especies.LenguadoEuropeo;
import Peces.Especies.LubinaRayada;
import Peces.Especies.LucioDelNorte;
import Peces.Especies.Pejerrey;
import Peces.Especies.PercaEuropea;
import Peces.Especies.SalmonAtlantico;
import Peces.Especies.TilapiaDeNilo;
import Peces.Especies.TruchaArcoiris;
import Recompensas.Generador;
import Recompensas.RecompensasHelper;
import Registros.Registro;
import estadisticas.Estadisticas;
import propiedades.AlmacenPropiedades;

/**
 * Clase que efectúa la lógica del programa.
 *
 * @author Daniel Ansias
 */
public class Simulador {

    /** Estructuras de datos para almacenar las recompensas */
    protected Map<String, String> recompensasGuardadas = null;
    protected List<File> listaRecompensas2 = null;

    /**
     * Número de días pasados. Por defecto se inicializa a 1.
     */
    protected int numeroDias = 1;
    /**
     * Piscifactorías guardadas.
     */
    protected ArrayList<Piscifactoria> piscifactorias;
    /**
     * Nombre de la empresa.
     */
    protected String nombreEmpresa;
    /**
     * Almacen central. Se inicializará en caso de que el usuario compre un almacén.
     */
    public AlmacenCentral almacenCentral = null;
    /**
     * Objeto Scanner para las entradas del usuario.
     */
    protected Scanner sc;
    /**
     * Número de la piscifactoría seleccionada.
     */
    protected int piscSelec;
    /**
     * Array de peces implementados.
     */
    protected static String[] peces = {AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),
            AlmacenPropiedades.SALMON_ATLANTICO.getNombre(),
            AlmacenPropiedades.CARPA_PLATEADA.getNombre(), AlmacenPropiedades.PERCA_EUROPEA.getNombre(),
            AlmacenPropiedades.PEJERREY.getNombre(), AlmacenPropiedades.LENGUADO_EUROPEO.getNombre(),
            AlmacenPropiedades.LUBINA_RAYADA.getNombre(), AlmacenPropiedades.LUCIO_NORTE.getNombre(),
            AlmacenPropiedades.TILAPIA_NILO.getNombre(), AlmacenPropiedades.TRUCHA_ARCOIRIS.getNombre(),
            AlmacenPropiedades.BESUGO.getNombre(), AlmacenPropiedades.CABALLA.getNombre()};
    /**
     * Objeto de la clase Estadisticas
     */
    public static Estadisticas estadisticas = new Estadisticas(peces);
    /**
     * Objeto de la clase Pez.
     */
    protected Pez pezSelec;
    /**
     * Boolean que controla el final de la partida.
     */
    protected boolean fin = false;
    /**
     * Generador de recompensas
     */
    Generador recompensas = new Generador();

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * Método que inicializa todo el sistema. Inicializa la piscifactoría inicial,
     * añade 100 monedas al
     * Monedero y muestra el menú principal.
     */
    public void init() {
        sc = new Scanner(System.in);
        System.out.println("Introduce el nombre de la empresa: ");
        nombreEmpresa = sc.nextLine();

        crearCarpetas();

        // inicializar objeto Registro
        Registro registro = new Registro(nombreEmpresa);

        // se inicializa el Array de piscifactorías
        piscifactorias = new ArrayList<Piscifactoria>();
        System.out.println("Introduce el nombre de la piscifactoría inicial: ");
        String nombrePisc = sc.nextLine();
        // se crea la piscifactoria inicial
        Piscifactoria inicial = new Piscifactoria("rio", nombrePisc);
        // se añade al array de piscifactorías
        piscifactorias.add(inicial);
        // se inicializan el número de monedas a 100 en el Monedero
        Monedero.sumar(100);

        // registrar inicio de partida en transcriptor y log
        Registro.registrarInicioPartida(nombreEmpresa, String.valueOf(Monedero.dinero), peces, nombrePisc);
        // guardado inicial de la partida
        Guardar.guardarPartida(this);

        // bucle para mostrar el menú hasta que el usuario salga
        showGeneralStatus();
        while (!fin) {
            menu();
        }

        Registro.registroLog("Cierre de la partida.");
        // cerrar Streams de escritura al terminar la partida
        Registro.cerrarStreams();
        // guardado al terminar la partida
        Guardar.guardarPartida(this);
        // cerrar el Scanner
        sc.close();
    }

    /**
     * Crea un nuevo fichero en el sistema si no existe.
     *
     * @param fichero El fichero en cuestión.
     */
    public void crearFichero(File fichero) {
        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                // Registrar posible error
                Registro.registrarEnLog("Error durante la creación del archivo " + fichero.getName());
            }
        }
    }

    /**
     * Crea un directorio si no existe.
     *
     * @param directorio Directorio en cuestión.
     */
    public void crearDirectorio(File directorio) {
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    /**
     * Crea las carpetas necesarias para el guardado de logs, transcripciones, recompensas y guardado de partida.
     */
    public void crearCarpetas() {
        // crear directorios de registros si no existen
        File transcripcionesDir = new File(".\\Transcripciones");
        File logsDir = new File(".\\Logs");
        File rewards = new File(".\\Rewards");
        File saves = new File(".\\Saves");
        crearDirectorio(transcripcionesDir);
        crearDirectorio(logsDir);
        crearDirectorio(rewards);
        crearDirectorio(saves);
        // crear ficheros de transcripción y log
        File ficheroTranscripcion = new File(transcripcionesDir, nombreEmpresa + ".tr");
        File ficheroLogs = new File(logsDir, nombreEmpresa + ".log");
        File errors_log = new File(logsDir, "0_errors.log");
        crearFichero(ficheroTranscripcion);
        crearFichero(ficheroLogs);
        crearFichero(errors_log);
    }

    /**
     * Método que muestra el texto del menú.
     */
    public void menu() {
        System.out.println("-------- Selecciona una opción --------");
        System.out.println("1. Estado general");
        System.out.println("2. Estado piscifactoría");
        System.out.println("3. Estado tanques");
        System.out.println("4. Informes");
        System.out.println("5. Ictiopedia");
        System.out.println("6. Pasar día");
        System.out.println("7. Comprar comida");
        System.out.println("8. Comprar peces");
        System.out.println("9. Vender peces");
        System.out.println("10. Limpiar tanques");
        System.out.println("11. Vaciar tanque");
        System.out.println("12. Mejorar");
        System.out.println("13. Canjear recompensa");
        System.out.println("14. Pasar varios días");
        System.out.println("15. Salir");

        int[] opcionesOcultas = {99, 101};
        int opcion = InputHelper.inputMenu(1, 15, opcionesOcultas, "Introduzca un valor entre 1 y 14.");

        switch (opcion) {
            case -1:
                break;
            case 1:
                showGeneralStatus();
                break;
            case 2:
                showSpecificStatus();
                break;
            case 3:
                showTankStatus();
                break;
            case 4:
                estadisticas.mostrar();
                break;
            case 5:
                showIctio();
                break;
            case 6:
                nextDay();
                Guardar.guardarPartida(this);
                showGeneralStatus();
                break;
            case 7:
                addFood();
                break;
            case 8:
                addFish();
                break;
            case 9:
                sell();
                break;
            case 10:
                cleanTank();
                break;
            case 11:
                emptyTank();
                break;
            case 12:
                upgrade();
                break;
            case 13:
                canjearRecompensa();
                break;
            case 14:
                for (int i = 0; i < 3; i++) {
                    nextDay();
                }
                showGeneralStatus();
                break;
            case 15:
                fin = true;
                break;
            // opciones ocultas
            case 99:
                Monedero.sumar(1000);
                System.out.println("1.000 Monedas añadidas.");
                Registro.registrarOpcionOculta1(Monedero.dinero);
                break;
            case 101:
                recompensas.coinRewards(2);
                Registro.registroLog("Recompensa recibida por opción oculta.");
                recompensas.piscifactoriaMarReward("a");
                Registro.registroLog("Recompensa recibida por opción oculta.");
                recompensas.piscifactoriaMarReward("b");
                Registro.registroLog("Recompensa recibida por opción oculta.");
        }
    }

    /**
     * Método que muestra la lista de piscifactorías actuales en forma de menú, más
     * una opción 0 para cancelar.
     */
    public void menuPisc() {
        System.out.println("Selecciona una opción: (0 para cancelar)");
        System.out.println("---------- Piscifactorías ----------");
        System.out.println("[Peces vivos / Peces totales / Espacio total]");

        for (int i = 0; i < piscifactorias.size(); i++) {
            System.out.println((i + 1) + ". " + piscifactorias.get(i).nombre + " ["
                    + piscifactorias.get(i).getTotalVivos() + "/"
                    + piscifactorias.get(i).getTotalPeces() + "/" + piscifactorias.get(i).getCapacidadTotal() + "]");
        }
    }

    /**
     * Muestra el menú de piscifactorías y permite seleccionar una de ellas
     *
     * @return La opción seleccionada.
     */
    public int selectPisc() {
        menuPisc();
        // se utiliza InputHelper para validar el input del usuario
        int opcion = InputHelper.inputOption(0, piscifactorias.size(),
                "Introduzca un valor entre 0 y " + piscifactorias.size() + ".");
        if (opcion == 0) {
            return -1;
        } else {
            System.out.println("Piscifactoría seleccionada: " + piscifactorias.get(opcion - 1).getNombre());
            return opcion - 1;
        }
    }

    /**
     * Muestra el estado de las piscifactorías, la comida disponible en cada una y
     * todos sus datos. También
     * muestra el día actual y el número de monedas disponibles.
     */
    public void showGeneralStatus() {
        DecimalFormat df = new DecimalFormat("#.##"); // Definir el formato
        System.out.println("---------DÍA " + numeroDias + "---------");
        System.out.println("Dinero disponible: " + Monedero.dinero + " monedas.");
        for (Piscifactoria p : piscifactorias) {
            p.showStatus();
        }
        if (almacenCentral != null) {
            System.out.println("=========Almacen Central=========");
            double porcentaje = ((double) AlmacenCentral.comidaDisponible / (double) AlmacenCentral.capacidad * 100);
            System.out.println("Comida almacenada: " + AlmacenCentral.comidaDisponible + "/" + AlmacenCentral.capacidad
                    + " (" + df.format(porcentaje) + "%)");
        }
    }

    /**
     * Muestra un menú para seleccionar una piscifactoría y luego muestra el estado
     * de todos los tanques de
     * dicha piscifactoría
     */
    public void showSpecificStatus() {
        int selectedPisc = selectPisc();
        if (selectedPisc != -1) {
            Piscifactoria p = piscifactorias.get(selectedPisc);
            p.showTankStatus();
        } else {
            System.out.println("Operación cancelada");
        }
    }

    /**
     * Muestra un menú para seleccionar un tanque de una piscifactoría y luego
     * muestra el estado de todos
     * los peces de dicho tanque.
     */
    public void showTankStatus() {
        int selectedPisc = selectPisc();
        if (selectedPisc != -1) {
            Piscifactoria p = piscifactorias.get(selectedPisc);
            int opcion = selectTank(p);
            if (opcion != -1) {
                p.showFishStatus(opcion);
            }
        } else {
            System.out.println("Operación cancelada");
        }
    }

    /**
     * Muestra el menú de tanques de una piscifactoría y permite seleccionar uno de
     * ellos.
     *
     * @param p La piscifactoría seleccionada.
     * @return La opción seleccionada.
     */
    public int selectTank(Piscifactoria p) {
        // comprobar que la piscifactoría tenga tanques
        if (p.tanques.size() > 0) {
            System.out.println("Selecciona un tanque: (0 para cancelar)");
            System.out.println("---------- Tanques ----------");

            for (int i = 0; i < p.tanques.size(); i++) {
                if (p.tanques.get(i).getPeces().size() > 0) {
                    System.out.println("Tanque #" + (i + 1) + " | Tipo de pez: "
                            + p.tanques.get(i).getPeces().get(0).getDatosPez().getNombre());
                } else {
                    System.out.println("Tanque #" + (i + 1) + " | Tipo de pez: VACÍO");
                }
            }

            // se utiliza InputHelper para validar el input del usuario
            int opcion = InputHelper.inputOption(0, p.tanques.size(),
                    "Introduzca un valor entre 0 y " + p.tanques.size() + ".");
            if (opcion == 0) {
                return -1;
            } else {
                return opcion - 1;
            }
        } else {
            System.out.println("No hay tanques en la piscifactoría saleccionada.");
            return -1;
        }
    }

    /**
     * Realiza la lógica para comprobar los tanques disponibles para introducir un
     * nuevo pez.
     *
     * @param pezSelec El pez seleccionado por el usuario.
     */
    public void comprobarTanquesPez(Pez pezSelec) {
        // ArrayList que guarda los tanques disponibles de una piscifactoría
        ArrayList<Tanque<? extends Pez>> tanquesDisponibles = new ArrayList<>();
        System.out.println("-------- Tanques disponibles --------");

        // comprobar de que tipo es el pez
        if (pezSelec instanceof IRio) {
            // si es de rio, unicamente recorremos los tanques de las piscifactorias que
            // sean de rio
            for (int i = 0; i < piscifactorias.size(); i++) {
                if (piscifactorias.get(i).tipo.equals("rio")) {
                    tanquesDisponibles.addAll(piscifactorias.get(i).comprobarPecesTanque(pezSelec));
                }
            }
        } else if (pezSelec instanceof IMar) {
            for (int i = 0; i < piscifactorias.size(); i++) {
                if (piscifactorias.get(i).tipo.equals("mar")) {
                    tanquesDisponibles.addAll(piscifactorias.get(i).comprobarPecesTanque(pezSelec));
                }
            }
        }

        // si no hay espacio en ningun tanque de ninguna piscifactoria se muestra un
        // mensaje
        if (tanquesDisponibles.size() == 0) {
            System.out.println("No hay tanques disponibles donde introducir este pez");
            // si hay espacio, se da la opcion de elegir donde se quiere introducir el pez
        } else {
            System.out.println(
                    "Selecciona una opcion: (número del 1 al " + tanquesDisponibles.size() + ") - 0 para cancelar.");
            int opcion = InputHelper.inputOption(0, tanquesDisponibles.size(),
                    "Introduzca un valor entre 1 y " + tanquesDisponibles.size() + ".");
            if (opcion != 0) {
                // método para añadir el pez al tanque seleccionado
                tanquesDisponibles.get(opcion - 1).addFish(pezSelec);
                // Restar dinero
                Monedero.restar(pezSelec.getDatosPez().getCoste());
                // Registrar compra
                Registro.registrarNuevoPez(pezSelec.getDatosPez().getNombre(), pezSelec.getSexoString(),
                        tanquesDisponibles.get(opcion - 1).toString(),
                        String.valueOf(pezSelec.getDatosPez().getCoste()));
            } else {
                System.out.println("Operación cancelada.");
            }
        }
    }

    /**
     * Realiza las comprobaciones de dinero y tanques disponibles antes de
     * introducir un
     * nuevo pez a un tanque.
     *
     * @param pezSelec Pez seleccionado por el usuario.
     */
    public void comprobaciones(Pez pezSelec) {
        // se comprueba si tiene el dinero necesario para comprar el pez
        if (Monedero.dinero >= pezSelec.getDatosPez().getCoste()) {
            // se comprueba si hay tanques disponibles
            comprobarTanquesPez(pezSelec);
        } else {
            System.out.println("No hay dinero suficiente para comprar este pez");
        }
    }

    /**
     * Crea un pez nuevo según la opción que elija el usuario al comprar.
     *
     * @param opcion Opción seleccionada por el usuario.
     */
    public void pezSelec(int opcion) {
        if (opcion != 0) {
            switch (opcion) {
                case 1:
                    pezSelec = new CarpaPlateada(false, null);
                    break;
                case 2:
                    pezSelec = new Pejerrey(false, null);
                    break;
                case 3:
                    pezSelec = new LucioDelNorte(false, null);
                    break;
                case 4:
                    pezSelec = new PercaEuropea(false, null);
                    break;
                case 5:
                    pezSelec = new TilapiaDeNilo(false, null);
                    break;
                case 6:
                    pezSelec = new ArenqueDelAtlantico(false, null);
                    break;
                case 7:
                    pezSelec = new LubinaRayada(false, null);
                    break;
                case 8:
                    pezSelec = new Caballa(false, null);
                    break;
                case 9:
                    pezSelec = new LenguadoEuropeo(false, null);
                    break;
                case 10:
                    pezSelec = new Besugo(false, null);
                    break;
                case 11:
                    pezSelec = new TruchaArcoiris(false, null);
                    break;
                case 12:
                    pezSelec = new SalmonAtlantico(false, null);
                    break;
            }
            comprobaciones(pezSelec);
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Muestra un menú de los peces disponibles para comprar.
     *
     * @return La opción seleccionada.
     */
    public int menuPeces() {
        System.out.println("Dinero disponible: " + Monedero.dinero);
        System.out.println("Selecciona un pez: (0 para cancelar).");
        System.out.println("-------- Peces --------");
        System.out.println("1. Carpa Plateada (35 monedas)");
        System.out.println("2. Pejerrey (25 monedas)");
        System.out.println("3. Lucio del norte (150 monedas)");
        System.out.println("4. Perca Europea (100 monedas)");
        System.out.println("5. Tilapia del nilo (25 monedas)");
        System.out.println("6. Arenque del Atlántico (50 monedas)");
        System.out.println("7. Lubina rayada (200 monedas)");
        System.out.println("8. Caballa (30 monedas)");
        System.out.println("9. Lenguado europeo (250 monedas)");
        System.out.println("10. Besugo (200 monedas)");
        System.out.println("11. Trucha arcoíris (60 monedas)");
        System.out.println("12. Salmón atlántico (200 monedas)");
        int opcion = InputHelper.inputOption(12);
        return opcion;
    }

    /**
     * Añade un pez escogido por el usuario a una piscifactoría si hay sitio.
     */
    public void addFish() {
        pezSelec(menuPeces());
    }

    /**
     * Vende todos los peces adultos que estén vivos.
     */
    public void sell() {
        int totalPecesVendidos = 0;
        int totalDineroGanado = 0;
        // Modificacion --> la venta de peces ahora se realizará de una única
        // piscifactoria seleccionada por el usuario, no de todas
        int selectedPisc = selectPisc();
        if (selectedPisc != -1) {
            Piscifactoria p = piscifactorias.get(selectedPisc);
            int[] ventasTotal = p.sellFish();
            totalPecesVendidos += ventasTotal[0];
            totalDineroGanado += ventasTotal[1];
            System.out
                    .println(totalPecesVendidos + " peces vendidos por un total de " + totalDineroGanado + " monedas.");
            // Registrar venta
            Registro.registrarVenta(totalPecesVendidos, p.getNombre(), totalDineroGanado);
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Elimina todos los peces muertos de los tanques de una piscifactoría
     */
    public void cleanTank() {
        int selectedPisc = selectPisc();
        if (selectedPisc != -1) {
            Piscifactoria p = piscifactorias.get(selectedPisc);
            p.cleanTank();
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Elimina todos los peces de un tanque concreto independientemente de su estado
     */
    public void emptyTank() {
        int selectedPisc = selectPisc();
        if (selectedPisc != -1) {
            Piscifactoria p = piscifactorias.get(selectedPisc);
            int tanqueSeleccionado = selectTank(p);
            p.tanques.get(tanqueSeleccionado).peces.clear();
            System.out.println("Tanque vaciado");
            System.out.println("Ocupación del tanque: " + p.tanques.get(tanqueSeleccionado).peces.size());
            // Registro
            Registro.registrar(
                    "Vaciado el tanque " + (tanqueSeleccionado + 1) + " de la piscifactoria " + p.getNombre());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Permite escoger que mejorar.
     */
    public void upgrade() {
        System.out.println("1. Comprar edificios");
        System.out.println("2. Mejorar edificos");
        System.out.println("3. Cancelar");
        int opcion = InputHelper.inputOption(1, 3, "Introduzca un valor entre 1 y 3.");
        switch (opcion) {
            case 1:
                menuComprarEdificios();
                break;
            case 2:
                menuMejorarEdificios();
                break;
            case 3:
                System.out.println("Operación cancelada");
                break;
        }
    }

    /**
     * Controla la lógica de compra de un nuevo edificio.
     */
    public void menuComprarEdificios() {
        System.out.println("¿Qué edificio deseas comprar? (0 para salir)");
        System.out.println("1. Piscifactoría.");
        System.out.println("2. Almacén central.");
        int opcion = InputHelper.inputOption(0, 2, "Introduzca un valor entre 0 y 2.");
        switch (opcion) {
            case 1:
                System.out.println("Introduce el tipo de piscifactoría: (0 para cancelar).");
                System.out.println("1.Rio");
                System.out.println("2.Mar");
                int op = InputHelper.inputOption(0, 2, "Introduzca un valor entre 0 y 2.");
                if (op != 0) {
                    String tipo = "";
                    if (op == 1) {
                        tipo = "rio";
                        nuevaPiscifactoria(tipo, 500);
                    } else if (op == 2) {
                        tipo = "mar";
                        nuevaPiscifactoria(tipo, 2000);
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
                break;
            case 2:
                System.out.println("Estas seguro de que deseas comprar Almacen Central? El coste es de 2000 monedas.");
                System.out.println("1. Sí");
                System.out.println("2. No (Cancelar)");
                int op2 = InputHelper.inputOption(1, 2, "Introduzca un valor entre 1 y 2");
                if (op2 == 1) {
                    // Comprobar que no disponga ya de un almacen
                    if (almacenCentral == null) {
                        if (comprobarDineroUpgrade(2000)) {
                            almacenCentral = new AlmacenCentral();
                            AlmacenCentral.almacenActivado = true;
                            System.out.println("Almacen Central añadido!");
                            Monedero.restar(2000);
                            // Registrar operacion
                            Registro.registrar("Comprado el Almacén Central");
                        } else {
                            System.out.println("No dispones del dinero suficiente.");
                        }
                    } else {
                        System.out.println("Ya dispones de un Almacen Central.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
                break;
            case 0:
                System.out.println("Operación cancelada.");
                break;
        }
    }

    /**
     * Control la lógica de compra de una nueva Piscifactoría.
     *
     * @param tipo   Tipo de piscifactoría.
     * @param precio Precio base de la piscifactoría.
     */
    public void nuevaPiscifactoria(String tipo, int precio) {
        int precioFinal = 0;
        int contadorPisc = 0;
        // COMPROBAR CUANTAS PISCIFACTORÍAS TIENE DE ESE TIPO
        for (Piscifactoria p : piscifactorias) {
            if (p.tipo.equals(tipo)) {
                contadorPisc++;
            }
        }
        precioFinal = (contadorPisc == 0) ? precio : precio * contadorPisc;
        System.out.println("El precio de la piscifactoría es de " + precioFinal + " monedas.");
        if (comprobarDineroUpgrade(precioFinal)) {
            System.out.println("Introduce el nombre de la nueva piscifactoría: (0 para salir)");
            String nombre = sc.nextLine();
            if (!nombre.equals("0")) {
                // Crear piscifactoria
                Piscifactoria piscNueva = new Piscifactoria(tipo, nombre);
                // Añadir piscifactoria al array de piscifactorias
                piscifactorias.add(piscNueva);
                System.out.println("Nueva piscifactoría " + nombre + " añadida!");
                // Restar el dinero del monedero
                Monedero.restar(precioFinal);
                // Registrar
                Registro.registrarNuevaPiscifactoria(tipo, piscifactorias.size(), precio);
            } else {
                System.out.println("Operación cancelada");
            }
        } else {
            System.out.println("No dipones del dinero suficiente.");
        }
    }

    /**
     * Controla la lógica de mejora de edificios.
     */
    public void menuMejorarEdificios() {
        System.out.println("¿Qué deseas mejorar? (0 para salir)");
        System.out.println("1. Piscifactoría.");
        System.out.println("2. Almacen central.");
        int opcion = InputHelper.inputOption(2);
        switch (opcion) {
            case 1:
                System.out.println("0. Salir");
                System.out.println("1. Comprar tanque.");
                System.out.println("2. Aumentar almacén de comida.");
                int selec = InputHelper.inputOption(2);
                switch (selec) {
                    case 0:
                        break;
                    case 1:
                        comprarTanque();
                        break;
                    case 2:
                        aumentarAlmacenPisc();
                        break;
                }
                break;
            case 2:
                if (almacenCentral == null) {
                    System.out.println("No dispones de Almacen Central. No se puede mejorar.");
                } else {
                    aumentarAlmacenCentral();
                }
                break;
            case 0:
                break;
        }
    }

    /**
     * Controla la lógica de compra de un nuevo tanque para una piscifactoría.
     */
    public void comprarTanque() {
        Piscifactoria p = null;
        int precioTanque = 0;
        System.out.println("Selecciona la piscifactoría en la que quieres añadir el nuevo tanque: ");
        int piscSelec = selectPisc();
        if (piscSelec != -1) {
            p = piscifactorias.get(piscSelec);
            // Comprobar el tipo de piscifactoria para calcular precio
            if (p.tipo.equals("rio")) {
                precioTanque = (p.tanques.size() == 0) ? 150 : p.tanques.size() * 150;
            } else if (p.tipo.equals("mar")) {
                precioTanque = (p.tanques.size() == 0) ? 600 : p.tanques.size() * 600;
            }
            System.out.println("El precio del nuevo tanque es de " + precioTanque + " monedas");
            // Comprobar que tenga dinero suficiente
            if (comprobarDineroUpgrade(precioTanque)) {
                // Comprobar que no haya alcanzado el máximo de tanques
                if (p.tanques.size() == p.tanquesMax) {
                    System.out.println("Límite de tanques alcanzado. No puedes añadir un nuevo tanque.");
                } else {
                    // Añadir nuevo tanque
                    Tanque<Pez> nuevoTanque = p.crearTanque();
                    System.out.println("Nuevo tanque añadido a " + p.nombre + "!");
                    // Registrar operacion
                    Registro.registrarNuevoTanque(nuevoTanque.getTankIndex(), p.getNombre());
                }
            } else {
                System.out.println("No dispones del dinero suficiente.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Controla la lógica de aumento de capacidad del almacén de una piscifactoría.
     */
    public void aumentarAlmacenPisc() {
        Piscifactoria p = null;
        System.out.println("Selecciona la piscifactoría de la que quieres aumentar el almacen: ");
        int piscSelec = selectPisc();
        if (piscSelec != -1) {
            p = piscifactorias.get(piscSelec);
            // método upgradeFood de piscifactoría
            p.upgradeFood();
        } else {
            System.out.println("Operación cancelada");
        }
    }

    /**
     * Controla la lógica de aumento de la capacidad del Almacen Central en caso de
     * tenerlo.
     */
    public void aumentarAlmacenCentral() {
        System.out.println("Aumentar capacidad del Almacen Central en 50 unidades. 100 monedas.");
        if (comprobarDineroUpgrade(100)) {
            AlmacenCentral.capacidad += 50;
            Monedero.restar(100);
            System.out
                    .println("Capacidad del Almacen Central aumentada. Capacidad actual: " + AlmacenCentral.capacidad);
            Registro.registrarMejoraAlmacen(AlmacenCentral.capacidad, 100);
        } else {
            System.out.println("No dispones del dinero suficiente.");
        }
    }

    /**
     * Comprueba si el usuario dispone de dinero suficiente para realizar una
     * operación.
     *
     * @param coste Coste de la operación a realizar.
     * @return True si se dispone del dinero suficiente para realizar la operación,
     * false si no.
     */
    public boolean comprobarDineroUpgrade(int coste) {
        if (Monedero.dinero >= coste) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Añade comida a una piscifactoría seleccionada.
     */
    public void addFood() {
        int selectedPisc = selectPisc();
        if (selectedPisc != -1) {
            Piscifactoria p = piscifactorias.get(selectedPisc);
            System.out.println("Comida actual: " + p.comidaDisponible);
            System.out.println("Selecciona la cantidad de comida: (0 para cancelar)");
            System.out.println("1. 5 unidades (5 monedas)");
            System.out.println("2. 10 unidades (10 monedas)");
            System.out.println("3. 25 unidades (25 monedas)");
            System.out.println("4. Llenar");
            int opcion = InputHelper.inputOption(4);
            int cantidadComida = 0;
            switch (opcion) {
                case 0:
                    break;
                case 1:
                    cantidadComida = 5;
                    break;
                case 2:
                    cantidadComida = 10;
                    break;
                case 3:
                    cantidadComida = 25;
                    break;
                case 4:
                    // Llenar
                    cantidadComida = p.comidaPiscifactoriaMax - p.comidaDisponible;
                    break;
            }
            int descuento = (cantidadComida / 25) * 5;
            // comprobar que el usuario tenga dinero suficiente
            if (Monedero.dinero >= cantidadComida) {
                // si el usuario no dispone de almacen, la comida se guarda en la piscifactoria
                if (almacenCentral == null) {
                    if ((p.comidaPiscifactoriaMax - p.comidaDisponible) >= cantidadComida) {
                        // añadir la comida al la piscifactoria
                        p.comidaDisponible += cantidadComida;
                        // comprobar si se hace descuento
                        // restar dinero del monedero
                        Monedero.restar(cantidadComida - descuento);
                        System.out
                                .println("Añadida " + cantidadComida + " de comida por " + (cantidadComida - descuento)
                                        + " monedas.");
                        System.out.println("Deposito de la piscifactoría " + p.nombre + " al "
                                + ((double) p.comidaDisponible / (double) p.comidaPiscifactoriaMax * 100)
                                + "% de su capacidad ["
                                + p.comidaDisponible + "/" + p.comidaPiscifactoriaMax + "]");
                        // registrar compra
                        Registro.registrarCompraComida(String.valueOf(cantidadComida),
                                String.valueOf(cantidadComida - descuento), p.nombre);
                    } else {
                        System.out.println(
                                "No hay espacio suficiente para almacenar la cantidad de comida seleccionada.");
                    }
                    // logica para almacenar la comida en el almacen central
                } else {
                    // comprobar que haya espacio en el almacen central
                    if ((AlmacenCentral.capacidad - AlmacenCentral.comidaDisponible) >= cantidadComida) {
                        AlmacenCentral.comidaDisponible += cantidadComida;
                        System.out.println("Añadida " + cantidadComida + " de comida al Almacen Central por "
                                + (cantidadComida - descuento)
                                + " monedas.");
                        // registrar compra
                        Registro.registrarCompraComida(String.valueOf(cantidadComida),
                                String.valueOf(cantidadComida - descuento), "almacén central");
                        // distribuir comida
                        AlmacenCentral.distribuirComida(piscifactorias);
                    } else {
                        System.out
                                .println("No hay espacio suficiente para almacenar la cantidad de comida seleccionada");
                    }
                }
            } else {
                System.out.println("No dispones del dinero suficiente.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Realiza la lógica de canjeo de recompensas disponibles.
     */
    public void canjearRecompensa() {
        // Recorrer la carpeta con los archivos XML de recompensas
        File directorio = new File("Rewards");
        ArrayList<File> listaRecompensas = new ArrayList<>(Arrays.asList(directorio.listFiles()));
        if (listaRecompensas.size() > 0) {
            // Estructura de datos para almacenar las recompensas
            recompensasGuardadas = new LinkedHashMap<>();
            listaRecompensas2 = new ArrayList<>();

            int fileIndex = 0;
            // Recorrer los archivos de recompensas y guardar las recompensas de edificio
            for (File file : listaRecompensas) {
                SAXReader reader = new SAXReader();
                try {
                    Document doc = reader.read(file);
                    Element root = doc.getRootElement();
                    String name = root.element("name").getText();
                    String desc = root.element("desc").getText();

                    // Si es una recompensa de edificio, agregar las partes a la lista
                    if (root.element("give").element("building") != null) {
                        String buildingType = root.element("give").element("building").getText();
                        String buildingCode = root.element("give").element("building").attributeValue("code");
                        String part = root.element("give").element("part").getText();
                        RecompensasHelper.addParte(buildingCode, part);

                        if (!recompensasGuardadas.containsKey(buildingType)) {
                            recompensasGuardadas.put(buildingType, desc);
                            listaRecompensas2.add(listaRecompensas.get(fileIndex));
                        }

                    } else {
                        recompensasGuardadas.put(name, desc);
                        listaRecompensas2.add(listaRecompensas.get(fileIndex));
                    }

                } catch (DocumentException e) {
                    e.printStackTrace();
                    Registro.registrarEnLog("Error en la lectura del archivo de recompensas.");
                }
                fileIndex++;
            }
            // Mostrar menú de recompensas
            menuRecompensas(recompensasGuardadas, listaRecompensas2);
        } else {
            System.out.println("No tienes recompensas disponibles.");
        }
    }

    /**
     * Muestra un menú con las recompensas diponibles.
     *
     * @param recompensasGuardadas Lista de los nombres y descripción de las recompensas.
     * @param listaRecompensas2    Lista de ficheros de recompensas.
     */
    public void menuRecompensas(Map<String, String> recompensasGuardadas, List<File> listaRecompensas2) {
        System.out.println("--------- Recompensas disponibles --------");
        System.out.println("Seleccione una opción: (0 para cancelar)");
        int fileIndex = 0;
        // Mostrar las recompensas disponibles
        for (Map.Entry<String, String> entry : recompensasGuardadas.entrySet()) {
            String nombreRecompensa = entry.getKey();
            String partes = RecompensasHelper.getPartes(nombreRecompensa);
            if (!partes.isEmpty()) {
                System.out.println(++fileIndex + ". " + nombreRecompensa + " [" + partes + "]");
            } else {
                System.out.println(++fileIndex + ". " + nombreRecompensa);
            }
            System.out.println("\tRecompensa: " + entry.getValue());
        }

        int selectedOption = InputHelper.inputOption(listaRecompensas2.size(),
                "Introduzca una opción entre 0 y " + listaRecompensas2.size() + ".");
        if (selectedOption != 0) {
            File recompensaSeleccionada = listaRecompensas2.get(selectedOption - 1);
            List<Element> rewardElements = recompensas.getReward(recompensaSeleccionada);
            getReward(rewardElements, recompensaSeleccionada);
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Otorga la recompensa al usuario.
     *
     * @param rewardElements Lista de elementos contenidos en la etiqueta "give" de
     *                       la recompensa.
     */
    public void getReward(List<Element> rewardElements, File selectedReward) {
        String rewardType = rewardElements.get(0).getName();
        String reward = rewardElements.get(0).getText();
        switch (rewardType) {
            case "coins":
                Monedero.sumar(Integer.valueOf(reward));
                System.out.println("¡Recompensa canjeada con éxito!");
                System.out.println("Sumadas " + reward + " monedas.");
                Registro.registrar("Recompensa " + selectedReward.getName() + " usada");
                consumeReward(selectedReward);
                break;
            case "food":
                System.out.println("¡Recompensa canjeada con éxito!");
                if (almacenCentral != null) {
                    int foodToAdd = Math.min(Integer.valueOf(reward),
                            AlmacenCentral.capacidad - AlmacenCentral.comidaDisponible);
                    AlmacenCentral.comidaDisponible += foodToAdd;
                    System.out.println("Añadidas " + foodToAdd + " unidades de comida al almacén central.");
                } else {
                    distribuirComida(piscifactorias, Integer.valueOf(reward));
                    System.out.println("Comida distriuida entre las piscifactorías");
                }
                Registro.registrar("Recompensa " + selectedReward.getName() + " usada");
                consumeReward(selectedReward);
                break;
            case "building":
                // si es un tanque se construye directamente
                if (rewardElements.get(0).getText().contains("Tanque")) {
                    build(reward);
                } else {
                    // si no lo es, comprobamos sus partes primero
                    String partesDisponibles = RecompensasHelper.getPartes(reward);
                    String partesNecesarias = rewardElements.get(2).getText();
                    if (partesDisponibles.equals(partesNecesarias)) {
                        Registro.registrar("Recompensa " + selectedReward.getName() + " usada");
                        build(reward);
                    } else {
                        System.out.println("No dispones de todas las partes necesarias para canjear este edificio.");
                        menuRecompensas(recompensasGuardadas, listaRecompensas2);
                    }
                }
                break;
        }
    }

    /**
     * Crea un nuevo edificio especificado por parámetro.
     *
     * @param buildingType Tipo de edificio.
     */
    public void build(String buildingType) {
        String nombre = "";
        switch (buildingType) {
            case "Piscifactoría de río":
                System.out.println("Introduce el nombre de la nueva piscifactoría: (0 para salir)");
                nombre = sc.nextLine();
                if (!nombre.equals("0")) {
                    Piscifactoria piscNueva = new Piscifactoria("rio", nombre);
                    piscifactorias.add(piscNueva);
                    System.out.println("Nueva piscifactoría " + nombre + " añadida!");
                    consumeRewards(buildingType);
                }
                break;
            case "Piscifactoría de mar":
                System.out.println("Introduce el nombre de la nueva piscifactoría: (0 para salir)");
                nombre = sc.nextLine();
                if (!nombre.equals("0")) {
                    Piscifactoria piscNueva = new Piscifactoria("mar", nombre);
                    piscifactorias.add(piscNueva);
                    System.out.println("Nueva piscifactoría " + nombre + " añadida!");
                    consumeRewards(buildingType);
                }
                break;
            case "Almacén central":
                if (almacenCentral == null) {
                    almacenCentral = new AlmacenCentral();
                    AlmacenCentral.almacenActivado = true;
                    System.out.println("Almacen Central añadido!");
                    consumeRewards(buildingType);
                }
            case "Tanque de mar":
                Piscifactoria p = null;
                System.out.println("Selecciona la piscifactoría en la que quieres añadir el nuevo tanque.");
                int piscSelec = selectPisc();
                if (piscSelec != -1) {
                    p = piscifactorias.get(piscSelec);
                    if (p.tipo.equals("mar")) {
                        if (p.tanques.size() == p.tanquesMax) {
                            System.out.println("Límite de tanques alcanzado. No puedes añadir un nuevo tanque.");
                        } else {
                            Tanque<Pez> nuevoTanque = p.crearTanque();
                            System.out.println("Recompensa canjeada con éxito!");
                            System.out.println("Nuevo tanque añadido a " + p.nombre);
                            consumeRewards(buildingType);
                            Registro.registrarNuevoTanque(nuevoTanque.getTankIndex(), p.getNombre());
                        }
                    } else {
                        System.out.println("No se puede introducir el tanque a esta piscifactoría. Operación cancelada.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
                break;
            case "Tanque de río":
                Piscifactoria p2 = null;
                System.out.println("Selecciona la piscifactoría en la que quieres añadir el nuevo tanque.");
                int piscSelec2 = selectPisc();
                if (piscSelec2 != -1) {
                    p2 = piscifactorias.get(piscSelec2);
                    if (p2.tipo.equals("rio")) {
                        if (p2.tanques.size() == p2.tanquesMax) {
                            System.out.println("Límite de tanques alcanzado. No puedes añadir un nuevo tanque.");
                        } else {
                            Tanque<Pez> nuevoTanque = p2.crearTanque();
                            System.out.println("Recompensa canjeada con éxito!");
                            System.out.println("Nuevo tanque añadido a " + p2.nombre);
                            consumeRewards(buildingType);
                            Registro.registrarNuevoTanque(nuevoTanque.getTankIndex(), p2.getNombre());
                        }
                    } else {
                        System.out.println("No se puede introducir el tanque a esta piscifactoría. Operación cancelada.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
                break;

            default:
                return;
        }
    }

    /**
     * Reduce en uno o elimina una recomensa utilizada por el usuario.
     *
     * @param recompensa La recompensa utilizada.
     */
    public void consumeReward(File recompensa) {
        // restar uno "quantity"
        recompensas.reduceQuantity(recompensa);
        if (recompensas.getQuantity(recompensa) < 1) {
            // eliminar si la cantidad es igual a cero
            recompensa.delete();
        }
    }

    /**
     * Elimina todos los ficheros de partes de un edificio.
     *
     * @param buildingType Tipo de edifico.
     */
    public void consumeRewards(String buildingType) {
        File directorio = new File("Rewards");
        ArrayList<File> listaFicheros = new ArrayList<>(Arrays.asList(directorio.listFiles()));
        switch (buildingType) {
            case "Piscifactoría de río":
                for (File f : listaFicheros) {
                    if (f.getName().contains("pisci_r_")) {
                        f.delete();
                    }
                }
                break;
            case "Piscifactoría de mar":
                for (File f : listaFicheros) {
                    if (f.getName().contains("pisci_m_")) {
                        f.delete();
                    }
                }
                break;
            case "Almacén central":
                for (File f : listaFicheros) {
                    if (f.getName().contains("almacen_")) {
                        f.delete();
                    }
                }
                break;
            default:
        }
    }

    /**
     * Distribuye de forma equitativa una cantidad de comida entre las
     * piscifactorias. La comida que no entre se pierde.
     *
     * @param piscifactorias Lista de piscifactorias.
     * @param cantidadComida La cantidad de comida a distribuir.
     */
    public void distribuirComida(ArrayList<Piscifactoria> piscifactorias, int cantidadComida) {
        // Verificar que haya al menos una piscifactoría
        if (piscifactorias.size() > 0) {
            // Calcular la cantidad de comida que cada piscifactoría debería recibir
            // inicialmente
            int cantidadPorPiscifactoria = cantidadComida / piscifactorias.size();
            // Distribuir la comida equitativamente entre todas las piscifactorías
            for (Piscifactoria p : piscifactorias) {
                // Calcular la cantidad de comida adicional que puede recibir esta piscifactoría
                // sin exceder su capacidad máxima
                int cantidadAdicional = Math.min(cantidadPorPiscifactoria,
                        p.comidaPiscifactoriaMax - p.comidaDisponible);
                // Agregar la cantidad adicional de comida a esta piscifactoría
                p.comidaDisponible += cantidadAdicional;
                // Restar la cantidad distribuida de comida del total
                cantidadComida -= cantidadAdicional;
            }
        }
    }

    /**
     * Permite consultar la información de cada pez mostrando un menú de selección
     * de los disponibles.
     */
    public void showIctio() {
        // mostrar menú peces
        switch (menuPeces()) {
            case 1:
                Pez p1 = new CarpaPlateada(true, null);
                getPropiedadesPez(p1);
                break;
            case 2:
                Pez p2 = new Pejerrey(true, null);
                getPropiedadesPez(p2);
                break;
            case 3:
                Pez p3 = new LucioDelNorte(true, null);
                getPropiedadesPez(p3);
                break;
            case 4:
                Pez p4 = new PercaEuropea(true, null);
                getPropiedadesPez(p4);
                break;
            case 5:
                Pez p5 = new TilapiaDeNilo(true, null);
                getPropiedadesPez(p5);
                break;
            case 6:
                Pez p6 = new ArenqueDelAtlantico(false, null);
                getPropiedadesPez(p6);
                break;
            case 7:
                Pez p7 = new LubinaRayada(false, null);
                getPropiedadesPez(p7);
                break;
            case 8:
                Pez p8 = new Caballa(false, null);
                getPropiedadesPez(p8);
                break;
            case 9:
                Pez p9 = new LenguadoEuropeo(false, null);
                getPropiedadesPez(p9);
                break;
            case 10:
                Pez p10 = new Besugo(false, null);
                getPropiedadesPez(p10);
                break;
            case 11:
                Pez p11 = new TruchaArcoiris(false, null);
                getPropiedadesPez(p11);
                break;
            case 12:
                Pez p12 = new SalmonAtlantico(false, null);
                getPropiedadesPez(p12);
                break;
        }
    }

    /**
     * Muestra por pantalla las propiedades de un pez seleccionado
     *
     * @param p Pez seleccionado
     */
    public void getPropiedadesPez(Pez p) {
        System.out.println("-------- " + p.getDatosPez().getNombre() + " --------");
        System.out.println("Nombre científico: " + p.getDatosPez().getCientifico());
        System.out.println("Coste: " + p.getDatosPez().getCoste());
        System.out.println("Monedas: " + p.getDatosPez().getMonedas());
        System.out.println("Huevos: " + p.getDatosPez().getHuevos());
        System.out.println("Ciclo: " + p.getDatosPez().getCiclo());
        System.out.println("Madurez: " + p.getDatosPez().getMadurez());
        System.out.println("Edad óptima: " + p.getDatosPez().getOptimo());
    }

    /**
     * Avanza un día en todas las piscifactorías, realizando el crecimiento,
     * reproducción
     * y venta de peces óptimos.
     */
    public void nextDay() {
        // guardamos en una variable la cantidad de monedas antes de la venta automática
        // del nextDay()
        int monedasAntes = Monedero.dinero;

        // lógica del nextDay
        for (Piscifactoria p : piscifactorias) {
            p.nextDay();
        }

        // obtener numero de peces para el registro
        int pecesRio = 0;
        int pecesMar = 0;
        // separar los peces de rio y mar
        for (Piscifactoria p : piscifactorias) {
            if (p.tipo.equals("rio")) {
                pecesRio += p.getTotalPeces();
            } else {
                pecesMar += p.getTotalPeces();
            }

        }

        // calculamos la monedas ganadas con la venta automática
        int monedasGanadas = Monedero.dinero - monedasAntes;
        // registrar dia pasado
        Registro.registrarDiaPasado(numeroDias, pecesRio, pecesMar, monedasGanadas, Monedero.dinero, (numeroDias + 1));

        // sumar un dia
        numeroDias += 1;
        // distribucion de comida a cada piscifactoria en caso de tener almacen central
        if (AlmacenCentral.almacenActivado) {
            AlmacenCentral.distribuirComida(piscifactorias);
        }
    }

    /**
     * Convierte el AlmacenCentral en un objeto JSON.
     *
     * @return Objecto JSON del Almacen.
     */
    public JsonObject almacenJsonObject() {
        JsonObject jsonObjectAlmacen = new JsonObject();
        jsonObjectAlmacen.addProperty("disponible", this.almacenCentral == null ? false : true);
        jsonObjectAlmacen.addProperty("capacidad", AlmacenCentral.capacidad);
        JsonObject jsonObjectComida = new JsonObject();
        jsonObjectComida.addProperty("general", AlmacenCentral.comidaDisponible);
        jsonObjectAlmacen.add("comida", jsonObjectComida);
        return jsonObjectAlmacen;
    }

    /**
     * Convierte la lista de peces implementados en un Array JSON.
     *
     * @return Array JSON de la lista de peces.
     */
    public JsonArray pecesJsonArray() {
        JsonArray jsonArrayPeces = new JsonArray();
        for (String nombre : peces) {
            jsonArrayPeces.add(nombre);
        }
        return jsonArrayPeces;
    }

    /**
     * Convierte el simulador en un Objeto de tipo JSON.
     *
     * @return Un objeto JSON del simulador/partida.
     */
    public JsonObject partidaJsonObject() {
        JsonObject partidaJsonObject = new JsonObject();
        partidaJsonObject.add("implementados", pecesJsonArray());
        partidaJsonObject.add("empresa", new JsonPrimitive(nombreEmpresa));
        partidaJsonObject.add("dia", new JsonPrimitive(numeroDias));
        partidaJsonObject.add("monedas", new JsonPrimitive(Monedero.dinero));
        partidaJsonObject.add("orca", new JsonPrimitive(estadisticas.exportarDatos(peces)));
        partidaJsonObject.add("edificios", almacenJsonObject());
        JsonArray piscifactoriasJsonArray = new JsonArray();
        for (Piscifactoria p : piscifactorias) {
            piscifactoriasJsonArray.add(p.convertirAJson());
        }
        partidaJsonObject.add("piscifactorias", piscifactoriasJsonArray);
        return partidaJsonObject;
    }

    public static void main(String[] args) throws Exception {
        try {
            Simulador sim = new Simulador();
            sim.init();
        } catch (Exception e) {
            Registro.registrarEnLog("Error durante la simulación.");
            e.printStackTrace();
        } finally {
            Registro.cerrarStreams();
        }
    }
}