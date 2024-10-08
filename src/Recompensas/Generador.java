package Recompensas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import Registros.Registro;

/**
 * Clase que efectúa la lógica de generación de recompensas.
 *
 * @author Daniel Ansias.
 */
public class Generador {

    private final String COMIDA_PATH = "Rewards/comida_";
    private final String MONEDAS_PATH = "Rewards/monedas_";
    private final String ALMACEN_PATH = "Rewards/almacen_";
    private final String PISCI_M_PATH = "Rewards/pisci_m_";
    private final String PISCI_R_PATH = "Rewards/pisci_r_";
    private final String TANQUE_PATH = "Rewards/tanque_";

    /**
     * Crea un nuevo documento XML y le añade el elemento raiz.
     */
    public Document createDocument() {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("reward");
        return doc;
    }

    /**
     * Realiza la lógica de generación de rewards de comida.
     * 
     * @param nivel Nivel del reward.
     */
    public void foodReward(int nivel) {
        File file = new File(COMIDA_PATH + nivel + ".xml");
        Document doc = createDocument();
        Element root = doc.getRootElement();

        String rewardLevel = "";
        int reward = 0;
        int rarity = 0;
        int quantity = setQuantity(file);

        switch (nivel) {
            case 1:
                rewardLevel = "I";
                reward = 50;
                rarity = 0;
                break;
            case 2:
                rewardLevel = "II";
                reward = 100;
                rarity = 1;
                break;
            case 3:
                rewardLevel = "III";
                reward = 250;
                rarity = 2;
                break;
            case 4:
                rewardLevel = "IV";
                reward = 500;
                rarity = 3;
                break;
            case 5:
                rewardLevel = "V";
                reward = 1000;
                rarity = 4;
                break;
        }

        root.addElement("name").addText("Comida general " + rewardLevel);
        root.addElement("origin").addText("Daniel");
        root.addElement("desc").addText(reward + " unidades de pienso multipropósito para todo tipo de peces");
        root.addElement("rarity").addText(String.valueOf(rarity));
        Element give = root.addElement("give");
        give.addElement("food").addAttribute("type", "general").addText(String.valueOf(reward));
        root.addElement("quantity").addText(String.valueOf(quantity));

        save(doc, file);
        Registro.registrarRecompensaCreada(file.getName());
    }

    /**
     * Realiza la lógica de generación de rewards de monedas.
     * 
     * @param nivel Nivel del reward.
     */
    public void coinRewards(int nivel) {
        File file = new File(MONEDAS_PATH + nivel + ".xml");
        Document doc = createDocument();
        Element root = doc.getRootElement();

        String rewardLevel = "";
        String reward = "0";
        String rarity = "0";
        int quantity = setQuantity(file);

        switch (nivel) {
            case 1:
                rewardLevel = "I";
                reward = "100";
                rarity = "0";
                break;
            case 2:
                rewardLevel = "II";
                reward = "300";
                rarity = "1";
                break;
            case 3:
                rewardLevel = "IIi";
                reward = "500";
                rarity = "2";
                break;
            case 4:
                rewardLevel = "IV";
                reward = "750";
                rarity = "3";
                break;
            case 5:
                rewardLevel = "V";
                reward = "1000";
                rarity = "4";
                break;
        }

        root.addElement("name").addText("Monedas " + rewardLevel);
        root.addElement("origin").addText("Daniel");
        root.addElement("desc").addText(reward + " monedas");
        root.addElement("rarity").addText(rarity);
        Element give = root.addElement("give");
        give.addElement("coins").addText(reward);
        root.addElement("quantity").addText(String.valueOf(quantity));

        save(doc, file);
        Registro.registrarRecompensaCreada(file.getName());
    }

    /**
     * Realiza la lógica de generación de rewards de almacén.
     * 
     * @param nivel Nivel del reward.
     */
    public void almacenReward(String nivel) {
        File file = new File(ALMACEN_PATH + nivel.toLowerCase() + ".xml");
        Document doc = createDocument();
        Element root = doc.getRootElement();
        int quantity = setQuantity(file);

        root.addElement("name").addText("Almacén central [" + nivel + "]");
        root.addElement("origin").addText("Daniel");
        root.addElement("desc").addText(
                "Materiales para la construcción de un almacén central. Con la parte A, B, C y D, puedes obtenerlo de forma gratuita.");
        root.addElement("rarity").addText("3");
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", "4").addText("Almacén central");
        give.addElement("part").addText(nivel);
        give.addElement("total").addText("ABCD");
        root.addElement("quantity").addText(String.valueOf(quantity));

        save(doc, file);
        Registro.registrarRecompensaCreada(file.getName());
    }

    /**
     * Realiza la lógica de generación de rewards de piscifactoría de mar.
     * 
     * @param nivel Nivel del reward.
     */
    public void piscifactoriaMarReward(String nivel) {
        File file = new File(PISCI_M_PATH + nivel.toLowerCase() + ".xml");
        Document doc = createDocument();
        Element root = doc.getRootElement();
        int quantity = setQuantity(file);

        root.addElement("name").addText("Piscifactoría de mar [" + nivel.toUpperCase() + "]");
        root.addElement("origin").addText("Daniel");
        root.addElement("desc").addText(
                "Materiales para la construcción de una piscifactoría de mar. Con la parte A y B, puedes obtenerla de forma gratuita.");
        root.addElement("rarity").addText("4");
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", "1").addText("Piscifactoría de mar");
        give.addElement("part").addText(nivel.toUpperCase());
        give.addElement("total").addText("AB");
        root.addElement("quantity").addText(String.valueOf(quantity));

        save(doc, file);
        Registro.registrarRecompensaCreada(file.getName());
    }

    /**
     * Realiza la lógica de generación de rewards de piscifactoría de rio.
     * 
     * @param nivel Nivel del reward.
     */
    public void piscifactoriaRioReward(String nivel) {
        File file = new File(PISCI_R_PATH + nivel.toLowerCase() + ".xml");
        Document doc = createDocument();
        Element root = doc.getRootElement();
        int quantity = setQuantity(file);

        root.addElement("name").addText("Piscifactoría de río [" + nivel.toUpperCase() + "]");
        root.addElement("origin").addText("Daniel");
        root.addElement("desc").addText(
                "Materiales para la construcción de una piscifactoría de río. Con la parte A y B, puedes obtenerla de forma gratuita.");
        root.addElement("rarity").addText("3");
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", "0").addText("Piscifactoría de río");
        give.addElement("part").addText(nivel.toUpperCase());
        give.addElement("total").addText("AB");
        root.addElement("quantity").addText(String.valueOf(quantity));

        save(doc, file);
        Registro.registrarRecompensaCreada(file.getName());
    }

    /**
     * Realiza la lógica de generación de rewards de tanque.
     * 
     * @param tipo Nivel de reward.
     */
    public void tanqueReward(String tipo) {
        File file = new File(TANQUE_PATH + tipo.toLowerCase() + ".xml");
        Document doc = createDocument();
        Element root = doc.getRootElement();

        String tipoTanque = "";
        String rarity = "";
        String buildingCode = "";
        int quantity = setQuantity(file);

        switch (tipo) {
            case "m":
                tipoTanque = "mar";
                rarity = "3";
                buildingCode = "3";
                break;
            case "r":
                tipoTanque = "río";
                rarity = "2";
                buildingCode = "2";
                break;
        }

        root.addElement("name").addText("Tanque de " + tipoTanque);
        root.addElement("origin").addText("Daniel");
        root.addElement("desc").addText(
                "Materiales para la construcción, de forma gratuita, de un tanque de una piscifactoría de " + tipoTanque
                        + ".");
        root.addElement("rarity").addText(rarity);
        Element give = root.addElement("give");
        give.addElement("building").addAttribute("code", buildingCode).addText("Tanque de " + tipoTanque);
        give.addElement("part").addText("A");
        give.addElement("total").addText("A");
        root.addElement("quantity").addText(String.valueOf(quantity));

        save(doc, file);
        Registro.registrarRecompensaCreada(file.getName());
    }

    /**
     * Devuelve la cantidad de la etiqueta "quantity" de un documento XML.
     * 
     * @param file El fichero XML.
     * @return El valor de la etiqueta quantity, -1 si el fichero no existe.
     */
    public int getQuantity(File file) {
        if (file.exists()) {
            SAXReader reader = new SAXReader();
            try {
                Document doc = reader.read(file);
                Element root = doc.getRootElement();
                int quantity = Integer.valueOf(root.element("quantity").getText());
                return quantity;
            } catch (DocumentException e) {
                e.printStackTrace();
                // Registrar posible error
                Registro.registrarEnLog("Error durante la lectura del archivo de recompensas.- getQuantity()");
                return -1;
            }
        }
        return -1;
    }

    /**
     * Devuelve la cantidad de la etiqueta "quantity" dependiendo de si el fichero
     * ya existe o no.
     * 
     * @param file El fichero XML.
     * @return El valor de la etiqueta quantity.
     */
    public int setQuantity(File file) {
        int quantity = getQuantity(file);
        // si el fichero no existe la cantidad será uno
        if (quantity == -1) {
            return 1;
        } else {
            // si ya existe la cantidad será la que tenía más uno
            return quantity + 1;
        }
    }

    /**
     * Reduce en uno la etiqueta "quantity" de un XML.
     * 
     * @param file El fichero XML.
     */
    public void reduceQuantity(File file) {
        if (file.exists()) {
            int quantity = getQuantity(file) - 1;
            SAXReader reader = new SAXReader();
            try {
                Document doc = reader.read(file);
                Element root = doc.getRootElement();
                root.element("quantity").setText(String.valueOf(quantity));
                save(doc, file);
            } catch (DocumentException e) {
                e.printStackTrace();
                // Registrar posible error
                Registro.registrarEnLog("Error durante la lectura del archivo de recompensas.- reduceQuantity()");
            }
        }
    }

    /**
     * Devuelve la recompensa establecida en la etiqueta "give" del XML
     * 
     * @param file El fichero XML.
     */
    public List<Element> getReward(File file) {
        List<Element> list = null;
        if (file.exists()) {
            SAXReader reader = new SAXReader();
            try {
                Document doc = reader.read(file);
                Element root = doc.getRootElement();
                list = root.element("give").elements();
            } catch (DocumentException e) {
                e.printStackTrace();
                // Registrar posible error
                Registro.registrarEnLog("Error durante la lectura del archivo de recompensas.- reduceQuantity()");
            }
        }
        return list;
    }

    /**
     * Guarda en un fichero en disco el archivo XML.
     * 
     * @param doc  Documento a gurdar.
     * @param file Ruta donde se guardará el archivo.
     */
    public void save(Document doc, File file) {
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")),
                    OutputFormat.createPrettyPrint());
            writer.write(doc);
            writer.flush();
        } catch (IOException ex) {
            Registro.registrarEnLog(
                    "Error al guardar en disco el archivo de recompensas " + doc.getDocument().getName() + ".");
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                Registro.registrarEnLog(
                        "Error al guardar en disco el archivo de recompensas. Fallo al cerrar el Writer.");
                e.printStackTrace();
            }
        }
    }
}
