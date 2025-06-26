package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends AbstractDAO<ProductBean> {

    private static final String TABLE_NAME = "product";

    /*
     * Salva un nuovo prodotto nel database
     */
    @Override
    public synchronized void doSave(ProductBean product) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO " + TABLE_NAME + " (id, name, description, quantity, price, gender, image, categoryName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            
            ps.setInt(1, product.getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getQuantity());
            ps.setDouble(5, product.getPrice());
            ps.setString(6, product.getGender().toString());
            ps.setString(7, product.getImage());
            ps.setString(8, product.getCategoryName());

            ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
    }

    /*
     * Elimina un prodotto dal database usando l'ID come chiave
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
     * Recupera un prodotto dal database utilizzando l'ID come chiave primaria
     */
    @Override
    public synchronized ProductBean doRetrieveByKey(String key) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ProductBean product = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(key));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = extractProductFromResultSet(rs);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return product;
    }

    /*
     * Recupera tutti i prodotti dal database, con ordinamento opzionale
     */
    @Override
    public synchronized List<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        List<ProductBean> products = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductBean product = extractProductFromResultSet(rs);
                products.add(product);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return products;
    }

    /*
     * Aggiorna le informazioni di un prodotto nel database
     */
    @Override
    public synchronized boolean doUpdate(ProductBean product) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        String query = "UPDATE product SET name = ?, description = ?, quantity = ?, price = ?, gender = ?, image = ?, categoryName = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getGender().toString());
            ps.setString(6, product.getImage());
            ps.setString(7, product.getCategoryName());
            ps.setInt(8, product.getId());

            result = ps.executeUpdate();
            con.commit();
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return result != 0;
    }

    /*
     * Recupera tutti i prodotti appartenenti a una specifica categoria (case-insensitive)
     */
    @Override
    public synchronized List<ProductBean> doRetrieveAllByKey(String category) throws SQLException {
        List<ProductBean> products = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(categoryName) = LOWER(?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductBean product = extractProductFromResultSet(rs);
                products.add(product);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return products;
    }

    /*
     * Recupera un prodotto dal database utilizzando il nome (case-insensitive)
     */
    public synchronized ProductBean doRetrieveByName(String name) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ProductBean product = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(name) = LOWER(?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = extractProductFromResultSet(rs);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return product;
    }
    
    public synchronized List<ProductBean> doRetrieveByNameLike(String name) throws SQLException {
        List<ProductBean> products = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(name) LIKE LOWER(?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + name + "%");  // cerca anche parzialmente

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductBean p = extractProductFromResultSet(rs);
                products.add(p);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return products;
    }


    /*
     * Cerca prodotti per nome, categoria o genere (ricerca parziale e case-insensitive)
     */
    public synchronized List<ProductBean> searchBy(String search) throws SQLException {
        List<ProductBean> productsFound = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        String query = "SELECT * FROM " + TABLE_NAME +
            " WHERE LOWER(name) LIKE LOWER(?) OR LOWER(categoryName) LIKE LOWER(?) OR LOWER(gender) LIKE LOWER(?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(query);
            String likeSearch = "%" + search + "%";
            ps.setString(1, likeSearch);
            ps.setString(2, likeSearch);
            ps.setString(3, likeSearch);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductBean product = extractProductFromResultSet(rs);
                productsFound.add(product);
            }
        } finally {
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }

        return productsFound;
    }
    
    public ProductBean doRetrieveById(int id) {
        ProductBean product = null;
        try (Connection con = DriverManagerConnectionPool.getConnection()) {
            String sql = "SELECT * FROM product WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new ProductBean();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image")); 
                product.setCategoryName(rs.getString("categoryName")); 
                
                // Solo se il campo gender esiste ed è usato correttamente come enum
                product.setGender(ProductBean.ProductGender.valueOf(rs.getString("gender")));

                // NON chiamiamo setCategoryId perché non esiste
                // Se vuoi mostrare anche la categoria, serve un altro metodo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }



    /*
     * Estrae e costruisce un ProductBean da un ResultSet
     */
    private ProductBean extractProductFromResultSet(ResultSet rs) throws SQLException {
        ProductBean product = new ProductBean();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setQuantity(rs.getInt("quantity"));
        product.setPrice(rs.getDouble("price"));
        product.setGender(ProductBean.ProductGender.valueOf(rs.getString("gender")));
        product.setImage(rs.getString("image"));
        product.setCategoryName(rs.getString("categoryName"));
        return product;
    }
    
    public synchronized List<ProductBean> doRetrieveFiltered(String search, String category) throws SQLException {
    List<ProductBean> products = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;

    StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE 1=1");

    if (category != null && !category.isEmpty()) {
        query.append(" AND LOWER(categoryName) = LOWER(?)");
    }

    if (search != null && !search.isEmpty()) {
        query.append(" AND LOWER(name) LIKE LOWER(?)");
    }

    try {
        con = DriverManagerConnectionPool.getConnection();
        ps = con.prepareStatement(query.toString());

        int index = 1;
        if (category != null && !category.isEmpty()) {
            ps.setString(index++, category);
        }
        if (search != null && !search.isEmpty()) {
            ps.setString(index, "%" + search + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductBean product = extractProductFromResultSet(rs);
            products.add(product);
        }
    } finally {
        if (ps != null) ps.close();
        DriverManagerConnectionPool.releaseConnection(con);
    }

    return products;
}


}
