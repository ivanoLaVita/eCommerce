package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/OrdineServlet")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersBean user = (UsersBean) session.getAttribute("user");
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (user == null || cart == null || cart.isEmpty()) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            double totalCost = Double.parseDouble(request.getParameter("totalCost"));
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));

            // 1. Inserimento ordine
            OrderDAO orderDAO = new OrderDAO();
            OrderBean ordine = new OrderBean();
            ordine.setUserEmail(user.getEmail());
            ordine.setDate(LocalDate.now().toString());
            ordine.setTotalCost(totalCost);
            orderDAO.doSave(ordine);

            // 2. Recupero ID ordine appena salvato
            List<OrderBean> orders = orderDAO.doRetrieveByEmail(user.getEmail());
            OrderBean lastOrder = orders.get(orders.size() - 1);
            int orderId = lastOrder.getId();

            // 3. Inserimento prodotti nel carrello nella tabella inserimento
            ProductDAO productDAO = new ProductDAO();
            InserimentoDAO inserimentoDAO = new InserimentoDAO();
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();
                ProductBean p = productDAO.doRetrieveById(productId);

                InserimentoBean ins = new InserimentoBean();
                ins.setOrdineId(orderId);
                ins.setProdottoId(productId);
                ins.setQuantita(quantity);
                ins.setNome(p.getName());
                ins.setCosto((int) p.getPrice());
                ins.setImmagine(p.getImage());

                inserimentoDAO.doSave(ins);
            }

            // 4. Svuota il carrello e reindirizza
            session.removeAttribute("cart");
            response.sendRedirect("memberArea.jsp?success=order");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp?msg=Errore durante l'elaborazione dell'ordine");
        }
    }
}
