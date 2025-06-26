<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.ProductBean, model.ProductDAO" %>
<%@ include file="fragments/Header.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Carrello</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <script>
        function updateQuantity(productId) {
            const quantity = document.getElementById('qty-' + productId).value;

            fetch('cart?mode=update&productId=' + productId + '&quantity=' + quantity)
                .then(response => response.text())
                .then(data => {
                    if (data.trim() === "reload") {
                        location.reload();
                    }
                });
        }

        function removeItem(productId) {
            window.location.href = 'cart?mode=remove&productId=' + productId;
        }

        function resetCart() {
            window.location.href = 'cart?mode=reset';
        }
    </script>
</head>
<body>

<%
    String message = (String) session.getAttribute("message");
    if (message != null) {
%>
<div class="notification success" id="cart-message">
    <%= message %>
</div>
<script>
    setTimeout(function () {
        var msg = document.getElementById("cart-message");
        if (msg) msg.style.display = "none";
    }, 2000);
</script>
<%
    session.removeAttribute("message");
    }
%>

<div class="cart-container">
    <h2>Il tuo Carrello</h2>

    <%
        Object rawCart = session.getAttribute("cart");
        double total = 0;

        if (rawCart == null || !(rawCart instanceof Map) || ((Map) rawCart).isEmpty()) {
    %>
        <p>Il carrello è vuoto.</p>
    <%
        } else {
            Map cart = (Map) rawCart;
            ProductDAO productDAO = new ProductDAO();
    %>
        <table class="cart-table">
            <tr>
                <th>Prodotto</th>
                <th>Prezzo</th>
                <th>Quantità</th>
                <th>Totale</th>
                <th>Azioni</th>
            </tr>
            <%
                Set entries = cart.entrySet();
                Iterator it = entries.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    int productId = (Integer) entry.getKey();
                    int quantity = (Integer) entry.getValue();

                    ProductBean product = productDAO.doRetrieveByKey(String.valueOf(productId));
                    if (product != null) {
                        double subtotal = product.getPrice() * quantity;
                        total += subtotal;
            %>
            <tr>
                <td><%= product.getName() %></td>
                <td>€ <%= String.format("%.2f", product.getPrice()) %></td>
                <td>
                    <input type="number" id="qty-<%= productId %>" value="<%= quantity %>" min="1" onchange="updateQuantity(<%= productId %>)">
                </td>
                <td>€ <%= String.format("%.2f", subtotal) %></td>
                <td>
                    <button onclick="removeItem(<%= productId %>)">Rimuovi</button>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <div class="cart-total">
            <strong>Totale: € <%= String.format("%.2f", total) %></strong>
        </div>

        <div class="cart-actions">
            <button onclick="resetCart()">Svuota Carrello</button>
            <a href="checkout.jsp" class="btn-acquista">Procedi al pagamento</a>
        </div>
    <%
        }
    %>
</div>

<%@ include file="fragments/Footer.jsp" %>
</body>
<script src = js/cart.js></script>
</html>
