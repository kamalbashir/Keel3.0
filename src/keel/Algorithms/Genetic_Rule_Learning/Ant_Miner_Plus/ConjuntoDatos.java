package keel.Algorithms.Genetic_Rule_Learning.Ant_Miner_Plus;

import java.util.*;

/**
 * <p>T�tulo: Ant Colony Optimization</p>
 * <p>Descripci�n: Clase conjunto de datos.
 *    Representa el conjunto de datos leidos del fichero en un formato entendible
 *    para el algoritmo ACO </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Empresa: </p>
 * @author Vicente Rub�n del Pino Ruiz
 * @version 1.0
 */

public class ConjuntoDatos {

    private Vector muestras; //Vector con las muestras sacadas del fichero

    /**
     * Constructor por defecto
     * Crea un conjunto vacio.
     */
    public ConjuntoDatos() {
        muestras = new Vector();
    }


    /**
     * Constructor a partir de un vector de muestras
     * @param datos Vector Vector con las muestras a incluir en el conjunto de datos
     */
    public ConjuntoDatos(Vector datos) {
        muestras = new Vector(datos);
    }

    /**
     * Modulo que inserta una muestra en el conjunto de datos
     * @param ejemplo Muestra
     */
    public void insertaMuestra(Muestra ejemplo) {
        //addElement A�ade un elemento al final del vector (perfecto)
        muestras.addElement(ejemplo);
    }

    /**
     * Modulo que elimina una muestra del conjunto de datos
     * @param indice int Indice de la muestra a eliminar
     */
    public void eliminaMuestra(int indice) {

        muestras.removeElementAt(indice);
    }

    /**
     * Funcion que elimina una muestra del conjunto de datos
     * @param muestra Muestra Muestra a eliminar del conjunto de datos
     * @return boolean Booleano que indica si ha sido eliminado o por el contrario no
     */
    public boolean eliminaMuestra(Muestra muestra) {
        boolean devolver;
        devolver = muestras.remove(muestra);
        return devolver;
    }

    /**
     * Funcion que devuelve la primera muestra del  conjunto de datos
     * @return Muestra
     */
    public Muestra obtenerMuestra() {
        Muestra devolver;
        devolver = (Muestra) muestras.firstElement();
        return devolver;

    }

    /**
     * Funcion que devuelve la muestra del conjunto de datos en la posicion indice
     * @param indice int Indice de la muestra a obtener
     * @return Muestra
     */

    public Muestra obtenerMuestra(int indice) {
        Muestra devolver;
        devolver = (Muestra) muestras.get(indice);
        return devolver;
    }

    /**
     * Modulo que imprime el conjunto de datos entero por pantalla
     * @param nombre String Nombre del conjunto de datos
     */

    public void imprimir(String nombre) {
        Muestra actual;

        System.out.println("Conjunto de muestras " + nombre);
        for (int i = 0; i < muestras.size(); i++) {
            actual = (Muestra) muestras.get(i);
            actual.imprimir();
        }
    }

    /**
     * Funcion que devuelve el numero de muestras que posee el conjunto de datos
     * @return int Numero de muestras que contiene el conjunto
     */
    public int tamanio() {
        int devolver;
        devolver = muestras.size();
        return devolver;

    }


    /**
     * Funcion que devuelve un vector de probabilidades de que haya en las muestras Aij y cada W
     * @param atributo Atributo Atributo junto con su valor
     * @param clases Vector Lista con las distintas clases que hay
     * @return float [] Vector con las probabilidades.
     */
    public float[] listaProbabilidadesAtributoClase(Atributo atributo,
            Vector clases) {
        int numClases = clases.size();
        float[] devolver = new float[numClases];
        Muestra actual;
        int total = 0;
        boolean terminado = false;
        Atributo clase;

        for (int i = 0; i < numClases; i++) {
            devolver[i] = 0;
        }

        for (int i = 0; i < muestras.size(); i++) {
            actual = (Muestra) muestras.get(i);
            if (actual.estaAtributo(atributo)) { //Esta el atributo
                total++;
                terminado = false;
                for (int j = 0; j < numClases && !terminado; j++) {
                    clase = (Atributo) clases.get(j);
                    if (actual.estaClase(clase)) {
                        devolver[j]++;
                        terminado = true;
                    }
                }
            }
        }

        for (int i = 0; i < numClases; i++) {
            if (devolver[i] != 0) {
                devolver[i] = devolver[i] / total;
            }
        }

        return devolver;

    }

    /**
     * Modulo que elimina las muestras cubiertas por la regla que se pasa por parametro
     * @param regla Regla Regla que debe cubrir las muestras a eliminar
     * @param cVacias Condiciones Vacias
     */
    public void eliminaMuestrasCubiertas(Regla regla, Vector cVacias) {
        Vector lista = new Vector();
        Muestra mt;

        for (int i = 0; i < muestras.size(); i++) {
            mt = (Muestra) muestras.get(i);
            if (regla.estanCondicionesEn(mt, cVacias)) {
                lista.addElement(mt);
            }
        }
        muestras.removeAll(lista);
    }


    /**
     * Modulo que elimina las muestras que tienen la clase de la regla que se pasa por parametro
     * @param regla Regla que contiene la clase que se va a eliminar
     */
    public void eliminaMuestrasClase(Regla regla) {
        Vector lista = new Vector();
        Muestra mt;
        Atributo clase = regla.obtenerReglaPredicha();

        for (int i = 0; i < muestras.size(); i++) {
            mt = (Muestra) muestras.get(i);
            if (mt.estaClase(clase)) {
                lista.addElement(mt);
            }
        }

        muestras.removeAll(lista);

    }


    /**
     * Funcion que calcula el porcentaje de muestras cubiertas por la regla que se
     * le pasa por parametro.
     * @param regla Regla Regla a comprobar su numero de muestras cubiertas.
     * @param cVacias Condiciones Vacias
     * @return float Porcentaje de muestras cubiertas.
     */
    public float porcentajeMuestrasCubiertas(Regla regla, Vector cVacias) {
        float porcentaje = 0;
        float total = muestras.size();
        float cubiertas = 0;
        Muestra mt;
        Vector eliminar = new Vector();

        for (int i = 0; i < muestras.size(); i++) {
            mt = (Muestra) muestras.get(i);
            if (regla.cubreMuestra(mt, cVacias)) {
                cubiertas++;
                eliminar.addElement(mt);
            }
        }

        muestras.removeAll(eliminar);
        porcentaje = cubiertas;
        return porcentaje;
    }

    /**
     * Funcion que devuelve un Vector con las muestras que contiene el conjunto
     * de datos.
     * @return Vector Muestras que contiene el conjunto de datos.
     */
    public Vector obtenerMuestras() {
        Vector devolver = new Vector(muestras);
        return devolver;
    }

    /**
     * Funcion que devuelve la clase que aparece mas veces en el conjunto de datos
     * @param listaClases Vector Lista con las distintas clases que pueden aparecer
     * @return int Numero de la clase que aparece mas veces en el conjunto de datos
     */
    public int obtenerMayorClase(Vector listaClases) {
        int[] ocurrencias = new int[listaClases.size()];
        Muestra mt;
        Atributo clase;
        Atributo original;
        int mejor = 0;
        int posMejor = 0;

        for (int i = 0; i < listaClases.size(); i++) {
            ocurrencias[i] = 0;
        }

        for (int i = 0; i < muestras.size(); i++) {
            mt = (Muestra) muestras.get(i);
            clase = mt.getClase();
            for (int j = 0; j < listaClases.size(); j++) {
                original = (Atributo) listaClases.get(j);
                if (clase.esIgual(original)) {
                    ocurrencias[j]++;
                }
            }
        }

        for (int i = 0; i < listaClases.size(); i++) {
            if (ocurrencias[i] >= mejor) {
                mejor = ocurrencias[i];
                posMejor = i;
            }
        }

        return posMejor;

    }

    /**
     * Funcion que calcula el porcentaje de muestras que tienen la condicion y clase.
     * @param condicion Condicion que deben cumplir las muestras
     * @param clase Clase que deben tener las muestras
     * @return Porcentaje de las muestras que cumplen la condicion
     */
    public float porcentajeMuestrasCondicion(Condicion condicion,
                                             Atributo clase) {
        Muestra mt;
        Atributo comprobar;
        Atributo at;
        int indice;
        float tamanio = 0;
        float aciertos = 0;
        float porcentaje;
        double valorOriginal;
        double valorCondicion;

        at = condicion.getValor();
        indice = at.getAtributo();

        for (int i = 0; i < muestras.size(); i++) {
            mt = (Muestra) muestras.get(i);
            comprobar = mt.getValor(indice); //en muestra tb puede estar vacio!!!!!

            if (at.getValor().equals("Null") ||
                comprobar.getValor().equals("Null")) {
                tamanio++;
            } else {
                switch (condicion.getOperador()) {
                case 0: //=
                    if (comprobar.esIgual(at)) {
                        tamanio++;
                        if (mt.getClase().esIgual(clase)) {
                            aciertos++;
                        }
                    }
                    break;
                case 1: //<
                    valorOriginal = Double.parseDouble(comprobar.getValor());
                    valorCondicion = Double.parseDouble(at.getValor());
                    if (valorOriginal <= valorCondicion) {
                        tamanio++;
                        if (mt.getClase().esIgual(clase)) {
                            aciertos++;
                        }
                    }
                    break;
                case 2: //>
                    valorOriginal = Double.parseDouble(comprobar.getValor());
                    valorCondicion = Double.parseDouble(at.getValor());
                    if (valorOriginal >= valorCondicion) {
                        tamanio++;
                        if (mt.getClase().esIgual(clase)) {
                            aciertos++;
                        }
                    }
                    break;
                }
            }
        }

        if (aciertos == 0) {
            porcentaje = 0;
        } else {
            porcentaje = aciertos / tamanio;
        }
        return porcentaje;

    }


    /**
     * Funcion que calcula el porcentaje de muestras vacias con la clase.
     * @param vacio Atributo Vacio
     * @param clase Clase que deben tener las muestras.
     * @return Porcentaje de muestras que cumplen las condiciones.
     */
    public float porcentajeMuestrasVacias(Atributo vacio, Atributo clase) {
        float porcentaje;
        float aciertos = 0;
        float tamanio = 0;
        Muestra mt;

        for (int i = 0; i < muestras.size(); i++) {
            mt = (Muestra) muestras.get(i);
            if (mt.estaAtributo(vacio) && mt.estaClase(clase)) {
                aciertos++;
            }
            if (mt.estaAtributo(vacio)) {
                tamanio++;
            }
        }
        if (aciertos == 0 || tamanio == 0) {
            porcentaje = 0;
        } else {
            porcentaje = aciertos / tamanio;
        }
        return porcentaje;
    }


    /**
     * Funcion que calcula el porcentaje de muestras que tienen la clase.
     * @param clase Clase que deben tener las muestras
     * @return Porcentaje con las muestras que cumplen las condiciones.
     */
    public float porcentajeMuestrasClase(Atributo clase) {
        int tamanio = muestras.size();
        Muestra mt;
        float aciertos = 0;

        for (int i = 0; i < tamanio; i++) {
            mt = (Muestra) muestras.get(i);
            if (mt.estaClase(clase)) {
                aciertos++;
            }
        }

        return aciertos / tamanio;

    }


    /**
     * Funcion que calcula el porcentaje de cada clase en el conjunto.
     * @param clases Distintas clases de las que se quiere saber su porcentaje.
     * @return Vector con los porcentajes que tiene cada clase.
     */
    public int[] porcentajeMuestrasClase(Vector clases) {
        int tamanio = muestras.size();
        Atributo clase;
        Muestra mt;
        boolean parada = false;

        int[] aciertos = new int[clases.size()];
        for (int i = 0; i < clases.size(); i++) {
            aciertos[i] = 0;
        }

        for (int i = 0; i < tamanio; i++) {
            mt = (Muestra) muestras.get(i);
            parada = false;
            for (int j = 0; j < clases.size() && !parada; j++) {
                clase = (Atributo) clases.get(j);
                if (mt.estaClase(clase)) {
                    aciertos[j]++;
                    parada = true;
                }
            }
        }

        return aciertos;

    }


    /**
     * Funcion que devuelve el numero de muestras que cumplen con las condiciones de la regla
     * @param hormiga Regla con las condiciones que deben cumplir las muestras.
     * @param cVacias Condiciones Vacias
     * @return Numero de muestras.
     */
    public int numeroMuestrasCondiciones(Regla hormiga, Vector cVacias) {
        int devolver = 0;
        Muestra actual;

        for (int i = 0; i < muestras.size(); i++) {
            actual = (Muestra) muestras.get(i);
            if (hormiga.cubreMuestraCondiciones(actual, cVacias)) {
                devolver++;
            }
        }
        return devolver;
    }


    /**
     * Funcion que calcula el numero de muestras cubiertas por la regla
     * @param hormiga Regla con la que se cubren las muestras.
     * @param cVacias Condiciones vacias
     * @return Numero de muestras vacias.
     */
    public int numeroMuestrasCubiertas(Regla hormiga, Vector cVacias) {
        int devolver = 0;
        Muestra actual;

        for (int i = 0; i < muestras.size(); i++) {
            actual = (Muestra) muestras.get(i);
            if (hormiga.cubreMuestra(actual, cVacias)) {
                devolver++;
            }
        }
        return devolver;
    }


    /**
     * Funcion que indica si una regla cumple con el minimo impuesto o no
     * @param hormiga Regla
     * @param cVacias Condiciones Vacias
     * @param minimoCasosRegla Minimo impuesto que debe cubrir
     * @return Booleano que indica si la regla cumple con el minimo o no.
     */
    public boolean cubreMinimo(Regla hormiga, Vector cVacias,
                               int minimoCasosRegla) {
        Muestra actual;
        int cubiertas = 0;

        for (int i = 0; i < muestras.size(); i++) {
            actual = (Muestra) muestras.get(i);
            if (hormiga.cubreMuestra(actual, cVacias)) {
                cubiertas++;
            }
            if (cubiertas >= minimoCasosRegla) {
                return true;
            }
        }

        return false;

    }


}
