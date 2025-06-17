package model;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe astratta che implementa l'interfaccia DAO.
 * Fornisce implementazioni di default che segnalano l'assenza di supporto per un'operazione.
 * Le sottoclassi devono sovrascrivere i metodi che desiderano utilizzare.
 */
public abstract class AbstractDAO<T> implements InterfaceDAO<T> {

    @Override
    public synchronized void doSave(T bean) throws SQLException {
        throw new UnsupportedOperationException("doSave() non supportato da questo DAO");
    }

    @Override
    public synchronized boolean doDelete(String key) throws SQLException {
        throw new UnsupportedOperationException("doDelete(String) non supportato da questo DAO");
    }

    @Override
    public synchronized boolean doDelete(String key1, String key2) throws SQLException {
        throw new UnsupportedOperationException("doDelete(String, String) non supportato da questo DAO");
    }

    @Override
    public synchronized T doRetrieveByKey(String key) throws SQLException {
        throw new UnsupportedOperationException("doRetrieveByKey(String) non supportato da questo DAO");
    }

    @Override
    public synchronized T doRetrieveByKey(String key1, String key2) throws SQLException {
        throw new UnsupportedOperationException("doRetrieveByKey(String, String) non supportato da questo DAO");
    }

    @Override
    public synchronized List<T> doRetrieveAll(String order) throws SQLException {
        throw new UnsupportedOperationException("doRetrieveAll() non supportato da questo DAO");
    }

    @Override
    public synchronized boolean doUpdate(T bean) throws SQLException {
        throw new UnsupportedOperationException("doUpdate() non supportato da questo DAO");
    }

    @Override
    public synchronized List<T> doRetrieveAllByKey(String key) throws SQLException {
        throw new UnsupportedOperationException("doRetrieveAllByKey() non supportato da questo DAO");
    }
}
