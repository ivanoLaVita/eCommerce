package controller;

import model.ProductBean;
import model.ProductDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CatalogServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        ProductDAO dao = new ProductDAO();
        List<ProductBean> products = new ArrayList<>();
        List<ProductBean> randomProducts = new ArrayList<>();

        try {
            products = dao.doRetrieveAll("");

            if (products != null && !products.isEmpty()) {
            	randomProducts = getRandomProducts(products,3);
            }
            //randomProducts = getRandomProducts(products, 3);

            request.getSession().setAttribute("products", products);
            request.getSession().setAttribute("randomProducts", randomProducts);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String targetPage = "/catalogo.jsp";

        if ("home".equalsIgnoreCase(mode)) {
            targetPage = "/HomePage.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(targetPage);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        String category = request.getParameter("category");

        ProductDAO dao = new ProductDAO();
        List<ProductBean> filteredProducts = new ArrayList<>();

        try {
            filteredProducts = dao.doRetrieveFiltered(search, category);
            request.setAttribute("products", filteredProducts);

            // Se la richiesta è AJAX (fetch o XMLHttpRequest), restituiamo solo il frammento HTML
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("fragments/ProductList.jsp");
                dispatcher.forward(request, response);
            } else {
                // Altrimenti restituiamo la pagina intera
                RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il filtraggio dei prodotti.");
        }
    }
    
    
    
    
    private List<ProductBean> getRandomProducts(List<ProductBean> products, int count) {
        List<ProductBean> randomProducts = new ArrayList<>();
        Set<Integer> selectedIndexes = new HashSet<>();
        Random random = new Random();

        if (products.size() <= count) {
            // Se ci sono meno o esattamente il numero di prodotti richiesti, restituisci tutti i prodotti
            return new ArrayList<>(products);
        }

        while (randomProducts.size() < count) {
            int randomIndex = random.nextInt(products.size());
            if (!selectedIndexes.contains(randomIndex)) {
                randomProducts.add(products.get(randomIndex));
                selectedIndexes.add(randomIndex);
            }
        }

        return randomProducts;
    }

}
