package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProductBean;
import model.ProductDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CartServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
        HttpSession session = request.getSession();
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        ProductDAO productDAO = new ProductDAO();

        try {
            if ("add".equalsIgnoreCase(mode)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                cart.merge(productId, quantity, Integer::sum);
                session.setAttribute("cart", cart);

            } else if ("update".equalsIgnoreCase(mode)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (quantity <= 0) {
                    cart.remove(productId);
                } else {
                    cart.put(productId, quantity);
                }

                if (cart.isEmpty()) {
                    session.removeAttribute("cart");
                } else {
                    session.setAttribute("cart", cart);
                }

                response.getWriter().print("reload");

            } else if ("remove".equalsIgnoreCase(mode)) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                cart.remove(productId);

                if (cart.isEmpty()) {
                    session.removeAttribute("cart");
                }

                RequestDispatcher view = request.getRequestDispatcher("cart.jsp");
                view.forward(request, response);

            } else if ("reset".equalsIgnoreCase(mode)) {
                session.removeAttribute("cart");
                RequestDispatcher view = request.getRequestDispatcher("cart.jsp");
                view.forward(request, response);

            } else if ("getTotal".equalsIgnoreCase(mode)) {
                double total = 0.0;

                for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue();

                    ProductBean product = productDAO.doRetrieveByKey(String.valueOf(productId));
                    if (product != null) {
                        total += product.getPrice() * quantity;
                    }
                }

                response.getWriter().print(String.format("%.2f", total));
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nella gestione del carrello.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
