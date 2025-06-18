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

            randomProducts = getRandomProducts(products, 3);

            request.getSession().setAttribute("products", products);
            request.getSession().setAttribute("randomProducts", randomProducts);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String targetPage = "/catalog.jsp";

        if ("home".equalsIgnoreCase(mode)) {
            targetPage = "/homePage.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(targetPage);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private List<ProductBean> getRandomProducts(List<ProductBean> products, int count) {
        List<ProductBean> randomProducts = new ArrayList<>();
        Set<Integer> selectedIndexes = new HashSet<>();
        Random random = new Random();

        if (products.size() <= count) {
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
