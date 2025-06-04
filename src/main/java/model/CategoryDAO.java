package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends AbstractDAO<CategoryBean> {
    private static final String TABLE_NAME = "category";

    /*
     * Salva una nuova categoria nel database.
     */
    @Override
    public synchronized void doSave(CategoryBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO " + TABLE_NAME + " (name) VALUES (?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getName());
            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /*
     * Elimina una categoria dal database in base al nome.
     */
    @Override
    public synchronized boolean doDelete(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE name = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, key);
            result = ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }

    /*
     * Recupera una categoria dal database in base al nome.
     */
    @Override
    public synchronized CategoryBean doRetrieveByKey(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        CategoryBean category = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, key);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = new CategoryBean();
                category.setName(rs.getString("name"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return category;
    }

    /*
     * Recupera tutte le categorie dal database con ordinamento opzionale.
     */
    @Override
    public synchronized List<CategoryBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<CategoryBean> categories = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CategoryBean category = new CategoryBean();
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return categories;
    }

    /*
     * Esegue una ricerca case-insensitive sul nome delle categorie.
     */
    public synchronized List<CategoryBean> searchBy(String search) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<CategoryBean> categories = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(name) LIKE LOWER(?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + search + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryBean category = new CategoryBean();
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return categories;
    }
}
