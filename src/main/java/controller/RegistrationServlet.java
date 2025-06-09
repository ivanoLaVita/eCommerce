package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.UsersBean;
import model.UsersDAO;
import utils.PasswordUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
        UsersDAO dao = new UsersDAO();

        // --- AJAX: check email ---
        if ("checkEmail".equalsIgnoreCase(mode)) {
            String email = request.getParameter("email");
            response.setContentType("text/plain");
            try (PrintWriter out = response.getWriter()) {
                if (dao.checkEmail(email)) {
                    out.print("not available");
                } else {
                    out.print("available");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        // --- AJAX: check username ---
        if ("checkUsername".equalsIgnoreCase(mode)) {
            String username = request.getParameter("username");
            response.setContentType("text/plain");
            try (PrintWriter out = response.getWriter()) {
                if (dao.checkUsername(username)) {
                    out.print("not available");
                } else {
                    out.print("available");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        // --- REGISTRAZIONE classica ---
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        try {
            // Password match
            if (!password.equals(passwordConfirm)) {
                request.setAttribute("error", "Passwords do not match.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Email già registrata
            if (dao.checkEmail(email)) {
                request.setAttribute("error", "Email already registered.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Username già esistente
            if (dao.checkUsername(username)) {
                request.setAttribute("error", "Username already taken.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Hash della password
            String hashedPassword = PasswordUtils.hashPassword(password);

            // Crea l'utente
            UsersBean user = new UsersBean();
            user.setId(0); // sarà auto-incrementato dal DB
            user.setEmail(email);
            user.setUsersname(username);
            user.setPassword(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAdmin(false);

            // Salva nel DB
            dao.doSave(user);

            // Registrazione completata
            response.sendRedirect("index.jsp?registered=true");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
