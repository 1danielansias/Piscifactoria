package Guardado;

import Commons.Simulador;
import Registros.Registro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Clase que efectúa la lógica de guardado de partida.
 *
 * @author Daniel Ansias.
 */
public class Guardar {

    /**
     * Guarda el estado de la partida en formato JSON dentro del archivo de guardado.
     * 
     * @param sim Objeto simulador.
     */
    public static void guardarPartida(Simulador sim) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File saveFile = new File("Saves/" + sim.getNombreEmpresa() + ".save");
        BufferedWriter writer = null;
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(gson.toJson(sim.partidaJsonObject()));
            writer.flush();
            // registrar guardado
            Registro.registrar("Sistema guardado.");
        } catch (IOException e) {
            Registro.registrarEnLog("Error al crear el fichero de guardado.");
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    Registro.registrarEnLog("Error al cerrar el stream de datos.- guardarPartida()");
                    e.printStackTrace();
                }
            }
        }
    }

}
