package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends AbstractDAO<UsersBean> {

    private static final String TABLE_NAME = "users";

    /*
     * Salva un nuovo utente nel database
     */
    @Override
    public synchronized void doSave(UsersBean users) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO " + TABLE_NAME + " (email, username, password, firstname, lastname, admin) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);

            ps.setString(1, users.getEmail());
            ps.setString(2, users.getUsername());
            ps.setString(3, users.getPassword());
            ps.setString(4, users.getFirstName());
            ps.setString(5, users.getLastName());
            ps.setBoolean(6, users.isAdmin());

            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /* 
     * Elimina un utente dal database usando l'ID come chiave 
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
     * Recupera un utente dal database utilizzando l'ID come chiave
     */
    @Override
    public synchronized UsersBean doRetrieveByKey(String key) throws SQLException {
        return doRetrieveByEmail(key);
    }

    /* 
     * Recupera tutti gli utenti dal database con la possibilità di specificare un ordine 
     */
    @Override
    public synchronized List<UsersBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<UsersBean> users = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UsersBean user = new UsersBean();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setAdmin(rs.getBoolean("admin"));
                users.add(user);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return users;
    }

    /* 
     * Aggiorna le informazioni di un utente nel database
     */
    public synchronized boolean doUpdate(UsersBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        // Update base: senza cambiare l'email (che è PK)
        String query = "UPDATE " + TABLE_NAME +
                       " SET username = ?, password = ?, firstName = ?, lastName = ?, admin = ? " +
                       "WHERE email = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getUsername());
            statement.setString(2, bean.getPassword());
            statement.setString(3, bean.getFirstName());
            statement.setString(4, bean.getLastName());
            statement.setBoolean(5, bean.isAdmin());
            statement.setString(6, bean.getEmail()); // WHERE email = ?

            result = statement.executeUpdate();
            con.commit();
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }

    /*
     * Recupera un utente dal database utilizzando l username
     */
    public synchronized UsersBean doRetrieveByUsername(String username) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        UsersBean user = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new UsersBean();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setAdmin(rs.getBoolean("admin"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return user;
    }
    /*
     * Recupera un utente dal database utilizzando l'email 
     */
    public synchronized UsersBean doRetrieveByEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        UsersBean user = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new UsersBean();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return user;
    }

    /* 
     Recupera un utente dal database utilizzando l'ID 
    
    public synchronized UsersBean doRetrieveById(int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        UsersBean user = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new UsersBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setAdmin(rs.getBoolean("admin"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return user;
    }
    */

    /*
     * Verifica se un'email è già presente nel database
     */
    public synchronized boolean checkEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        boolean found = false;

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE email = ? LIMIT 1";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            found = rs.next();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return found;
    }

    /*
     * Verifica se uno username è già presente nel database
     */
    public synchronized boolean checkUsername(String username) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        boolean found = false;

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE username = ? LIMIT 1";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            found = rs.next();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return found;
    }
}
