package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.OrderBean;
import model.OrderDAO;
import model.OrderItemBean;
import model.OrderItemDAO;
import model.ProductBean;
import model.ProductDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OrderServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        if (request.getSession().getAttribute("logged") == null) {
            request.getSession().setAttribute("error", "You must be logged in to place an order.");
            response.getWriter().print("login.jsp");
        } else {
            request.getSession().setAttribute("totalCost", request.getParameter("totalCost"));
            response.getWriter().print("checkout.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String redirectPath = null;

        if (request.getSession().getAttribute("logged") == null) {
            redirectPath = "login.jsp";
        } else {
            int userId = (int) request.getSession().getAttribute("userId");
            String costString = (String) request.getSession().getAttribute("totalCost");
            if (costString == null) costString = "0";
            costString = costString.replace(",", ".");
            double totalCost = Double.parseDouble(costString);

            OrderDAO orderDAO = new OrderDAO();
            ProductDAO productDAO = new ProductDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();

            try {
                // Salva l'ordine
                OrderBean order = new OrderBean();
                order.setUserId(userId);
                order.setTotalCost(totalCost);
                order.setDate(LocalDate.now().toString());
                orderDAO.doSave(order);

                // Recupera l'ultimo ordine per l'utente (potresti usare getGeneratedKeys)
                List<OrderBean> userOrders = orderDAO.doRetrieveByUserId(userId);
                int orderId = userOrders.get(userOrders.size() - 1).getId();

                // Salva i dettagli degli item dell’ordine
                Map<String, Integer> cart = (Map<String, Integer>) request.getSession().getAttribute("cart");
                if (cart != null) {
                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        String productId = entry.getKey();
                        int quantity = entry.getValue();

                        ProductBean product = productDAO.doRetrieveByKey(productId);
                        if (product != null) {
                            // salva inserimento
                            OrderItemBean item = new OrderItemBean();
                            item.setOrderId(orderId);
                            item.setProductId(product.getId());
                            item.setQuantity(quantity);
                            item.setName(product.getName());
                            item.setPrice(product.getPrice());
                            item.setImage(product.getImage());
                            orderItemDAO.doSave(item);

                            // aggiorna quantità prodotto
                            product.setQuantity(product.getQuantity() - quantity);
                            productDAO.doUpdate(product);
                        }
                    }
                }

                // Pulisce sessione
                request.getSession().removeAttribute("cart");
                request.getSession().removeAttribute("totalCost");
                response.sendRedirect("homePage.jsp");
                return;

            } catch (SQLException e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Order processing error.");
                redirectPath = "error.jsp";
            }
        }

        if (redirectPath != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPath);
            dispatcher.forward(request, response);
        }
    }
}
