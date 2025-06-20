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
            if (!password.equals(passwordConfirm)) {
                request.setAttribute("error", "Passwords do not match.");
                request.getRequestDispatcher("HomePage.jsp").forward(request, response);
                return;
            }

            if (dao.checkEmail(email)) {
                request.setAttribute("error", "Email already registered.");
                request.getRequestDispatcher("HomePage.jsp").forward(request, response);
                return;
            }

            if (dao.checkUsername(username)) {
                request.setAttribute("error", "Username already taken.");
                request.getRequestDispatcher("HomePage.jsp").forward(request, response);
                return;
            }

            String hashedPassword = PasswordUtils.hashPassword(password);

            UsersBean user = new UsersBean();
            user.setId(0); // AUTO_INCREMENT
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAdmin(false);

            dao.doSave(user);

            response.sendRedirect("HomePage.jsp?registered=true");

        } catch (SQLException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Database error: " + e.getMessage());
            out.println("<br><a href='html/Login-Registration-Page.html'>Back to login</a>");
        }
    }
}
