package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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
            switch (mode != null ? mode.toLowerCase() : "") {

                case "add": {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    cart.merge(productId, quantity, Integer::sum);
                    session.setAttribute("cart", cart);

                    // ✅ Messaggio e redirect
                    session.setAttribute("message", "Prodotto aggiunto al carrello!");
                    break;
                }

                case "update": {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    if (quantity <= 0) {
                        cart.remove(productId);
                    } else {
                        cart.put(productId, quantity);
                    }

                    if (cart.isEmpty()) {
                        session.removeAttribute("cart");
                        session.setAttribute("cartSize", 0);
                    } else {
                        session.setAttribute("cart", cart);
                        session.setAttribute("cartSize", cart.values().stream().mapToInt(Integer::intValue).sum());
                    }

                    response.getWriter().print("reload");
                    return; // 🔁 evita doppio aggiornamento cartSize sotto
                }

                case "remove": {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    cart.remove(productId);

                    if (cart.isEmpty()) {
                        session.removeAttribute("cart");
                        session.setAttribute("cartSize", 0);
                    } else {
                        session.setAttribute("cart", cart);
                    }

                    RequestDispatcher view = request.getRequestDispatcher("cart.jsp");
                    view.forward(request, response);
                    return;
                }

                case "reset": {
                    session.removeAttribute("cart");
                    session.setAttribute("cartSize", 0);
                    RequestDispatcher view = request.getRequestDispatcher("cart.jsp");
                    view.forward(request, response);
                    return;
                }

                case "gettotal": {
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
                    return;
                }

                default:
                    // Nessuna modalità o modalità sconosciuta: ignora
                    break;
            }

            // ✅ CartSize aggiornato dopo ogni modifica utile
            int cartSize = 0;
            if (cart != null) {
                cartSize = cart.values().stream().mapToInt(Integer::intValue).sum();
            }
            session.setAttribute("cartSize", cartSize);

            // Solo l'aggiunta esegue il redirect
            String referer = request.getHeader("referer");
            if ("add".equalsIgnoreCase(mode)) {
            	response.sendRedirect(referer);
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
