package Recompensas;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase auxiiar que gestiona las partes que tiene un usuario de ciertas recompensas.
 *
 * @author Daniel Ansias.
 */
public class RecompensasHelper {

    private static ArrayList<String> partesAlmacen = new ArrayList<String>(Arrays.asList("x", "x", "x", "x"));
    private static ArrayList<String> partesPiscMar = new ArrayList<String>(Arrays.asList("x", "x"));
    private static ArrayList<String> partesPiscRio = new ArrayList<String>(Arrays.asList("x", "x"));

    /**
     * Añade una parte a la lista de partes de un edificio pasado por parámetro.
     *
     * @param buildingCode Código del edificio.
     * @param parte Parte a añadir.
     */
    public static void addParte(String buildingCode, String parte) {
        switch (buildingCode) {
            case "0":
                addPartePiscRio(parte);
                break;
            case "1":
                addPartePiscMar(parte);
                break;
            case "4":
                addParteAlmacen(parte);
                break;
        }
    }

    /**
     * Añade una parte pasada por parámetro a la lista de partes del alamcén.
     *
     * @param parte La parte a añadir.
     */
    private static void addParteAlmacen(String parte) {
        switch (parte) {
            case "A":
                partesAlmacen.set(0, parte);
                break;
            case "B":
                partesAlmacen.set(1, parte);
                break;
            case "C":
                partesAlmacen.set(2, parte);
                break;
            case "D":
                partesAlmacen.set(3, parte);
                break;
        }
    }

    /**
     * Añade una parte pasada por parámetro a la lista de partes de la piscifactoría de mar.
     *
     * @param parte La parte a añadir.
     */
    private static void addPartePiscMar(String parte) {
        switch (parte) {
            case "A":
                partesPiscMar.set(0, parte);
                break;
            case "B":
                partesPiscMar.set(1, parte);
                break;
        }
    }

    /**
     * Añade una parte pasada por parámetro a la lista de partes de la piscifactoría de río.
     *
     * @param parte La parte a añadir.
     */
    private static void addPartePiscRio(String parte) {
        switch (parte) {
            case "A":
                partesPiscRio.set(0, parte);
                break;
            case "B":
                partesPiscRio.set(1, parte);
                break;
        }
    }

    /**
     * Devuelve las partes almacenadas de un edificio.
     *
     * @param buildingType El tipo de edificio.
     * @return String con las partes almacenadas.
     */
    public static String getPartes(String buildingType) {
        switch(buildingType) {
            case "Piscifactoría de río":
                return format(partesPiscRio);
            case "Piscifactoría de mar":
                return format(partesPiscMar);
            case "Almacén central":
                return format(partesAlmacen);
            default:
                return "";
        }
    }

    /**
     * Formatea la lista de partes.
     *
     * @param partes La lista de partes a formatear.
     * @return String formateado.
     */
    private static String format(ArrayList<String> partes) {
        StringBuilder formattedPartes = new StringBuilder();
        for (String parte : partes) {
            formattedPartes.append(parte);
        }
        return formattedPartes.toString();
    }
}
