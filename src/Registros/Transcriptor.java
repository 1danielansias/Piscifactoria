package Registros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import propiedades.AlmacenPropiedades;
import propiedades.PecesDatos;

/**
 * Clase que efectúa la lógica de registro de transcripciones.
 * 
 * @author Daniel Ansias
 */
class Transcriptor {
    private BufferedWriter writer = null;
    private File f = null;

    public Transcriptor(String filePath) {
        // Abrir el Stream al inicio
        f = new File(filePath);
        try {
            writer = new BufferedWriter(new FileWriter(f, true));
        } catch (IOException e) {
            Registro.registrarEnLog("Error al crear el stream de escritura de datos de transcripciones.");
            e.printStackTrace();
        }
    }

    /**
     * Registro del inicio de partida.
     * 
     * @param nombrePartida Nombre de la partida.
     * @param dineroInicial Dinero inicial.
     * @param pecesImplementados Lista de peces implementados.
     * @param piscifactoriaInicial Nombre de la piscifactoria inicial.
     */
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

    /**
     * Registra la compra de comida.
     * 
     * @param comidaComprada Cantidad de comida comprada.
     * @param monedasGastadas Total de monedas gastadas.
     * @param alamacenadoEn Lugar donde se almacena.
     */
    public void registrarCompraComida(String comidaComprada, String monedasGastadas, String alamacenadoEn) {
        registrar(comidaComprada + " de comida comprada por " + monedasGastadas + " monedas. Se almacena en " + alamacenadoEn + ".");
    }

    /**
     * Registra la compra de nuevo pez.
     * 
     * @param nombrePez Nombre del pez.
     * @param sexo Sexo del pez.
     * @param tanque Tanque en el que se almacena.
     * @param dineroGastado Total de monedas gastadas.
     */
    public void registrarNuevoPez(String nombrePez, String sexo, String tanque, String dineroGastado) {
        registrar(nombrePez + " (" + sexo + ") comprado por " + dineroGastado + ". Añadido al " + tanque);
    }

    /**
     * Registra la venta manual de peces de una piscifactoria.
     * 
     * @param numPeces Número de peces vendidos.
     * @param piscifactoria Piscifactoria correspondiente.
     * @param dineroObtenido Dinero obtenido por la venta.
     */
    public void registrarVenta(int numPeces, String piscifactoria, int dineroObtenido) {
        registrar("Vendidos " + numPeces + " peces de la piscifactoría " + piscifactoria + " de forma manual por " + dineroObtenido + " monedas");
    }

    /**
     * Registra la compra de una nueva piscifactoria.
     * 
     * @param piscifactoria Tipo de piscifactoria comprada.
     * @param numPisc Número de la piscidactoría.
     * @param monedasGastadas Monedas gastadas.
     */
    public void registrarNuevoPiscifactoria(String piscifactoria, int numPisc, int monedasGastadas) {
        registrar("Comprada la piscifactoria de " + piscifactoria + " " + numPisc + " por " + monedasGastadas + " monedas");
    }

    /**
     * Registra la compra de un nuevo tanque para una piscifactoria.
     * 
     * @param indiceTanque Indice del tanque dentro de la lista de tanques de la piscifactoria.
     * @param piscifactoria Piscifactoria correspondiente.
     */
    public void registrarNuevoTanque(int indiceTanque, String piscifactoria) {
        registrar("Comprado un tanque número " + indiceTanque + " de la piscifactoria " + piscifactoria);
    }

    /**
     * Registra la compra de mejora de capacidad de comida de una piscifactoria.
     * 
     * @param piscifactoria Piscifactoria correspondiente.
     * @param mejora Capacidad total tras el aumento.
     * @param monedasGastadas Monedas gastadas.
     */
    public void registrarMejoraComida(String piscifactoria, int mejora, int monedasGastadas) {
        registrar("Mejorada la piscifactoria " + piscifactoria + " aumentando su capacidad de comida hasta un total de " + mejora + " por " + monedasGastadas + " monedas.");
    }

    /**
     * Registra la compra de mejora de capacidad de comida del alamcen central.
     * 
     * @param capacidadNueva Capacidad total tras el aumento.
     * @param monedasGastadas Monedas gastadas.
     */
    public void registrarMejoraAlmacen(int capacidadNueva, int monedasGastadas) {
        registrar("Mejorado el Almacen Central aumentando su capacidad de comida hasta un total de " + capacidadNueva + " por " + monedasGastadas + " monedas.");
    }

    /**
     * Registra el paso de un día dentro del simulador.
     * 
     * @param diaActual Día actual.
     * @param numPecesRio Número de peces de río.
     * @param numPecesMar Número de peces de mar.
     * @param monedasGanadas Monedas ganadas por la venta automática.
     * @param totalMonedas Total de monedas.
     * @param nuevoDia Día siguiente.
     */
    public void registrarDiaPasado(int diaActual, int numPecesRio, int numPecesMar, int monedasGanadas, int totalMonedas, int nuevoDia) {
        registrar("Fin del día " + diaActual);
        registrar("Peces actuales: " + numPecesRio + " de río, " + numPecesMar + " de mar");
        registrar(monedasGanadas + " monedas ganadas por un total de " + totalMonedas);
        registrar("--------------------------");
        registrar(">>>> Inicio del dia " + nuevoDia);
    }

    /**
     * Registra el uso de una opción oculta.
     * 
     * @param monedasActuales Total de monedas actuales.
     */
    public void registrarOpcionOculta(int monedasActuales) {
        registrar("Añadidas 1000 monedas mediante la opción oculta. Monedas actuales, " + monedasActuales + ".");
    }

    /**
     * Registra la creación de una recompensa en el sistema.
     *
     * @param nombreRecompensa Nombre de la recompensa creada.
     */
    public void registrarRecompensaCreada(String nombreRecompensa) {
        registrar("Recompensa " + nombreRecompensa + " creada");
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
            Registro.registrarEnLog("Error de escritura del fichero de transcripciones.");
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
                Registro.registrarEnLog("Error al cerrar el stream de escritura de datos de transcripciones.");
                e.printStackTrace();
            }
        }
    }

}