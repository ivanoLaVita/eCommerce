<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.stream.Collectors, model.ProductBean, model.ProductDAO" %>

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
    <title>Catalogo Prodotti - LaVita Jewelry</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body>

    <jsp:include page="fragments/Header.jsp" />

    <main class="catalog-page-content">
        <div class="catalog-header">
            <h1>Il Nostro Catalogo</h1>
            <p>Scopri gioielli unici, creati con passione e maestria.</p>
        </div>

        <div class="filter-bar-container">
            <form id="filter-form" action="<%= request.getContextPath() %>/catalog" method="post" class="filter-bar">
                <div class="form-group-search">
                    <input type="search" id="search-input" name="search" placeholder="Cerca per nome..." class="search-field">
                    <div id="searchResults"></div>
                </div>
                <div class="form-group-category">
                    <select id="category-select" name="category" class="category-select">
                        <option value="">Tutte le categorie</option>
                        <%
                            for (String category : categories) {
                        %>
                            <option value="<%= category %>"><%= category %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <button type="submit" class="button-primary">Filtra</button>
            </form>
        </div>

        <div id="product-grid-container" class="product-grid">
            <%
                if (products != null && !products.isEmpty()) {
                    for (ProductBean product : products) {
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
                               <a href="productDetails?id=<%= product.getId() %>" class="button-outline">Dettagli</a>
                                <form action="cart" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="productId" value="<%= product.getId() %>">
                                    <input type="hidden" name="quantity" value="1">                            
                 	   		<input type="hidden" name="mode" value="add" />
                  		    <input type="hidden" name="productId" value="1" />
                    		<input type="hidden" name="quantity" value="1" />
                    		<button type="submit" class="button-secondary add-to-cart-btn">Aggiungi al carrello</button>
                			</form>
                            </div>
                        </div>
            <%
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
    </main>

    <jsp:include page="fragments/Footer.jsp" />

</body>
 <script src="js/search.js"></script>
</html>
