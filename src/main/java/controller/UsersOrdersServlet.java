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

@WebServlet("/user/orders")
public class UsersOrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UsersOrdersServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica login
        if (request.getSession().getAttribute("logged") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) request.getSession().getAttribute("userId");

        OrderDAO orderDAO = new OrderDAO();
        List<OrderBean> orders = null;

        try {
            orders = orderDAO.doRetrieveByUserId(userId);
            request.setAttribute("userOrders", orders);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve orders.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("myOrders.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
