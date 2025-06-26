package controller;

import model.ProductBean;
import model.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search")
public class SearchAjaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Se query vuota, ritorna lista vuota
        if (query == null || query.trim().isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        try {
            ProductDAO dao = new ProductDAO();
            List<ProductBean> results = dao.searchBy(query);

            PrintWriter out = response.getWriter();
            out.print("[");
            for (int i = 0; i < results.size(); i++) {
                ProductBean p = results.get(i);
                out.print("{\"id\":" + p.getId() +
                          ",\"name\":\"" + escapeJson(p.getName()) + "\"" +
                          ",\"price\":" + p.getPrice() + "}");
                if (i < results.size() - 1) {
                    out.print(",");
                }
            }
            out.print("]");
            out.flush();
            out.close();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Metodo per fare escaping di eventuali virgolette nel nome prodotto
    private String escapeJson(String text) {
        return text.replace("\"", "\\\""); // Es: Anello "Oro" → Anello \"Oro\"
    }
}
