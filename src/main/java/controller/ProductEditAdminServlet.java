package controller;

//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ProductBean;
import model.ProductDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/adminEditProduct")
public class ProductEditAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductEditAdminServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
        ProductDAO productDAO = new ProductDAO();
        String pathForward = null;

        try {
            if ("delete".equalsIgnoreCase(mode)) {
                String productId = request.getParameter("productId");
                productDAO.doDelete(productId);
                pathForward = "catalog"; // servlet che mostra tutti i prodotti

            } else if ("edit".equalsIgnoreCase(mode)) {
                String id = request.getParameter("productId");
                ProductBean product = productDAO.doRetrieveByKey(id);
                request.setAttribute("product", product);
                pathForward = "editProduct.jsp";

            } else if ("add".equalsIgnoreCase(mode)) {
                ProductBean product = new ProductBean();

                product.setId(Integer.parseInt(request.getParameter("id")));
                product.setName(request.getParameter("name"));
                product.setDescription(request.getParameter("description"));
                product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                product.setPrice(Double.parseDouble(request.getParameter("price")));
                product.setGender(ProductBean.ProductGender.valueOf(request.getParameter("gender")));
                product.setImage(request.getParameter("image"));
                product.setCategoryName(request.getParameter("category"));

                productDAO.doSave(product);
                pathForward = "catalog";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            pathForward = "error.jsp";
        }

        if (pathForward != null) {
            request.getRequestDispatcher(pathForward).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO productDAO = new ProductDAO();

        try {
            String id = request.getParameter("id");
            ProductBean product = productDAO.doRetrieveByKey(id);

            if (!product.getName().equals(request.getParameter("name")))
                product.setName(request.getParameter("name"));

            if (!product.getDescription().equals(request.getParameter("description")))
                product.setDescription(request.getParameter("description"));

            int newQty = Integer.parseInt(request.getParameter("quantity"));
            if (product.getQuantity() != newQty)
                product.setQuantity(newQty);

            double newPrice = Double.parseDouble(request.getParameter("price"));
            if (product.getPrice() != newPrice)
                product.setPrice(newPrice);

            String newGender = request.getParameter("gender");
            if (!product.getGender().toString().equalsIgnoreCase(newGender))
                product.setGender(ProductBean.ProductGender.valueOf(newGender));

            if (!product.getImage().equals(request.getParameter("image")))
                product.setImage(request.getParameter("image"));

            if (!product.getCategoryName().equals(request.getParameter("category")))
                product.setCategoryName(request.getParameter("category"));

            productDAO.doUpdate(product);

            // aggiorna prodotti in sessione se usato
            List<ProductBean> updatedProducts = productDAO.doRetrieveAll("");
            request.getSession().setAttribute("products", updatedProducts);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("catalog").forward(request, response);
    }
}
