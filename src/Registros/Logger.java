package Registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * Registro 
     * 
     * @param nombrePartida Nombre de la partida.
     * @param piscifactoriaInicial Nombre de la piscifactoría inicial.
     */
    public void logInicial(String nombrePartida, String piscifactoriaInicial) {
        String fecha = getFecha();
        registrar("[" + fecha + "] Inicio de la simulación " + nombrePartida + ".");
        registrar("[" + fecha + "] Piscifactoría inicial: " + piscifactoriaInicial + ".");
    }

    public void registrarCompraComida(String comidaComprada, String alamacenadoEn) {
        String fecha = getFecha();
        registrar("[" + fecha + "]" + comidaComprada + " de comida comprada. Se almacena en " + alamacenadoEn + ".");
    }

    /**
     * Escribe una nueva linea en el documento.
     * 
     * @param mensaje Mensaje a escribir.
     */
    public void registrar(String mensaje) {
        try {
            writer.write(mensaje);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
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
                e.printStackTrace();
            }
        }
    }

    
}