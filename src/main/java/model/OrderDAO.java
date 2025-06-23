package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<OrderBean> {

    private static final String TABLE_NAME = "orders";

    /*
     * Salva un nuovo ordine nel database
     */
    @Override
    public synchronized void doSave(OrderBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        
        int rs = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (date, totalCost, userEmail) VALUES (?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDate());
            ps.setDouble(2, bean.getTotalCost());
            ps.setString(3, bean.getUserEmail());
            rs = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /*
     * Elimina un ordine dal database usando l'ID come chiave
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
     * Recupera un ordine dal database utilizzando l'ID come chiave
     */
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
                order.setUserEmail(rs.getString("userEmail"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return order;
    }

    /*
     * Recupera tutti gli ordini presenti nel database, con ordinamento opzionale
     */
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
                orderBean.setUserEmail(rs.getString("userEmail"));
                orders.add(orderBean);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return orders;
    }

    /*
     * Aggiorna le informazioni di un ordine nel database
     */
    @Override
    public synchronized boolean doUpdate(OrderBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET date = ?, totalCost = ?, userEmail = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, bean.getDate());
            ps.setDouble(2, bean.getTotalCost());
            ps.setString(3, bean.getUserEmail());
            ps.setInt(4, bean.getId());
            result = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }
    
	public synchronized List<OrderBean> doRetrieveByEmail(String key) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		
		List<OrderBean> ordini = new ArrayList<>();
		
		String query = "SELECT * FROM " + OrderDAO.TABLE_NAME + " WHERE userEmail = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			statement.setString(1, key);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				OrderBean ordine = new OrderBean();
				
				ordine.setId(result.getInt("id"));
				ordine.setDate(result.getString("date"));
				ordine.setTotalCost(result.getDouble("totalCost"));
				ordine.setUserEmail(result.getString("userEmail"));

				ordini.add(ordine);
			}
		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
		
		return ordini;
	}

    /*
     * Recupera tutti gli ordini compresi tra due date (inclusi)
     */
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
                order.setUserEmail(rs.getString("userId"));
                orders.add(order);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return orders;
    }
    
    public synchronized List<OrderBean> doRetrieveByUserId(int userId) throws SQLException {
        List<OrderBean> orders = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        String query = "SELECT * FROM orders WHERE userId = ? ORDER BY id";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderBean order = new OrderBean();
                order.setId(rs.getInt("id"));
                order.setUserEmail(rs.getString("userEmail"));
                order.setDate(rs.getString("orderDate"));
                order.setTotalCost(rs.getDouble("totalCost"));
                orders.add(order);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return orders;
    }

}
