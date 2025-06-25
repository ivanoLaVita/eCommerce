<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List, java.util.stream.Collectors, model.ProductBean, model.ProductDAO" %>
<%
    Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
    int cartSize = 0;
    if (cart != null) {
        for (Integer qty : cart.values()) {
            cartSize += qty;
        }
    }
    session.setAttribute("cartSize", cartSize);

    String message = (String) session.getAttribute("message");
%>

<%
    // --- Logica di Recupero Dati ---
    List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");
    if (products == null) {
        products = (List<ProductBean>) session.getAttribute("products");
    }

    ProductDAO productDao = new ProductDAO();

    // Se products è ancora null, carica tutti i prodotti dal database
    if (products == null) {
        try {
            products = productDao.doRetrieveAll(null);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            products = new java.util.ArrayList<>();
        }
    }

    // Recupero categorie
    List<String> categories = new java.util.ArrayList<>();
    try {
        List<ProductBean> allProducts = productDao.doRetrieveAll(null);
        if (allProducts != null) {
            categories = allProducts.stream()
                                    .map(ProductBean::getCategoryName)
                                    .distinct()
                                    .sorted()
                                    .collect(Collectors.toList());
        }
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LaVita Jewels - Homepage</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<jsp:include page="fragments/Header.jsp" />

<% if (message != null) { %>
    <div class="notification success" id="cart-message">
        <%= message %>
    </div>
    <script>
        setTimeout(function () {
            var msg = document.getElementById("cart-message");
            if (msg) msg.style.display = "none";
        }, 2000); // nasconde dopo 2 secondi
    </script>
<% session.removeAttribute("message"); } %>

<main class="homepage-content">
    <section class="hero-section">
        <h1>Esplora la Bellezza Senza Tempo</h1>
        <p>Scopri la nostra esclusiva collezione di gioielli, pensati per ogni momento speciale.</p>
        <a href="catalogo.jsp" class="button-primary">Scopri la Collezione</a>
    </section>

    <section class="featured-products">
        <h2>I Nostri Gioielli in Evidenza</h2>
 

<div id="product-grid-container" class="product-grid">
    <%
        if (products != null && !products.isEmpty()) {
            for (ProductBean product : products) {
                if (product.getId() >= 1 && product.getId() <= 3) {
    %>
        <div class="product-item">
            <a href="productDetail?id=<%= product.getId() %>" class="product-link">
                <img src="<%= request.getContextPath() %>/<%= product.getImage() %>" alt="Immagine di <%= product.getName() %>">
            </a>
            <h3><%= product.getName() %></h3>

            <p class="product-meta">
               <strong>Categoria:</strong> <%= product.getCategoryName() %> | <strong>Genere:</strong> <%= product.getGender() %>
            </p>

            <p class="product-description">
               <%= product.getDescription() %>
            </p>

            <span class="price">€ <%= String.format("%.2f", product.getPrice()) %></span>

            <div class="product-actions">
                <a href="productDetail?id=<%= product.getId() %>" class="button-outline">Dettagli</a>
                <form action="cart" method="post" style="display: inline;">
                    <input type="hidden" name="mode" value="add">
                    <input type="hidden" name="productId" value="<%= product.getId() %>">
                    <input type="hidden" name="quantity" value="1">
                    <button type="submit" class="button-secondary add-to-cart-btn">Aggiungi al carrello</button>
                </form>
            </div>
        </div>
    <%
                }
            }
        } else {
    %>
        <div class="no-results">
            <p>Nessun prodotto trovato. Prova a modificare i filtri di ricerca.</p>
        </div>
    <%
        }
    %>
</div>

    </section>

    <section class="about-us-preview">
        <h2>La Nostra Storia</h2>
        <p>LaVita Jewels nasce dalla passione per l'arte orafa e il desiderio di creare gioielli che narrano storie. Ogni pezzo è realizzato con cura e dedizione, utilizzando solo i materiali più pregiati.</p>
        <a href="#" class="button-outline">Scopri di più su di noi</a>
    </section>
</main>

<jsp:include page="fragments/Footer.jsp" />

</body>
</html>
