<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LaVita Jewels - Home</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <jsp:include page="fragments/Header.jsp" />

    <main>
        <section class="hero">
            <h1>Benvenuti a LaVita Jewels</h1>
            <p>Scopri la nostra squisita collezione di gioielli che celebrano l'eleganza e la bellezza eterna.</p>
            <a href="#" class="button">Esplora la Collezione</a>
        </section>

        <section class="featured-products">
            <h2>I Nostri Gioielli in Evidenza</h2>
            <div class="product-grid">
                <div class="product-item">
                    <img src="assets/img/AnelloSmeraldo.jpg" alt="Anello Smeraldo">
                    <h3>Anello con Smeraldo</h3>
                    <p>Eleganza senza tempo.</p>
                    <span class="price">€ 499.99</span>
                    <a href="#" class="button">Vedi Dettagli</a>
                </div>
                <div class="product-item">
                    <img src="assets/img/Collana.jpg" alt="Collana Diamante">
                    <h3>Collana con Diamante</h3>
                    <p>Un tocco di lusso.</p>
                    <span class="price">€ 799.99</span>
                    <a href="#" class="button">Vedi Dettagli</a>
                </div>
                <div class="product-item">
                    <img src="https://via.placeholder.com/300x200" alt="Orecchini Perla">
                    <h3>Orecchini di Perle</h3>
                    <p>Classici e raffinati.</p>
                    <span class="price">€ 249.99</span>
                    <a href="#" class="button">Vedi Dettagli</a>
                </div>
            </div>
        </section>

        <section class="about-us">
            <h2>La Nostra Storia</h2>
            <p>LaVita Jewels nasce dalla passione per l'artigianato e il desiderio di creare gioielli che non solo adornano, ma raccontano storie. Ogni pezzo è realizzato con la massima cura e attenzione ai dettagli, utilizzando solo i materiali più pregiati.</p>
            <a href="#" class="button">Scopri di Più</a>
        </section>
    </main>

    <jsp:include page="fragments/Footer.jsp" />
</body>
</html>