<%@ page import="java.util.List, java.util.stream.Collectors, model.ProductBean, model.ProductDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ProductBean" %>
<%
    ProductBean product = (ProductBean) request.getAttribute("product");

	ProductDAO productDao = new ProductDAO();

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
    <title>Dettagli Prodotto - LaVita Jewels</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/infoProdotti.css">
</head>
<body>
<jsp:include page="fragments/Header.jsp" />

<main class="product-detail-container">
    <% if (product != null) { %>
    <div class="product-detail-card">
        <div class="product-image">
            <img src="<%= request.getContextPath() %>/<%= product.getImage() %>" alt="Immagine di <%= product.getName() %>">
        </div>
        <div class="product-info">
            <h1><%= product.getName() %></h1>
            <p class="product-meta"><strong>Categoria:</strong> <%= product.getCategoryName() %> | <strong>Genere:</strong> <%= product.getGender() %></p>
            <p class="product-description"><%= product.getDescription() %></p>
            <p class="product-price">€ <%= String.format("%.2f", product.getPrice()) %></p>
            <form action="cart" method="post" class="add-to-cart-form">
                <input type="hidden" name="mode" value="add">
                <input type="hidden" name="productId" value="<%= product.getId() %>">
                <label for="quantity">Quantità:</label>
                <input type="number" id="quantity" name="quantity" value="1" min="1" required>
                <button type="submit" class="button-primary">Aggiungi al carrello</button>
            </form>
        </div>
    </div>
    <% } else { %>
        <p class="no-product">Prodotto non trovato.</p>
    <% } %>
</main>

<jsp:include page="fragments/Footer.jsp" />
</body>
</html>
