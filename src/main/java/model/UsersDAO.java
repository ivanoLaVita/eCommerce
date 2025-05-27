package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO extends AbstractDAO<UsersBean> {
	private static final String TABLE_NAME = "users";
	
	
	//Salva un nuovo utente nel database
	@Override
	public synchronized void doSave(UsersBean users) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO " + UsersDAO.TABLE_NAME + "(id, email, username, password, firstname, lastname, admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
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
	 * Restituisce un UsersBean se trovato, altrimenti null
	 */
	public synchronized UsersBean doRetrieveByEmail(String email) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		UsersBean user = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + "Where email = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user = new UsersBean();
				user.setEmail(rs.getString("email"));
				user.setEmail(rs.getString("username"));
				user.setEmail(rs.getString("password"));
				user.setEmail(rs.getString("nome"));
				user.setEmail(rs.getString("cognome"));
			}
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
		return user;
	}
	
	/*
	 * Controlla se la mail è gia stata registrata nel database
	 * Restituisce true se trovata, false altrimenti
	 */
	public synchronized boolean checkEmail(String email) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		boolean found = false;
		
		String query = "SELECT 1 FROM " + TABLE_NAME + "WHERE email = ? LIMIT 1";
		
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
	 * Controlla se un username è gia' in uso
	 * Restituisce true se giaì in uso
	 */
	public synchronized boolean checkUsername(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		boolean found = false;
		
		String query = "SELECT 1 FROM " + TABLE_NAME + "WHERE username = ? LIMIT 1";
		
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
