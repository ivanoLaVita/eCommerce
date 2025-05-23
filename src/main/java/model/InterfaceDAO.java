package model;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia generica per l'accesso ai dati (DAO).
 * Definisce le operazioni di base che ogni DAO deve implementare.
 * @param <T> il tipo di oggetto gestito dal DAO
 */
public interface InterfaceDAO<T> {

    /**
     * Salva un nuovo oggetto nel database.
     */
    public void doSave(T bean) throws SQLException;

    /**
     * Elimina un oggetto tramite chiave semplice.
     */
    public boolean doDelete(String key) throws SQLException;

    /**
     * Elimina un oggetto tramite chiave composta.
     */
    public boolean doDelete(String key1, String key2) throws SQLException;

    /**
     * Recupera un oggetto dato un identificativo.
     */
    public T doRetrieveByKey(String key) throws SQLException;

    /**
     * Recupera un oggetto dato un identificativo composto.
     */
    public T doRetrieveByKey(String key1, String key2) throws SQLException;

    /**
     * Recupera tutti gli oggetti, con eventuale ordinamento.
     */
    public List<T> doRetrieveAll(String order) throws SQLException;

    /**
     * Aggiorna un oggetto esistente nel database.
     */
    public boolean doUpdate(T bean) throws SQLException;

    /**
     * Recupera tutti gli oggetti filtrati da una chiave.
     */
    public List<T> doRetrieveAllByKey(String key) throws SQLException;
} 
