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

    public static void registrarInicioPartida(String nombrePartida, String dineroInicial, String[] pecesImplementados, String piscifactoriaInicial) {
        tr.registroInicial(nombrePartida, dineroInicial, pecesImplementados, piscifactoriaInicial);
        log.logInicial(nombrePartida, piscifactoriaInicial);
    }

    public static void registrarCompraComida(String comidaComprada, String monedasGastadas, String alamacenadoEn) {
        tr.registrarCompraComida(comidaComprada, monedasGastadas, alamacenadoEn);
        log.registrarCompraComida(comidaComprada, alamacenadoEn);
    }

    /**
     * public void registrarVenta() {
     *      tr.resgistrarVenta();
     *      log.registrarVenta();
     * }
     * 
     */
}
