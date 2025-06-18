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

        // 1. Recupera parametri dalla form (il campo "username" del form conterrà email O username)
        String loginIdentifier = request.getParameter("username"); // Ora può essere email o username
        String password = request.getParameter("password");

        UsersDAO dao = new UsersDAO();
        UsersBean user = null;

        try {
            // 2. Tenta di recuperare utente dal database prima tramite email, poi tramite username
            user = dao.doRetrieveByEmail(loginIdentifier); // Nuovo metodo nel DAO
            if (user == null) {
                user = dao.doRetrieveByUsername(loginIdentifier); // Metodo esistente nel DAO
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Errore nel login. Riprova più tardi.");
            request.getRequestDispatcher("login.jsp").forward(request, response); // O "login.jsp"
            return;
        }

        // 3. Verifica credenziali e reindirizza
        // Importante: La password nel DB è l'hashedPassword, quindi controlliamo il plain text vs hashed DB password
        if (user != null && PasswordUtils.checkPassword(password, user.getPassword())) { // Usa checkPassword per confrontare
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsersname());
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("admin", user.isAdmin());
            session.setAttribute("logged", true);

            if (user.isAdmin()) {
                response.sendRedirect("adminArea.jsp");
            } else {
                response.sendRedirect("memberArea.jsp");
            }

        } else {
            // Login fallito
            request.setAttribute("message", "Email/Username o password errati.");
            request.getRequestDispatcher("login.jsp").forward(request, response); // Reindirizza a login.jsp
        }
    }
}