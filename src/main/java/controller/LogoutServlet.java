package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Rimuove gli attributi di sessione
        request.getSession().removeAttribute("firstName");
        request.getSession().removeAttribute("lastName");
        request.getSession().removeAttribute("logged");
        request.getSession().removeAttribute("admin");
        request.getSession().removeAttribute("orders");
        request.getSession().removeAttribute("paymentMethods");
        request.getSession().removeAttribute("addresses");
        request.getSession().removeAttribute("cart");

        // Invalida la sessione
        request.getSession().invalidate();

        // Reindirizza alla pagina di login
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
