package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AddressBean;
import model.AddressDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/info")
public class EditInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditInfoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
        String target = request.getParameter("target");
        String path = null;

        if (mode == null || target == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        if (mode.equalsIgnoreCase("add") && target.equalsIgnoreCase("address")) {
            AddressDAO addressDAO = new AddressDAO();
            AddressBean address = new AddressBean();

            try {
                address.setCity(request.getParameter("city"));
                address.setProvince(request.getParameter("province"));
                address.setPostalCode(request.getParameter("postalCode"));
                address.setStreet(request.getParameter("street"));
                address.setStreetNumber(request.getParameter("streetNumber"));

                // Recupera l'id utente dalla sessione
                Integer userId = (Integer) request.getSession().getAttribute("userId");
                if (userId == null) {
                    request.getSession().setAttribute("error", "Devi essere loggato per aggiungere un indirizzo.");
                    response.sendRedirect("login.jsp");
                    return;
                }
                address.setUserId(userId);

                addressDAO.doSave(address);
                request.getSession().setAttribute("message", "Indirizzo aggiunto con successo.");
                path = "./memberArea.jsp";

            } catch (SQLException e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Errore durante l'aggiunta dell'indirizzo.");
                path = "./memberArea.jsp";
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("memberArea.jsp");
    }
}
