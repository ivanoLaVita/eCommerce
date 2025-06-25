package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductBean;
import model.ProductDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/productDetails")
public class ProductDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductDetailsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productId = request.getParameter("id");
        ProductBean product = null;

        /* if (productId != null && !productId.isEmpty()) {
            try {
                ProductDAO dao = new ProductDAO();
                product = dao.doRetrieveByKey(productId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } */
        
        try {
            int id = Integer.parseInt(productId); // può lanciare solo NumberFormatException
            ProductDAO dao = new ProductDAO();
            product = dao.doRetrieveById(id);     // NON lancia SQLException
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }



        if (product != null) {
            request.setAttribute("product", product);
            RequestDispatcher dispatcher = request.getRequestDispatcher("infoProdotti.jsp");
            dispatcher.forward(request, response);
        } else {
            // prodotto non trovato: redirect o errore
            response.sendRedirect("catalogo.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
