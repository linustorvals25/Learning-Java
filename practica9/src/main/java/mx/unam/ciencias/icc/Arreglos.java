package mx.unam.ciencias.icc;

import java.util.Comparator;
import java.util.Random;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

     /**
     * Intercambia de posicion los elementos de los indices recibidos
     * en el arreglo.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param i el i-esimo elemento del arreglo.
     * @param j el j-esimo elemento del arreglo.
     */
    private static <T> void intercambia(T[] arreglo, int i, int j) {
        T temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[min_idx]) < 0) 
                    min_idx = j;
            }
            if (min_idx != i)
                intercambia(arreglo, i, min_idx);
        }
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, comparador, 0, arreglo.length - 1);
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     * @param inicio indice de cota inferior del ordenamiento.
     * @param fin indice de cota superior del ordenamiento.
     */
    private static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador, int inicio, int fin) {
        // Caso base: 
        if (inicio >= fin)
            return;
        // Ordenamos el pivate:
        int pivote_idx = particionar(arreglo, comparador, inicio, fin);
        // Llamada recursiva del subarreglo izquierdo:
        quickSort(arreglo, comparador, inicio, pivote_idx - 1);
        // Llamada recursiva del subarreglo derecho:
        quickSort(arreglo, comparador, pivote_idx + 1, fin);
    }

    /**
     * Particiona el arreglo recibido en: [menores, pivote, mayores]
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     * @param inicio indice de cota inferior del ordenamiento.
     * @param fin indice de cota superior del ordenamiento.
     * @return el indice del arreglo que corresponde a la posicion ordenada del pivate
     */
    private static <T> int particionar(T[] arreglo, Comparator<T> comparador, int inicio, int fin) {
        T pivote = arreglo[fin];
        int pivoteIdx = inicio - 1;
        for (int i = inicio; i < fin; i++) {
            if (comparador.compare(arreglo[i], pivote) <= 0) {
                pivoteIdx++;
                intercambia(arreglo, pivoteIdx, i);
            }
        }
        pivoteIdx++;
        intercambia(arreglo, pivoteIdx, fin);
        return pivoteIdx;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int random = new Random().nextInt(2);
        if (random == 0)
            return busquedaBinariaRecursiva(arreglo, elemento, comparador, random, random);
        else 
            return busquedaBinariaIterativa(arreglo, elemento, comparador);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @param inicio la cota inferior de busqueda.
     * @param fin la cota superior de busqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    private static <T> int
    busquedaBinariaRecursiva(T[] arreglo, T elemento, Comparator<T> comparador, int inicio, int fin) {
        // Caso base:
        if (inicio >= fin)
            return -1;
        // Comparacion:
        int medio = inicio + (fin - inicio);
        int comp = comparador.compare(elemento, arreglo[medio]);
        // Llamada recusiva en el subarreglo izquierdo:
        if (comp < 0)
            return busquedaBinariaRecursiva(arreglo, elemento, comparador, inicio, medio - 1);
        // Llamada recursiva en el subarreglo derecho:
        if (comp > 0)
            return busquedaBinariaRecursiva(arreglo, elemento, comparador, inicio, medio - 1);
        // Caso base:
        return medio;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @param inicio la cota inferior de busqueda.
     * @param fin la cota superior de busqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    private  static <T> int
    busquedaBinariaIterativa(T[] arreglo, T elemento, Comparator<T> comparador) {
        int izquierda = 0;
        int derecha = arreglo.length - 1;
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            int comp = comparador.compare(elemento, arreglo[medio]);
            if (comp < 0) 
                derecha = medio - 1;
            else if (comp > 0)
                izquierda = medio + 1;
            else
                return  medio;
        }
        return -1;
    } 
}
