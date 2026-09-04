package mx.unam.ciencias.icc;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Metodo auxiliar.
     * Intercambia de posicion los dos elementos del arreglo recibido.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a utiliza.
     * @param i el indice del primer elemento.
     * @param j el indice del segundo elemento.
     */
    private static <T> void swap(T[] arreglo, int i, int j) {
        T aux = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = aux;
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
                swap(arreglo, i, min_idx);
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
     * @param bajo el limite inferior del subarreglo a ordenar.
     * @param alto el limite superior de subarreglo a ordenar.
     */
    private static <T> void 
    quickSort(T[] arreglo, Comparator<T> comparador, int bajo, int alto) {
        // Caso Base:
        if (bajo >= alto)
            return;
        // Ponemos al pivote en su posicion ordenada y particionamos el arreglo [menores, pivote, mayore]
        int pivoteIdx = particionar(arreglo, comparador, bajo, alto);
        // Llamada recursiva en el subarreglo izquierdo:
        quickSort(arreglo, comparador, bajo, pivoteIdx - 1);
        // Llamada recursiva en el subarreglo derecho:
        quickSort(arreglo, comparador, pivoteIdx + 1, alto);
    }

    /**
     * Metodo auxiliar del metodo quickSort.
     * Particiona el arreglo en [menores, pivote, mayores].
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a particionar.
     * @param comparador el comparador para hacer la particion.
     * @param bajo el limite inferior del subarreglo a particionar.
     * @param alto el limite superior de subarreglo a ordenar.
     * @return el indice del arreglo que corresponde a la posicion correcto del pivote.
     */
    private static <T> int
    particionar(T[] arreglo, Comparator<T> comparador, int bajo, int alto) {
        T pivote = arreglo[alto];
        int pivoteIdx = bajo - 1;
        for (int i = bajo; i < alto; i++) {
            if (comparador.compare(arreglo[i], pivote) <= 0) {
                pivoteIdx++;
                swap(arreglo, i, pivoteIdx);
            }
        }
        swap(arreglo, pivoteIdx + 1, alto);
        return pivoteIdx + 1;
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
        return busquedaBinaria(arreglo, elemento, comparador, 0, arreglo.length);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo de manera recursiva. 
     * Regresa el índice del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @param inicio la cota inferior de la busqueda.
     * @param fin la cota superior de la busqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    private static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador, int inicio, int fin) {
        // Caso Base:
        if (inicio >= fin)
            return -1;
        // Elemento medio:
        int medio = inicio + (fin - inicio) / 2;
        int comp = comparador.compare(arreglo[medio], elemento);
        // Busqueda en el subarreglo izquierdo:
        if (comp > 0)
            return busquedaBinaria(arreglo, elemento, comparador, inicio, medio);
        // Busqueda en el suarreglo derecho:
        if (comp < 0)
            return busquedaBinaria(arreglo, elemento, comparador, medio + 1, fin);
        // Se encontro el elemento:
        return medio;
    }
}
