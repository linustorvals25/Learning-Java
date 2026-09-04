package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Iterator;

/**
 * Clase abstracta para bases de datos genéricas. Provee métodos para agregar y
 * eliminar registros, y para guardarse y cargarse de una entrada y salida
 * dados. Además, puede hacer búsquedas con valores arbitrarios sobre los campos
 * de los registros.
 *
 * Las clases que extiendan a BaseDeDatos deben implementar el método {@link
 * #creaRegistro}, que crea un registro genérico en blanco.
 *
 * @param <R> El tipo de los registros, que deben implementar la interfaz {@link
 * Registro}.
 * @param <C> El tipo de los campos de los registros, que debe ser una
 * enumeración {@link Enum}.
 */
public abstract class BaseDeDatos<R extends Registro<R, C>, C extends Enum> {

    /* Lista de registros en la base de datos. */
    private Lista<R> registros;

    /**
     * Constructor único.
     */
    public BaseDeDatos() {
        registros = new Lista<>();
    }

    /**
     * Regresa el número de registros en la base de datos.
     * @return el número de registros en la base de datos.
     */
    public int getNumRegistros() {
        return registros.getLongitud();
    }

    /**
     * Regresa una lista con los registros en la base de datos. Modificar esta
     * lista no cambia a la información en la base de datos.
     * @return una lista con los registros en la base de datos.
     */
    public Lista<R> getRegistros() {
        return registros.copia();
    }

    /**
     * Agrega el registro recibido a la base de datos.
     * @param registro el registro que hay que agregar a la base de datos.
     */
    public void agregaRegistro(R registro) {
        registros.agregaFinal(registro);
    }

    /**
     * Elimina el registro recibido de la base de datos.
     * @param registro el registro que hay que eliminar de la base de datos.
     */
    public void eliminaRegistro(R registro) {
        registros.elimina(registro);
    }

    /**
     * Limpia la base de datos.
     */
    public void limpia() {
        registros.limpia();
    }

    /**
     * Guarda todos los registros en la base de datos en la salida recibida.
     * @param out la salida donde hay que guardar los registos.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void guarda(BufferedWriter out) throws IOException {
        Iterator<R> iterator = registros.iterator();
        while (iterator.hasNext()) {
            try {
                out.append(iterator.next().seria());
            } catch (IOException io) {
                throw new IOException("Error al escribir los registros en el archivo.");
            }
        }
    }

    /**
     * Carga los registros de la entrada recibida en la base de datos. Si antes
     * de llamar el método había registros en la base de datos, estos son
     * eliminados.
     * @param in la entrada de donde hay que cargar los registos.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void carga(BufferedReader in) throws IOException {
        limpia();
        String linea = "";
        while ((linea = in.readLine()) != null) {
            R registro = creaRegistro();
            try {
                registro.deseria(linea);
            } catch (ExcepcionLineaInvalida eli) {
                return;
            }
            registros.agregaFinal(registro);
        }
    }

    /**
     * Busca registros por un campo específico.
     * @param campo el campo del registro por el cuál buscar.
     * @param valor el valor a buscar.
     * @return una lista con los registros tales que casan el campo especificado
     *         con el valor dado.
     * @throws IllegalArgumentException si el campo no es de la enumeración
     *         correcta.
     */
    public Lista<R> buscaRegistros(C campo, Object valor) {
        if (!(campo instanceof Enum))
            throw new IllegalArgumentException("El campo recibido no es una Enumeracion.");
        Lista<R> casa = new Lista<>();
        if (valor == null)
            return casa;
        Iterator<R> iterator = registros.iterator();
        while (iterator.hasNext()) {
            R registo = iterator.next();
            if (registo.casa(campo, valor))
                casa.agregaFinal(registo);
        }
        return casa;
    }

    /**
     * Crea un registro en blanco.
     * @return un registro en blanco.
     */
    public abstract R creaRegistro();
}
