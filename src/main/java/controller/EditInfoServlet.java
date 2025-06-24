package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import model.UsersBean;
import model.UsersDAO;
import utils.PasswordUtils;

@WebServlet("/info")
public class EditInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String mode = request.getParameter("mode");
		String target = request.getParameter("target");

		if (!"update".equals(mode) || !"utente".equals(target)) {
			session.setAttribute("error", "Richiesta non valida.");
			response.sendRedirect("memberArea.jsp");
			return;
		}

		try {
			String email = request.getParameter("utente");
			String firstName = request.getParameter("nomeNuovo");
			String lastName = request.getParameter("cognomeNuovo");
			String username = request.getParameter("usernameNuovo");
			String password = request.getParameter("password");
			String passwordCheck = request.getParameter("passwordCheck");

			UsersDAO dao = new UsersDAO();
			UsersBean user = dao.doRetrieveByEmail(email);
			if (user == null) {
				session.setAttribute("error", "Utente non trovato.");
				response.sendRedirect("memberArea.jsp");
				return;
			}

			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUsername(username);

			if (password != null && !password.isEmpty()) {
				if (!password.equals(passwordCheck)) {
					session.setAttribute("error", "Le password non coincidono.");
					response.sendRedirect("memberArea.jsp");
					return;
				}
				user.setPassword(PasswordUtils.hashPassword(password));
			}

			boolean updated = dao.doUpdate(user);
			if (updated) {
				// Recupera l'utente aggiornato dal DB e aggiorna la sessione
				UsersBean updatedUser = dao.doRetrieveByEmail(email);
				session.setAttribute("user", updatedUser);
				session.removeAttribute("error");
				response.sendRedirect("memberArea.jsp");
			} else {
				session.setAttribute("error", "Errore durante l'aggiornamento.");
				response.sendRedirect("memberArea.jsp");
			}

		} catch (Exception e) {
			session.setAttribute("error", "Eccezione: " + e.getMessage());
			response.sendRedirect("memberArea.jsp");
		}
	}
}
