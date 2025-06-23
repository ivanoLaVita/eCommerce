package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO extends AbstractDAO<PaymentMethodBean> {

    private static final String TABLE_NAME = "payment_method";

    /*
     * Salva un nuovo metodo di pagamento nel database
     */
    @Override
    public synchronized void doSave(PaymentMethodBean method) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO " + TABLE_NAME + " (type, iban, cardNumber, userEmail) VALUES (?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, method.getType().toString());
            ps.setString(2, method.getIban());
            ps.setString(3, method.getCardNumber());
            ps.setString(4, method.getUserEmail());
            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /*
     * Elimina un metodo di pagamento dal database usando l'ID come chiave
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
     * Recupera un metodo di pagamento dal database utilizzando l'ID come chiave
     */
    @Override
    public synchronized PaymentMethodBean doRetrieveByKey(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        PaymentMethodBean method = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(key));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                method = new PaymentMethodBean();
                method.setId(rs.getInt("id"));
                method.setType(rs.getString("type"));
                method.setIban(rs.getString("iban"));
                method.setCardNumber(rs.getString("cardNumber"));
                method.setUserEmail(rs.getString("userEmail"));
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return method;
    }

    /*
     * Recupera tutti i metodi di pagamento dal database, con ordinamento opzionale
     */
    @Override
    public synchronized List<PaymentMethodBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<PaymentMethodBean> methods = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PaymentMethodBean method = new PaymentMethodBean();
                method.setId(rs.getInt("id"));
                method.setType(rs.getString("type"));
                method.setIban(rs.getString("iban"));
                method.setCardNumber(rs.getString("cardNumber"));
                method.setUserEmail(rs.getString("userEmail"));
                methods.add(method);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return methods;
    }

    /*
     * Aggiorna un metodo di pagamento esistente nel database
     */
    @Override
    public synchronized boolean doUpdate(PaymentMethodBean method) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET type = ?, iban = ?, cardNumber = ?, userEmail = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, method.getType().toString());
            ps.setString(2, method.getIban());
            ps.setString(3, method.getCardNumber());
            ps.setString(4, method.getUserEmail());
            ps.setInt(5, method.getId());
            result = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }
    /*
     * Recupera tutti i metodi di pagamento associati a uno specifico utente
     */
    public synchronized List<PaymentMethodBean> doRetrieveByUserId(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<PaymentMethodBean> methods = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE userId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PaymentMethodBean method = new PaymentMethodBean();
                method.setId(rs.getInt("id"));
                method.setType(rs.getString("type"));
                method.setIban(rs.getString("iban"));
                method.setCardNumber(rs.getString("cardNumber"));
                method.setUserEmail(rs.getString("userEmail"));

                methods.add(method);
            }

        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return methods;
    }
    
	public synchronized List<PaymentMethodBean> doRetrieveByEmail(String key) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		
		List<PaymentMethodBean> metodiPagamento = new ArrayList<>();
		
		String query = "SELECT * FROM " + PaymentMethodDAO.TABLE_NAME + " WHERE utenteEmail = ?;";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			
			statement.setString(1, key);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				PaymentMethodBean pagamento = new PaymentMethodBean();
				
				pagamento.setId(result.getInt("id"));
				pagamento.setType(result.getString("tipo"));
				pagamento.setIban(result.getString("iban"));
				pagamento.setCardNumber(result.getString("numeroCarta"));
				pagamento.setUserEmail(result.getString("utenteEmail"));

				metodiPagamento.add(pagamento);
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
		
		return metodiPagamento;
	}
}

