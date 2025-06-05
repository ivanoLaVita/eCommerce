package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO extends AbstractDAO<OrderItemBean> {

    private static final String TABLE_NAME = "order_item";

    /*
     * Salva un nuovo articolo dell'ordine nel database
     */
    @Override
    public synchronized void doSave(OrderItemBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO " + TABLE_NAME + " (productId, orderId, quantity, image, name, price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, bean.getProductId());
            ps.setInt(2, bean.getOrderId());
            ps.setInt(3, bean.getQuantity());
            ps.setString(4, bean.getImage());
            ps.setString(5, bean.getName());
            ps.setDouble(6, bean.getPrice());
            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /*
     * Elimina un articolo dell'ordine dato il suo ID
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
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }

    /*
     * Recupera un articolo dell'ordine tramite ID
     */
    @Override
    public synchronized OrderItemBean doRetrieveByKey(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        OrderItemBean item = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(key));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = new OrderItemBean();
                item.setId(rs.getInt("id"));
                item.setProductId(rs.getInt("productId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setImage(rs.getString("image"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return item;
    }

    /*
     * Recupera tutti gli articoli dell'ordine presenti nel database, ordinati se richiesto
     */
    @Override
    public synchronized List<OrderItemBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<OrderItemBean> items = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItemBean item = new OrderItemBean();
                item.setId(rs.getInt("id"));
                item.setProductId(rs.getInt("productId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setImage(rs.getString("image"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return items;
    }

    /*
     * Recupera tutti gli articoli appartenenti a un determinato ordine
     */
    public synchronized List<OrderItemBean> doRetrieveByOrderId(int orderId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<OrderItemBean> items = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE orderId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItemBean item = new OrderItemBean();
                item.setId(rs.getInt("id"));
                item.setProductId(rs.getInt("productId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setImage(rs.getString("image"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return items;
    }

    /*
     * Recupera tutti gli articoli ordinati che hanno un determinato nome prodotto
     */
    public synchronized List<OrderItemBean> doRetrieveByProductName(String name) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<OrderItemBean> items = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(name) = LOWER(?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItemBean item = new OrderItemBean();
                item.setId(rs.getInt("id"));
                item.setProductId(rs.getInt("productId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setImage(rs.getString("image"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return items;
    }
}
