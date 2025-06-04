package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO extends AbstractDAO<ProductDAO> {
	private static final String TABLE_NAME = "product";
	
	/*
	 * Salva un nuovo prodotto nel database
	 */
	public synchronized void doSave(ProductBean product) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		
		String query = "INSERT INTO " + TABLE_NAME + "(name, description, quantity, price, gender, image, categoryName,)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getGender().toString());  // da enum a stringa 'M' o 'F'
            ps.setString(6, product.getImage());
            ps.setString(7, product.getCategoryName());

            ps.executeUpdate();
            con.commit();
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
	}
	/*
	 * Recupera un prodotto dal database tramite nome.
	 * Restituisce un ProductBean se trovato, altrimenti null.
	 */
	public synchronized ProductBean doRetrieveByName(String name) throws SQLException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ProductBean product = null;

	    String query = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?";

	    try {
	        con = DriverManagerConnectionPool.getConnection();
	        ps = con.prepareStatement(query);
	        ps.setString(1, name);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            product = new ProductBean();
	            product.setId(rs.getInt("id"));
	            product.setName(rs.getString("name"));
	            product.setDescription(rs.getString("description"));
	            product.setQuantity(rs.getInt("quantity"));
	            product.setPrice(rs.getDouble("price"));
	            product.setGender(ProductBean.ProductGender.valueOf(rs.getString("gender")));
	            product.setImage(rs.getString("image"));
	            product.setCategoryName(rs.getString("categoryName"));
	        }
	    } finally {
	        if (ps != null) ps.close();
	        DriverManagerConnectionPool.releaseConnection(con);
	    }

	    return product;
	}
	/*
	 * Recupera un prodotto dal database utilizzando L'ID come chiave.
	 */
	public synchronized ProductBean doRetrieveById(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ProductBean product = null;
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1,id);
			
			ResultSet rs = ps.executeQuery();
			
		    if (rs.next()) {
		        product = new ProductBean();
		        product.setId(rs.getInt("id"));
		        product.setName(rs.getString("name"));
		        product.setDescription(rs.getString("description"));
		        product.setQuantity(rs.getInt("quantity"));
		        product.setPrice(rs.getDouble("price"));
		        product.setGender(ProductBean.ProductGender.valueOf(rs.getString("gender")));
		        product.setImage(rs.getString("image"));
		        product.setCategoryName(rs.getString("categoryName"));
		      }
		  } finally {
			  if (ps != null) ps.close();
			  DriverManagerConnectionPool.releaseConnection(con);
		  }
		return product;
	}
	/*
	 * Aggiorna le informazioni di un prodotto nel database
	 */
	public synchronized boolean doUpdate(ProductBean product) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		String query = "UPDATE product SET name = ?, description = ?, quantity = ?, price = ?, gender = ?, image = ?, categoryName = ?  WHERE id = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(query);
			
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getGender().toString());  // da enum a stringa 'M' o 'F'
            ps.setString(6, product.getImage());
            ps.setString(7, product.getCategoryName());
            ps.setInt(8, product.getId());
	        
	        ps.executeUpdate();
	        con.commit();
		} finally {
			if (ps != null) ps.close();
			DriverManagerConnectionPool.releaseConnection(con);
		}
		return result != 0; //true se l update ha modificato almeno una riga.
	}
	/*
	 * Elimina un prodotto dal database usando l'ID come chiave
	 */
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		String query = "DELETE FROM product WHERE id = ?";
		
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
}

