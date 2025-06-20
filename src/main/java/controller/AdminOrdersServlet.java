package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderBean;
import model.OrderDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminOrdersServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica che l'utente sia loggato e sia admin
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("login.jsp");  // o a una pagina di accesso negato
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        List<OrderBean> allOrders = null;

        try {
            allOrders = orderDAO.doRetrieveAll("");
            request.setAttribute("allOrders", allOrders);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve orders.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminOrders.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
