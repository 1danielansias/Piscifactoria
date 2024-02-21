package Registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Peces.Pez;
import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

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

        ArrayList<String> pecesRio = new ArrayList<>();
        ArrayList<String> pecesMar = new ArrayList<>();
        
        registrar("Inicio de la simulación " + nombrePartida);
        registrar("Dinero: " + dineroInicial);

        // separar los peces de rio y mar
        for (String nombrePez : pecesImplementados) {
            PecesDatos datosPez = AlmacenPropiedades.getPropByName(nombrePez);
            if (datosPez.getPiscifactoria().toString().equals("RIO")) {
                pecesRio.add(nombrePez);
            } else {
                pecesMar.add(nombrePez);
            }
        }

        registrar("Río:");
        for (String pez : pecesRio) {
            registrar("- " + pez);
        }

        registrar("Mar:");
        for (String pez : pecesMar) {
            registrar("- " + pez);
        }

        registrar("---------------------------");
        registrar("Piscifactoría inicial: " + piscifactoriaInicial);
    }

    public void registrarCompraComida(String comidaComprada, String monedasGastadas, String alamacenadoEn) {
        registrar(comidaComprada + " de comida comprada por " + monedasGastadas + " monedas. Se almacena en " + alamacenadoEn + ".");
    }

    public void registrarNuevoPez(String nombrePez, String sexo, String tanque, String dineroGastado) {
        registrar(nombrePez + " (" + sexo + ") comprado por " + dineroGastado + ". Añadido al " + tanque);
    }

    public void registrarVenta(int numPeces, String piscifactoria, int dineroObtenido) {
        registrar("Vendidos " + numPeces + " peces de la piscifactoría " + piscifactoria + " de forma manual por " + dineroObtenido + " monedas");
    }

    public void registrarNuevoPiscifactoria(String piscifactoria, int numPisc, int monedasGastadas) {
        registrar("Comprada la piscifactoria de " + piscifactoria + " " + numPisc + " por " + monedasGastadas + " monedas");
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