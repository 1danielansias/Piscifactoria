package Registros;

/**
 * Clase que efectúa la lógica de registro de logs y transcripciones.
 * 
 * @author Daniel Ansias
 */
public class Registro {
    private static Transcriptor tr;
    private static Logger log;
    private static Logger error_log;

    public Registro(String filePath) {
        tr = new Transcriptor(".\\Transcripciones\\" + filePath + ".tr");
        log = new Logger(".\\Logs\\" + filePath + ".log");
    }

    // Cerrar los streams del Transcriptor y Logger
    public static void cerrarStreams() {
        tr.cerrarStream();
        log.cerrarStream();
    }

    /**
     * Realiza las operaciones de registro del inicio de partida.
     * 
     * @param nombrePartida Nombre de la partida.
     * @param dineroInicial Dinero incial.
     * @param pecesImplementados Lista de peces implementados.
     * @param piscifactoriaInicial Nombre de la piscifactoria incial.
     */
    public static void registrarInicioPartida(String nombrePartida, String dineroInicial, String[] pecesImplementados, String piscifactoriaInicial) {
        tr.registroInicial(nombrePartida, dineroInicial, pecesImplementados, piscifactoriaInicial);
        log.logInicial(nombrePartida, piscifactoriaInicial);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario compra comida.
     * 
     * @param comidaComprada Cantidad de comida comprada.
     * @param monedasGastadas Número total de monedas gastadas.
     * @param alamacenadoEn Lugar donde se almacena la comida.
     */
    public static void registrarCompraComida(String comidaComprada, String monedasGastadas, String alamacenadoEn) {
        tr.registrarCompraComida(comidaComprada, monedasGastadas, alamacenadoEn);
        log.registrarCompraComida(comidaComprada, alamacenadoEn);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario compra un nuevo pez.
     * 
     * @param nombrePez Nombre del pez.
     * @param sexo Sexo del pez.
     * @param tanque Tanque donde se almacena el pez.
     * @param dineroGastado Dinero gastado.
     */
    public static void registrarNuevoPez(String nombrePez, String sexo, String tanque, String dineroGastado) {
        tr.registrarNuevoPez(nombrePez, sexo, tanque, dineroGastado);
        log.registrarNuevoPez(nombrePez, sexo, tanque);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario vende peces de una piscifactoría.
     * 
     * @param numPeces Número de peces vendidos.
     * @param piscifactoria Piscifactoría seleccionada.
     * @param dineroGanado Dinero obtenido con la venta.
     */
    public static void registrarVenta(int numPeces, String piscifactoria, int dineroGanado) {
        tr.registrarVenta(numPeces, piscifactoria, dineroGanado);
        log.registrarVenta(numPeces, piscifactoria);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario compra una nueva piscifactoria.
     * 
     * @param piscifactoria Nombre de la nueva piscifactoria.
     * @param numPisc Numero de la piscifactoria.
     * @param monedasGastadas Total de monedas gastadas.
     */
    public static void registrarNuevaPiscifactoria(String piscifactoria, int numPisc, int monedasGastadas) {
        tr.registrarNuevoPiscifactoria(piscifactoria, numPisc, monedasGastadas);
        log.registrarNuevoPiscifactoria(piscifactoria, numPisc);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario compra un nuevo tanque.
     * 
     * @param indiceTanque Indice del nuevo tanque.
     * @param piscifactoria Piscifactoria al que pertenece.
     */
    public static void registrarNuevoTanque(int indiceTanque, String piscifactoria) {
        tr.registrarNuevoTanque(indiceTanque, piscifactoria);
        log.registrarNuevoTanque(piscifactoria);
    }

    /**
     * Realiza las operaciones de registro cuendo el usuario mejora la capacidad de comida de una piscifactoria.
     * 
     * @param piscifactoria Nombre de la piscifactoria mejorada.
     * @param mejora Nueva capacidad tras la mejora.
     * @param monedasGastadas Total de monedas gastadas.
     */
    public static void registrarMejoraComida(String piscifactoria, int mejora, int monedasGastadas) {
        tr.registrarMejoraComida(piscifactoria, mejora, monedasGastadas);
        log.registrarMejoraComida(piscifactoria);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario mejora la capacidad del Almacen Central.
     * 
     * @param capacidadNueva Nueva capacidad tras la mejora.
     * @param monedasGastadas Total de monedas gastadas.
     */
    public static void registrarMejoraAlmacen(int capacidadNueva, int monedasGastadas) {
        tr.registrarMejoraAlmacen(capacidadNueva, monedasGastadas);
        log.registrar("Mejorado el Almacen Central aumentando su capacidad de comida.");
    }

    /**
     * Realiza las operaciones de registro cuando el usuario pasa un dia en la simulación.
     * 
     * @param diaActual Día actual de la simulación antes de pasar.
     * @param numPecesRio Número de peces de río que tiene el jugador.
     * @param numPecesMar Número de peces de mar que tiene el jugador.
     * @param monedasGanadas Total de monedas ganadas por la venta automática.
     * @param totalMonedas Total de monedas.
     * @param nuevoDia Día nuevo.
     */
    public static void registrarDiaPasado(int diaActual, int numPecesRio, int numPecesMar, int monedasGanadas, int totalMonedas, int nuevoDia) {
        tr.registrarDiaPasado(diaActual, numPecesRio, numPecesMar, monedasGanadas, totalMonedas, nuevoDia);
        log.registrarDiaPasado(diaActual);
    }

    /**
     * Realiza las operaciones de registro cuando el usuario utiliza una de las opciones ocultas.
     * 
     * @param monedasActuales Total de monedas.
     */
    public static void registrarOpcionOculta1(int monedasActuales) {
        tr.registrarOpcionOculta(monedasActuales);
        log.registrar("Añadidas monedas mediante la opción oculta.");
    }

    /**
     * Realiza las operaciones de registro cuando el usuario finaliza la partida.
     * 
     * @param mensaje Mensaje a registrar.
     */
    public static void registroLog(String mensaje) {
        log.registrar(mensaje);
    }

    /**
     * Realiza las operacones de registro cuando se crea una nueva recompensa en el sistema.
     *
     * @param nombreRecompensa Nombre de la recompensa creada.
     */
    public static void registrarRecompensaCreada(String nombreRecompensa) {
        tr.registrarRecompensaCreada(nombreRecompensa);
        log.registrar("Recompensa creada.");
    }

    /**
     * Realiza las operaciones de registro de errores del sistema.
     * 
     * @param mensaje Mensaje de error.
     */
    public static void registrarEnLog(String mensaje) {
        // nueva instancia de un logger de un solo uso
        error_log = new Logger(".\\Logs\\0_errors.log");
        error_log.registrar(mensaje);
        // el stream se cierra al finalizar el registro
        error_log.cerrarStream();
    }

    /**
     * Reliza las operaciones de registro general.
     * 
     * @param mensaje Mensaje a registrar.
     */
    public static void registrar(String mensaje) {
        tr.registrar(mensaje);
        log.registrar(mensaje);
    }

}
