package mx.unam.ciencias.icc;

import java.util.Iterator;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para listas genéricas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Iterable<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if (!hasNext())
                throw new NoSuchElementException("No hay elemento siguiente.");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException("No hay elemento anterior.");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;
    /* Mensaje de error al tratar de agregar un elemento null a la lista. */
    private static final String MSJ_NULL_ELEM = "La lista no acepta a null como elemento.";
    /* Mensaje de error al tratar de eliminar cuando la lista esta vacia. */
    private static final String MSJ_EMPTY_LIST = "La lista esta vacio, no hay elementos.";

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException(MSJ_NULL_ELEM);
        Nodo nuevo = new Nodo(elemento);
        if (esVacia()) {
            cabeza = rabo = nuevo;
        } else {
            rabo.siguiente = nuevo;
            nuevo.anterior = rabo;
            rabo = nuevo;
        }
        longitud++;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException(MSJ_NULL_ELEM);
        Nodo nuevo = new Nodo(elemento);
        if (esVacia()) {
            cabeza = rabo = nuevo;
        } else {
            cabeza.anterior = nuevo;
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
        longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException(MSJ_NULL_ELEM);
        if (i <= 0) {
            agregaInicio(elemento);
            return;
        }
        if (i >= longitud) {
            agregaFinal(elemento);
            return;
        }
        Nodo temp = cabeza;
        for (int j = 0; j < i - 1; j++) {
            temp = temp.siguiente;
        }
        Nodo nuevo = new Nodo(elemento);
        nuevo.anterior = temp;
        nuevo.siguiente = temp.siguiente;
        temp.siguiente.anterior = nuevo;
        temp.siguiente = nuevo;
        longitud++;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
        if (esVacia() || elemento == null)
            return;
        Nodo elim = getNodo(elemento);
        if (elim == null)
            return;
        if (elim == cabeza) {
            eliminaPrimero();
            return;
        }
        if (elim == rabo) {
            eliminaUltimo();
            return;
        }
        elim.anterior.siguiente = elim.siguiente;
        elim.siguiente.anterior = elim.anterior;
        longitud--;
    }

    /**
     * Metodo auxiliar al metodo elimina.
     * Retorna el Nodo que contega la primer ocurrencia del elemento recibido.
     * @param elemento el elemento a buscar el la lista.
     * @return el Nodo que contega al elemento, o <code>null</code> si el elemento 
     * no esta en la lista.
     */
    private Nodo getNodo(T elemento) {
        if (esVacia() || elemento == null) 
            return null;
        Nodo temp = cabeza;
        while (temp != null) {
            if (temp.elemento.equals(elemento))
                return temp;
            temp = temp.siguiente;
        }
        return null;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY_LIST);
        T elim = cabeza.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        longitud--;
        return elim;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY_LIST);
        T elim = rabo.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        }
        longitud--;
        return elim;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(T elemento) {
        return getNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reversa = new Lista<>();
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            reversa.agregaInicio(iterator.next());
        }
        return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<>();
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            copia.agregaFinal(iterator.next());
        }
        return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY_LIST);
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY_LIST);
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido("Indice fuera de rango: [0, longitud).");
        Nodo temp = cabeza;
        for (int j = 0; j < i; j++) {
            temp = temp.siguiente;
        }
        return temp.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        if (esVacia() || elemento == null)
            return -1;
        Nodo temp = cabeza;
        for (int i = 0; i < longitud; i++) {
            if (temp.elemento.equals(elemento))
                return i;
            temp = temp.siguiente;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext())
                sb.append(", ");
        }
        return sb.toString() + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if (longitud != lista.longitud)
            return false;
        Iterator<T> ite1 = iterator();
        Iterator<T> ite2 = lista.iterator();
        for (int i = 0; i < longitud; i++) {
            if (!ite1.next().equals(ite2.next()))
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Case Base:
        if (longitud <= 1)
            return this.copia();
        // Dividir la lista en dos mitades:
        Lista<T> lista1 = new Lista<>();
        Lista<T> lista2 = new Lista<>();
        int mitad = longitud / 2;
        Nodo temp = cabeza;
        for (int i = 0; i < longitud; i++) {
            if (i < mitad) {
                lista1.agregaFinal(temp.elemento);
            } else {
                lista2.agregaFinal(temp.elemento);
            }
            temp = temp.siguiente;
        }
        // Ordenar recursivamente cada mitad:
        lista1 = lista1.mergeSort(comparador);
        lista2 = lista2.mergeSort(comparador);
        // Mezclar las dos mitades ordenadas:
        return mezcla(comparador, lista1, lista2);
    }

    /**
     * Metodo auxiliar al metodo mergeSort.
     * Mezcla las dos listas recibinas en una nueva lista ordenada.
     * @param comparador el comparador que lse usará para hacer el ordenamiento.
     * @param lista1 la primer lista ordenada.
     * @param lista2 la segunda lista ordenada.
     * @return una nueva lista ordenada con los elementos de lista1 y lista2. 
     */
    private Lista<T> mezcla(Comparator<T> comparador, Lista<T> lista1, Lista<T> lista2) {
        Lista<T> mezcla = new Lista<>();
        Nodo n1 = lista1.cabeza;
        Nodo n2 = lista2.cabeza;
        while (n1 != null && n2 != null) {
            int comp = comparador.compare(n1.elemento, n2.elemento);
            if (comp <= 0) {
                mezcla.agregaFinal(n1.elemento);
                n1 = n1.siguiente;
            } else {
                mezcla.agregaFinal(n2.elemento);
                n2 = n2.siguiente;
            }
        }
        n1 = (n1 != null) ? n1 : n2;
        while (n1 != null) {
            mezcla.agregaFinal(n1.elemento);
            n1 = n1.siguiente;
        }
        return mezcla;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        if (esVacia() || elemento == null)
            return false;
        Nodo temp = cabeza;
        while (temp != null) {
            int comp = comparador.compare(temp.elemento, elemento);
            if (comp == 0)
                return true;
            if (comp > 1)
                return false;
            temp = temp.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
