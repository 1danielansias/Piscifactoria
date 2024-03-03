package Registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que efectúa la lógica de registro de logs.
 * 
 * @author Daniel Ansias
 */
class Logger {
    private static final String FORMATO_FECHA = "yyyy-MM-dd HH:mm:ss";
    private BufferedWriter writer = null;
    private File f = null;

    public Logger(String filePath) {
        // Abrir el Stream al inicio 
        f = new File(filePath);
        try {
            writer = new BufferedWriter(new FileWriter(f, true));
        } catch (IOException e) {
            Registro.registrarEnLog("Error al crear el stream de escritura de datos de logs.");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la fecha actual del sistema en el formato especificado.
     * 
     * @return String con la fecha.
     */
    private static String getFecha() {
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
        return formato.format(fecha);
    }

    /**
     * Log inicial de la partida.
     * 
     * @param nombrePartida Nombre de la partida.
     * @param piscifactoriaInicial Nombre de la piscifactoría inicial.
     */
    public void logInicial(String nombrePartida, String piscifactoriaInicial) {
        String fecha = getFecha();
        registrar("Inicio de la simulación " + nombrePartida + ".");
        registrar("Piscifactoría inicial: " + piscifactoriaInicial + ".");
    }

    /**
     * Log de comida comprada.
     * 
     * @param comidaComprada Cantidad de comida comprada por el usuario.
     * @param alamacenadoEn Lugar donde se almacena la comida.
     */
    public void registrarCompraComida(String comidaComprada, String alamacenadoEn) {
        String fecha = getFecha();
        registrar(comidaComprada + " de comida comprada. Se almacena en " + alamacenadoEn + ".");
    }

    /**
     * Log de nuevo pez comprado.
     * 
     * @param nombrePez Nombre del pez.
     * @param sexo Sexo del pez.
     * @param tanque Tanque en el que se almacena el pez.
     */
    public void registrarNuevoPez(String nombrePez, String sexo, String tanque) {
        registrar(nombrePez + " (" + sexo + ") comprado. Añadido al " + tanque);
    }

    /**
     * Log de venta de peces.
     * 
     * @param numPeces Número de peces vendidos.
     * @param piscifactoria Piscifactoria correspondiente.
     */
    public void registrarVenta(int numPeces, String piscifactoria) {
        registrar("Vendidos " + numPeces + " peces de la piscifactoría " + piscifactoria + " de forma manual.");
    }

    /**
     * Log de compra de piscifactoria.
     * 
     * @param piscifactoria Nombre de la nueva piscifactoria.
     * @param numPisc Número de la piscifactoria.
     */
    public void registrarNuevoPiscifactoria(String piscifactoria, int numPisc) {
        registrar("Comprada la piscifactoria de " + piscifactoria + " " + numPisc);
    }

    /**
     * Log de compra de nuevo tanque.
     * 
     * @param piscifactoria Piscifactoria correspondiente.
     */
    public void registrarNuevoTanque(String piscifactoria) {
        registrar("Comprado un tanque para la piscifactoria " + piscifactoria + ".");
    }

    /**
     * Log de compra de mejora de comida.
     * 
     * @param piscifactoria Piscifactoria correspondiente.
     */
    public void registrarMejoraComida(String piscifactoria) {
        registrar("Mejorada la piscifactoria " + piscifactoria + " aumentando su capacidad de comida.");
    }

    /**
     * Log de dia pasado.
     * 
     * @param dia Dia finalizado.
     */
    public void registrarDiaPasado(int dia) {
        registrar("Fin del dia " + dia);
    }

    /**
     * Escribe una nueva linea en el documento.
     * 
     * @param mensaje Mensaje a escribir.
     */
    public void registrar(String mensaje) {
        try {
            writer.write("[" + getFecha() + "] " + mensaje);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            Registro.registrarEnLog("Error de escritura del fichero de logs.");
            e.printStackTrace();
        }
    }

    /**
     * Cierra el Stream de datos.
     */
    public void cerrarStream() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                Registro.registrarEnLog("Error al cerrar el stream de escritura de datos de logs.");
                e.printStackTrace();
            }
        }
    }

    
}