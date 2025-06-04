package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<OrderBean> {

    private static final String TABLE_NAME = "orders";

    @Override
    public synchronized void doSave(OrderBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO " + TABLE_NAME + " (date, totalCost, userId) VALUES (?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDate());
            ps.setDouble(2, bean.getTotalCost());
            ps.setInt(3, bean.getUserId());
            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

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

    @Override
    public synchronized OrderBean doRetrieveByKey(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        OrderBean order = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(key));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new OrderBean();
                order.setId(rs.getInt("id"));
                order.setDate(rs.getString("date"));
                order.setTotalCost(rs.getDouble("totalCost"));
                order.setUserId(rs.getInt("userId"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return order;
    }

    @Override
    public synchronized List<OrderBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<OrderBean> orders = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderBean orderBean = new OrderBean();
                orderBean.setId(rs.getInt("id"));
                orderBean.setDate(rs.getString("date"));
                orderBean.setTotalCost(rs.getDouble("totalCost"));
                orderBean.setUserId(rs.getInt("userId"));
                orders.add(orderBean);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return orders;
    }

    @Override
    public synchronized boolean doUpdate(OrderBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET date = ?, totalCost = ?, userId = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDate());
            ps.setDouble(2, bean.getTotalCost());
            ps.setInt(3, bean.getUserId());
            ps.setInt(4, bean.getId());
            result = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }

    public synchronized List<OrderBean> doRetrieveByDateRange(String startDate, String endDate) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<OrderBean> orders = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE date BETWEEN ? AND ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderBean order = new OrderBean();
                order.setId(rs.getInt("id"));
                order.setDate(rs.getString("date"));
                order.setTotalCost(rs.getDouble("totalCost"));
                order.setUserId(rs.getInt("userId"));
                orders.add(order);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return orders;
    }
}
