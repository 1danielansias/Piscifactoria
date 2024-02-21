package Registros;

public class Registro {
    private static Transcriptor tr;
    private static Logger log;

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

    public static void registrarNuevaPiscifactoria(String piscifactoria, int numPisc, int monedasGastadas) {
        tr.registrarNuevoPiscifactoria(piscifactoria, numPisc, monedasGastadas);
        log.registrarNuevoPiscifactoria(piscifactoria, numPisc);
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

    /**
     * public void registrarVenta() {
     *      tr.resgistrarVenta();
     *      log.registrarVenta();
     * }
     * 
     */
}
