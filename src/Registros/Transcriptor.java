package Registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Transcriptor {
    private BufferedWriter writer = null;
    private File f = null;

    public Transcriptor(String filePath) {
        // Abrir el Stream al inicio
        f = new File(filePath);
        try {
            writer = new BufferedWriter(new FileWriter(f, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registroInicial(String nombrePartida, String dineroInicial, String[] pecesImplementados,
            String piscifactoriaInicial) {

        registrar("Inicio de la simulación " + nombrePartida);
        registrar("Dinero: " + dineroInicial);

        for (String nombrePez : pecesImplementados) {
            registrar("-" + nombrePez);
        }

        registrar("Piscifactoría inicial: " + piscifactoriaInicial);
    }

    public void registrarCompraComida(String comidaComprada, String monedasGastadas, String alamacenadoEn) {
        registrar(comidaComprada + " de comida comprada por " + monedasGastadas + " monedas. Se almacena en " + alamacenadoEn + ".");
    }

    /**
     * Escribe una nueva linea en el documento.
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
     * Cierra el stream de datos.
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