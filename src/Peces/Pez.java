package Peces;

import Commons.AlmacenCentral;
import Commons.Piscifactoria;
import Commons.Tanque;
import com.google.gson.JsonObject;
import propiedades.PecesDatos;

import java.util.Random;

/**
 * Clase padre para los diferentes tipos de peces.
 *
 * @author Daniel Ansias
 */
public abstract class Pez {
    /**
     * Edad del pez.
     */
    protected int edad = 0;
    /**
     * Sexo del pez, true si es macho, false si es hembra.
     */
    protected boolean sexo = true;
    /**
     * True si es fértil, false si no lo es.
     */
    protected boolean fertil = false;
    /**
     * True si está vivo, false si no lo está. Por defecto se incializa en true.
     */
    protected boolean vivo = true;
    /**
     * True si está alimentado, false si no lo está.
     */
    protected boolean alimentado = false;
    /**
     * True si es adulto, false si no lo es
     */
    protected boolean maduro;
    /**
     * Ciclo de reproducción del pez
     */
    protected int ciclo;
    /**
     * Objeto de la clase PecesDatos
     */
    protected PecesDatos datosPez;
    /**
     * Piscifactoria a la que pertenece el pez
     */
    protected Piscifactoria piscifactoria;

    /**
     * Constructor parametrizado
     *
     * @param datosPez Datos del pez
     * @param sexo     Sexo del pez
     */
    public Pez(PecesDatos datosPez, boolean sexo, Piscifactoria p) {
        this.datosPez = datosPez;
        this.sexo = sexo;
        this.piscifactoria = p;
        this.ciclo = this.getDatosPez().getCiclo();
    }

    /**
     * @return Objeto datos del pez.
     */
    public PecesDatos getDatosPez() {
        return datosPez;
    }

    /**
     * @return True si está vivo, false si no lo está.
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * @return True si está alimentado, false si no lo está.
     */
    public boolean isAlimentado() {
        return alimentado;
    }

    /**
     * @return True si es maduro, false si no lo es.
     */
    public boolean isMaduro() {
        return maduro;
    }

    /**
     * @return True si es macho, false si es hembra.
     */
    public boolean sexo() {
        return sexo;
    }

    /**
     * @return True si es fertil, false si no lo es.
     */
    public boolean isFertil() {
        return fertil;
    }

    /**
     * @return Edad del pez.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Devuelve el sexo del pez en un String.
     *
     * @return Sexo del pez.
     */
    public String getSexoString() {
        if (sexo) {
            return "M";
        } else {
            return "H";
        }
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public void setPiscifactoria(Piscifactoria p) {
        this.piscifactoria = p;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFertil(boolean fertil) {
        this.fertil = fertil;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public void setAlimentado(boolean alimentado) {
        this.alimentado = alimentado;
    }

    public void setMaduro(boolean maduro) {
        this.maduro = maduro;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public void setDatosPez(PecesDatos datosPez) {
        this.datosPez = datosPez;
    }

    /**
     * Muestra el estado de los peces.
     */
    public void showStatus() {
        String sexoPez = (sexo) ? "Macho" : "Hembra";
        String pezVivo = (vivo) ? "Sí" : "No";
        String pezAlimentado = (alimentado) ? "Sí" : "No";
        String pezMaduro = (maduro) ? "Sí" : "No";
        String pezFertil = (fertil) ? "Sí" : "No";
        System.out.println("------" + datosPez.getNombre() + "------" + "\n" + "Edad: " + edad + " días" + "\n"
                + "Sexo: " + sexoPez + "\n"
                + "Vivo: " + pezVivo + "\n" + "Alimentado: " + pezAlimentado + "\n" + "Maduro: " + pezMaduro + "\n"
                + "Fértil: " + pezFertil);
    }

    /**
     * Comprueba la madurez/edad de los peces, su fértilidad y ciclos
     */
    public void comprobarMadurez() {
        if (edad >= this.datosPez.getMadurez()) {
            maduro = true;
        }
        if (maduro) {
            if (ciclo == 0) {
                fertil = true;
            } else {
                ciclo--;
            }
        }
    }

    /**
     * Hace crecer un día el pez.
     *
     * @param tanque Tanque de la piscifactoría.
     */
    public void grow(Tanque<? extends Pez> tanque) {
        // modificacion P2, 5% de probabilidad de morir cada día par (del pez) antes de madurez
        if (!this.maduro && (this.edad % 2 == 0)) {
            Random rand = new Random();
            double probabilidadMuerte = 0.05; // 5% de probabilidad de morir
            if (rand.nextDouble() < probabilidadMuerte) {
                vivo = false;
            }
        }
        // en caso de que el pez esté muerto, no se realiza nada.
        if (vivo) {
            // alimentar al pez
            alimentar(tanque);
            // si se alimenta, se aumenta su edad y se comprueba su madurez
            if (alimentado) {
                edad++;
                comprobarMadurez();
            } else {
                // si no se alimenta, tiene un 50% de posibilidades de morir
                Random rand = new Random();
                int a = rand.nextInt(2);
                if (a != 0) {
                    // el pez muere pero se mantiene dentro del array
                    vivo = false;
                } else {
                    // si no muere se aumenta su edad y se comprueba la madurez
                    edad++;
                    comprobarMadurez();
                }
            }
            this.alimentado = false;
        }
    }

    /**
     * Comprueba si hay comida disponible en una piscifactoría o en el Almacén
     * Central (en caso de tenerlo) para alimentar un pez.
     *
     * @return True si hay comida disponible, false si no hay.
     */
    public boolean hayComidaDisponible() {
        if (piscifactoria.getComidaDisponible() > 0) {
            return true;
        } else if (AlmacenCentral.almacenActivado && AlmacenCentral.comidaDisponible > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Controla la lógica de la reproducción de los peces
     *
     * @param tanque Tanque de la piscifactoría.
     */
    public void reproducir(Tanque<? extends Pez> tanque) {
        // Comprobar si el pez es fértil
        if (this.fertil) {
            // Se realiza el proceso de reproducción
            for (int i = 0; i < this.datosPez.getHuevos(); i++) {
                // Comprobar que haya espacio en el tanque para que se pueda reproducir
                if (tanque.hayEspacio()) {
                    // Obtener el tipo del pez para añadirlo al tanque
                    Pez nuevo = this.getInstance();
                    // Añadir pez al tanque
                    tanque.addFish(nuevo);
                } else {
                    break;
                }
            }
            // resetear el ciclo del pez una vez se haya reproducido
            this.ciclo = this.datosPez.getCiclo();
            this.fertil = false;
        }
    }

    /**
     * Reinicia el pez, estableciendo su edad a 0 y el resto de sus atributos a sus
     * estados iniciales.
     */
    public void reset() {
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
        this.maduro = false;
        this.ciclo = this.getDatosPez().getCiclo();
    }

    /**
     * Convierte un pez en un objeto de tipo JSON.
     *
     * @return Objeto JSON del pez.
     */
    public JsonObject convertirAJson() {
        JsonObject jsonPez = new JsonObject();
        jsonPez.addProperty("edad", this.edad);
        jsonPez.addProperty("sexo", this.sexo);
        jsonPez.addProperty("vivo", this.vivo);
        jsonPez.addProperty("maduro", this.maduro);
        jsonPez.addProperty("fertil", this.fertil);
        jsonPez.addProperty("ciclo", this.ciclo);
        jsonPez.addProperty("alimentado", this.alimentado);
        return jsonPez;
    }

    /**
     * método abstracto que se encarga de obtener una instancia del objeto
     */
    public abstract Pez getInstance();

    /**
     * método abstracto que se encarga de la alimentación específica de cada pez
     */
    public abstract void alimentar(Tanque<? extends Pez> tanque);
}