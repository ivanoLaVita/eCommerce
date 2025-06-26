package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

import model.AddressBean;
import model.AddressDAO;
import model.PaymentMethodBean;
import model.PaymentMethodDAO;
import model.UsersBean;
import model.UsersDAO;
import utils.PasswordUtils;

@WebServlet("/info")
public class EditInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public EditInfoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String target = request.getParameter("target");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        request.setAttribute("mode", mode);
        request.setAttribute("target", target);

        String path = null;

        try {
            if ("update".equals(mode) && "user".equalsIgnoreCase(target)) {
                UsersDAO usersDAO = new UsersDAO();
                UsersBean user = usersDAO.doRetrieveByEmail(email);
                request.setAttribute("user", user);
                path = "/memberArea.jsp";

            } else if ("add".equals(mode)) {
                // per add, passiamo email e target
                request.setAttribute("email", email);
                if ("indirizzo".equalsIgnoreCase(target) || "metodoPagamento".equalsIgnoreCase(target)) {
                    path = "/aggiungiInfoForm.jsp";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Errore di sistema: " + e.getMessage());
            path = "/memberArea.jsp";
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String mode = request.getParameter("mode");
        String target = request.getParameter("target");
        String email = (String) session.getAttribute("email");

        try {
            if ("update".equalsIgnoreCase(mode) && "utente".equalsIgnoreCase(target)) {
                // Modifica utente
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
                    session.setAttribute("user", dao.doRetrieveByEmail(email));
                    session.setAttribute("message", "Dati aggiornati correttamente.");
                } else {
                    session.setAttribute("error", "Errore durante l'aggiornamento.");
                }

                response.sendRedirect("memberArea.jsp");
                return;
            }

            if ("add".equalsIgnoreCase(mode)) {
                if ("indirizzo".equalsIgnoreCase(target)) {
                    // Aggiunta indirizzo
                    String street = request.getParameter("street");
                    String streetNumber = request.getParameter("streetNumber");
                    String city = request.getParameter("city");
                    String province = request.getParameter("province");
                    String postalCode = request.getParameter("postalCode");

                    AddressBean address = new AddressBean();
                    address.setUserEmail(email);
                    address.setStreet(street);
                    address.setStreetNumber(streetNumber);
                    address.setCity(city);
                    address.setProvince(province);
                    address.setPostalCode(postalCode);

                    AddressDAO addrDAO = new AddressDAO();
                    addrDAO.doSave(address);

                    session.setAttribute("message", "Indirizzo aggiunto con successo!");
                    response.sendRedirect("memberArea.jsp");
                    return;
                }

                if ("metodoPagamento".equalsIgnoreCase(target)) {
                    // Aggiunta metodo di pagamento
                    String tipo = request.getParameter("type");
                    String iban = request.getParameter("iban");
                    String cardNumber = request.getParameter("cardNumber");
                 
                    PaymentMethodBean metodo = new PaymentMethodBean();
                    metodo.setUserEmail(email);
                    metodo.setType(tipo);

                    if (tipo.equals("IBAN")) {
                        if (iban == null || iban.trim().isEmpty()) {
                            session.setAttribute("error", "IBAN non valido.");
                            response.sendRedirect("info?mode=add&target=metodoPagamento");
                            return;
                        }
                        metodo.setIban(iban);

                    } else if (tipo.equals("CARD")) {
                        if (cardNumber == null || cardNumber.trim().isEmpty()) {
                            session.setAttribute("error", "Numero carta non valido.");
                            response.sendRedirect("info?mode=add&target=metodoPagamento");
                            return;
                        }
                        metodo.setCardNumber(cardNumber);
                    }

                    PaymentMethodDAO metodoDAO = new PaymentMethodDAO();
                    metodoDAO.doSave(metodo);

                    session.setAttribute("message", "Metodo di pagamento aggiunto con successo!");
                    response.sendRedirect("memberArea.jsp");
                    return;
                }
            }

            // Se non entra in nessuna condizione valida
            session.setAttribute("error", "Richiesta non valida.");
            response.sendRedirect("memberArea.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Errore interno: " + e.getMessage());
            response.sendRedirect("memberArea.jsp");
        }
    }



}
