package controller;

import model.UsersBean;
import model.AddressBean;
import model.PaymentMethodBean;
import model.UsersDAO;
import model.AddressDAO;
import model.PaymentMethodDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ModificaInfoServlet")
public class ModificaInfoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UsersBean user = (UsersBean) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // --- Dati utente ---
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String username = request.getParameter("username");

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            new UsersDAO().doUpdate(user);
            session.setAttribute("user", user);  // aggiorna anche la sessione

            // --- Indirizzo ---
            String city = request.getParameter("city");
            String province = request.getParameter("province");
            String postalCode = request.getParameter("postalCode");
            String street = request.getParameter("street");
            String streetNumber = request.getParameter("streetNumber");

            AddressDAO addressDAO = new AddressDAO();
            List<AddressBean> addresses = addressDAO.doRetrieveByEmail(user.getEmail());
            AddressBean address = addresses.isEmpty() ? null : addresses.get(0);

            if (address == null) {
                address = new AddressBean();
                address.setUserEmail(user.getEmail());
            }

            address.setCity(city);
            address.setProvince(province);
            address.setPostalCode(postalCode);
            address.setStreet(street);
            address.setStreetNumber(streetNumber);

            if (address.getId() == 0) {
                addressDAO.doSave(address);
            } else {
                addressDAO.doUpdate(address);
            }

            // --- Metodo di pagamento ---
            String type = request.getParameter("paymentType");
            String cardNumber = request.getParameter("cardNumber");
            String iban = request.getParameter("iban");

            PaymentMethodDAO paymentDAO = new PaymentMethodDAO();
            List<PaymentMethodBean> payments = paymentDAO.doRetrieveByEmail(user.getEmail());
            PaymentMethodBean payment = payments.isEmpty() ? null : payments.get(0);

            if (payment == null) {
                payment = new PaymentMethodBean();
                payment.setUserEmail(user.getEmail());
            }

            payment.setType(type);
            payment.setCardNumber("CARD".equals(type) ? cardNumber : null);
            payment.setIban("IBAN".equals(type) ? iban : null);

            if (payment.getId() == 0) {
                paymentDAO.doSave(payment);
            } else {
                paymentDAO.doUpdate(payment);
            }

            // --- Inoltra con i dati aggiornati ---
            request.setAttribute("address", address);
            request.setAttribute("payment", payment);
            request.getRequestDispatcher("memberArea.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'aggiornamento delle informazioni.");
        }
    }
}
