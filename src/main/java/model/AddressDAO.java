package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO extends AbstractDAO<AddressBean> {

    private static final String TABLE_NAME = "address";

    /*
     * Salva un nuovo indirizzo nel database
     */
    @Override
    public synchronized void doSave(AddressBean address) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO " + TABLE_NAME + " (city, province, postalCode, street, streetNumber, userId) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);

            ps.setString(1, address.getCity());
            ps.setString(2, address.getProvince());
            ps.setString(3, address.getPostalCode());
            ps.setString(4, address.getStreet());
            ps.setString(5, address.getStreetNumber());
            ps.setInt(6, address.getUserId());

            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /*
     * Elimina un indirizzo dato il suo ID
     */
    @Override
    public synchronized boolean doDelete(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(key));

            result = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }

    /*
     * Recupera un indirizzo tramite ID
     */
    @Override
    public synchronized AddressBean doRetrieveByKey(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        AddressBean address = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(key));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                address = new AddressBean();
                address.setId(rs.getInt("id"));
                address.setCity(rs.getString("city"));
                address.setProvince(rs.getString("province"));
                address.setPostalCode(rs.getString("postalCode"));
                address.setStreet(rs.getString("street"));
                address.setStreetNumber(rs.getString("streetNumber"));
                address.setUserId(rs.getInt("userId"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return address;
    }

    /*
     * Recupera tutti gli indirizzi dal database con ordinamento opzionale
     */
    @Override
    public synchronized List<AddressBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<AddressBean> addresses = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AddressBean address = new AddressBean();
                address.setId(rs.getInt("id"));
                address.setCity(rs.getString("city"));
                address.setProvince(rs.getString("province"));
                address.setPostalCode(rs.getString("postalCode"));
                address.setStreet(rs.getString("street"));
                address.setStreetNumber(rs.getString("streetNumber"));
                address.setUserId(rs.getInt("userId"));
                address.setUtenteEmail(rs.getString("utenteEmail"));
                addresses.add(address);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return addresses;
    }

    /*
     * Aggiorna un indirizzo esistente nel database
     */
    @Override
    public synchronized boolean doUpdate(AddressBean address) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET city = ?, province = ?, postalCode = ?, street = ?, streetNumber = ?, userId = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);

            ps.setString(1, address.getCity());
            ps.setString(2, address.getProvince());
            ps.setString(3, address.getPostalCode());
            ps.setString(4, address.getStreet());
            ps.setString(5, address.getStreetNumber());
            ps.setInt(6, address.getUserId());
            ps.setInt(7, address.getId());

            result = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }
    /*
     * Recupera tutti gli indirizzi associati a uno specifico utente
     */
    public synchronized List<AddressBean> doRetrieveByUserId(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<AddressBean> addresses = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE userId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AddressBean address = new AddressBean();
                address.setId(rs.getInt("id"));
                address.setCity(rs.getString("city"));
                address.setProvince(rs.getString("province"));
                address.setPostalCode(rs.getString("postalCode"));
                address.setStreet(rs.getString("street"));
                address.setStreetNumber(rs.getString("streetNumber"));
                address.setUserId(rs.getInt("userId"));
                addresses.add(address);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return addresses;
    }
    
    // Recupera tutti gli indirizzi associati a un utente specifico
    public synchronized List<AddressBean> doRetrieveByEmail(String key) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<AddressBean> indirizzi = new ArrayList<>();

        // Query SQL per selezionare gli indirizzi di un utente specifico
        String query = "SELECT * FROM " + AddressDAO.TABLE_NAME + " WHERE utenteEmail = ?;";

        try {
            // Ottieni una connessione dal pool
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, key);

            // Esegui la query
            ResultSet result = statement.executeQuery();

            // Recupera i dati dal ResultSet
            while (result.next()) {
            	AddressBean indirizzo = new AddressBean();
                indirizzo.setId(result.getInt("id"));
                indirizzo.setCity(result.getString("citta"));
                indirizzo.setProvince(result.getString("provincia"));
                indirizzo.setPostalCode(result.getString("cap"));
                indirizzo.setStreet(result.getString("via"));
                indirizzo.setStreetNumber(result.getString("civico"));
                indirizzo.setUtenteEmail(result.getString("utenteEmail"));

                indirizzi.add(indirizzo);
            }
        } finally {
            // Chiudi lo statement e rilascia la connessione
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }

        return indirizzi;
    }
}

