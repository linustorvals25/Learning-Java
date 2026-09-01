package mx.unam.ciencias.icc;

/**
 * <p>Clase para listas de estudiantes doblemente ligadas.</p>
 *
 * <p>Las listas de estudiantes nos permiten agregar elementos al inicio o final
 * de la lista, eliminar elementos de la lista, comprobar si un elemento está o
 * no en la lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas de estudiantes son iterables utilizando sus nodos. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 *
 * <p>Los elementos en una lista de estudiantes siempre son instancias de la
 * clase {@link Estudiante}.</p>
 */
public class ListaEstudiante {

    /**
     * Clase interna para nodos.
     */
    public class Nodo {

        /* El elemento del nodo. */
        private Estudiante elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(Estudiante elemento) {
            this.elemento = elemento;
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
        public Estudiante get() {
            return elemento;    
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

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
     * @param elemento el elemento a agregar. El elemento se agrega únicamente
     *                 si es distinto de <code>null</code>.
     */
    public void agregaFinal(Estudiante elemento) {
        if (elemento == null)
            return;
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
     * @param elemento el elemento a agregar. El elemento se agrega únicamente
     *                 si es distinto de <code>null</code>.
     */
    public void agregaInicio(Estudiante elemento) {
        if (elemento == null) 
            return;
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
     * @param elemento el elemento a insertar. El elemento se inserta únicamente
     *                 si es distinto de <code>null</code>.
     */
    public void inserta(int i, Estudiante elemento) {
        if (elemento == null) 
            return;
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
    public void elimina(Estudiante elemento) {
        if (esVacia() || elemento == null)
            return;
        Nodo eliminado = getNodo(cabeza, elemento);
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
     * Busca en la lista el elemento recibido y lo regresa.
     * @return la primer ocurrencia del elemento recibido en la lista, o
     * <code>null</code> si el elemento no se encuentra en la lista.
     */
    private Nodo getNodo(Nodo nodo, Estudiante elemento) {
        if (nodo == null || elemento == null)
            return null;
        if (nodo.elemento.equals(elemento))
            return nodo;
        return getNodo(nodo.siguiente, elemento);
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo, o
     *         <code>null</code> si la lista es vacía.
     */
    public Estudiante eliminaPrimero() {
        if (esVacia())
            return null;
        Estudiante eliminado = cabeza.elemento;
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
     * @return el último elemento de la lista antes de eliminarlo, o
     *         <code>null</code> si la lista es vacía.
     */
    public Estudiante eliminaUltimo() {
        if (esVacia())
            return null;
        Estudiante eliminado = rabo.elemento;
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
    public boolean contiene(Estudiante elemento) {
        return getNodo(cabeza, elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public ListaEstudiante reversa() {
        ListaEstudiante reversa = new ListaEstudiante();
        return reversa(reversa, cabeza);
    }

    /**
     * Metodo auxiliar recursivo.
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    private ListaEstudiante reversa(ListaEstudiante lista, Nodo nodo) {
        if (nodo == null)
            return lista;
        lista.agregaInicio(nodo.elemento);
        return reversa(lista, nodo.siguiente);
    } 

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public ListaEstudiante copia() {
        ListaEstudiante copia = new ListaEstudiante();
        return copia(copia, cabeza);
    }

    /**
     * Método auxiliar recursivo.
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    private ListaEstudiante copia(ListaEstudiante lista, Nodo nodo) {
        if (nodo == null)
            return lista;
        lista.agregaFinal(nodo.elemento);
        return copia(lista, nodo.siguiente);
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
     * @return el primer elemento de la lista, o <code>null</code> si la lista
     *         es vacía.
     */
    public Estudiante getPrimero() {
        return (!esVacia()) ? cabeza.elemento : null;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el último elemento de la lista, o <code>null</code> si la lista
     *         es vacía.
     */
    public Estudiante getUltimo() {
        return (!esVacia()) ? rabo.elemento : null;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista, o <code>null</code> si
     *         <em>i</em> es menor que cero o mayor o igual que el número de
     *         elementos en la lista.
     */
    public Estudiante get(int i) {
        if (esVacia() || i < 0 || i >= longitud)
            return null;
        return get(cabeza, i);
    }

    /**
     * Método auxiliar recursivo.
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param nodo el nodo auxiliar que permite recorrer la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     */
    private Estudiante get(Nodo nodo, int i) {
        if (i == 0)
            return nodo.elemento;
        return get(nodo.siguiente, i - 1);
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(Estudiante elemento) {
        if (esVacia() || elemento == null)
            return -1;
        return indiceDe(cabeza, elemento, 0);
    }

    /**
     * Método auxiliar recursivo.
     * Regresa el índice del elemento recibido en la lista.
     * @param nodo el nodo auxiliar que permite recorrer la lista.
     * @param elemento el elemento del que se busca el índice.
     * @param indice el contador de la posicion.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    private int indiceDe(Nodo nodo, Estudiante elemento, int indice) {
        if (nodo == null)
            return -1;
        if (nodo.elemento.equals(elemento))
            return indice;
        return indiceDe(nodo.siguiente, elemento, indice + 1);
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    public String toString() {
        if (esVacia())
            return "[]";
        return toString(cabeza, "[") + "]";
    }

    /**
     * Método auxiliar recursivo.
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    private String toString(Nodo nodo, String str) {
        if (nodo == null)
            return str;
        str += nodo.elemento;
        if (nodo.siguiente != null)
            str += ", ";
        return toString(nodo.siguiente, str);
    }

    /**
     * Nos dice si la lista es igual a la lista recibida.
     * @param lista la lista con la que hay que comparar.
     * @return <code>true</code> si la lista es igual a la recibida;
     *         <code>false</code> en otro caso.
     */
    public boolean equals(ListaEstudiante lista) {
        if (lista == null)
            return  false;
        if (longitud != lista.longitud)
            return false;
        return equals(cabeza, lista.cabeza);
    }

    /**
     * Método auxiliar recursivo.
     * Nos dice si la lista es igual a la lista recibida.
     * @param lista la lista con la que hay que comparar.
     * @return <code>true</code> si la lista es igual a la recibida;
     *         <code>false</code> en otro caso.
     */
    private boolean equals(Nodo n1,  Nodo n2) {
        if (n1 == null && n2 == null)
            return true;
        if (!n1.elemento.equals(n2.elemento))
            return false;
        return equals(n1.siguiente, n2.siguiente);
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
