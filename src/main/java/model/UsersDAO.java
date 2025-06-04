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
	 * Salva un nuovo utente nel database.
	 */
	@Override
	public synchronized void doSave(UsersBean users) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO " + UsersDAO.TABLE_NAME + " (id, email, username, password, firstname, lastname, admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			
			ps.setInt(1, users.getId());
			ps.setString(2, users.getEmail());
			ps.setString(3, users.getUsersname());
			ps.setString(4, users.getPassword());
			ps.setString(5, users.getFirstName());
			ps.setString(6, users.getLastName());
			ps.setBoolean(7, users.isAdmin());
			
			ps.executeUpdate();
			con.commit();
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
	}
	/*
	 * Recupera un utente dal database tramite emal.
	 * Restituisce un UsersBean se trovato, altrimenti null.
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
				user.setUsersname(rs.getString("username"));
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
	 * Recupera un utente dal database utilizzando L'ID come chiave.
	 */
	public synchronized UsersBean doRetrieveById(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		UsersBean user = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1,id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user = new UsersBean();
		            user.setEmail(rs.getString("email"));
		            user.setUsersname(rs.getString("username"));
		            user.setPassword(rs.getString("password"));
		            user.setFirstName(rs.getString("firtsname"));
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
	 * Controlla se la mail è gia stata registrata nel database.
	 * Restituisce true se trovata, false altrimenti.
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
	 * Controlla se un username è gia' in uso.
	 * Restituisce true se giaì in uso.
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
	/*
	 * Aggiorna le informazioni di un utente nel database
	 */
	public synchronized boolean doUpdate(UsersBean user) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		String query = "UPDATE users SET email = ?, username = ?, password = ?, firstname = ?, lastname = ?, admin = ? WHERE id = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			

	        ps.setString(1, user.getEmail());
	        ps.setString(2, user.getUsersname());
	        ps.setString(3, user.getPassword());
	        ps.setString(4, user.getFirstName());
	        ps.setString(5, user.getLastName());
	        ps.setBoolean(6, user.isAdmin());
	        ps.setInt(7, user.getId());
	        
	        ps.executeUpdate();
	        con.commit();
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
		return result != 0; //true se l update ha modificato almeno una riga.
	}
	/*
	 * Elimina un utente dal database usando l'ID come chiave
	 */
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		String query = "DELETE FROM users WHERE id = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			ps.executeQuery();
			con.commit();
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
		return result != 0;
	}
	/*
	 * Recupera tutti gli utenti dal database con la possibilità di specificare un ordine
	 */
	public synchronized List<UsersBean> doRetrieveAll(String order) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		List<UsersBean> users = new ArrayList<>();
		
		String query = "SELECT * FROM users ";
		if (order != null && !order.isEmpty()) {
			query += "ORDER BY " + order;
		}
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
	        while (rs.next()) {
	            UsersBean user = new UsersBean();
	            user.setId(rs.getInt("id"));
	            user.setEmail(rs.getString("email"));
	            user.setUsersname(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setFirstName(rs.getString("firstName"));
	            user.setLastName(rs.getString("lastName"));
	            user.setAdmin(rs.getBoolean("admin"));
	            users.add(user);
	        }
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
		return users;
	}
}
