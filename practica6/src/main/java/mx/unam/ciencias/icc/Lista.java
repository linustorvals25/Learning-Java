package mx.unam.ciencias.icc;

import java.util.NoSuchElementException;

/**
 * <p>Clase para listas genéricas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas son iterables utilizando sus nodos. Las listas no aceptan a
 * <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> {

    /**
     * Clase interna para nodos.
     */
    public class Nodo {

        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento =  elemento;
        }

        /**
         * Regresa el nodo anterior del nodo.
         * @return el nodo anterior del nodo.
         */
        public Nodo getAnterior() {
            return anterior;
        }

        /**
         * Regresa el nodo siguiente del nodo.
         * @return el nodo siguiente del nodo.
         */
        public Nodo getSiguiente() {
            return siguiente;
        }

        /**
         * Regresa el elemento del nodo.
         * @return el elemento del nodo.
         */
        public T get() {
            return elemento;
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
    /* Mensaje al tratar de eliminar cuando la lista esta vacia. */
    private static final String MSJ_EMPTY_LIST = "La lista esta vacia, no tiene elementos a eliminar.";
    /* Mensaje al tratar de acceder a elementos de la lista cuando esta vacia. */
    private static final String MSJ_EMPTY = "La lista esta vacia, no tiene elementos.";

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
     * la lista, el elemento se agrega al final de la misma. En otro caso,
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
        Nodo nuevo = new Nodo(elemento);
        Nodo temp = cabeza;
        for (int j = 0; j < i - 1; j++) {
            temp = temp.siguiente;
        }
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
        Nodo eliminado = getNodo(elemento);
        if (eliminado == null)
            return;
        if (eliminado == cabeza) {
            eliminaPrimero();
            return;
        }
        if (eliminado == rabo) {
            eliminaUltimo();
            return;
        }
        eliminado.anterior.siguiente = eliminado.siguiente;
        eliminado.siguiente.anterior = eliminado.anterior;
        longitud--;
    }

    /**
     * Metodo auxiliar del metodo elimina.
     * Regresa el Lista<T>.Nodo que contega la primera occurrencia el elemento
     * recibido como parametro, o <code>null</null> si el elemento no esta en la lista.
     * @param elemento el elemento a buscar.
     * @return Lista<T>.Nodo con la primer ocurrencia de elento, o null si no esta en la lista.
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
        T eliminado = cabeza.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        longitud--;
        return eliminado;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY_LIST);
        T eliminado = rabo.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        }
        longitud--;
        return eliminado;
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
        Nodo temp = cabeza;
        while (temp != null) {
            reversa.agregaInicio(temp.elemento);
            temp = temp.siguiente;
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
        Nodo temp = cabeza;
        while (temp != null) {
            copia.agregaFinal(temp.elemento);
            temp = temp.siguiente;
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
            throw new NoSuchElementException(MSJ_EMPTY);
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY);
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
        Nodo temp = cabeza;
        while (temp != null) {
            sb.append(temp.elemento);
            if (temp.siguiente != null) 
                sb.append(", ");
            temp = temp.siguiente;
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
        Nodo n1 = cabeza;
        Nodo n2 = lista.cabeza;
        for (int i = 0; i < longitud; i++) {
            if (!n1.elemento.equals(n2.elemento))
                return false;
            n1 = n1.siguiente;
            n2 = n2.siguiente;
        }
        return true;
    }

    /**
     * Regresa el nodo cabeza de la lista.
     * @return el nodo cabeza de la lista.
     */
    public Nodo getCabeza() {
        return cabeza;
    }

    /**
     * Regresa el nodo rabo de la lista.
     * @return el nodo rabo de la lista.
     */
    public Nodo getRabo() {
        return rabo;
    }
}
