<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
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
        <div class="product-grid">

            <div class="product-item">
                <img src="assets/img/anello_esempio.jpg" alt="Anello Smeraldo">
                <h3>Anello Smeraldo Reale</h3>
                <p>Un capolavoro di eleganza e raffinatezza.</p>
                <span class="price">€ 499.00</span>
                <a href="#" class="button-secondary">Vedi Dettagli</a>
                <form action="cart" method="get">
                    <input type="hidden" name="mode" value="add" />
                    <input type="hidden" name="productId" value="1" />
                    <input type="hidden" name="quantity" value="1" />
                    <button type="submit" class="button-secondary add-to-cart-btn">Aggiungi al carrello</button>
                </form>
            </div>

            <div class="product-item">
                <img src="assets/img/collana_esempio.jpg" alt="Collana Perle">
                <h3>Collana di Perle Luminose</h3>
                <p>Classica e sofisticata, perfetta per ogni occasione.</p>
                <span class="price">€ 280.00</span>
                <a href="#" class="button-secondary">Vedi Dettagli</a>
                <form action="cart" method="get">
                    <input type="hidden" name="mode" value="add" />
                    <input type="hidden" name="productId" value="2" />
                    <input type="hidden" name="quantity" value="1" />
                    <button type="submit" class="button-secondary add-to-cart-btn">Aggiungi al carrello</button>
                </form>
            </div>

            <div class="product-item">
                <img src="assets/img/orecchini_esempio.jpg" alt="Orecchini Diamante">
                <h3>Orecchini Punto Luce Diamante</h3>
                <p>Brillanti e delicati, ideali per illuminare il tuo viso.</p>
                <span class="price">€ 750.00</span>
                <a href="#" class="button-secondary">Vedi Dettagli</a>
                <form action="cart" method="get">
                    <input type="hidden" name="mode" value="add" />
                    <input type="hidden" name="productId" value="3" />
                    <input type="hidden" name="quantity" value="1" />
                    <button type="submit" class="button-secondary add-to-cart-btn">Aggiungi al carrello</button>
                </form>
            </div>

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
