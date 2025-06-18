package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UsersBean;
import model.UsersDAO;
import utils.PasswordUtils;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recupera parametri dalla form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. Hash della password in chiaro
        String hashedPassword = PasswordUtils.hashPassword(password);

        UsersDAO dao = new UsersDAO();
        UsersBean user = null;

        try {
            // 3. Recupera utente dal database tramite username
            user = dao.doRetrieveByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Errore nel login.");
            request.getRequestDispatcher("loginRegistration.jsp").forward(request, response);
            return;
        }

        // 4. Verifica credenziali
        if (user != null && user.getPassword().equals(hashedPassword)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // opzionale: puoi accedere a tutto da qui
            session.setAttribute("username", user.getUsersname());
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("admin", user.isAdmin());
            session.setAttribute("logged", true);

            // 5. Redirect in base al ruolo
            if (user.isAdmin()) {
                response.sendRedirect("adminArea.jsp");
            } else {
                response.sendRedirect("memberArea.jsp");
            }

        } else {
            // Login fallito
            request.setAttribute("message", "Username o password errati.");
            request.getRequestDispatcher("loginRegistration.jsp").forward(request, response);
        }
    }
}
